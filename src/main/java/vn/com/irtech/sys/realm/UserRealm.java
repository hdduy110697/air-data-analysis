package vn.com.irtech.sys.realm;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import vn.com.irtech.sys.common.ActiverUser;
import vn.com.irtech.sys.common.Constast;
import vn.com.irtech.sys.entity.Permission;
import vn.com.irtech.sys.entity.User;
import vn.com.irtech.sys.service.IPermissionService;
import vn.com.irtech.sys.service.IRoleService;
import vn.com.irtech.sys.service.IUserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author:  Admin
 * @Date: 2019/11/21 20:44
 */
public class UserRealm extends AuthorizingRealm {

    @Autowired
    /**
     * Only load when needed. That is: the userService will be parsed after the CacheAspect is parsed, otherwise the aspect will not take effect
     */
    @Lazy
    private IUserService userService;

    @Autowired
    @Lazy
    private IPermissionService permissionService;

    @Autowired
    @Lazy
    private IRoleService roleService;

    @Override
    public String getName(){
        return this.getClass().getSimpleName();
    }

    /**
     * Authorization
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        ActiverUser activerUser = (ActiverUser) principalCollection.getPrimaryPrincipal();
        User user = activerUser.getUser();
        List<String> superPermission = new ArrayList<>();
        superPermission.add("*:*");
        List<String> permissions = activerUser.getPermission();
        if (user.getType().equals(Constast.USER_TYPE_SUPER)){
            authorizationInfo.addStringPermissions(superPermission);
        }else {
            if (null!=permissions&&permissions.size()>0){
                authorizationInfo.addStringPermissions(permissions);
            }
        }
        return authorizationInfo;
    }

    /**
     * Certification
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("loginname",authenticationToken.getPrincipal().toString());
        //Query the user from the database by user name
        User user = userService.getOne(queryWrapper);
        if (null!=user){
            ActiverUser activerUser = new ActiverUser();
            activerUser.setUser(user);

            //Query percode based on user ID
            QueryWrapper<Permission> qw = new QueryWrapper<>();
            //Set only query menu
            qw.eq("type", Constast.TYPE_PERMISSION);
            //Set to query only available menus
            qw.eq("available",Constast.AVAILABLE_TRUE);
            Integer userId = user.getId();
            //Query role ID based on user ID
            List<Integer> currentUserRoleIds = roleService.queryUserRoleIdsByUid(userId);
            //Query permission ID based on role ID
            Set<Integer> pids = new HashSet<>();
            for (Integer rid : currentUserRoleIds) {
                List<Integer> permissionIds = roleService.queryRolePermissionIdsByRid(rid);
                pids.addAll(permissionIds);
            }
            List<Permission> list = new ArrayList<>();
            if (pids.size()>0){
                qw.in("id",pids);
                list = permissionService.list(qw);
            }
            List<String> percodes = new ArrayList<>();
            for (Permission permission : list) {
                percodes.add(permission.getPercode());
            }
            //Put into activerUser
            activerUser.setPermission(percodes);

            //Salt formation
            ByteSource credentialsSalt=ByteSource.Util.bytes(user.getSalt());
            /**
             * Parameter Description:
             * Parameter 1: Active User
             * Parameter 2: The password retrieved from the database (encrypted by MD5)
             * Parameter 3: the salt queried from the database
             * Parameter 4: the current class name
             */
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(activerUser,user.getPwd(),credentialsSalt,this.getName());
            return info;
        }
        return null;
    }
}
