package com.framework.quartz.service.impl;

import com.framework.quartz.entities.TeacherSubjectLimit;
import com.framework.quartz.mapper.TeacherAssessLimitMapper;
import com.framework.quartz.service.TeacherAssessLimitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherAssessLimitServiceImpl implements TeacherAssessLimitService {

    @Autowired
    private TeacherAssessLimitMapper teacherAssessLimitMapper;

    @Override
    public List<TeacherSubjectLimit> getTchSubLimits() {
        return teacherAssessLimitMapper.getTchLimits();
    }
}
