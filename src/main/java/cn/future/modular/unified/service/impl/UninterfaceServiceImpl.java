package cn.future.modular.unified.service.impl;

import cn.future.modular.unified.service.IUninterfaceService;
import cn.future.modular.unified.model.Uninterface;
import cn.future.modular.unified.dao.UninterfaceMapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 接口管理 服务实现类
 * </p>
 *
 * @author zp
 * @since 2018-12-10
 */
@Service
public class UninterfaceServiceImpl extends ServiceImpl<UninterfaceMapper, Uninterface> implements IUninterfaceService {

    @Override
    public List<Map<String, Object>> selectUninterfaces() {
        return this.baseMapper.selectUninterfaces();
    }

    @Override
    public Uninterface selectByUrl(String url){
        return this.baseMapper.selectByUrl(url);
    }

    @Override
    public List<Uninterface> selectByName(String name) {
        return this.baseMapper.selectByName(name);
    }
}
