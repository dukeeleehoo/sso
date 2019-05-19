package cn.future.modular.unified.dao;

import cn.future.modular.unified.model.Application;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 接入应用管理 Mapper 接口
 * </p>
 *
 * @author zp
 * @since 2018-12-06
 */
public interface ApplicationMapper extends BaseMapper<Application> {
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
    Application auth(@Param("appid") String appid, @Param("token") String token);

    /**
     * 根据appid得到应用
     * @param appid
     * @return
     */
    Application getByAppid(@Param("appid") String appid);
}
