package cn.future.modular.unified.service;

import cn.future.modular.unified.model.UnifiedRelation;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 统一菜单和角色关联表 服务类
 * </p>
 *
 * @author zp
 * @since 2019-04-18
 */
public interface IUnifiedRelationService extends IService<UnifiedRelation> {


    /**
     * 根据菜单id删除
     */

    void delByMenuId(Integer menuid);
}
