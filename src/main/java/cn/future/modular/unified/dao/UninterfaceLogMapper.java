package cn.future.modular.unified.dao;

import cn.future.modular.unified.model.UninterfaceLog;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 接口日志 Mapper 接口
 * </p>
 *
 * @author zp
 * @since 2018-12-14
 */
public interface UninterfaceLogMapper extends BaseMapper<UninterfaceLog> {

    /**
     * 获取登录日志
     */
    List<Map<String, Object>> getLoginLogs(@Param("page") Page<UninterfaceLog> page, @Param("beginTime") String beginTime,
                                           @Param("endTime") String endTime, @Param("uninterfaceid") String uninterfaceid);

}
