package cn.future.modular.unified.controller;

import cn.future.core.common.annotion.BussinessLog;
import cn.future.core.common.annotion.Permission;
import cn.future.core.common.constant.Const;
import cn.future.core.common.constant.factory.PageFactory;
import cn.future.core.common.page.PageInfoBT;
import cn.future.modular.unified.warpper.UninterpaseLogWarpper;
import cn.stylefeng.roses.core.base.controller.BaseController;
import com.baomidou.mybatisplus.mapper.SqlRunner;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import cn.future.modular.unified.model.UninterfaceLog;
import cn.future.modular.unified.service.IUninterfaceLogService;

import java.util.List;
import java.util.Map;

/**
 * 接口日志控制器
 *
 * @author fengshuonan
 * @Date 2018-12-14 13:29:09
 */
@Controller
@RequestMapping("/uninterfaceLog")
public class UninterfaceLogController extends BaseController {

    private String PREFIX = "/unified/uninterfaceLog/";

    @Autowired
    private IUninterfaceLogService uninterfaceLogService;

    /**
     * 跳转到接口日志首页
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "uninterfaceLog.html";
    }

    /**
     * 获取接口日志列表
     */
    @RequestMapping(value = "/list")
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Object list(@RequestParam(required = false) String beginTime, @RequestParam(required = false) String endTime, @RequestParam(required = false) String uninterfaceid) {
        Page<UninterfaceLog> page = new PageFactory<UninterfaceLog>().defaultPage();
        List<Map<String, Object>> result = uninterfaceLogService.getLoginLogs(page, beginTime, endTime, uninterfaceid);
        page.setRecords(new UninterpaseLogWarpper(result).wrap());
        return new PageInfoBT<>(page);
    }

    /**
     * 清空接口日志
     */
    @BussinessLog("清空接口日志")
    @RequestMapping("/delUninterfaceLog")
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Object delLog() {
        SqlRunner.db().delete("delete from unified_uninterface_log");
        return SUCCESS_TIP;
    }

    /**
     * 接口日志详情
     */
    @RequestMapping(value = "/detail/{uninterfaceLogId}")
    @ResponseBody
    public Object detail(@PathVariable("uninterfaceLogId") Integer uninterfaceLogId) {
        return uninterfaceLogService.selectById(uninterfaceLogId);
    }
}
