package cn.future.modular.system.dao;

import cn.future.modular.system.model.Rank;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 职级 Mapper 接口
 * </p>
 *
 * @author zp
 * @since 2019-04-21
 */
public interface RankMapper extends BaseMapper<Rank> {

    /**
     * 删除方法
     * @param rabkId
     * @return
     */
    boolean deleteById(@Param("rabkId") Integer rabkId);

    /**
     * 查询职级列表
     */
    List<Map<String, Object>> list(@Param("condition") String conditiion);

    /**
     * 查询职级列表
     */
    List<Rank> findList();

    /**
     * 根据code找值
     */
    Rank getByCode(@Param("code") String code);
}
