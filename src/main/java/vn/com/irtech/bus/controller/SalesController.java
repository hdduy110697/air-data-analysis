package vn.com.irtech.bus.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import vn.com.irtech.bus.entity.Customer;
import vn.com.irtech.bus.entity.Goods;
import vn.com.irtech.bus.entity.Sales;
import vn.com.irtech.bus.service.ICustomerService;
import vn.com.irtech.bus.service.IGoodsService;
import vn.com.irtech.bus.service.ISalesService;
import vn.com.irtech.bus.vo.SalesVo;
import vn.com.irtech.sys.common.DataGridView;
import vn.com.irtech.sys.common.ResultObj;
import vn.com.irtech.sys.common.WebUtils;
import vn.com.irtech.sys.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * @author:  Admin
 * @since 2019-12-21
 */
@RestController
@RequestMapping("/sales")
public class SalesController {

    @Autowired
    private ISalesService salesService;

    @Autowired
    private ICustomerService customerService;

    @Autowired
    private IGoodsService goodsService;

    /**
     * Query all product sales information
     * @param salesVo
     * @return
     */
    @RequestMapping("loadAllSales")
    public DataGridView loadAllSales(SalesVo salesVo){
        IPage<Sales> page = new Page<>(salesVo.getPage(),salesVo.getLimit());
        QueryWrapper<Sales> queryWrapper = new QueryWrapper<Sales>();
        //Fuzzy query by customer
        queryWrapper.eq(salesVo.getCustomerid()!=null&&salesVo.getCustomerid()!=0,"customerid",salesVo.getCustomerid());
        //Fuzzy query by product
        queryWrapper.eq(salesVo.getGoodsid()!=null&&salesVo.getGoodsid()!=0,"goodsid",salesVo.getGoodsid());
        //Fuzzy query based on time
        queryWrapper.ge(salesVo.getStartTime()!=null,"salestime",salesVo.getStartTime());
        queryWrapper.le(salesVo.getEndTime()!=null,"salestime",salesVo.getEndTime());
        IPage<Sales> page1 = salesService.page(page, queryWrapper);
        List<Sales> records = page1.getRecords();
        for (Sales sales : records) {
            //Set customer name
            Customer customer = customerService.getById(sales.getCustomerid());
            sales.setCustomername(customer.getCustomername());
            //Set product name
            Goods goods = goodsService.getById(sales.getGoodsid());
            sales.setGoodsname(goods.getGoodsname());
            //Set product specifications
            sales.setSize(goods.getSize());
        }
        return new DataGridView(page1.getTotal(),page1.getRecords());
    }


    /**
     * Add product sales information
     * @param salesVo
     * @return
     */
    @RequestMapping("addSales")
    public ResultObj addSales(SalesVo salesVo){
        try {
            //Get current system user
            User user = (User) WebUtils.getSession().getAttribute("user");
            //Set the operator
            salesVo.setOperateperson(user.getName());
            //Set sales hours
            salesVo.setSalestime(new Date());
            salesService.save(salesVo);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }
    }

    /**
     * Update product sales information
     * @param salesVo
     * @return
     */
    @RequestMapping("updateSales")
    public ResultObj updateSales(SalesVo salesVo){
        try {
            salesService.updateById(salesVo);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }

    }

    /**
     * Delete product sales information
     * @param id
     * @return
     */
    @RequestMapping("deleteSales")
    public ResultObj deleteSales(Integer id){
        try {
            salesService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }

}

