package cn.future.modular.system.service.impl;

import cn.future.modular.system.model.Rank;
import cn.future.modular.system.dao.RankMapper;
import cn.future.modular.system.service.IRankService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 职级 服务实现类
 * </p>
 *
 * @author zp
 * @since 2019-04-21
 */
@Service
public class RankServiceImpl extends ServiceImpl<RankMapper, Rank> implements IRankService {

    @Override
    public boolean deleteById(Integer rankId) {
        return this.baseMapper.deleteById(rankId);
    }

    @Override
    public List<Map<String, Object>> list(String conditiion) {
        return this.baseMapper.list(conditiion);
    }

    @Override
    public List<Rank> findList() {
        return this.baseMapper.findList();
    }

    @Override
    public Rank getByCode(String code) {
        return this.baseMapper.getByCode(code);
    }


}
