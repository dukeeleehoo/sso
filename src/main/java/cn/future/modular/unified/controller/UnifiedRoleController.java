package cn.future.modular.unified.controller;

import cn.future.core.common.exception.BizExceptionEnum;
import cn.future.core.common.node.ZTreeNode;
import cn.future.core.log.LogObjectHolder;
import cn.future.core.shiro.ShiroKit;
import cn.future.core.shiro.ShiroUser;
import cn.future.modular.unified.model.UnifiedUserRole;
import cn.future.modular.system.model.User;
import cn.future.modular.system.service.IUserService;
import cn.future.modular.unified.service.IUnifiedUserRoleService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import cn.future.modular.system.model.UnifiedRole;
import cn.future.modular.unified.service.IUnifiedRoleService;

import java.util.List;

/**
 * 应用角色表控制器
 *
 * @author fengshuonan
 * @Date 2019-04-17 22:48:23
 */
@Controller
@RequestMapping("/unifiedRole")
public class UnifiedRoleController extends BaseController {

    private String PREFIX = "/unified/unifiedRole/";

    @Autowired
    private IUnifiedRoleService unifiedRoleService;

    @Autowired
    private IUserService userService;

    @Autowired
    IUnifiedUserRoleService unifiedUserRoleService;


    /**
     * 跳转到应用角色表首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "unifiedRole.html";
    }

    /**
     * 跳转到添加应用角色表
     */
    @RequestMapping("/unifiedRole_add")
    public String unifiedRoleAdd() {
        return PREFIX + "unifiedRole_add.html";
    }

    /**
     * 跳转到修改应用角色表
     */
    @RequestMapping("/unifiedRole_update/{unifiedRoleId}")
    public String unifiedRoleUpdate(@PathVariable Integer unifiedRoleId, Model model) {
        UnifiedRole unifiedRole = unifiedRoleService.selectById(unifiedRoleId);
        model.addAttribute("item",unifiedRole);
        LogObjectHolder.me().set(unifiedRole);
        return PREFIX + "unifiedRole_edit.html";
    }

    /**
     * 获取应用角色表列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        Wrapper<UnifiedRole> wrapper = new EntityWrapper<>();
        wrapper.like("name", condition);
        ShiroUser shiroUser = ShiroKit.getUser();
        User user = userService.selectById(shiroUser.getId());
        wrapper.eq("appid", user.getSubsystemadmin());
        return unifiedRoleService.selectList(wrapper);
    }


    /**
     * 跳转到角色分配
     */
    @RequestMapping(value = "/role_assign/{roleId}")
    public String roleAssign(@PathVariable("roleId") Integer roleId, Model model) {
        if (ToolUtil.isEmpty(roleId)) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        UnifiedRole unifiedRole = unifiedRoleService.selectById(roleId);
        model.addAttribute("roleId", roleId);
        model.addAttribute("roleName", unifiedRole.getName());
        return PREFIX + "/role_assign.html";
    }

    /**
     * 新增应用角色表
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(UnifiedRole unifiedRole) {
        ShiroUser shiroUser = ShiroKit.getUser();
        User user = userService.selectById(shiroUser.getId());
        unifiedRole.setAppid(user.getSubsystemadmin());
        unifiedRoleService.insert(unifiedRole);
        return SUCCESS_TIP;
    }

    /**
     * 删除应用角色表
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer unifiedRoleId) {
        unifiedRoleService.deleteById(unifiedRoleId);
        return SUCCESS_TIP;
    }

    /**
     * 修改应用角色表
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(UnifiedRole unifiedRole) {
        unifiedRoleService.updateById(unifiedRole);
        return SUCCESS_TIP;
    }

    /**
     * 应用角色表详情
     */
    @RequestMapping(value = "/detail/{unifiedRoleId}")
    @ResponseBody
    public Object detail(@PathVariable("unifiedRoleId") Integer unifiedRoleId) {
        return unifiedRoleService.selectById(unifiedRoleId);
    }

    /**
     * 获取统一角色列表
     */
    @RequestMapping(value = "/unifiedColeTreeList/{userId}")
    @ResponseBody
    public List<ZTreeNode> unifiedColeTreeList(@PathVariable("userId")String userId) {
        User user = userService.selectById(ShiroKit.getUser().getId());
             List<ZTreeNode> list = this.unifiedRoleService.roleTreeList(user.getSubsystemadmin());
            Wrapper<UnifiedUserRole> wrapper = new EntityWrapper<>();
            wrapper.eq("appid", user.getSubsystemadmin());
            wrapper.eq("userid", userId);
            List<UnifiedUserRole> list1 = unifiedUserRoleService.selectList(wrapper);
            for (ZTreeNode z:list){
             for(UnifiedUserRole u:list1){
                    if(u.getRoleid().toString().equals(z.getId().toString())){
                        z.setChecked(true);
                    }
                }
        }

        return list;
    }

    /**
     * 配置权限
     */
    @RequestMapping("/setAuthority")
    @ResponseBody
    public ResponseData setAuthority(@RequestParam("roleId") Integer roleId, @RequestParam("ids") String ids) {
        if (ToolUtil.isOneEmpty(roleId)) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        this.unifiedRoleService.setAuthority(roleId, ids);
        return SUCCESS_TIP;
    }



    /**
     * 获取角色列表
     */
    @RequestMapping(value = "/roleTreeListByUserId/{userId}")
    @ResponseBody
    public List<ZTreeNode> roleTreeListByUserId(@PathVariable Integer userId) {
        User theUser = this.userService.selectById(userId);
        String roleid = theUser.getRoleid();
        if (ToolUtil.isEmpty(roleid)) {
            ShiroUser shiroUser = ShiroKit.getUser();
            User user = userService.selectById(shiroUser.getId());
            return this.unifiedRoleService.roleTreeList(user.getSubsystemadmin());
        } else {
            String[] strArray = roleid.split(",");
            return this.unifiedRoleService.roleTreeListByRoleId(strArray);
        }
    }


}
