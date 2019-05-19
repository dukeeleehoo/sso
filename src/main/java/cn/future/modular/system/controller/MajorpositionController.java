package cn.future.modular.system.controller;

import cn.future.core.common.exception.BizExceptionEnum;
import cn.future.core.log.LogObjectHolder;
import cn.future.modular.system.service.IPushService;
import cn.future.modular.system.warpper.MajorpositionWarpper;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import cn.future.modular.system.model.Majorposition;
import cn.future.modular.system.service.IMajorpositionService;

import java.util.List;
import java.util.Map;

/**
 * 岗位控制器
 *
 * @author fengshuonan
 * @Date 2019-04-21 18:55:15
 */
@Controller
@RequestMapping("/majorposition")
public class MajorpositionController extends BaseController {

    private String PREFIX = "/system/majorposition/";

    @Autowired
    private IMajorpositionService majorpositionService;

    @Autowired
    private IPushService pushService;

    /**
     * 跳转到岗位首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "majorposition.html";
    }

    /**
     * 跳转到添加岗位
     */
    @RequestMapping("/majorposition_add")
    public String majorpositionAdd() {
        return PREFIX + "majorposition_add.html";
    }

    /**
     * 跳转到修改岗位
     */
    @RequestMapping("/majorposition_update/{majorpositionId}")
    public String majorpositionUpdate(@PathVariable Integer majorpositionId, Model model) {
        Majorposition majorposition = majorpositionService.selectById(majorpositionId);
        model.addAttribute("item",majorposition);
        LogObjectHolder.me().set(majorposition);
        return PREFIX + "majorposition_edit.html";
    }

    /**
     * 获取岗位列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        List<Map<String, Object>> list = this.majorpositionService.list(condition);
        return super.warpObject(new MajorpositionWarpper(list));
    }

    /**
     * 新增岗位
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Majorposition majorposition) {
        // 判断code是否重复
        Majorposition m = majorpositionService.getByCode(majorposition.getCode());
        if(m != null){
            throw new ServiceException(BizExceptionEnum.CODE_ERROR);
        }
        majorposition.setStatus(1);
        majorpositionService.insert(majorposition);
        pushService.pushMajorposition(majorposition,"add");
        return SUCCESS_TIP;
    }

    /**
     * 删除岗位
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer majorpositionId) {
        majorpositionService.deleteById(majorpositionId);
        pushService.pushMajorposition(majorpositionService.selectById(majorpositionId),"delete");
        return SUCCESS_TIP;
    }

    /**
     * 修改岗位
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Majorposition majorposition) {
        // 判断code是否重复
        Majorposition oldMajorposition = majorpositionService.selectById(majorposition.getId());
        if (ToolUtil.isNotEmpty(majorposition.getCode())) {
            Majorposition m = majorpositionService.getByCode(majorposition.getCode());
            if (m != null) {
                if(!oldMajorposition.getCode().equals(majorposition.getCode())){
                    throw new ServiceException(BizExceptionEnum.CODE_ERROR);
                }
            }
        }
        majorpositionService.updateById(majorposition);
        pushService.pushMajorposition(majorposition,"update");
        return SUCCESS_TIP;
    }

    /**
     * 岗位详情
     */
    @RequestMapping(value = "/detail/{majorpositionId}")
    @ResponseBody
    public Object detail(@PathVariable("majorpositionId") Integer majorpositionId) {
        return majorpositionService.selectById(majorpositionId);
    }
}
