package org.example.utils;

import org.example.model.entity.system.SysMenu;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MenuHelpUtil {


    public List<SysMenu> builderTree(List<SysMenu> list) {
        List<SysMenu> sysMenuList = new ArrayList<SysMenu>();

        for(SysMenu sysMenu : list){
            // 2.1 判断当前集合是否为父级菜单
            if(sysMenu.getParentId() == 0){
                sysMenuList.add(findChildren(sysMenu,list));
            }
        }
        return sysMenuList;
    }

    // 查找是否存在父级菜单的子菜单
    private SysMenu findChildren(SysMenu sysMenu, List<SysMenu> list) {
        // 1.初始化SysMenu的 children属性
        sysMenu.setChildren(new ArrayList<SysMenu>());

        for(SysMenu menu : list){
            // 2.判断sysMenu是否存在子菜单
            if(sysMenu.getId().longValue() == menu.getParentId().longValue()){
                // 进而下一步接着寻找menu是否存在子菜单
                sysMenu.getChildren().add(findChildren(menu,list));
            }
        }
        return sysMenu;
    }
}
