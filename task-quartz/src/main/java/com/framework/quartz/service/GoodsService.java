package com.framework.quartz.service;

import com.framework.quartz.entities.Goods;
import com.framework.quartz.multiDataSource.DataSource;
import org.springframework.stereotype.Service;

/**
 * Created by HR on 2017/9/21.
 */
@Service
@DataSource("eval")
public interface GoodsService {
    Goods getGoods(Long goodsId);
}
