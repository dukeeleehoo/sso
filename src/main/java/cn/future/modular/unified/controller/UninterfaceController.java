package cn.future.modular.unified.controller;

import cn.future.modular.unified.warpper.UninterpaseWarpper;
import cn.future.core.common.annotion.BussinessLog;
import cn.future.core.common.constant.dictmap.UserDict;
import cn.future.core.common.exception.BizExceptionEnum;
import cn.future.modular.unified.service.IApplicationService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import cn.future.core.log.LogObjectHolder;
import org.springframework.web.bind.annotation.RequestParam;
import cn.future.modular.unified.model.Uninterface;
import cn.future.modular.unified.service.IUninterfaceService;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 接口管理控制器
 *
 * @author fengshuonan
 * @Date 2018-12-10 21:39:02
 */
@Controller
@RequestMapping("/uninterface")
public class UninterfaceController extends BaseController {

    private String PREFIX = "/unified/uninterface/";

    @Autowired
    private IUninterfaceService uninterfaceService;
    @Autowired
    private IApplicationService applicationService;

    /**
     * 跳转到接口管理首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "uninterface.html";
    }

    /**
     * 跳转到添加接口管理
     */
    @RequestMapping("/uninterface_add")
    public String uninterfaceAdd(Model model) {
        return PREFIX + "uninterface_add.html";
    }

    /**
     * 跳转到修改接口管理
     */
    @RequestMapping("/uninterface_update/{uninterfaceId}")
    public String uninterfaceUpdate(@PathVariable Integer uninterfaceId, Model model) {
        Uninterface uninterface = uninterfaceService.selectById(uninterfaceId);
        model.addAttribute("item",uninterface);
        LogObjectHolder.me().set(uninterface);
        return PREFIX + "uninterface_edit.html";
    }

    /**
     * 获取接口管理列表
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Object list(String condition) {
        List<Map<String, Object>> uninterfaces= uninterfaceService.selectUninterfaces();
        return new UninterpaseWarpper(uninterfaces).wrap();
    }

    /**
     * 新增接口管理
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public Object add(Uninterface uninterface) {
        uninterface.setCreatetime(new Date());
        uninterfaceService.insert(uninterface);
        return SUCCESS_TIP;
    }

    /**
     * 删除接口管理
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public Object delete(@RequestParam Integer uninterfaceId) {
        Uninterface uninterface = uninterfaceService.selectById(uninterfaceId);
        //修改删除标记
        uninterface.setStatus(3);
        uninterfaceService.updateById(uninterface);
        return SUCCESS_TIP;
    }

    /**
     * 修改接口管理
     */
    @RequestMapping(value = "/update")
    @ResponseBody
    public Object update(Uninterface uninterface) {
        uninterfaceService.updateById(uninterface);
        return SUCCESS_TIP;
    }

    /**
     * 接口管理详情
     */
    @RequestMapping(value = "/detail/{uninterfaceId}")
    @ResponseBody
    public Object detail(@PathVariable("uninterfaceId") Integer uninterfaceId) {
        return uninterfaceService.selectById(uninterfaceId);
    }

    /**
     * 启用接口
     */
    @RequestMapping("/freeze")
    @BussinessLog(value = "启用接口", key = "uninterfaceId", dict = UserDict.class)
    @ResponseBody
    public ResponseData freeze(@RequestParam Integer uninterfaceId) {
        if (ToolUtil.isEmpty(uninterfaceId)) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        Uninterface uninterface = uninterfaceService.selectById(uninterfaceId);
        uninterface.setStatus(1);
        uninterfaceService.updateById(uninterface);
        return SUCCESS_TIP;
    }

    /**
     * 停用接口
     */
    @RequestMapping("/unfreeze")
    @BussinessLog(value = "停用接口", key = "uninterfaceId", dict = UserDict.class)
    @ResponseBody
    public ResponseData unfreeze(@RequestParam Integer uninterfaceId) {
        if (ToolUtil.isEmpty(uninterfaceId)) {
            throw new ServiceException(BizExceptionEnum.REQUEST_NULL);
        }
        Uninterface uninterface = uninterfaceService.selectById(uninterfaceId);
        uninterface.setStatus(2);
        uninterfaceService.updateById(uninterface);
        return SUCCESS_TIP;
    }
}
