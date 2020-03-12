package vn.com.irtech.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import vn.com.irtech.sys.common.*;
import vn.com.irtech.sys.entity.Dept;
import vn.com.irtech.sys.entity.Permission;
import vn.com.irtech.sys.entity.User;
import vn.com.irtech.sys.service.IDeptService;
import vn.com.irtech.sys.service.IPermissionService;
import vn.com.irtech.sys.service.IRoleService;
import vn.com.irtech.sys.service.IUserService;
import vn.com.irtech.sys.vo.DeptVo;
import vn.com.irtech.sys.vo.PermissionVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @Author:  Admin
 * @Date: 2019/11/22 15:35
 */
@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private IPermissionService permissionService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

    @RequestMapping("loadIndexLeftMenuJson")
    public DataGridView loadIndexLeftMenuJson(PermissionVo permissionVo){
        //Query all menus
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<Permission>();
        //Set search conditions
        //The query must be a menu, not crud permissions
        queryWrapper.eq("type",Constast.TYPE_MENU);
        //Menu must be available
        queryWrapper.eq("available", Constast.AVAILABLE_TRUE);

        //Get users, determine the type of user
        User user = (User) WebUtils.getSession().getAttribute("user");
        List<Permission> list = null;
        if (user.getType().equals(Constast.USER_TYPE_SUPER)){
            //User type is super administrator
            list = permissionService.list(queryWrapper);
        }else {
            //User type is ordinary user
            //Query based on user ID + role + permissions
            Integer userId = user.getId();
            //1.Query roles based on user ID
            List<Integer> currentUserRoleIds = roleService.queryUserRoleIdsByUid(userId);
            //2.Query menu ID and permission ID based on role ID
            //Use set to remove duplicates
            Set<Integer> pids = new HashSet<>();
            for (Integer rid : currentUserRoleIds) {
                //Query menu ID and permission ID based on role ID
                List<Integer> permissionIds = roleService.queryRolePermissionIdsByRid(rid);
                //Put menu ID and permission ID into Set
                pids.addAll(permissionIds);
            }
            //3.Query permissions based on role ID
            if (pids.size()>0){
                queryWrapper.in("id",pids);
                list = permissionService.list(queryWrapper);
            }else {
                list=new ArrayList<>();
            }

        }

        List<TreeNode> treeNodes = new ArrayList<TreeNode>();
        for (Permission p : list) {
            Integer id =p.getId();
            Integer pid = p.getPid();
            String title = p.getTitle();
            String icon = p.getIcon();
            String href = p.getHref();
            Boolean spread = p.getOpen().equals(Constast.OPEN_TRUE)?true:false;
            treeNodes.add(new TreeNode(id,pid,title,icon,href,spread));
        }

        //Constructing hierarchical relationships
        List<TreeNode> list2 = TreeNodeBuilder.build(treeNodes,1);
        return new DataGridView(list2);

    }
    
    
    /************************Menu management*********************************/

    /**
     * Menu tree to the left of the load menu
     * @param permissionVo
     * @return
     */
    @RequestMapping("loadMenuManagerLeftTreeJson")
    public DataGridView loadMenuManagerLeftTreeJson(PermissionVo permissionVo){

        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type",Constast.TYPE_MENU);
        //Query all menus and store them in list
        List<Permission> list = permissionService.list(queryWrapper);
        List<TreeNode> treeNodes = new ArrayList<>();
        //Put the menu into treeNodes and assemble it into json
        for (Permission menu : list) {
            Boolean open = menu.getOpen()==1?true:false;
            treeNodes.add(new TreeNode(menu.getId(),menu.getPid(),menu.getTitle(),open));
        }
        return new DataGridView(treeNodes);
    }

    /**
     * Query all menu data
     * @param permissionVo
     * @return
     */
    @RequestMapping("loadAllMenu")
    public DataGridView loadAllMenu(PermissionVo permissionVo){
        IPage<Permission> page = new Page<>(permissionVo.getPage(),permissionVo.getLimit());
        //Perform fuzzy query
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(permissionVo.getId()!=null,"id",permissionVo.getId()).or().eq(permissionVo.getId()!=null,"pid",permissionVo.getId());
        //Only query menu
        queryWrapper.eq("type",Constast.TYPE_MENU);
        queryWrapper.like(StringUtils.isNotBlank(permissionVo.getTitle()),"title",permissionVo.getTitle());
        queryWrapper.orderByAsc("ordernum");
        //Make a query
        permissionService.page(page,queryWrapper);
        //Back to DataGridView
        return new DataGridView(page.getTotal(),page.getRecords());
    }

    /**
     * Add menu
     * @param permissionVo
     * @return
     */
    @RequestMapping("addMenu")
    public ResultObj addMenu(PermissionVo permissionVo){
        try {
            //Set the add type to menu
            permissionVo.setType(Constast.TYPE_MENU);
            permissionService.save(permissionVo);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }
    }

    /**
     * Load sort code
     * @return
     */
    @RequestMapping("loadMenuMaxOrderNum")
    public Map<String,Object> loadMenuMaxOrderNum(){
        Map<String,Object> map = new HashMap<String,Object>();
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("ordernum");
        IPage<Permission> page = new Page<>(1,1);
        List<Permission> list = permissionService.page(page,queryWrapper).getRecords();
        if (list.size()>0){
            map.put("value",list.get(0).getOrdernum()+1);
        }else {
            map.put("value",1);
        }
        return map;
    }

    /**
     * Update menu
     * @param permissionVo
     * @return
     */
    @RequestMapping("updateMenu")
    public ResultObj updateMenu(PermissionVo permissionVo){
        try {
            permissionService.updateById(permissionVo);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }

    /**
     * Check if the current menu has submenus
     * @param permissionVo
     * @return
     */
    @RequestMapping("checkMenuHasChildrenNode")
    public Map<String,Object> checkMenuHasChildrenNode(PermissionVo permissionVo){
        Map<String,Object> map = new HashMap<String, Object>();
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pid",permissionVo.getId());
        List<Permission> list = permissionService.list(queryWrapper);
        if (list.size()>0){
            map.put("value",true);
        }else {
            map.put("value",false);
        }
        return map;
    }

    /**
     * Delete menu
     * @param permissionVo
     * @return
     */
    @RequestMapping("deleteMenu")
    public ResultObj deleteMenu(PermissionVo permissionVo){
        try {
            permissionService.removeById(permissionVo.getId());
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }
    
    
}
