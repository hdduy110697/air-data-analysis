package vn.com.irtech.sys.controller;

import vn.com.irtech.sys.cache.CachePool;
import vn.com.irtech.sys.common.CacheBean;
import vn.com.irtech.sys.common.DataGridView;
import vn.com.irtech.sys.common.ResultObj;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Cache management controller
 * @Author:  Admin
 * @Date: 2019/12/20 18:36
 */
@RestController
@RequestMapping("cache")
public class CacheController {

    private static volatile Map<String,Object> CACHE_CONTAINER= CachePool.CACHE_CONTAINER;

    /**
     * Query all caches
     * @return
     */
    @RequestMapping("loadAllCache")
    public DataGridView loadAllCache(){
        List<CacheBean> list = new ArrayList<>();

        Set<Map.Entry<String, Object>> entrySet = CACHE_CONTAINER.entrySet();
        for (Map.Entry<String, Object> entry : entrySet) {
            list.add(new CacheBean(entry.getKey(),entry.getValue()));
        }
        return new DataGridView(list);
    }

    /**
     * Delete cache
     * @param key
     * @return
     */
    @RequestMapping("deleteCache")
    public ResultObj deleteCache(String key){
        CachePool.removeCacheByKey(key);
        return ResultObj.DELETE_SUCCESS;
    }

    /**
     * Clear all caches
     * @return
     */
    @RequestMapping("removeAllCache")
    public ResultObj removeAllCache(){
        CachePool.removeAll();
        return ResultObj.DELETE_SUCCESS;
    }

    /**
     * Synchronous cache
     * @return
     */
    @RequestMapping("syncCache")
    public ResultObj syncCache(){
        CachePool.syncData();
        return ResultObj.SYNCCACHE_SUCCESS;
    }

}
