package vn.com.irtech.sys.controller;


import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import vn.com.irtech.sys.common.*;
import vn.com.irtech.sys.entity.Dept;
import vn.com.irtech.sys.entity.Role;
import vn.com.irtech.sys.entity.User;
import vn.com.irtech.sys.service.IDeptService;
import vn.com.irtech.sys.service.IRoleService;
import vn.com.irtech.sys.service.IUserService;
import vn.com.irtech.sys.vo.UserVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author:  Admin
 * @since 2019-11-21
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IDeptService deptService;

    @Autowired
    private IRoleService roleService;

    /**
     * Query all users
     * @param userVo
     * @return
     */
    @RequestMapping("loadAllUser")
    public DataGridView loadAllUser(UserVo userVo){
        IPage<User> page = new Page<User>(userVo.getPage(),userVo.getLimit());
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
        queryWrapper.eq(StringUtils.isNotBlank(userVo.getName()),"loginname",userVo.getName()).or().eq(StringUtils.isNotBlank(userVo.getName()),"name",userVo.getName());
        queryWrapper.eq(StringUtils.isNotBlank(userVo.getAddress()),"address",userVo.getAddress());
        //Querying system users
        queryWrapper.eq("type", Constast.USER_TYPE_NORMAL);
        queryWrapper.eq(userVo.getDeptid()!=null,"deptid",userVo.getDeptid());
        userService.page(page,queryWrapper);

        //Put all user data in the list
        List<User> list = page.getRecords();
        for (User user : list) {
            Integer deptid = user.getDeptid();
            if (deptid!=null){
                //First fetch from the cache, if not in the cache, fetch from the database
                Dept one = deptService.getById(deptid);
                //Set the user's department name
                user.setDeptname(one.getName());
            }
            Integer mgr = user.getMgr();
            if (mgr!=null){
                User one = userService.getById(mgr);
                //Set the user's leader name
                user.setLeadername(one.getName());
            }
        }
        return new DataGridView(page.getTotal(),list);
    }

    /**
     * Load sort code
     * @return
     */
    @RequestMapping("loadUserMaxOrderNum")
    public Map<String,Object> loadUserMaxOrderNum(){
        Map<String,Object> map = new HashMap<String,Object>();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("ordernum");
        IPage<User> page = new Page<>(1,1);
        List<User> list = userService.page(page,queryWrapper).getRecords();
        if (list.size()>0){
            map.put("value",list.get(0).getOrdernum()+1);
        }else {
            map.put("value",1);
        }
        return map;
    }

    /**
     * Query users by department ID
     * @param deptid
     * @return
     */
    @RequestMapping("loadUsersByDeptId")
    public DataGridView loadUsersByDeptIp(Integer deptid){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(deptid!=null,"deptid",deptid);
        queryWrapper.eq("available",Constast.AVAILABLE_TRUE);
        queryWrapper.eq("type",Constast.USER_TYPE_NORMAL);
        List<User> list = userService.list(queryWrapper);
        return new DataGridView(list);
    }

    /**
     * Turn username into pinyin
     * @param username
     * @return
     */
    @RequestMapping("changeChineseToPinyin")
    public Map<String,Object> changeChineseToPinyin(String username){
        Map<String,Object> map = new HashMap<>();
        if (null!=username){
            map.put("value", PinyinUtils.getPingYin(username));
        }else {
            map.put("value","");
        }
        return map;
    }

    /**
     * Add user
     * @param userVo
     * @return
     */
    @RequestMapping("addUser")
    public ResultObj addUser(UserVo userVo){
        try {
            //Setting type
            userVo.setType(Constast.USER_TYPE_NORMAL);
            //Set salt
            String salt = IdUtil.simpleUUID().toUpperCase();
            userVo.setSalt(salt);
            //Set default password
            userVo.setPwd(new Md5Hash(Constast.USER_DEFAULT_PWD,salt,2).toString());
            //Set user default avatar
            userVo.setImgpath(Constast.DEFAULT_IMG_USER);
            userService.save(userVo);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }
    }

    /**
     * Query a user based on id
     * @param id  Leadership id
     * @return
     */
    @RequestMapping("loadUserById")
    public DataGridView loadUserById(Integer id){
        return new DataGridView(userService.getById(id));
    }

    /**
     * Modify user
     * @param userVo
     * @return
     */
    @RequestMapping("updateUser")
    public ResultObj updateUser(UserVo userVo){
        try {
            userService.updateById(userVo);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }

    /**
     * delete users
     * @param id
     * @return
     */
    @RequestMapping("deleteUser/{id}")
    public ResultObj deleteUser(@PathVariable("id") Integer id){
        try {
            userService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }

    /**
     * Query whether the current user is a direct leader of another user according to the user ID
     * @param userId
     * @return
     */
    @RequestMapping("queryMgrByUserId")
    public ResultObj queryMgrByUserId(Integer userId){
        Boolean isMgr = userService.queryMgrByUserId(userId);
        if (isMgr){
            return ResultObj.DELETE_ERROR_NEWS;
        }else {
            return ResultObj.DELETE_QUERY;
        }
    }

    /**
     * Reset user password
     * @param id
     * @return
     */
    @RequestMapping("resetPwd/{id}")
    public ResultObj resetPwd(@PathVariable("id") Integer id){
        try {
            User user = new User();
            user.setId(id);
            //Set salt 32 digits (capital English letters (A-Z) plus numbers (0-9))
            String salt = IdUtil.simpleUUID().toUpperCase();
            user.setSalt(salt);
            //set password
            user.setPwd(new Md5Hash(Constast.USER_DEFAULT_PWD,salt,2).toString());
            userService.updateById(user);
            return ResultObj.RESET_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.RESET_ERROR;
        }
    }

    /**
     * Query roles based on user id and select owned roles
     * @param id 用户id
     * @return
     */
    @RequestMapping("initRoleByUserId")
    public DataGridView initRoleByUserId(Integer id){
        //1.Query all available roles
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("available",Constast.AVAILABLE_TRUE);
        List<Map<String, Object>> listMaps = roleService.listMaps(queryWrapper);
        //2.Query the set of role IDs owned by the current user
        List<Integer> currentUserRoleIds = roleService.queryUserRoleIdsByUid(id);
        for (Map<String, Object> map : listMaps) {
            Boolean LAY_CHECKED=false;
            Integer roleId = (Integer) map.get("id");
            for (Integer rid : currentUserRoleIds) {
                //If the current user already has the role, let LAY_CHECKED be true. Check box selected when LAY_CHECKED is true
                if (rid.equals(roleId)){
                    LAY_CHECKED=true;
                    break;
                }
            }
            map.put("LAY_CHECKED",LAY_CHECKED);
        }
        return new DataGridView(Long.valueOf(listMaps.size()),listMaps);
    }

    /**
     * Save user and role relationships
     * @param uid User ID
     * @param ids Array of user-owned role IDs
     * @return
     */
    @RequestMapping("saveUserRole")
    public ResultObj saveUserRole(Integer uid,Integer[] ids){
        try {
            userService.saveUserRole(uid,ids);
            return ResultObj.DISPATCH_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DISPATCH_ERROR;
        }
    }

    /**
     * Change user password
     * @param oldPassword  User's original password
     * @param newPwdOne     New password entered by the user for the first time
     * @param newPwdTwo     New password entered by the user for the second time
     * @return
     */
    @RequestMapping("changePassword")
    public ResultObj changePassword(String oldPassword,String newPwdOne,String newPwdTwo){
        //1.First get the ID of the current user through the session
        User user =(User) WebUtils.getSession().getAttribute("user");
        //2.Salt and hash oldPassword twice to compare with the password in the database
        Integer userId = user.getId();
        User user1 = userService.getById(userId);
        //2.1Get the user's salt
        String salt = user1.getSalt();
        //2.2 The old password entered by the user, the salt found from the database, and the hash number to generate a new and old password
        String oldPassword2 = new Md5Hash(oldPassword,salt,Constast.HASHITERATIONS).toString();
        if (oldPassword2.equals(user1.getPwd())){
            if (newPwdOne.equals(newPwdTwo)){
                //3.Generate new password
                String newPassword = new Md5Hash(newPwdOne,salt,Constast.HASHITERATIONS).toString();
                user1.setPwd(newPassword);
                userService.updateById(user1);
                return ResultObj.UPDATE_SUCCESS;
            }else {
                return ResultObj.UPDATE_ERROR;
            }
        }else {
            return ResultObj.UPDATE_ERROR;
        }
    }

    /**
     * Returns the currently logged in user
     * @return
     */
    @RequestMapping("getNowUser")
    public User getNowUser(){
        //1.Get the user in the current session
        User user = (User) WebUtils.getSession().getAttribute("user");
        return user;
    }


    /**
     * Modify user
     * @param userVo
     * @return
     */
    @RequestMapping("updateUserInfo")
    public ResultObj updateUserInfo(UserVo userVo){
        try {
            //User avatar is not the default picture
            if (!(userVo.getImgpath()!=null&&userVo.getImgpath().equals(Constast.DEFAULT_IMG_GOODS))){
                if (userVo.getImgpath().endsWith("_temp")){
                    String newName = AppFileUtils.renameFile(userVo.getImgpath());
                    userVo.setImgpath(newName);
                    //Delete original picture
                    String oldPath = userService.getById(userVo.getId()).getImgpath();
                    AppFileUtils.removeFileByPath(oldPath);
                }
            }
            userService.updateById(userVo);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }

}

