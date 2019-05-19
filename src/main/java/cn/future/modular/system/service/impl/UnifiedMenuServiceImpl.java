package cn.future.modular.system.service.impl;

import cn.future.core.common.node.ZTreeNode;
import cn.future.core.common.node.UnifiedMenuNode;
import cn.future.modular.system.model.UnifiedMenu;
import cn.future.modular.system.dao.UnifiedMenuMapper;
import cn.future.modular.system.service.IUnifiedMenuService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 统一菜单表 服务实现类
 * </p>
 *
 * @author lee
 * @since 2019-04-18
 */
@Service
public class UnifiedMenuServiceImpl extends ServiceImpl<UnifiedMenuMapper, UnifiedMenu> implements IUnifiedMenuService {



    @Resource
    private UnifiedMenuMapper unifiedMenuMapper;

    @Override
    @Transactional
    public void delMenu(Long menuId) {

        //删除菜单
        this.unifiedMenuMapper.deleteById(menuId);

        //删除关联的relation
        this.unifiedMenuMapper.deleteRelationByMenu(menuId);
    }

    @Override
    @Transactional
    public void delMenuContainSubMenus(Long menuId) {

        UnifiedMenu menu = unifiedMenuMapper.selectById(menuId);

        //删除当前菜单
        delMenu(menuId);

        //删除所有子菜单
        Wrapper<UnifiedMenu> wrapper = new EntityWrapper<>();
        wrapper = wrapper.like("pcodes", "%[" + menu.getCode() + "]%");
        List<UnifiedMenu> menus = unifiedMenuMapper.selectList(wrapper);
        for (UnifiedMenu temp : menus) {
            delMenu(temp.getId());
        }
    }

    @Override
    public List<Map<String, Object>> selectMenus(String condition, String level,String appid) {
        return this.baseMapper.selectMenus(condition, level,appid);
    }

    @Override
    public List<Long> getMenuIdsByRoleId(Integer roleId) {
        return this.baseMapper.getMenuIdsByRoleId(roleId);
    }

    @Override
    public List<ZTreeNode> menuTreeList(String appid) {
        return this.baseMapper.menuTreeList(appid);
    }

    @Override
    public List<ZTreeNode> menuTreeListByMenuIds(List<Long> menuIds,String appid) {
        return this.baseMapper.menuTreeListByMenuIds(menuIds,appid);
    }

    @Override
    public int deleteRelationByMenu(Long menuId) {
        return this.baseMapper.deleteRelationByMenu(menuId);
    }

    @Override
    public List<String> getResUrlsByRoleId(Integer roleId) {
        return this.baseMapper.getResUrlsByRoleId(roleId);
    }

    @Override
    public List<UnifiedMenuNode> getMenusByRoleIds(List list) {
        return this.baseMapper.getMenusByRoleIds(list);
    }

}
