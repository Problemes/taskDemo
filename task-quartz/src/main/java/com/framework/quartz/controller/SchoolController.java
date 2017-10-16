package com.framework.quartz.controller;

import com.framework.quartz.entities.Goods;
import com.framework.quartz.entities.School;
import com.framework.quartz.rabbitmq.MessageProducer;
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

    @Autowired
    MessageProducer messageProducer;

    @RequestMapping("/test")
    public String testSchoolController(Model model, Long schId){

        School school = schoolService.testmy(schId);
        model.addAttribute("school",school);


        //MessageProducer messageProducer = new MessageProducer();
        int x = 10;

        while (x > 0){
            messageProducer.sendMessage("Hello, I am amq sender num :" + x--);
            try {
                //暂停一下，好让消息消费者去取消息打印出来
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return "success";
    }

    @RequestMapping("/goods")
    public String testGoodsBecauseMultiDS(Model model, Long goodsId){

        Goods goods = goodsService.getGoods(goodsId);
        model.addAttribute("goods",goods);

        return "success";
    }



}
