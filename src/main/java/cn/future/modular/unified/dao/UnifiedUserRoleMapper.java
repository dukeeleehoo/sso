package cn.future.modular.unified.dao;

import cn.future.modular.unified.model.UnifiedUserRole;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 统一用户和角色关联表 Mapper 接口
 * </p>
 *
 * @author zp
 * @since 2019-04-18
 */
public interface UnifiedUserRoleMapper extends BaseMapper<UnifiedUserRole> {

    /**
     * 设置用户的角色
     */
    int setRoles(@Param("list") List<UnifiedUserRole> list);

    /**
     * 删除原有角色
     */
    void delUserAppRole(@Param("userId") Integer userId, @Param("appid") String appid);


    /**
     * 根据appid和userid得到roleid集合
     * @param userId
     * @param appid
     * @return
     */
    List<UnifiedUserRole> getRole(@Param("userId") Integer userId, @Param("appid")String appid);
}
