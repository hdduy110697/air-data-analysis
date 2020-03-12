package vn.com.irtech.bus.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import vn.com.irtech.bus.entity.Goods;
import vn.com.irtech.bus.entity.Inport;
import vn.com.irtech.bus.entity.Provider;
import vn.com.irtech.bus.service.IGoodsService;
import vn.com.irtech.bus.service.IInportService;
import vn.com.irtech.bus.service.IProviderService;
import vn.com.irtech.bus.vo.InportVo;
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
 * @since 2019-12-18
 */
@RestController
@RequestMapping("inport")
public class InportController {

    @Autowired
    private IInportService inportService;

    @Autowired
    private IProviderService providerService;

    @Autowired
    private IGoodsService goodsService;

    /**
     * Check the merchandise purchase
     * @param inportVo
     * @return
     */
    @RequestMapping("loadAllInport")
    public DataGridView loadAllInport(InportVo inportVo){
        IPage<Inport> page = new Page<Inport>(inportVo.getPage(),inportVo.getLimit());
        QueryWrapper<Inport> queryWrapper = new QueryWrapper<Inport>();
        //Inquiries to suppliers
        queryWrapper.eq(inportVo.getProviderid()!=null&&inportVo.getProviderid()!=0,"providerid",inportVo.getProviderid());
        //Search for products
        queryWrapper.eq(inportVo.getGoodsid()!=null&&inportVo.getGoodsid()!=0,"goodsid",inportVo.getGoodsid());
        //Querying the time requires greater than the start time and less than the end time
        queryWrapper.ge(inportVo.getStartTime()!=null,"inporttime",inportVo.getStartTime());
        queryWrapper.le(inportVo.getEndTime()!=null,"inporttime",inportVo.getEndTime());
        //Sort products by time of arrival
        queryWrapper.orderByDesc("inporttime");
        IPage<Inport> page1 = inportService.page(page, queryWrapper);
        List<Inport> records = page1.getRecords();
        for (Inport inport : records) {
            Provider provider = providerService.getById(inport.getProviderid());
            if (provider!=null){
                //Set vendor name
                inport.setProvidername(provider.getProvidername());
            }
            Goods goods = goodsService.getById(inport.getGoodsid());
            if (goods!=null){
                //Set product name
                inport.setGoodsname(goods.getGoodsname());
                //Set product specifications
                inport.setSize(goods.getSize());
            }
        }
        return new DataGridView(page1.getTotal(),page1.getRecords());
    }


    /**
     * Add stock
     * @param inportVo
     * @return
     */
    @RequestMapping("addInport")
    public ResultObj addInport(InportVo inportVo){
        try {
            //Get current system user
            User user = (User) WebUtils.getSession().getAttribute("user");
            //Set the operator
            inportVo.setOperateperson(user.getName());
            //Set the arrival time
            inportVo.setInporttime(new Date());
            inportService.save(inportVo);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }
    }

    /**
     * Update incoming goods
     * @param inportVo
     * @return
     */
    @RequestMapping("updateInport")
    public ResultObj updateInport(InportVo inportVo){
        try {
            inportService.updateById(inportVo);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }

    }

    /**
     * Delete stock item
     * @param id
     * @return
     */
    @RequestMapping("deleteInport")
    public ResultObj deleteInport(Integer id){
        try {
            inportService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }


}

