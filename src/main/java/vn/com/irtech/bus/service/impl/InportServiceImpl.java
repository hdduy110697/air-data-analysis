package vn.com.irtech.bus.service.impl;

import vn.com.irtech.bus.entity.Goods;
import vn.com.irtech.bus.entity.Inport;
import vn.com.irtech.bus.mapper.GoodsMapper;
import vn.com.irtech.bus.mapper.InportMapper;
import vn.com.irtech.bus.service.IInportService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

/**
 * @author:  Admin
 * @since 2019-12-18
 */
@Service
@Transactional
public class InportServiceImpl extends ServiceImpl<InportMapper, Inport> implements IInportService {

    @Autowired
    private GoodsMapper goodsMapper;

    /**
     * Save merchandise
     * @param entity
     * @return
     */
    @Override
    public boolean save(Inport entity) {
        //Find products by product ID
        Goods goods = goodsMapper.selectById(entity.getGoodsid());
        goods.setNumber(goods.getNumber()+entity.getNumber());
        goodsMapper.updateById(goods);
        //Save stock information
        return super.save(entity);
    }

    /**
     * Update product arrival
     * @param entity
     * @return
     */
    @Override
    public boolean updateById(Inport entity) {
        //Query purchase information based on purchase ID
        Inport inport = baseMapper.selectById(entity.getId());
        //Query product information based on product ID
        Goods goods = goodsMapper.selectById(entity.getGoodsid());
        //Inventory algorithm Current inventory-quantity before modification of purchase order + quantity after modification
        goods.setNumber(goods.getNumber()-inport.getNumber()+entity.getNumber());
        goodsMapper.updateById(goods);
        //Update purchase order
        return super.updateById(entity);
    }

    /**
     * Delete product purchase information
     * @param id
     * @return
     */
    @Override
    public boolean removeById(Serializable id) {
        //Query purchase information based on purchase ID
        Inport inport = baseMapper.selectById(id);
        //Query product information based on product ID
        Goods goods = goodsMapper.selectById(inport.getGoodsid());
        //Inventory Algorithm Current Inventory-Receiving Order Quantity
        goods.setNumber(goods.getNumber()-inport.getNumber());
        goodsMapper.updateById(goods);
        //Update the number of items
        return super.removeById(id);
    }
}
