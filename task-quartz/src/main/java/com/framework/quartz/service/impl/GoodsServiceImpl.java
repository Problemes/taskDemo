package com.framework.quartz.service.impl;

import com.framework.quartz.entities.Goods;
import com.framework.quartz.mapper.GoodsMapper;
import com.framework.quartz.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by HR on 2017/9/21.
 */
@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public Goods getGoods(Long goodsId) {
        return goodsMapper.getGoodsById(goodsId);
    }
}
