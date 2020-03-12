package vn.com.irtech.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sun.org.apache.regexp.internal.RE;
import vn.com.irtech.sys.common.Constast;
import vn.com.irtech.sys.common.DataGridView;
import vn.com.irtech.sys.common.ResultObj;
import vn.com.irtech.sys.common.TreeNode;
import vn.com.irtech.sys.entity.Permission;
import vn.com.irtech.sys.entity.Role;
import vn.com.irtech.sys.service.IPermissionService;
import vn.com.irtech.sys.service.IRoleService;
import vn.com.irtech.sys.vo.RoleVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author:  Admin
 * @since 2019-11-28
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IPermissionService permissionService;

    /**
     * Query all roles
     * @param roleVo
     * @return
     */
    @RequestMapping("loadAllRole")
    public DataGridView loadAllRole(RoleVo roleVo){
        IPage<Role> page = new Page<Role>(roleVo.getPage(),roleVo.getLimit());
        QueryWrapper<Role> queryWrapper = new QueryWrapper<Role>();
        queryWrapper.like(StringUtils.isNotBlank(roleVo.getName()),"name",roleVo.getName());
        queryWrapper.like(StringUtils.isNotBlank(roleVo.getRemark()),"remark",roleVo.getRemark());
        queryWrapper.eq(roleVo.getAvailable()!=null,"available",roleVo.getAvailable());
        queryWrapper.ge(roleVo.getStartTime()!=null,"createtime",roleVo.getStartTime());
        queryWrapper.le(roleVo.getEndTime()!=null,"createtime",roleVo.getEndTime());
        queryWrapper.orderByDesc("createtime");
        roleService.page(page,queryWrapper);
        return new DataGridView(page.getTotal(),page.getRecords());
    }

    /**
     * Add role
     * @param roleVo
     * @return
     */
    @RequestMapping("addRole")
    public ResultObj addRole(RoleVo roleVo){
        try {
            roleVo.setCreatetime(new Date());
            roleService.save(roleVo);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }
    }

    /**
     * Modify role
     * @param roleVo
     * @return
     */
    @RequestMapping("updateRole")
    public ResultObj updateRole(RoleVo roleVo){
        try {
            roleService.updateById(roleVo);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }

    /**
     * Delete role
     * @param id
     * @return
     */
    @RequestMapping("deleteRole")
    public ResultObj deleteRole(Integer id){
        try {
            this.roleService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }

    /**
     * Load json string of menu and permissions tree based on role ID
     * @param roleId
     * @return
     */
    @RequestMapping("initPermissionByRoleId")
    public DataGridView initPermissionByRoleId(Integer roleId){
        //Query all available menus and permissions
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("available", Constast.AVAILABLE_TRUE);
        List<Permission> allPermissions = permissionService.list(queryWrapper);
        //1.First, query the IDs of all menus and permissions IDs owned by the current role according to the role id.
        List<Integer> currentRolePermissions = roleService.queryRolePermissionIdsByRid(roleId);
        //2.Query the menu data and permission data according to the query menu ID and permission ID
        List<Permission> currentPermissions = null;
        //If the menu ID or permission ID is queried based on the role id, go to query
        if (currentRolePermissions.size()>0){
            queryWrapper.in("id",currentRolePermissions);
            currentPermissions = permissionService.list(queryWrapper);
        }else {
            currentPermissions = new ArrayList<>();
        }
        //Constructing a List<TreeNode>
        List<TreeNode> nodes = new ArrayList<>();
        for (Permission allPermission : allPermissions) {
            String checkArr = "0";
            for (Permission currentPermission : currentPermissions) {
                if (allPermission.getId().equals(currentPermission.getId())){
                    checkArr = "1";
                    break;
                }
            }
            Boolean spread = (allPermission.getOpen()==null||allPermission.getOpen()==1)?true:false;
            nodes.add(new TreeNode(allPermission.getId(),allPermission.getPid(),allPermission.getTitle(),spread,checkArr));
        }
        return new DataGridView(nodes);
    }

    /**
     * Save the relationship between roles and menu permissions
     * @param rid
     * @param ids
     * @return
     */
    @RequestMapping("saveRolePermission")
    public ResultObj saveRolePermission(Integer rid,Integer[] ids){
        try {
            roleService.saveRolePermission(rid,ids);
            return ResultObj.DISPATCH_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DISPATCH_ERROR;
        }

    }

}

