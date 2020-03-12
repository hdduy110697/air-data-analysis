package vn.com.irtech.sys.common;

/**
 * @Author:  Admin
 * @Date: 2019/11/21 21:39
 */
public class Constast {

    /**
     * Status code OK 200 Error -1
     */
    public static final Integer OK=200;
    public static final Integer ERROR=-1;

    /**
     * User default password
     */
    public static final String USER_DEFAULT_PWD="123456";

    /**
     * Menu available status 0 Not available 1 Available
     */
    public static final Object AVAILABLE_TRUE = 1;
    public static final Object AVAILABLE_FALSE = 0;

    /**
     * Menus and permission types menu menu permission
     */
    public static final String TYPE_MENU = "menu";
    public static final String TYPE_PERMISSION = "permission";

    /**
     * User type 0 Super administrator 1 System user
     */
    public static final Integer USER_TYPE_SUPER = 0;
    public static final Integer USER_TYPE_NORMAL = 1;

    /**
     * Whether the menu is expanded 0 Do not expand 1 Expand
     */
    public static final Integer OPEN_TRUE = 1;
    public static final Integer OPEN_FALSE = 0;

    /**
     * Product default picture
     */
    public static final String DEFAULT_IMG_GOODS = "/images/noDefaultImage.jpg";

    /**
     * hash hash times
     */
    public static final Integer HASHITERATIONS = 2;

    /**
     * User default picture
     */
    public static final String DEFAULT_IMG_USER="/images/defaultusertitle.jpg";
}
