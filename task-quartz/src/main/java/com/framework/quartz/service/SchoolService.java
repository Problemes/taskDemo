package com.framework.quartz.service;

import com.framework.quartz.entities.School;
import org.springframework.stereotype.Service;

/**
 * Created by HR on 2017/9/19.
 */
@Service
public interface SchoolService {

    School testmy(Long schId);
}
