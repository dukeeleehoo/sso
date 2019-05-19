package cn.future.modular.unified.dao;

import cn.future.modular.unified.model.Uninterface;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 接口管理 Mapper 接口
 * </p>
 *
 * @author zp
 * @since 2018-12-10
 */
public interface UninterfaceMapper extends BaseMapper<Uninterface> {

    /**
     * 根据条件查询接入应用
     */
    List<Map<String, Object>> selectUninterfaces();

    /**
     * 根据接口地址查询接口信息
     * @return
     */
    Uninterface selectByUrl(@Param("url") String url);

    /**
     * 根据接口名称查询接口信息
     * @return
     */
    List<Uninterface> selectByName(@Param("name") String name);
}
