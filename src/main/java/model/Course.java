package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by charbel pc on 2016-07-09.
 */

// Serializable so that we can send it as extra in  an intent.
public class Course implements Serializable {
    private static final long serialVersionUID = 10L; // Ce shit sert a "freeze le class pr le changer de activity"

    // Attributes.
    private int courseId_;      // So that we can delete it from the database.
    private String courseName_;
    private String courseAbbreviation_; // INF1600.
    private double currentGrade_;
    private ArrayList<Grade> gradeList_;
    private double passingGrade_;
    private double gradeNeededToPass_;
    private String courseLocation_;


    public Course() {
    }

    // grade needed to pass and course abbreviation are optional.
    public Course(int id, String courseName, String courseAbbreviation, double passingGrade)
    {
        courseId_ = id;
        courseName_ = courseName;
        courseAbbreviation_ = courseAbbreviation;
        passingGrade_ = passingGrade;
        currentGrade_ = 0;
        gradeNeededToPass_ = 0;

    }

    public Course(int id, String courseName, double passingGrade)
    {
        courseId_ = id;
        courseName_ = courseName;
        courseAbbreviation_ = "";
        passingGrade_ = passingGrade;
        currentGrade_ = 0;
        gradeNeededToPass_ = 0;

    }
    public Course(int id, String courseName)
    {
        courseId_ = id;
        courseName_ = courseName;
        courseAbbreviation_ = "";
        passingGrade_ = 0;
        currentGrade_ = 0;
        gradeNeededToPass_ = 0;
    }
    public Course(int id, String courseName,  String courseAbbreviation)
    {
        courseId_ = id;
        courseName_ = courseName;
        courseAbbreviation_ = courseAbbreviation;
        passingGrade_ = 0;
        currentGrade_ = 0;
        gradeNeededToPass_ = 0;

    }
    // Setters and getters.
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getCourseId_() {
        return courseId_;
    }

    public void setCourseId_(int courseId_) {
        this.courseId_ = courseId_;
    }

    public String getCourseName_() {
        return courseName_;
    }

    public void setCourseName_(String courseName_) {
        this.courseName_ = courseName_;
    }

    public double getCurrentGrade_() {
        return currentGrade_;
    }

    public void setCurrentGrade_(double currentGrade_) {
        this.currentGrade_ = currentGrade_;
    }

    public String getCourseAbbreviation_() {
        return courseAbbreviation_;
    }

    public void setCourseAbbreviation_(String courseAbbreviation_) {
        this.courseAbbreviation_ = courseAbbreviation_;
    }

    public ArrayList<Grade> getGradeList_() {
        return gradeList_;
    }

    public void setGradeList_(ArrayList<Grade> gradeList_) {
        this.gradeList_ = gradeList_;
    }

    public double getGradeNeededToPass_() {
        return gradeNeededToPass_;
    }

    public void setGradeNeededToPass_(double gradeNeededToPass_) {
        this.gradeNeededToPass_ = gradeNeededToPass_;
    }

    public double getPassingGrade() {
        return passingGrade_;
    }

    public void setPassingGrade(double passingGrade) {
        this.passingGrade_ = passingGrade;
    }

    public double getPassingGrade_() {
        return passingGrade_;
    }

    public void setPassingGrade_(double passingGrade_) {
        this.passingGrade_ = passingGrade_;
    }

    public String getCourseLocation_() {
        return courseLocation_;
    }

    public void setCourseLocation_(String courseLocation_) {
        this.courseLocation_ = courseLocation_;
    }
}
