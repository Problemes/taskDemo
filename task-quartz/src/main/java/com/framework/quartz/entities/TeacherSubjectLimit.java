package com.framework.quartz.entities;

import java.io.Serializable;

/**
 * Created by HR on 2017/9/26.
 */
public class TeacherSubjectLimit implements Serializable {

    private Long tchId;
    private Long subId;
    private int limit;

    public Long getTchId() {
        return tchId;
    }

    public void setTchId(Long tchId) {
        this.tchId = tchId;
    }

    public Long getSubId() {
        return subId;
    }

    public void setSubId(Long subId) {
        this.subId = subId;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }


}
