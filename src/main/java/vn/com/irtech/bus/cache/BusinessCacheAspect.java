package vn.com.irtech.bus.cache;

import vn.com.irtech.bus.entity.Customer;
import vn.com.irtech.bus.entity.Goods;
import vn.com.irtech.sys.cache.CachePool;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

/**
 * @Author:  Admin
 * @Date: 2019/12/5 16:39
 */
@Aspect
@Component
@EnableAspectJAutoProxy
public class BusinessCacheAspect {
    /**
     * Log source
     */
    private Log log = LogFactory.getLog(BusinessCacheAspect.class);

    /**
     * Declare a cache container
     */
    private Map<String,Object> CACHE_CONTAINER = CachePool.CACHE_CONTAINER;


    /**
     * Declaring a customer's aspect expression
     */
    private static final String POINTCUT_CUSTOMER_ADD="execution(* vn.com.irtech.bus.service.impl.CustomerServiceImpl.save(..))";
    private static final String POINTCUT_CUSTOMER_UPDATE="execution(* vn.com.irtech.bus.service.impl.CustomerServiceImpl.updateById(..))";
    private static final String POINTCUT_CUSTOMER_GET="execution(* vn.com.irtech.bus.service.impl.CustomerServiceImpl.getById(..))";
    private static final String POINTCUT_CUSTOMER_DELETE="execution(* vn.com.irtech.bus.service.impl.CustomerServiceImpl.removeById(..))";
    private static final String POINTCUT_CUSTOMER_BATCHDELETE="execution(* vn.com.irtech.bus.service.impl.CustomerServiceImpl.removeByIds(..))";

    private static final String CACHE_CUSTOMER_PROFIX="customer:";

    /**
     * Add customer cut-in
     * @param joinPoint
     * @return
     */
    @Around(value = POINTCUT_CUSTOMER_ADD)
    public Object cacheCustomerAdd(ProceedingJoinPoint joinPoint) throws Throwable {
        //Take out the first parameter
        Customer object = (Customer) joinPoint.getArgs()[0];
        Boolean res = (Boolean) joinPoint.proceed();
        if (res){
            CACHE_CONTAINER.put(CACHE_CUSTOMER_PROFIX + object.getId(),object);
        }
        return res;
    }

    /**
     * Query customer cut-in
     * @param joinPoint
     * @return
     */
    @Around(value = POINTCUT_CUSTOMER_GET)
    public Object cacheCustomerGet(ProceedingJoinPoint joinPoint) throws Throwable {
        //Take out the first parameter
        Integer object = (Integer) joinPoint.getArgs()[0];
        //Fetch from cache
        Object res1 = CACHE_CONTAINER.get(CACHE_CUSTOMER_PROFIX + object);
        if (res1!=null){
            log.info("Customer object found from cache "+CACHE_CUSTOMER_PROFIX + object);
            return res1;
        }else {
            log.info("The client object was not found from the cache. Query from the database and put it into the cache.");
            Customer res2 =(Customer) joinPoint.proceed();
            CACHE_CONTAINER.put(CACHE_CUSTOMER_PROFIX+res2.getId(),res2);
            return res2;
        }
    }

    /**
     * Update customer cut-in
     * @param joinPoint
     * @return
     */
    @Around(value = POINTCUT_CUSTOMER_UPDATE)
    public Object cacheCustomerUpdate(ProceedingJoinPoint joinPoint) throws Throwable {
        //Take out the first parameter
        Customer customerVo = (Customer) joinPoint.getArgs()[0];
        Boolean isSuccess = (Boolean) joinPoint.proceed();
        if (isSuccess){
            Customer customer =(Customer) CACHE_CONTAINER.get(CACHE_CUSTOMER_PROFIX + customerVo.getId());
            if (null==customer){
                customer=new Customer();
            }
            BeanUtils.copyProperties(customerVo,customer);
            log.info("Client object cache updated "+CACHE_CUSTOMER_PROFIX + customerVo.getId());
            CACHE_CONTAINER.put(CACHE_CUSTOMER_PROFIX+customer.getId(),customer);
        }
        return isSuccess;
    }

    /**
     * Delete customer cut-in
     * @param joinPoint
     * @return
     */
    @Around(value = POINTCUT_CUSTOMER_DELETE)
    public Object cacheCustomerDelete(ProceedingJoinPoint joinPoint) throws Throwable {

        //Take out the first parameter
        Integer id = (Integer) joinPoint.getArgs()[0];
        Boolean isSuccess = (Boolean) joinPoint.proceed();
        if (isSuccess){
            //Delete cache
            CACHE_CONTAINER.remove(CACHE_CUSTOMER_PROFIX+id);
        }
        return isSuccess;
    }

    /**
     * Batch delete customer cut-in
     *
     * @throws Throwable
     */
    @Around(value = POINTCUT_CUSTOMER_BATCHDELETE)
    public Object cacheCustomerBatchDelete(ProceedingJoinPoint joinPoint) throws Throwable {
        // Take out the first parameter
        @SuppressWarnings("unchecked")
        Collection<Serializable> idList = (Collection<Serializable>) joinPoint.getArgs()[0];
        Boolean isSuccess = (Boolean) joinPoint.proceed();
        if (isSuccess) {
            for (Serializable id : idList) {
                // Delete cache
                CACHE_CONTAINER.remove(CACHE_CUSTOMER_PROFIX + id);
                log.info("Customer object cache deleted " + CACHE_CUSTOMER_PROFIX + id);
            }
        }
        return isSuccess;
    }


    /**
     * Facet expressions that declare products
     */
    private static final String POINTCUT_GOODS_ADD="execution(* vn.com.irtech.bus.service.impl.GoodsServiceImpl.save(..))";
    private static final String POINTCUT_GOODS_UPDATE="execution(* vn.com.irtech.bus.service.impl.GoodsServiceImpl.updateById(..))";
    private static final String POINTCUT_GOODS_GET="execution(* vn.com.irtech.bus.service.impl.GoodsServiceImpl.getById(..))";
    private static final String POINTCUT_GOODS_DELETE="execution(* vn.com.irtech.bus.service.impl.GoodsServiceImpl.removeById(..))";

    private static final String CACHE_GOODS_PROFIX="goods:";

    /**
     * Add product cut-in
     * @param joinPoint
     * @return
     */
    @Around(value = POINTCUT_GOODS_ADD)
    public Object cacheGoodsAdd(ProceedingJoinPoint joinPoint) throws Throwable {
        //Take out the first parameter
        Goods object = (Goods) joinPoint.getArgs()[0];
        Boolean res = (Boolean) joinPoint.proceed();
        if (res){
            CACHE_CONTAINER.put(CACHE_GOODS_PROFIX + object.getId(),object);
        }
        return res;
    }

    /**
     * Query product cut-in
     * @param joinPoint
     * @return
     */
    @Around(value = POINTCUT_GOODS_GET)
    public Object cacheGoodsGet(ProceedingJoinPoint joinPoint) throws Throwable {
        //Take out the first parameter
        Integer object = (Integer) joinPoint.getArgs()[0];
        //Fetch from cache
        Object res1 = CACHE_CONTAINER.get(CACHE_GOODS_PROFIX + object);
        if (res1!=null){
            log.info("Item object found from cache "+CACHE_GOODS_PROFIX + object);
            return res1;
        }else {
            log.info("The product object was not found from the cache. Query from the database and put it into the cache");
            Goods res2 =(Goods) joinPoint.proceed();
            CACHE_CONTAINER.put(CACHE_GOODS_PROFIX+res2.getId(),res2);
            return res2;
        }
    }

    /**
     * Update product cut-in
     * @param joinPoint
     * @return
     */
    @Around(value = POINTCUT_GOODS_UPDATE)
    public Object cacheGoodsUpdate(ProceedingJoinPoint joinPoint) throws Throwable {
        //Take out the first parameter
        Goods goodsVo = (Goods) joinPoint.getArgs()[0];
        Boolean isSuccess = (Boolean) joinPoint.proceed();
        if (isSuccess){
            Goods goods =(Goods) CACHE_CONTAINER.get(CACHE_GOODS_PROFIX + goodsVo.getId());
            if (null==goods){
                goods=new Goods();
            }
            BeanUtils.copyProperties(goodsVo,goods);
            log.info("Item object cache updated "+CACHE_GOODS_PROFIX + goodsVo.getId());
            CACHE_CONTAINER.put(CACHE_GOODS_PROFIX+goods.getId(),goods);
        }
        return isSuccess;
    }

    /**
     * Delete entry
     * @param joinPoint
     * @return
     */
    @Around(value = POINTCUT_GOODS_DELETE)
    public Object cacheGoodsDelete(ProceedingJoinPoint joinPoint) throws Throwable {

        //Take out the first parameter
        Integer id = (Integer) joinPoint.getArgs()[0];
        Boolean isSuccess = (Boolean) joinPoint.proceed();
        if (isSuccess){
            //Delete cache
            CACHE_CONTAINER.remove(CACHE_GOODS_PROFIX+id);
        }
        return isSuccess;
    }

    
}
