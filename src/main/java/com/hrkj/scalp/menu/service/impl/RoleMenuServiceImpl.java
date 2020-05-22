package com.hrkj.scalp.menu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hrkj.scalp.menu.entity.RoleMenu;
import com.hrkj.scalp.menu.mapper.RoleMenuMapper;
import com.hrkj.scalp.menu.service.IRoleMenuService;
import org.jeecg.common.util.oConvertUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description: farming_role_menu
 * @Author: jeecg-boot
 * @Date:   2020-03-06
 * @Version: V1.0
 */
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements IRoleMenuService {

    @Override
    public void saveRolePermission(String roleId, String permissionIds) {

    }

    @Override
    public void saveRolePermission(String roleId, String newMenuIds, String lastMenuIds) {
        // 匹配新旧菜单  找出新菜单中新分配的菜单
        List<String> add = getDiff(lastMenuIds,newMenuIds);
        if(add!=null && add.size()>0) {
            List<RoleMenu> list = new ArrayList<RoleMenu>();
            for (String p : add) {
                if(oConvertUtils.isNotEmpty(p)) {
                    RoleMenu rolepms = new RoleMenu(roleId, p);
                    list.add(rolepms);
                }
            }
            this.saveBatch(list);
        }
        // 匹配新旧菜单  找出旧菜单中被删除的菜单
        List<String> delete = getDiff(newMenuIds,lastMenuIds);
        if(delete!=null && delete.size()>0) {
            for (String menuId : delete) {
                this.remove(new QueryWrapper<RoleMenu>().lambda().eq(RoleMenu::getRoleId, roleId).eq(RoleMenu::getMenuId, menuId));
            }
        }

    }
    /**
     * 从diff中找出main中没有的元素
     * @param main
     * @param diff
     * @return
     */
    private List<String> getDiff(String main,String diff){
        if(oConvertUtils.isEmpty(diff)) {
            return null;
        }
        if(oConvertUtils.isEmpty(main)) {
            return Arrays.asList(diff.split(","));
        }
        String[] mainArr = main.split(",");
        String[] diffArr = diff.split(",");
        Map<String, Integer> map = new HashMap<>();
        for (String string : mainArr) {
            map.put(string, 1);
        }
        List<String> res = new ArrayList<String>();
        for (String key : diffArr) {
            if(oConvertUtils.isNotEmpty(key) && !map.containsKey(key)) {
                res.add(key);
            }
        }
        return res;
//    }

//        if(oConvertUtils.isEmpty(main)) {
//            return Arrays.stream(diff.split(",")).map(Integer::parseInt).collect(Collectors.toList());
//        }
//      以前拥有的菜单id
//        List<String> mainArr = Arrays.stream(main.split(",")).map(Integer::parseInt).collect(Collectors.toList());
////      新分配的菜单id
//        List<String> diffArr = Arrays.stream(diff.split(",")).map(Integer::parseInt).collect(Collectors.toList());
//        Map<String, String> map = new HashMap<>();
//        for (String integer : mainArr) {
//            map.put(integer, 1);
//        }
//        List<Integer> res = new ArrayList<Integer>();
//        for (Integer key : diffArr) {
//            if(oConvertUtils.isNotEmpty(key) && !map.containsKey(key)) {
//                res.add(key);
//            }
//        }
    }
}
