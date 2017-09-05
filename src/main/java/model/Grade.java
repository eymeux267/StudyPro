package model;

import java.io.Serializable;

/**
 * Created by charbel pc on 2016-07-09.
 */
public class Grade implements Serializable{
    private double grade_;
    private double ponderation_;
    private String gradeName_;
    private String gradeDate_;
    private String gradeCourse_;
    private int gradeId_;

    private static final long serialVersionUID = 10L;

    public Grade() {
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public double getGrade_() {
        return grade_;
    }

    public void setGrade_(double grade_) {
        this.grade_ = grade_;
    }

    public double getPonderation_() {
        return ponderation_;
    }

    public void setPonderation_(double ponderation_) {
        this.ponderation_ = ponderation_;
    }

    public String getGradeName_() {
        return gradeName_;
    }

    public void setGradeName_(String gradeName_) {
        this.gradeName_ = gradeName_;
    }

    public String getGradeDate_() {
        return gradeDate_;
    }

    public void setGradeDate_(String gradeDate_) {
        this.gradeDate_ = gradeDate_;
    }

    public String getGradeCourse_() {
        return gradeCourse_;
    }

    public void setGradeCourse_(String gradeCourse_) {
        this.gradeCourse_ = gradeCourse_;
    }

    public int getGradeId_() {
        return gradeId_;
    }

    public void setGradeId_(int gradeId_) {
        this.gradeId_ = gradeId_;
    }
}

