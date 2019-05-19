package cn.future.modular.system.service;

import cn.future.core.common.node.ZTreeNode;
import cn.future.modular.system.model.UnifiedMenu;
import cn.future.core.common.node.UnifiedMenuNode;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 统一菜单表 服务类
 * </p>
 *
 * @author lee
 * @since 2019-04-18
 */
public interface IUnifiedMenuService extends IService<UnifiedMenu> {


    /**
     * 删除菜单
     *
     * @author stylefeng
     * @Date 2017/5/5 22:20
     */
    void delMenu(Long menuId);

    /**
     * 删除菜单包含所有子菜单
     *
     * @author stylefeng
     * @Date 2017/6/13 22:02
     */
    void delMenuContainSubMenus(Long menuId);

    /**
     * 根据条件查询菜单
     *
     * @return
     * @date 2017年2月12日 下午9:14:34
     */
    List<Map<String, Object>> selectMenus(String condition, String level,String appid);

    /**
     * 根据条件查询菜单
     *
     * @return
     * @date 2017年2月12日 下午9:14:34
     */
    List<Long> getMenuIdsByRoleId(Integer roleId);

    /**
     * 获取菜单列表树
     *
     * @return
     * @date 2017年2月19日 下午1:33:51
     */
    List<ZTreeNode> menuTreeList(String appid);

    /**
     * 获取菜单列表树
     *
     * @return
     * @date 2017年2月19日 下午1:33:51
     */
    List<ZTreeNode> menuTreeListByMenuIds(List<Long> menuIds,String appid);

    /**
     * 删除menu关联的relation
     *
     * @param menuId
     * @return
     * @date 2017年2月19日 下午4:10:59
     */
    int deleteRelationByMenu(Long menuId);

    /**
     * 获取资源url通过角色id
     *
     * @param roleId
     * @return
     * @date 2017年2月19日 下午7:12:38
     */
    List<String> getResUrlsByRoleId(Integer roleId);

    /**
     * 根据角色获取菜单
     *
     * @param list
     * @return
     * @date 2017年2月19日 下午10:35:40
     */
    List<UnifiedMenuNode> getMenusByRoleIds(List list);
}
