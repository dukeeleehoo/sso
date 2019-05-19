package cn.future.modular.unified.service.impl;

import cn.hutool.core.convert.Convert;
import cn.future.core.common.node.ZTreeNode;
import cn.future.modular.system.model.UnifiedRole;
import cn.future.modular.unified.dao.UnifiedRelationMapper;
import cn.future.modular.unified.dao.UnifiedRoleMapper;
import cn.future.modular.unified.model.UnifiedRelation;
import cn.future.modular.unified.service.IUnifiedRoleService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lee
 * @since 2019-04-17
 */
@Service
public class UnifiedRoleServiceImpl extends ServiceImpl<UnifiedRoleMapper, UnifiedRole> implements IUnifiedRoleService {


    @Resource
    private UnifiedRoleMapper unroleMapper;

    @Resource
    private UnifiedRelationMapper unrelationMapper;

    @Override
    public List<ZTreeNode> roleTreeList(String appid) {
        return this.baseMapper.roleTreeList(appid);
    }

    @Override
    public void setAuthority(Integer roleId, String ids) {

        // 删除该角色所有的权限
        this.unroleMapper.deleteRolesById(roleId);

        // 添加新的权限
        for (Long id : Convert.toLongArray(ids.split(","))) {
            UnifiedRelation relation = new UnifiedRelation();
            relation.setRoleid(roleId);
            relation.setMenuid(id);
            this.unrelationMapper.insert(relation);
        }
    }


    @Override
    public List<ZTreeNode> roleTreeListByRoleId(String[] roleId) {
        return this.baseMapper.roleTreeListByRoleId(roleId);
    }
}
