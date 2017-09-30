package com.framework.quartz.mapper;

import com.framework.quartz.entities.School;
import org.springframework.stereotype.Service;

@Service
public interface SchoolMapper  {

    School test(Long schId);
}