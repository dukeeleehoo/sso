package cn.future.modular.unified.controller;

import cn.future.core.log.LogObjectHolder;
import cn.future.modular.unified.model.UnifiedUserRole;
import cn.stylefeng.roses.core.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import cn.future.modular.unified.service.IUnifiedUserRoleService;

/**
 * 统一管理的用户和角色关联控制器
 *
 * @author fengshuonan
 * @Date 2019-04-18 20:47:13
 */
@Controller
@RequestMapping("/unifiedUserRole")
public class UnifiedUserRoleController extends BaseController {

    private String PREFIX = "/unified/unifiedUserRole/";

    @Autowired
    private IUnifiedUserRoleService unifiedUserRoleService;

    /**
     * 跳转到统一管理的用户和角色关联首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "unifiedUserRole.html";
    }

    /**
     * 跳转到添加统一管理的用户和角色关联
     */
    @RequestMapping("/unifiedUserRole_add")
    public String unifiedUserRoleAdd() {
        return PREFIX + "unifiedUserRole_add.html";
    }

    /**
     * 跳转到修改统一管理的用户和角色关联
     */
    @RequestMapping("/unifiedUserRole_update/{unifiedUserRoleId}")
    public String unifiedUserRoleUpdate(@PathVariable Integer unifiedUserRoleId, Model model) {
        UnifiedUserRole unifiedUserRole = unifiedUserRoleService.selectById(unifiedUserRoleId);
        model.addAttribute("item",unifiedUserRole);
        LogObjectHolder.me().set(unifiedUserRole);
        return PREFIX + "unifiedUserRole_edit.html";
    }

    /**
     * 获取统一管理的用户和角色关联列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return unifiedUserRoleService.selectList(null);
    }

    /**
     * 新增统一管理的用户和角色关联
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(UnifiedUserRole unifiedUserRole) {
        unifiedUserRoleService.insert(unifiedUserRole);
        return SUCCESS_TIP;
    }

    /**
     * 删除统一管理的用户和角色关联
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer unifiedUserRoleId) {
        unifiedUserRoleService.deleteById(unifiedUserRoleId);
        return SUCCESS_TIP;
    }

    /**
     * 修改统一管理的用户和角色关联
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(UnifiedUserRole unifiedUserRole) {
        unifiedUserRoleService.updateById(unifiedUserRole);
        return SUCCESS_TIP;
    }

    /**
     * 统一管理的用户和角色关联详情
     */
    @RequestMapping(value = "/detail/{unifiedUserRoleId}")
    @ResponseBody
    public Object detail(@PathVariable("unifiedUserRoleId") Integer unifiedUserRoleId) {
        return unifiedUserRoleService.selectById(unifiedUserRoleId);
    }
}
