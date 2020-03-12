package vn.com.irtech.sys.cache;

import vn.com.irtech.sys.entity.Dept;
import vn.com.irtech.sys.entity.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author:  Admin
 * @Date: 2019/11/27 18:42
 */
@Aspect
@Component
@EnableAspectJAutoProxy
public class CacheAspect {

    /**
     * Log source
     */
    private Log log = LogFactory.getLog(CacheAspect.class);

    /**
     * Declare a cache container
     */
    private Map<String,Object> CACHE_CONTAINER = CachePool.CACHE_CONTAINER;


    /**
     * Aspect expressions declaring departments
     */
    private static final String POINTCUT_DEPT_ADD="execution(* vn.com.irtech.sys.service.impl.DeptServiceImpl.save(..))";
    private static final String POINTCUT_DEPT_UPDATE="execution(* vn.com.irtech.sys.service.impl.DeptServiceImpl.updateById(..))";
    private static final String POINTCUT_DEPT_GET="execution(* vn.com.irtech.sys.service.impl.DeptServiceImpl.getById(..))";
    private static final String POINTCUT_DEPT_DELETE="execution(* vn.com.irtech.sys.service.impl.DeptServiceImpl.removeById(..))";

    private static final String CACHE_DEPT_PROFIX="dept:";

    /**
     * Add department cut-in
     * @param joinPoint
     * @return
     */
    @Around(value = POINTCUT_DEPT_ADD)
    public Object cacheDeptAdd(ProceedingJoinPoint joinPoint) throws Throwable {
        //Take out the first parameter
        Dept object = (Dept) joinPoint.getArgs()[0];
        Boolean res = (Boolean) joinPoint.proceed();
        if (res){
            CACHE_CONTAINER.put(CACHE_DEPT_PROFIX + object.getId(),object);
        }
        return res;
    }

    /**
     * Query department cut-in
     * @param joinPoint
     * @return
     */
    @Around(value = POINTCUT_DEPT_GET)
    public Object cacheDeptGet(ProceedingJoinPoint joinPoint) throws Throwable {
        //Take out the first parameter
        Integer object = (Integer) joinPoint.getArgs()[0];
        //Fetch from cache
        Object res1 = CACHE_CONTAINER.get(CACHE_DEPT_PROFIX + object);
        if (res1!=null){
            log.info("Department object found from cache "+CACHE_DEPT_PROFIX + object);
            return res1;
        }else {
            log.info("Department object not found from cache, query from database and put into cache");
            Dept res2 =(Dept) joinPoint.proceed();
            CACHE_CONTAINER.put(CACHE_DEPT_PROFIX+res2.getId(),res2);
            return res2;
        }
    }

    /**
     * Update department cut-in
     * @param joinPoint
     * @return
     */
    @Around(value = POINTCUT_DEPT_UPDATE)
    public Object cacheDeptUpdate(ProceedingJoinPoint joinPoint) throws Throwable {
        //Take out the first parameter
        Dept deptVo = (Dept) joinPoint.getArgs()[0];
        Boolean isSuccess = (Boolean) joinPoint.proceed();
        if (isSuccess){
            Dept dept =(Dept) CACHE_CONTAINER.get(CACHE_DEPT_PROFIX + deptVo.getId());
            if (null==dept){
                dept=new Dept();
            }
            BeanUtils.copyProperties(deptVo,dept);
            log.info("Department object cache updated "+CACHE_DEPT_PROFIX + deptVo.getId());
            CACHE_CONTAINER.put(CACHE_DEPT_PROFIX+dept.getId(),dept);
        }
        return isSuccess;
    }

    /**
     * Delete department cut-in
     * @param joinPoint
     * @return
     */
    @Around(value = POINTCUT_DEPT_DELETE)
    public Object cacheDeptDelete(ProceedingJoinPoint joinPoint) throws Throwable {

        //Take out the first parameter
        Integer id = (Integer) joinPoint.getArgs()[0];
        Boolean isSuccess = (Boolean) joinPoint.proceed();
        if (isSuccess){
            //Delete cache
            CACHE_CONTAINER.remove(CACHE_DEPT_PROFIX+id);
        }
        return isSuccess;
    }

    /**
     * Declaring a user's aspect expression
     */
    private static final String POINTCUT_USER_UPDATE="execution(* vn.com.irtech.sys.service.impl.UserServiceImpl.updateById(..))";
    private static final String POINTCUT_USER_ADD="execution(* vn.com.irtech.sys.service.impl.UserServiceImpl.updateById(..))";
    private static final String POINTCUT_USER_GET="execution(* vn.com.irtech.sys.service.impl.UserServiceImpl.getById(..))";
    private static final String POINTCUT_USER_DELETE="execution(* vn.com.irtech.sys.service.impl.UserServiceImpl.removeById(..))";

    private static final String CACHE_USER_PROFIX="user:";

    /**
     * Add user cut-in
     * @param joinPoint
     * @return
     */
    @Around(value = POINTCUT_USER_ADD)
    public Object cacheUserAdd(ProceedingJoinPoint joinPoint) throws Throwable {
        //Take out the first parameter
        User object = (User) joinPoint.getArgs()[0];
        Boolean res = (Boolean) joinPoint.proceed();
        if (res){
            CACHE_CONTAINER.put(CACHE_USER_PROFIX + object.getId(),object);
        }
        return res;
    }

    /**
     * Query user cut-in
     * @param joinPoint
     * @return
     */
    @Around(value = POINTCUT_USER_GET)
    public Object cacheUserGet(ProceedingJoinPoint joinPoint) throws Throwable {
        //Take out the first parameter
        Integer object = (Integer) joinPoint.getArgs()[0];
        //Fetch from cache
        Object res1 = CACHE_CONTAINER.get(CACHE_USER_PROFIX + object);
        if (res1!=null){
            log.info("User object found from cache "+CACHE_USER_PROFIX + object);
            return res1;
        }else {
            log.info("The user object was not found from the cache. Query from the database and put it into the cache.");
            User res2 =(User) joinPoint.proceed();
            CACHE_CONTAINER.put(CACHE_USER_PROFIX+res2.getId(),res2);
            return res2;
        }
    }

    /**
     * Update user cut-in
     * @param joinPoint
     * @return
     */
    @Around(value = POINTCUT_USER_UPDATE)
    public Object cacheUserUpdate(ProceedingJoinPoint joinPoint) throws Throwable {
        //Take out the first parameter
        User userVo = (User) joinPoint.getArgs()[0];
        Boolean isSuccess = (Boolean) joinPoint.proceed();
        if (isSuccess){
            User user =(User) CACHE_CONTAINER.get(CACHE_USER_PROFIX + userVo.getId());
            if (null==user){
                user=new User();
            }
            BeanUtils.copyProperties(userVo,user);
            log.info("User object cache updated "+CACHE_USER_PROFIX + userVo.getId());
            CACHE_CONTAINER.put(CACHE_USER_PROFIX+user.getId(),user);
        }
        return isSuccess;
    }

    /**
     * Delete user cut-in
     * @param joinPoint
     * @return
     */
    @Around(value = POINTCUT_USER_DELETE)
    public Object cacheUserDelete(ProceedingJoinPoint joinPoint) throws Throwable {

        //Take out the first parameter
        Integer id = (Integer) joinPoint.getArgs()[0];
        Boolean isSuccess = (Boolean) joinPoint.proceed();
        if (isSuccess){
            //Delete cache
            CACHE_CONTAINER.remove(CACHE_USER_PROFIX+id);
        }
        return isSuccess;
    }

}
