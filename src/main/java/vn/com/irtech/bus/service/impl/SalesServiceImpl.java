package vn.com.irtech.bus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import vn.com.irtech.bus.entity.Goods;
import vn.com.irtech.bus.entity.Sales;
import vn.com.irtech.bus.mapper.GoodsMapper;
import vn.com.irtech.bus.mapper.SalesMapper;
import vn.com.irtech.bus.service.ISalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * @author:  Admin
 * @since 2019-12-21
 */
@Service
public class SalesServiceImpl extends ServiceImpl<SalesMapper, Sales> implements ISalesService {

    @Autowired
    private GoodsMapper goodsMapper;

    /**
     * Add product sales
     * @param entity    Commodity sales entity class
     * @return
     */
    @Override
    public boolean save(Sales entity) {
        Goods goods = goodsMapper.selectById(entity.getGoodsid());
        goods.setNumber(goods.getNumber()-entity.getNumber());
        //Update inventory information
        goodsMapper.updateById(goods);
        return super.save(entity);
    }

    /**
     * Update product sales
     * @param entity    Commodity sales entity class
     * @return
     */
    @Override
    public boolean updateById(Sales entity) {
        //Query sales order information based on sales order ID
        Sales sales = baseMapper.selectById(entity.getId());
        Goods goods = goodsMapper.selectById(entity.getGoodsid());
        //Warehouse product quantity = original inventory-quantity before sales order modification + quantity after modification
        //     80  +40 -  50     30
        goods.setNumber(goods.getNumber()+sales.getNumber()-entity.getNumber());
        //Update product
        goodsMapper.updateById(goods);
        return super.updateById(entity);
    }

    /**
     * Delete product sales information
     * @param id    Merchandise sales order ID
     * @return
     */
    @Override
    public boolean removeById(Serializable id) {
        //Query sales order data based on product sales order ID
        Sales sales = baseMapper.selectById(id);
        Goods goods = goodsMapper.selectById(sales.getGoodsid());
        //Number of warehouse products = original inventory + quantity of sales order deleted
        goods.setNumber(goods.getNumber()+sales.getNumber());
        goodsMapper.updateById(goods);
        return super.removeById(id);
    }
}
