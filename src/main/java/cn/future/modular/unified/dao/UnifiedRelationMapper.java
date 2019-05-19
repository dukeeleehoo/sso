package cn.future.modular.unified.dao;

import cn.future.modular.unified.model.UnifiedRelation;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 统一菜单和角色关联表 Mapper 接口
 * </p>
 *
 * @author zp
 * @since 2019-04-18
 */
public interface UnifiedRelationMapper extends BaseMapper<UnifiedRelation> {


    void delByMenuId(@Param("menuid")Integer id);
}
