package cn.future.modular.system.service;

import cn.future.modular.system.model.Rank;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 职级 服务类
 * </p>
 *
 * @author zp
 * @since 2019-04-21
 */
public interface IRankService extends IService<Rank> {

    /**
     * 删除方法
     * @param rabkId
     * @return
     */
    boolean deleteById(Integer rabkId);

    /**
     * 查询职级 列表
     */
    List<Map<String, Object>> list(String conditiion);

    /**
     * 查询职级列表
     */
    List<Rank> findList();

    /**
     * 根据code找值
     */
    Rank getByCode(String code);
}
