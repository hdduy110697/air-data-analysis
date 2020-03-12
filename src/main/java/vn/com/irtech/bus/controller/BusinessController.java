package vn.com.irtech.bus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Router for business management
 * @Author:  Admin
 * @Date: 2019/12/5 9:33
 */
@Controller
@RequestMapping("bus")
public class BusinessController {

    /**
     * Jump to customer management page
     * @return
     */
    @RequestMapping("toCustomerManager")
    public String toCustomerManager(){
        return "business/customer/customerManager";
    }

    /**
     * Jump to supplier management page
     * @return
     */
    @RequestMapping("toProviderManager")
    public String toProviderManager(){
        return "business/provider/providerManager";
    }

    /**
     * Jump to product management page
     * @return
     */
    @RequestMapping("toGoodsManager")
    public String toGoodsManager(){
        return "business/goods/goodsManager";
    }

    /**
     * Jump to the purchase management page
     * @return
     */
    @RequestMapping("toInportManager")
    public String toInportManager(){
        return "business/inport/inportManager";
    }

    /**
     * Jump to return management page
     * @return
     */
    @RequestMapping("toOutportManager")
    public String toOutportManager(){
        return "business/outport/outportManager";
    }

    /**
     * Jump to product sales management page
     * @return
     */
    @RequestMapping("toSalesManager")
    public String toSalesManager(){
        return "business/sales/salesManager";
    }

    /**
     * Jump to product sales management page
     * @return
     */
    @RequestMapping("toSalesbackManager")
    public String toSalesbackManager(){
        return "business/salesback/salesbackManager";
    }

}
