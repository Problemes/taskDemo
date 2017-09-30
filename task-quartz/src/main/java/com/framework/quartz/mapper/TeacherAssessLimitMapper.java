package com.framework.quartz.mapper;


import com.framework.quartz.entities.TeacherSubjectLimit;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TeacherAssessLimitMapper {

    /**
     * 获取有效的老师学科评价限制
     * @return
     */
    List<TeacherSubjectLimit> getTchLimits();
}