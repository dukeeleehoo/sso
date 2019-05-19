package cn.future.modular.unified.service;

import cn.future.modular.unified.model.Application;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 接入应用管理 服务类
 * </p>
 *
 * @author zp
 * @since 2018-12-06
 */
public interface IApplicationService extends IService<Application> {

    /**
     * 根据条件查询接入应用
     */
    List<Map<String, Object>> selectApplications();

    /**
     * 验证参数
     * @param appid
     * @param token
     * @return
     */
    Application auth(String appid,String token);

    /**
     * 根据appid得到应用
     * @param appid
     * @return
     */
    Application getByAppid(String appid);

}
