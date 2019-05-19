package cn.future.modular.system.controller;

import cn.future.core.common.constant.factory.ConstantFactory;
import cn.future.core.common.constant.state.MenuStatus;
import cn.future.core.common.exception.BizExceptionEnum;
import cn.future.core.common.node.ZTreeNode;
import cn.future.core.log.LogObjectHolder;
import cn.future.core.shiro.ShiroKit;
import cn.future.core.shiro.ShiroUser;
import cn.hutool.core.bean.BeanUtil;
import cn.future.modular.system.model.Menu;
import cn.future.modular.system.model.User;
import cn.future.modular.system.service.IUserService;
import cn.future.modular.system.warpper.UnifledMenuWarpper;
import cn.future.modular.unified.service.IUnifiedRelationService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import cn.future.modular.system.model.UnifiedMenu;
import cn.future.modular.system.service.IUnifiedMenuService;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 统一用户菜单表控制器
 *
 * @author fengshuonan
 * @Date 2019-04-18 20:53:37
 */
@Controller
@RequestMapping("/unifiedMenu")
public class UnifiedMenuController extends BaseController {

    private String PREFIX = "/system/unifiedMenu/";

    @Autowired
    private IUnifiedMenuService unifiedMenuService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IUnifiedRelationService relationService;

    /**
     * 跳转到统一用户菜单表首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "unifiedMenu.html";
    }

    /**
     * 跳转到添加统一用户菜单表
     */
    @RequestMapping("/unifiedMenu_add")
    public String unifiedMenuAdd() {
        return PREFIX + "unifiedMenu_add.html";
    }



    /**
     * 跳转到菜单详情列表页面
     */
    @RequestMapping(value = "/unifiedMenu_update/{unifiedMenuId}")
    public String menuEdit(@PathVariable Long unifiedMenuId, Model model) {
        if (ToolUtil.isEmpty(unifiedMenuId)) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }

        ShiroUser user = ShiroKit.getUser();
        User user1 = userService.selectById(user.getId());
        UnifiedMenu menu = this.unifiedMenuService.selectById(unifiedMenuId);

        //获取父级菜单的id
        Menu temp = new Menu();
        temp.setCode(menu.getPcode());
        Wrapper<UnifiedMenu> wrapper = new EntityWrapper<>();
        wrapper.eq("appid", user1.getSubsystemadmin());
        UnifiedMenu pMenu = this.unifiedMenuService.selectOne(wrapper);

        //如果父级是顶级菜单
        if (pMenu == null) {
            menu.setPcode("0");
        } else {
            //设置父级菜单的code为父级菜单的id
            menu.setPcode(String.valueOf(pMenu.getId()));
        }

        Map<String, Object> menuMap = BeanUtil.beanToMap(menu);
        menuMap.put("pcodeName",temp.getName());
        model.addAttribute("menu", menuMap);
        LogObjectHolder.me().set(menu);
        return PREFIX + "unifiedMenu_edit.html";
    }

    /**
     * 获取菜单列表
     */
    @ResponseBody
    @RequestMapping("/list")
    public Object list(@RequestParam(required = false) String menuName, @RequestParam(required = false) String level) {
        ShiroUser shiroUser = ShiroKit.getUser();
        User user = userService.selectById(shiroUser.getId());

        List<Map<String, Object>> menus = this.unifiedMenuService.selectMenus(menuName, level,user.getSubsystemadmin());
        return super.warpObject(new UnifledMenuWarpper(menus));
    }


    /**
     * 新增菜单
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public ResponseData add(@Valid UnifiedMenu menu, BindingResult result) {
        if (result.hasErrors()) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }

        //判断是否存在该编号
        String existedMenuName = ConstantFactory.me().getMenuNameByCode(menu.getCode());
        if (ToolUtil.isNotEmpty(existedMenuName)) {
            throw new ServiceException(BizExceptionEnum.EXISTED_THE_MENU);
        }
        ShiroUser shiroUser = ShiroKit.getUser();
        User user = userService.selectById(shiroUser.getId());
        menu.setAppid(user.getSubsystemadmin());
        //设置父级菜单编号
        menuSetPcode(menu);

        menu.setStatus(MenuStatus.ENABLE.getCode());
        this.unifiedMenuService.insert(menu);
        return SUCCESS_TIP;
    }


    /**
     * 删除菜单
     */
    @RequestMapping(value = "/remove")
    @ResponseBody
    public ResponseData remove(@RequestParam Long unifiedMenuId) {
        if (ToolUtil.isEmpty(unifiedMenuId)) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        //删除菜单关联角色 关联关系



        this.unifiedMenuService.delMenuContainSubMenus(unifiedMenuId);
        return SUCCESS_TIP;
    }
    /**
     * 修该菜单
     */
    @RequestMapping(value = "/edit")
    @ResponseBody
    public ResponseData edit(@Valid UnifiedMenu menu, BindingResult result) {
        if (result.hasErrors()) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        //设置父级菜单编号
        menuSetPcode(menu);

        this.unifiedMenuService.updateById(menu);
        return SUCCESS_TIP;
    }
    /**
     * 统一用户菜单表详情
     */
    @RequestMapping(value = "/detail/{unifiedMenuId}")
    @ResponseBody
    public Object detail(@PathVariable("unifiedMenuId") Integer unifiedMenuId) {
        return unifiedMenuService.selectById(unifiedMenuId);
    }








    /**
     * 查看菜单
     */
    @RequestMapping(value = "/view/{unifiedMenuId}")
    @ResponseBody
    public ResponseData view(@PathVariable Long unifiedMenuId) {
        if (ToolUtil.isEmpty(unifiedMenuId)) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        this.unifiedMenuService.selectById(unifiedMenuId);
        return SUCCESS_TIP;
    }



    /**
     * 获取菜单列表(首页用)
     */
    @RequestMapping(value = "/menuTreeList")
    @ResponseBody
    public List<ZTreeNode> menuTreeList() {
        ShiroUser shiroUser = ShiroKit.getUser();
        User user = userService.selectById(shiroUser.getId());
        return this.unifiedMenuService.menuTreeList(user.getSubsystemadmin());
    }

    /**
     * 获取菜单列表(选择父级菜单用)
     */
    @RequestMapping(value = "/selectMenuTreeList")
    @ResponseBody
    public List<ZTreeNode> selectMenuTreeList() {
        ShiroUser shiroUser = ShiroKit.getUser();
        User user = userService.selectById(shiroUser.getId());

        List<ZTreeNode> roleTreeList = this.unifiedMenuService.menuTreeList(user.getSubsystemadmin());
        roleTreeList.add(ZTreeNode.createParent());
        return roleTreeList;
    }

    /**
     * 获取角色列表
     */
    @RequestMapping(value = "/menuTreeListByRoleId/{roleId}")
    @ResponseBody
    public List<ZTreeNode> menuTreeListByRoleId(@PathVariable Integer roleId) {
        List<Long> menuIds = this.unifiedMenuService.getMenuIdsByRoleId(roleId);
        if (ToolUtil.isEmpty(menuIds)) {
            ShiroUser shiroUser = ShiroKit.getUser();
            User user = userService.selectById(shiroUser.getId());
            return this.unifiedMenuService.menuTreeList(user.getSubsystemadmin());
        } else {
            ShiroUser user = ShiroKit.getUser();
            User user1 = userService.selectById(user.getId());
            return this.unifiedMenuService.menuTreeListByMenuIds(menuIds,user1.getSubsystemadmin());
        }
    }



    /**
     * 根据请求的父级菜单编号设置pcode和层级
     */
    private void menuSetPcode(@Valid UnifiedMenu menu) {
        if (ToolUtil.isEmpty(menu.getPcode()) || menu.getPcode().equals("0")) {
            menu.setPcode("0");
            menu.setPcodes("[0],");
            menu.setLevels(1);
        } else {
            long code = Long.parseLong(menu.getPcode());
            UnifiedMenu pMenu = unifiedMenuService.selectById(code);
            Integer pLevels = pMenu.getLevels();
            menu.setPcode(pMenu.getCode());

            //如果编号和父编号一致会导致无限递归
            if (menu.getCode().equals(menu.getPcode())) {
                throw new ServiceException(BizExceptionEnum.MENU_PCODE_COINCIDENCE);
            }

            menu.setLevels(pLevels + 1);
            menu.setPcodes(pMenu.getPcodes() + "[" + pMenu.getCode() + "],");
        }
    }




}
