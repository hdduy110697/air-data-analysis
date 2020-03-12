package vn.com.irtech.sys.service;

import vn.com.irtech.sys.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 *
 * @author:  Admin
 * @since 2019-11-21
 */
public interface IUserService extends IService<User> {

    /**
     * Save user and role relationships
     * @param uid User ID
     * @param ids Array of user-owned role IDs
     */
    void saveUserRole(Integer uid, Integer[] ids);

    /**
     * Query if the current user is a direct leader of another user
     * @param userId
     * @return
     */
    Boolean queryMgrByUserId(Integer userId);
}
