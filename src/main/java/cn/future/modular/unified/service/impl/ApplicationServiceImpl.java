package cn.future.modular.unified.service.impl;

import cn.future.modular.unified.service.IApplicationService;
import cn.future.modular.unified.model.Application;
import cn.future.modular.unified.dao.ApplicationMapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 接入应用管理 服务实现类
 * </p>
 *
 * @author zp
 * @since 2018-12-06
 */
@Service
public class ApplicationServiceImpl extends ServiceImpl<ApplicationMapper, Application> implements IApplicationService {

    @Override
    public List<Map<String, Object>> selectApplications() {
        return this.baseMapper.selectApplications();
    }
    @Override
    public Application auth(String appid,String token){
        return this.baseMapper.auth(appid,token);
    }
    @Override
    public Application getByAppid(String appid){
        return this.baseMapper.getByAppid(appid);
    }

}
