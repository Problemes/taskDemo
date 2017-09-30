package com.framework.quartz.service.impl;

import com.framework.quartz.entities.School;
import com.framework.quartz.mapper.SchoolMapper;
import com.framework.quartz.service.SchoolService;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by HR on 2017/9/19.
 */
@Service
public class SchoolServiceImpl implements SchoolService {

    @Autowired
    private SchoolMapper schoolMapper;

    @Override
    public School testmy(Long schId) {
        School school = schoolMapper.test(schId);

        System.out.println("SchoolInfo : " + JSONArray.fromObject(school));
        return school;
    }
}
