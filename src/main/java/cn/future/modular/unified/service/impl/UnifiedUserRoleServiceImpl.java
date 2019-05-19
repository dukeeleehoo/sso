package cn.future.modular.unified.service.impl;

import cn.future.modular.unified.dao.UnifiedUserRoleMapper;
import cn.future.modular.unified.model.UnifiedUserRole;
import cn.future.modular.unified.service.IUnifiedUserRoleService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 统一用户和角色关联表 服务实现类
 * </p>
 *
 * @author zp
 * @since 2019-04-18
 */
@Service
public class UnifiedUserRoleServiceImpl extends ServiceImpl<UnifiedUserRoleMapper, UnifiedUserRole> implements IUnifiedUserRoleService {

    @Override
    public int setRoles(Integer userId, String roleIds,String appid) {
            this.baseMapper.delUserAppRole(userId, appid);

        if(StringUtils.isNotBlank(roleIds)) {
            List<UnifiedUserRole> list = new ArrayList<>();
            String[] roleids = roleIds.split(",");
            for (String roleid : roleids) {
                UnifiedUserRole unifiedUserRole = new UnifiedUserRole();
                unifiedUserRole.setAppid(appid);
                unifiedUserRole.setUserid(userId);
                unifiedUserRole.setRoleid(Integer.valueOf(roleid));
                list.add(unifiedUserRole);
            }
            return this.baseMapper.setRoles(list);
        }else{
            return 0;
        }



    }

    @Override
    public List<UnifiedUserRole> getRole(Integer userId, String appid) {
        return this.baseMapper.getRole(userId,appid);
    }
}
