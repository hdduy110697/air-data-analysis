package vn.com.irtech.bus.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import vn.com.irtech.bus.entity.Goods;
import vn.com.irtech.bus.entity.Outport;
import vn.com.irtech.bus.entity.Provider;
import vn.com.irtech.bus.service.IGoodsService;
import vn.com.irtech.bus.service.IOutportService;
import vn.com.irtech.bus.service.IProviderService;
import vn.com.irtech.bus.vo.OutportVo;
import vn.com.irtech.sys.common.DataGridView;
import vn.com.irtech.sys.common.ResultObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author:  Admin
 * @since 2019-12-19
 */
@RestController
@RequestMapping("/outport")
public class OutportController {

    @Autowired
    private IOutportService outportService;

    @Autowired
    private IProviderService providerService;

    @Autowired
    private IGoodsService goodsService;

    /**
     * Add return information
     * @param id    Incoming note ID
     * @param number    Return quantity
     * @param remark    Note
     * @return
     */
    @RequestMapping("addOutport")
    public ResultObj addOutport(Integer id,Integer number,String remark){
        try {
            outportService.addOutport(id,number,remark);
            return ResultObj.BACKINPORT_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.BACKINPORT_ERROR;
        }
    }

    /**t
     * Query product returns
     * @param outportVo
     * @return
     */
    @RequestMapping("loadAllOutport")
    public DataGridView loadAllOuport(OutportVo outportVo){
        IPage<Outport> page = new Page<Outport>(outportVo.getPage(),outportVo.getLimit());
        QueryWrapper<Outport> queryWrapper = new QueryWrapper<Outport>();
        //Inquiries to suppliers
        queryWrapper.eq(outportVo.getProviderid()!=null&&outportVo.getProviderid()!=0,"providerid",outportVo.getProviderid());
        //Search for products
        queryWrapper.eq(outportVo.getGoodsid()!=null&&outportVo.getGoodsid()!=0,"goodsid",outportVo.getGoodsid());
        //Querying the time requires greater than the start time and less than the end time
        queryWrapper.ge(outportVo.getStartTime()!=null,"outputtime",outportVo.getStartTime());
        queryWrapper.le(outportVo.getEndTime()!=null,"outputtime",outportVo.getEndTime());
        //Sort products by time of arrival
        queryWrapper.orderByDesc("outputtime");
        IPage<Outport> page1 = outportService.page(page, queryWrapper);
        List<Outport> records = page1.getRecords();
        for (Outport ouport : records) {
            Provider provider = providerService.getById(ouport.getProviderid());
            if (provider!=null){
                //Set vendor name
                ouport.setProvidername(provider.getProvidername());
            }
            Goods goods = goodsService.getById(ouport.getGoodsid());
            if (goods!=null){
                //Set product name
                ouport.setGoodsname(goods.getGoodsname());
                //Set product specifications
                ouport.setSize(goods.getSize());
            }
        }
        return new DataGridView(page1.getTotal(),page1.getRecords());
    }

    /**
     * Delete return information
     * @param id
     * @return
     */
    @RequestMapping("deleteOutport")
    public ResultObj deleteOutport(Integer id){
        try {
            outportService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }


}

