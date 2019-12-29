package com.example.demo.Utils;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 乐农学习记录访问类
 * @author 类中域:徐林飞
 */
public class CourseReportQuery implements Serializable {

    private static final long serialVersionUID = 8378684358006141676L;
    private List<String> employeeCodes;
    private List<String> courseCodes;
    private Date startTime;
    private Date endTime;

    public List<String> getEmployeeCodes() {
        return employeeCodes;
    }

    public void setEmployeeCodes(List<String> employeeCodes) {
        this.employeeCodes = employeeCodes;
    }

    public List<String> getCourseCodes() {
        return courseCodes;
    }

    public void setCourseCodes(List<String> courseCodes) {
        this.courseCodes = courseCodes;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
