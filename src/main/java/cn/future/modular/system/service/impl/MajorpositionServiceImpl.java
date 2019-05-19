package cn.future.modular.system.service.impl;

import cn.future.modular.system.model.Majorposition;
import cn.future.modular.system.dao.MajorpositionMapper;
import cn.future.modular.system.service.IMajorpositionService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 岗位 服务实现类
 * </p>
 *
 * @author zp
 * @since 2019-04-21
 */
@Service
public class MajorpositionServiceImpl extends ServiceImpl<MajorpositionMapper, Majorposition> implements IMajorpositionService {


    @Override
    public boolean deleteById(Integer majorpositionId) {
        return this.baseMapper.deleteById(majorpositionId);
    }

    @Override
    public List<Map<String, Object>> list(String conditiion) {
        return this.baseMapper.list(conditiion);
    }

    @Override
    public List<Majorposition> findList() {
        return this.baseMapper.findList();
    }

    @Override
    public Majorposition getByCode(String code) {
        return this.baseMapper.getByCode(code);
    }
}
