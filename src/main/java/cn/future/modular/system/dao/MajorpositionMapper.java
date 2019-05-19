package cn.future.modular.system.dao;

import cn.future.modular.system.model.Majorposition;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 岗位 Mapper 接口
 * </p>
 *
 * @author zp
 * @since 2019-04-21
 */
public interface MajorpositionMapper extends BaseMapper<Majorposition> {

    /**
     * 删除方法
     * @param majorpositionId
     * @return
     */
    boolean deleteById(@Param("majorpositionId") Integer majorpositionId);

    /**
     * 查询岗位列表
     */
    List<Map<String, Object>> list(@Param("condition") String conditiion);

    /**
     * 查询岗位列表
     */
    List<Majorposition> findList();

    /**
     * 根据code找值
     */
    Majorposition getByCode(@Param("code") String code);
}
