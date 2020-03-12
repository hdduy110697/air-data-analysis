package vn.com.irtech.bus.service;

import vn.com.irtech.bus.entity.Salesback;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author:  Admin
 * @since 2019-12-23
 */
public interface ISalesbackService extends IService<Salesback> {

    /**
     * Returns processing for product sales
     * @param id    Sales order ID
     * @param number    Return quantity
     * @param remark    Note
     */
    void addSalesback(Integer id, Integer number, String remark);

}
