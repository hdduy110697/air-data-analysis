package vn.com.irtech.sys.mapper;

import vn.com.irtech.sys.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author:  Admin
 * @since 2019-11-28
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * Delete data in sys_role_permission table based on role ID
     * @param id Role id
     */
    void deleteRolePermissionByRid(@Param("pid") Serializable id);

    /**
     * Delete data in sys_user_role table based on role ID
     * @param id Role id
     */
    void deleteUserRoleByRid(@Param("pid") Serializable id);

    /**
     * Query the menu ID and permission ID owned by the current role according to the role ID
     * @param roleId
     * @return
     */
    List<Integer> queryRolePermissionIdsByRid(@Param("roleId") Integer roleId);

    /**
     * Save the relationship between roles and menu permissions
     * @param rid
     * @param pid
     */
    void saveRolePermission(@Param("rid") Integer rid,@Param("pid") Integer pid);

    /**
     * Delete the data of the user role intermediate table according to the user id
     * @param id
     */
    void deleteRoleUserByUid(@Param("id") Serializable id);

    /**
     * Query the set of role IDs owned by the current user
     * @param id
     * @return
     */
    List<Integer> queryUserRoleIdsByUid(@Param("id") Integer id);

    /**
     * Save user and role relationships
     * @param uid User ID
     * @param rid Array of user-owned role IDs
     */
    void insertUserRole(@Param("uid") Integer uid,@Param("rid") Integer rid);
}
