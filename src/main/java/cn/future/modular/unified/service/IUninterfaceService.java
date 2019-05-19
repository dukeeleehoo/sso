package cn.future.modular.unified.service;

import cn.future.modular.unified.model.Uninterface;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 接口管理 服务类
 * </p>
 *
 * @author zp
 * @since 2018-12-10
 */
public interface IUninterfaceService extends IService<Uninterface> {

    /**
     * 根据条件查询接口
     * @return
     */
    List<Map<String, Object>> selectUninterfaces();

    /**
     * 根据接口地址查询接口信息
     * @return
     */
    Uninterface selectByUrl(String url);

    /**
     * 根据接口名称查询接口信息
     * @return
     */
    List<Uninterface> selectByName(String name);
}
