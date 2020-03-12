package vn.com.irtech.bus.service;

import vn.com.irtech.bus.entity.Outport;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author:  Admin
 * @since 2019-12-19
 */
public interface IOutportService extends IService<Outport> {

    /**
     * Return processing of goods received
     * @param id    Incoming note ID
     * @param number    Return quantity
     * @param remark    Note
     */
    void addOutport(Integer id, Integer number, String remark);
}
