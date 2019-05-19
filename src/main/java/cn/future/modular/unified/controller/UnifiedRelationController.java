package cn.future.modular.unified.controller;

import cn.future.core.log.LogObjectHolder;
import cn.future.modular.unified.model.UnifiedRelation;
import cn.stylefeng.roses.core.base.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import cn.future.modular.unified.service.IUnifiedRelationService;

/**
 * 统一管理的菜单和角色关联控制器
 *
 * @author fengshuonan
 * @Date 2019-04-18 20:48:38
 */
@Controller
@RequestMapping("/unifiedRelation")
public class UnifiedRelationController extends BaseController {

    private String PREFIX = "/unified/unifiedRelation/";

    @Autowired
    private IUnifiedRelationService unifiedRelationService;

    /**
     * 跳转到统一管理的菜单和角色关联首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "unifiedRelation.html";
    }

    /**
     * 跳转到添加统一管理的菜单和角色关联
     */
    @RequestMapping("/unifiedRelation_add")
    public String unifiedRelationAdd() {
        return PREFIX + "unifiedRelation_add.html";
    }

    /**
     * 跳转到修改统一管理的菜单和角色关联
     */
    @RequestMapping("/unifiedRelation_update/{unifiedRelationId}")
    public String unifiedRelationUpdate(@PathVariable Integer unifiedRelationId, Model model) {
        UnifiedRelation unifiedRelation = unifiedRelationService.selectById(unifiedRelationId);
        model.addAttribute("item",unifiedRelation);
        LogObjectHolder.me().set(unifiedRelation);
        return PREFIX + "unifiedRelation_edit.html";
    }

    /**
     * 获取统一管理的菜单和角色关联列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        return unifiedRelationService.selectList(null);
    }

    /**
     * 新增统一管理的菜单和角色关联
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(UnifiedRelation unifiedRelation) {
        unifiedRelationService.insert(unifiedRelation);
        return SUCCESS_TIP;
    }

    /**
     * 删除统一管理的菜单和角色关联
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer unifiedRelationId) {
        unifiedRelationService.deleteById(unifiedRelationId);
        return SUCCESS_TIP;
    }

    /**
     * 修改统一管理的菜单和角色关联
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(UnifiedRelation unifiedRelation) {
        unifiedRelationService.updateById(unifiedRelation);
        return SUCCESS_TIP;
    }

    /**
     * 统一管理的菜单和角色关联详情
     */
    @RequestMapping(value = "/detail/{unifiedRelationId}")
    @ResponseBody
    public Object detail(@PathVariable("unifiedRelationId") Integer unifiedRelationId) {
        return unifiedRelationService.selectById(unifiedRelationId);
    }
}
