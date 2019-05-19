package cn.future.modular.unified.service.impl;

import cn.future.modular.unified.service.IUninterfaceLogService;
import cn.future.modular.unified.model.UninterfaceLog;
import cn.future.modular.unified.dao.UninterfaceLogMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static cn.stylefeng.roses.core.util.HttpContext.getIp;

/**
 * <p>
 * 接口日志 服务实现类
 * </p>
 *
 * @author zp
 * @since 2018-12-14
 */
@Service
public class UninterfaceLogServiceImpl extends ServiceImpl<UninterfaceLogMapper, UninterfaceLog> implements IUninterfaceLogService {

    public void addUninterfaceLog(Integer applicationid,Integer uninterfaceid,String time,String message){
        UninterfaceLog uninterfaceLog = new UninterfaceLog();
        uninterfaceLog.setApplicationid(applicationid);
        uninterfaceLog.setUninterfaceid(uninterfaceid);
        uninterfaceLog.setTime(time);
        uninterfaceLog.setMessage(message);
        uninterfaceLog.setCreatetime(new Date());
        uninterfaceLog.setIp(getIp());
        this.baseMapper.insert(uninterfaceLog);
    }

    public void addAppUninterfaceLog(Integer uninterfaceid,String time,String message){
        UninterfaceLog uninterfaceLog = new UninterfaceLog();
        uninterfaceLog.setUninterfaceid(uninterfaceid);
        uninterfaceLog.setTime(time);
        uninterfaceLog.setMessage(message);
        uninterfaceLog.setCreatetime(new Date());
        uninterfaceLog.setIp(getIp());
        this.baseMapper.insert(uninterfaceLog);
    }

    @Override
    public List<Map<String, Object>> getLoginLogs(Page<UninterfaceLog> page, String beginTime, String endTime, String uninterfaceid) {
        return this.baseMapper.getLoginLogs(page, beginTime, endTime, uninterfaceid);
    }
}
