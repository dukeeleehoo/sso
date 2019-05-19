package cn.future.modular.unified.service;

import cn.future.modular.unified.model.UnifiedUserRole;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 统一用户和角色关联表 服务类
 * </p>
 *
 * @author zp
 * @since 2019-04-18
 */
public interface IUnifiedUserRoleService extends IService<UnifiedUserRole> {
    /**
     *
     * @param userId
     * @param roleIds
     * @return appid应用appid
     */
    public int setRoles(Integer userId, String roleIds,String appid);

    /**
     * 根据appid和userid得到roleid集合
     * @param userId
     * @param appid
     * @return
     */
    List<UnifiedUserRole> getRole(Integer userId, String appid);

}
