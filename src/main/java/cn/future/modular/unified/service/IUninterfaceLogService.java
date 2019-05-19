package cn.future.modular.unified.service;

import cn.future.modular.unified.model.UninterfaceLog;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 接口日志 服务类
 * </p>
 *
 * @author zp
 * @since 2018-12-14
 */
public interface IUninterfaceLogService extends IService<UninterfaceLog> {

    /**
     * 添加一条接口日志
     * @param applicationid
     * @param uninterfaceid
     * @param time
     * @param message
     */
    void addUninterfaceLog(Integer applicationid,Integer uninterfaceid,String time,String message);

    /**
     * App添加一条接口日志
     * @param time
     * @param message
     */
    void addAppUninterfaceLog(Integer uninterfaceid,String time,String message);

    /**
     * 获取接口日志列表
     */
    List<Map<String, Object>> getLoginLogs(Page<UninterfaceLog> page, String beginTime, String endTime, String uninterfaceid);
}
