package vn.com.irtech.sys.controller;

import vn.com.irtech.sys.common.WebUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * The system's routing
 * @Author:  Admin
 * @Date: 2019/11/21 21:19
 */
@Controller
@RequestMapping("sys")
public class SystemController {

    /**
     * Jump to landing page
     * @return
     */
    @RequestMapping("toLogin")
    public String toLogin(){
        return "system/index/login";
    }

    /**
     * Jump to profile page
     * @return
     */
    @RequestMapping("toUserInfo")
    public String toUserInfo(){
        return "system/user/userInfo";
    }

    /**
     * Jump to the change password page
     * @return
     */
    @RequestMapping("toChangePassword")
    public String toChangePassword(){
        return "system/user/changePassword";
    }

    /**
     * Log out and jump to the landing page
     * @return
     */
    @RequestMapping("toSignOut")
    public String toSignOut(){
        //Destroy session
        WebUtils.getSession().removeAttribute("user");
        return "system/index/login";
    }

    /**
     * Go to homepage
     * @return
     */
    @RequestMapping("index")
    public String index(){
        return "system/index/index";
    }

    /**
     * Jump to landing
     * @return
     */
    @RequestMapping("toDeskManager")
    public String toDeskManager(){
        return "system/index/deskManager";
    }

    /**
     * Jump to log management
     * @return
     */
    @RequestMapping("toLoginfoManager")
    public String toLoginfoManager(){
        return "system/loginfo/loginfoManager";
    }

    /**
     * Jump to announcement management
     * @return
     */
    @RequestMapping("toNoticeManager")
    public String toNoticeManager(){
        return "system/notice/noticeManager";
    }

    /**
     * Jump to department management
     * @return
     */
    @RequestMapping("toDeptManager")
    public String toDeptManager(){
        return "system/dept/deptManager";
    }

    /**
     * Jump to department management--left
     * @return
     */
    @RequestMapping("toDeptLeft")
    public String toDeptLeft(){
        return "system/dept/deptLeft";
    }

    /**
     * Jump to department management--right
     * @return
     */
    @RequestMapping("toDeptRight")
    public String toDeptRight(){
        return "system/dept/deptRight";
    }

    /**
     * Jump to menu management
     * @return
     */
    @RequestMapping("toMenuManager")
    public String toMenuManager(){
        return "system/menu/menuManager";
    }

    /**
     * Jump to menu management--left
     * @return
     */
    @RequestMapping("toMenuLeft")
    public String toMenuLeft(){
        return "system/menu/menuLeft";
    }

    /**
     * Jump to menu management--right
     * @return
     */
    @RequestMapping("toMenuRight")
    public String toMenuRight(){
        return "system/menu/menuRight";
    }

    /**
     * Jump to permissions management
     * @return
     */
    @RequestMapping("toPermissionManager")
    public String toPermissionManager(){
        return "system/permission/permissionManager";
    }

    /**
     * Jump to permissions management--left
     * @return
     */
    @RequestMapping("toPermissionLeft")
    public String toPermissionLeft(){
        return "system/permission/permissionLeft";
    }

    /**
     * Jump to permissions management--right
     * @return
     */
    @RequestMapping("toPermissionRight")
    public String toPermissionRight(){
        return "system/permission/permissionRight";
    }

    /**
     * Jump to role management
     * @return
     */
    @RequestMapping("toRoleManager")
    public String toRoleManager(){
        return "system/role/roleManager";
    }

    /**
     * Jump to user management
     * @return
     */
    @RequestMapping("toUserManager")
    public String toUserManager(){
        return "system/user/userManager";
    }

    /**
     * Jump to cache management
     * @return
     */
    @RequestMapping("toCacheManager")
    public String toCacheManager(){
        return "system/cache/cacheManager";
    }

}
