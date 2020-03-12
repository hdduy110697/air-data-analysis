package vn.com.irtech.bus.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import vn.com.irtech.bus.entity.Customer;
import vn.com.irtech.bus.entity.Goods;
import vn.com.irtech.bus.entity.Salesback;
import vn.com.irtech.bus.service.ICustomerService;
import vn.com.irtech.bus.service.IGoodsService;
import vn.com.irtech.bus.service.ISalesbackService;
import vn.com.irtech.bus.vo.SalesbackVo;
import vn.com.irtech.sys.common.DataGridView;
import vn.com.irtech.sys.common.ResultObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author:  Admin
 * @since 2019-12-23
 */
@RestController
@RequestMapping("/salesback")
public class SalesbackController {

    @Autowired
    private ISalesbackService salesbackService;

    @Autowired
    private ICustomerService customerService;

    @Autowired
    private IGoodsService goodsService;

    /**
     * Add return information
     * @param id    Incoming note ID
     * @param number    Return quantity
     * @param remark    Note
     * @return
     */
    @RequestMapping("addSalesback")
    public ResultObj addSalesback(Integer id,Integer number,String remark){
        try {
            salesbackService.addSalesback(id,number,remark);
            return ResultObj.BACKINPORT_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.BACKINPORT_ERROR;
        }
    }

    /**t
     * Query product sales returns
     * @param salesbackVo
     * @return
     */
    @RequestMapping("loadAllSalesback")
    public DataGridView loadAllSalesback(SalesbackVo salesbackVo){
        IPage<Salesback> page = new Page<Salesback>(salesbackVo.getPage(),salesbackVo.getLimit());
        QueryWrapper<Salesback> queryWrapper = new QueryWrapper<Salesback>();
        //Inquiries from customers
        queryWrapper.eq(salesbackVo.getCustomerid()!=null&&salesbackVo.getCustomerid()!=0,"customerid",salesbackVo.getCustomerid());
        //Search for products
        queryWrapper.eq(salesbackVo.getGoodsid()!=null&&salesbackVo.getGoodsid()!=0,"goodsid",salesbackVo.getGoodsid());
        //Querying the time requires greater than the start time and less than the end time
        queryWrapper.ge(salesbackVo.getStartTime()!=null,"salesbacktime",salesbackVo.getStartTime());
        queryWrapper.le(salesbackVo.getEndTime()!=null,"salesbacktime",salesbackVo.getEndTime());
        //Sort items by product return time
        queryWrapper.orderByDesc("salesbacktime");
        salesbackService.page(page, queryWrapper);
        List<Salesback> records = page.getRecords();
        for (Salesback salesback : records) {
            // System.out.println("============================");
            Customer customer = customerService.getById(salesback.getCustomerid());
            if (customer!=null){
                //Set customer name
                salesback.setCustomername(customer.getCustomername());
            }
            Goods goods = goodsService.getById(salesback.getGoodsid());
            if (goods!=null){
                //Set product name
                salesback.setGoodsname(goods.getGoodsname());
                //Set product specifications
                salesback.setSize(goods.getSize());
            }
        }
        return new DataGridView(page.getTotal(),page.getRecords());
    }

    /**
     * Delete product sales return information
     * @param id
     * @return
     */
    @RequestMapping("deleteSalesback")
    public ResultObj deleteSalesback(Integer id){
        try {
            salesbackService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }
    
}

