package vn.com.irtech.sys.cache;

import vn.com.irtech.bus.entity.Customer;
import vn.com.irtech.bus.entity.Goods;
import vn.com.irtech.bus.entity.Provider;
import vn.com.irtech.bus.mapper.CustomerMapper;
import vn.com.irtech.bus.mapper.GoodsMapper;
import vn.com.irtech.bus.mapper.ProviderMapper;
import vn.com.irtech.sys.common.SpringUtil;
import vn.com.irtech.sys.entity.Dept;
import vn.com.irtech.sys.entity.User;
import vn.com.irtech.sys.mapper.DeptMapper;
import vn.com.irtech.sys.mapper.UserMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author:  Admin
 * @Date: 2019/12/20 18:05
 */
public class CachePool {

    /**
     * Put all cached data into this CACHE_CONTAINER similar to redis
     */
    public static volatile Map<String,Object> CACHE_CONTAINER = new HashMap<>();

    /**
     * Delete cache based on KEY
     * @param key   Key
     */
    public static void removeCacheByKey(String key){
        if (CACHE_CONTAINER.containsKey(key)){
            CACHE_CONTAINER.remove(key);
        }
    }

    /**
     * Clear all caches
     */
    public static void removeAll(){
        CACHE_CONTAINER.clear();
    }

    /**
     * Synchronous cache
     */
    public static void syncData(){
        //Sync department data
        DeptMapper deptMapper = SpringUtil.getBean(DeptMapper.class);
        List<Dept> deptList = deptMapper.selectList(null);
        for (Dept dept : deptList) {
            CACHE_CONTAINER.put("dept:"+dept.getId(),dept);
        }
        //Sync user data
        UserMapper userMapper = SpringUtil.getBean(UserMapper.class);
        List<User> userList = userMapper.selectList(null);
        for (User user : userList) {
            CACHE_CONTAINER.put("user:"+user.getId(),user);
        }

        //Sync customer data
        CustomerMapper customerMapper = SpringUtil.getBean(CustomerMapper.class);
        List<Customer> customerList = customerMapper.selectList(null);
        for (Customer customer : customerList) {
            CACHE_CONTAINER.put("customer:"+customer.getId(),customer);
        }

        //Synchronize supplier data
        ProviderMapper providerMapper = SpringUtil.getBean(ProviderMapper.class);
        List<Provider> providerList = providerMapper.selectList(null);
        for (Provider provider : providerList) {
            CACHE_CONTAINER.put("provider:"+provider.getId(),provider);
        }

        //Sync product data
        GoodsMapper goodsMapper = SpringUtil.getBean(GoodsMapper.class);
        List<Goods> goodsList = goodsMapper.selectList(null);
        for (Goods goods : goodsList) {
            CACHE_CONTAINER.put("goods:"+goods.getId(),goods);
        }


    }


}
