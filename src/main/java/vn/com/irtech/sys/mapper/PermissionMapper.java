package vn.com.irtech.sys.mapper;

import vn.com.irtech.sys.entity.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;

/**
 *
 * @author:  Admin
 * @since 2019-11-22
 */
public interface PermissionMapper extends BaseMapper<Permission> {

    /**
     * Delete the data in the sys_role_permission table according to the permission ID or menu ID
     * @param id
     */
    void deleteRolePermissionByPid(@Param("id") Serializable id);
}
