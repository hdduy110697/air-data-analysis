package vn.com.irtech.sys.service;

import vn.com.irtech.sys.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *
 * @author:  Admin
 * @since 2019-11-28
 */
public interface IRoleService extends IService<Role> {

    /**
     * Query the menu ID and permission ID owned by the current role according to the role ID
     * @param roleId
     * @return
     */
    List<Integer> queryRolePermissionIdsByRid(Integer roleId);

    /**
     * Save the relationship between roles and menu permissions
     * @param rid
     * @param ids
     */
    void saveRolePermission(Integer rid, Integer[] ids);

    /**
     * Query the set of role IDs owned by the current user
     * @param id
     * @return
     */
    List<Integer> queryUserRoleIdsByUid(Integer id);
}
