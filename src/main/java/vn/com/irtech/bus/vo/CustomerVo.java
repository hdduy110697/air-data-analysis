package vn.com.irtech.bus.vo;

import vn.com.irtech.bus.entity.Customer;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author:  Admin
 * @Date: 2019/12/5 9:30
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CustomerVo extends Customer{

    private Integer page=1;
    private Integer limit=10;

    /**
     * Delete customers in batches and store an array of customer IDs
     */
    private Integer[] ids;

}
