package com.framework.quartz.service;

import com.framework.quartz.entities.TeacherSubjectLimit;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TeacherAssessLimitService {

    List<TeacherSubjectLimit> getTchSubLimits();
}
