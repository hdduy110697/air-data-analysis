package vn.com.irtech.bus.service.impl;

import vn.com.irtech.bus.entity.Customer;
import vn.com.irtech.bus.mapper.CustomerMapper;
import vn.com.irtech.bus.service.ICustomerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author:  Admin
 * @since 2019-12-05
 */
@Service
@Transactional
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements ICustomerService {

    @Override
    public boolean save(Customer entity) {
        return super.save(entity);
    }

    @Override
    public boolean updateById(Customer entity) {
        return super.updateById(entity);
    }

    @Override
    public boolean removeById(Serializable id) {
        return super.removeById(id);
    }
    @Override
    public Customer getById(Serializable id) {
        return super.getById(id);
    }

    @Override
    public boolean removeByIds(Collection<? extends Serializable> idList) {
        return super.removeByIds(idList);
    }

}
