package cn.future.modular.unified.service.impl;

import cn.future.modular.unified.dao.UnifiedRelationMapper;
import cn.future.modular.unified.model.UnifiedRelation;
import cn.future.modular.unified.service.IUnifiedRelationService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 统一菜单和角色关联表 服务实现类
 * </p>
 *
 * @author zp
 * @since 2019-04-18
 */
@Service
public class UnifiedRelationServiceImpl extends ServiceImpl<UnifiedRelationMapper, UnifiedRelation> implements IUnifiedRelationService {

    @Override
    public void delByMenuId(Integer menuid) {
        this.baseMapper.delByMenuId(menuid);
    }
}
