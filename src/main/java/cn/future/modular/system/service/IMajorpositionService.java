package cn.future.modular.system.service;

import cn.future.modular.system.model.Majorposition;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 岗位 服务类
 * </p>
 *
 * @author zp
 * @since 2019-04-21
 */
public interface IMajorpositionService extends IService<Majorposition> {

    /**
     * 删除方法
     * @param majorpositionId
     * @return
     */
    boolean deleteById(Integer majorpositionId);

    /**
     * 查询岗位列表
     */
    List<Map<String, Object>> list(String conditiion);

    /**
     * 查询岗位列表
     */
    List<Majorposition> findList();

    /**
     * 根据code找值
     */
    Majorposition getByCode(String code);
}
