package cn.future.modular.unified.dao;

import cn.future.core.common.node.ZTreeNode;
import cn.future.modular.system.model.UnifiedRole;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lee
 * @since 2019-04-17
 */
public interface UnifiedRoleMapper extends BaseMapper<UnifiedRole> {

    /**
     * 获取角色列表树
     *
     * @return
     * @date 2017年2月18日 上午10:32:04
     */
    List<ZTreeNode> roleTreeList(@Param("appid") String appid);

    /**
     * 删除某个角色的所有权限
     *
     * @param roleId 角色id
     * @return
     * @date 2017年2月13日 下午7:57:51
     */
    int deleteRolesById(@Param("roleId") Integer roleId);

    /**
     * 获取角色列表树
     *
     * @return
     * @date 2017年2月18日 上午10:32:04
     */
    List<ZTreeNode> roleTreeListByRoleId(String[] roleId);


}
