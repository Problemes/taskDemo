package com.framework.quartz.mapper;

import com.framework.quartz.entities.Goods;
import org.springframework.stereotype.Service;

@Service
public interface GoodsMapper {

    Goods getGoodsById(Long goodsId);
}