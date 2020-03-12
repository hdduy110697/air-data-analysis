package vn.com.irtech.bus.service.impl;

import vn.com.irtech.bus.entity.Goods;
import vn.com.irtech.bus.entity.Inport;
import vn.com.irtech.bus.entity.Outport;
import vn.com.irtech.bus.mapper.GoodsMapper;
import vn.com.irtech.bus.mapper.InportMapper;
import vn.com.irtech.bus.mapper.OutportMapper;
import vn.com.irtech.bus.service.IOutportService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import vn.com.irtech.sys.common.WebUtils;
import vn.com.irtech.sys.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author:  Admin
 * @since 2019-12-19
 */
@Service
public class OutportServiceImpl extends ServiceImpl<OutportMapper, Outport> implements IOutportService {

    @Autowired
    private InportMapper inportMapper;

    @Autowired
    private GoodsMapper goodsMapper;

    /**
     * @param id    Incoming note ID
     * @param number    Return quantity
     * @param remark    Note
     */
    @Override
    public void addOutport(Integer id, Integer number, String remark) {
        //1.Query outbound order information by purchase order ID
        Inport inport = inportMapper.selectById(id);
        //2.Query product information based on product ID
        Goods goods = goodsMapper.selectById(inport.getGoodsid());
        //3.Modify the number of items     Number of items-number of returns
        goods.setNumber(goods.getNumber()-number);

        //Modify the quantity received
        inport.setNumber(inport.getNumber()-number);
        inportMapper.updateById(inport);

        //4.modify
        goodsMapper.updateById(goods);

        //5.Add return order information
        Outport outport = new Outport();
        outport.setGoodsid(inport.getGoodsid());
        outport.setNumber(number);
        User user = (User) WebUtils.getSession().getAttribute("user");
        outport.setOperateperson(user.getName());

        outport.setOutportprice(inport.getInportprice());

        outport.setPaytype(inport.getPaytype());
        outport.setOutputtime(new Date());
        outport.setRemark(remark);
        outport.setProviderid(inport.getProviderid());
        getBaseMapper().insert(outport);
    }
}
