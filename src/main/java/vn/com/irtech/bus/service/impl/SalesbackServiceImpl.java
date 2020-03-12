package vn.com.irtech.bus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import vn.com.irtech.bus.entity.Goods;
import vn.com.irtech.bus.entity.Sales;
import vn.com.irtech.bus.entity.Salesback;
import vn.com.irtech.bus.mapper.GoodsMapper;
import vn.com.irtech.bus.mapper.SalesMapper;
import vn.com.irtech.bus.mapper.SalesbackMapper;
import vn.com.irtech.bus.service.ISalesbackService;
import vn.com.irtech.sys.common.WebUtils;
import vn.com.irtech.sys.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author:  Admin
 * @since 2019-12-23
 */
@Service
public class SalesbackServiceImpl extends ServiceImpl<SalesbackMapper, Salesback> implements ISalesbackService {

    @Autowired
    private SalesMapper salesMapper;

    @Autowired
    private GoodsMapper goodsMapper;

    /**
     * @param id    Sales order ID
     * @param number    Return quantity
     * @param remark    Note
     */
    @Override
    public void addSalesback(Integer id, Integer number, String remark) {
        //1.Query sales order information by sales order ID
        Sales sales = salesMapper.selectById(id);
        //2.Query product information based on product ID
        Goods goods = goodsMapper.selectById(sales.getGoodsid());
        //3.Modify the quantity of the product
        goods.setNumber(goods.getNumber()+number);

        //Modify the quantity received
        sales.setNumber(sales.getNumber()-number);
        salesMapper.updateById(sales);

        //4.to modify
        goodsMapper.updateById(goods);

        //5.Add return order information
        Salesback salesback = new Salesback();
        salesback.setGoodsid(sales.getGoodsid());

        salesback.setNumber(number);
        User user = (User) WebUtils.getSession().getAttribute("user");
        salesback.setOperateperson(user.getName());


        salesback.setSalebackprice(sales.getSaleprice());
        salesback.setPaytype(sales.getPaytype());

        salesback.setSalesbacktime(new Date());
        salesback.setRemark(remark);


        salesback.setCustomerid(sales.getCustomerid());


        getBaseMapper().insert(salesback);
    }
    
}
