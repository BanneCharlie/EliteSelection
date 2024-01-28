package org.example.manager.service.imp;

import com.github.xiaoymin.knife4j.core.util.CollectionUtils;
import org.example.exception.CustomLoginException;
import org.example.manager.mapper.SysMenuMapper;
import org.example.manager.mapper.SysRoleMenuMapper;
import org.example.manager.service.SysMenuService;
import org.example.model.entity.system.SysMenu;
import org.example.model.vo.common.ResultCodeEnum;
import org.example.model.vo.system.SysMenuVo;
import org.example.utils.AuthContextUtil;
import org.example.utils.MenuHelpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SysMenuServiceImp  implements SysMenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Autowired
    private MenuHelpUtil menuHelpUtil;

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Autowired
    private SysRoleMenuMapper roleMenuMapper;

    @Override
    public List<SysMenu> findNodes() {
        // 1.查询所有菜单信息
        List<SysMenu> list = sysMenuMapper.findNodes();
        // 判断集合是否为空
        if(CollectionUtils.isEmpty(list)){
            return null;
        }
        // 2.将查询到的集合进行封装(封装为前端需要的内容)
        List<SysMenu> sysMenuList = menuHelpUtil.builderTree(list);

        return sysMenuList;
    }

    @Override
    public void save(SysMenu sysMenu) {
        sysMenuMapper.save(sysMenu);
        // 新添加一个菜单，那么此时就需要将该菜单所对应的父级菜单设置为半开
        updateSysRoleMenuIsHalf(sysMenu);
    }

    private void updateSysRoleMenuIsHalf(SysMenu sysMenu) {
        // 1.获取到当前菜单的父级菜单
        SysMenu parentMenu = sysMenuMapper.selectByParentId(sysMenu.getParentId());

        if (parentMenu!= null){
            // 2.将该id的菜单设置为半开
            sysRoleMenuMapper.updateSysRoleMenuIsHalf(parentMenu.getId());

            // 递归调用 可能需要几次才可以找到 顶级父级菜单
            updateSysRoleMenuIsHalf(parentMenu) ;
        }
    }

    @Override
    public void updateById(SysMenu sysMenu) {
        sysMenuMapper.updateById(sysMenu);
    }

    @Override
    public void removeById(Long id) {
        // 1.查询是否存在子菜单,存在则不允许删除
        int count = sysMenuMapper.countByParentId(id);
        if (count > 0){
            throw new CustomLoginException(ResultCodeEnum.NODE_ERROR);
        }
        sysMenuMapper.deleteById(id);
    }

    @Override
    public Map<String, Object> findSysRoleMenuByRoleId(Long roleId) {
        Map<String,Object> map = new HashMap<String, Object>();

        // 1.查询所有菜单信息
        List<SysMenu> list = sysMenuMapper.findNodes();
        // 判断集合是否为空
        if(CollectionUtils.isEmpty(list)){
            return null;
        }
        // 将查询到的集合进行封装(封装为前端需要的内容)
        List<SysMenu> sysMenuList = menuHelpUtil.builderTree(list);

        // 2.根据角色Id查询当前角色具有的菜单
        List<Long> roleMenuIds =  sysRoleMenuMapper.findSysRoleMenuByRoleId(roleId);

        map.put("sysMenuList",sysMenuList);
        map.put("roleMenuIds" , roleMenuIds) ;

        return map;
    }

    @Override
    public List<SysMenuVo> findUserMenuList() {
        // 1.获取到当前登录的用户id
        Long user_id = AuthContextUtil.getThreadLocal().getId();

        // 2.根据用户id获取到具有的菜单功能;
        List<SysMenu> list= sysMenuMapper.findUserMenuByUserId(user_id);

        // 3.将获取的用户具有的菜单信息,进行封装
        List<SysMenu> sysMenus = menuHelpUtil.builderTree(list);

        // 4.再进行进一步的转换
        List<SysMenuVo> sysMenuVos = buildMenus(sysMenus);

        return sysMenuVos;
    }

    // 将List<SysMenu>对象转换成List<SysMenuVo>对象
    private List<SysMenuVo> buildMenus(List<SysMenu> menus) {

        List<SysMenuVo> sysMenuVoList = new LinkedList<SysMenuVo>();
        for (SysMenu sysMenu : menus) {
            SysMenuVo sysMenuVo = new SysMenuVo();
            sysMenuVo.setTitle(sysMenu.getTitle());
            sysMenuVo.setName(sysMenu.getComponent());
            List<SysMenu> children = sysMenu.getChildren();
            if (!CollectionUtils.isEmpty(children)) {
                sysMenuVo.setChildren(buildMenus(children));
            }
            sysMenuVoList.add(sysMenuVo);
        }
        return sysMenuVoList;
    }
}
