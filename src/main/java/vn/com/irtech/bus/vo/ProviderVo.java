package vn.com.irtech.bus.vo;

import vn.com.irtech.bus.entity.Customer;
import vn.com.irtech.bus.entity.Provider;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author:  Admin
 * @Date: 2019/12/5 9:30
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ProviderVo extends Provider{

    private Integer page=1;
    private Integer limit=10;

    /**
     * Delete suppliers in batches and store an array of supplier IDs
     */
    private Integer[] ids;

}
