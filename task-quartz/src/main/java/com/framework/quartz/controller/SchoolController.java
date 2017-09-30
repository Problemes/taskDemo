package com.framework.quartz.controller;

import com.framework.quartz.entities.Goods;
import com.framework.quartz.entities.School;
import com.framework.quartz.service.GoodsService;
import com.framework.quartz.service.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by HR on 2017/9/19.
 */
@Controller
@RequestMapping("test")
public class SchoolController {

    @Autowired
    private SchoolService schoolService;

    @Autowired
    private GoodsService goodsService;

    @RequestMapping("/test")
    public String testSchoolController(Model model, Long schId){

        School school = schoolService.testmy(schId);
        model.addAttribute("school",school);

        return "success";
    }

    @RequestMapping("/goods")
    public String testGoodsBecauseMultiDS(Model model, Long goodsId){

        Goods goods = goodsService.getGoods(goodsId);
        model.addAttribute("goods",goods);

        return "success";
    }



}
