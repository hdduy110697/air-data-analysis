package vn.com.irtech.sys.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author:  Admin
 * @Date: 2019/11/21 21:35
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultObj {

    private Integer code;
    private String msg;

    public static final ResultObj LOGIN_SUCCESS=new ResultObj(Constast.OK,"Login Success");
    public static final ResultObj LOGIN_ERROR_PASS=new ResultObj(Constast.ERROR,"Username or password is not correct");
    public static final ResultObj LOGIN_ERROR_CODE=new ResultObj(Constast.ERROR,"Captcha is not correct");

    public static final ResultObj ADD_SUCCESS = new ResultObj(Constast.OK,"Add Success");
    public static final ResultObj ADD_ERROR = new ResultObj(Constast.ERROR,"Failed to add");

    public static final ResultObj DELETE_SUCCESS = new ResultObj(Constast.OK,"Delete Success");
    public static final ResultObj DELETE_ERROR = new ResultObj(Constast.ERROR,"Fail to delete");

    public static final ResultObj UPDATE_SUCCESS = new ResultObj(Constast.OK,"Update Success");
    public static final ResultObj UPDATE_ERROR = new ResultObj(Constast.ERROR,"Fail to update");

    public static final ResultObj RESET_SUCCESS = new ResultObj(Constast.OK,"Reset Success");
    public static final ResultObj RESET_ERROR = new ResultObj(Constast.ERROR,"Fail to reset");

    public static final ResultObj DISPATCH_SUCCESS = new ResultObj(Constast.OK,"Dispatch Success");
    public static final ResultObj DISPATCH_ERROR = new ResultObj(Constast.ERROR,"Fail to dispatch");

    public static final ResultObj BACKINPORT_SUCCESS = new ResultObj(Constast.OK,"Return Success");
    public static final ResultObj BACKINPORT_ERROR = new ResultObj(Constast.ERROR,"Fail to return");
    public static final ResultObj SYNCCACHE_SUCCESS = new ResultObj(Constast.OK,"Sync cache succeeded");

    public static final ResultObj DELETE_ERROR_NEWS = new ResultObj(Constast.ERROR,"Failed to delete the user, the user is a direct leader of another user, please modify the direct leader of the user ’s subordinate, and then delete the user");
    public static final ResultObj DELETE_QUERY = new ResultObj();

}
