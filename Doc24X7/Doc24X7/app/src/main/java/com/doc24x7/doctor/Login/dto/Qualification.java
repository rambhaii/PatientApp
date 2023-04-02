package com.doc24x7.doctor.Login.dto;

import java.util.ArrayList;

public class Qualification {
    public Qualification(String qualification_id, String qualification_name, String college_name, String passing_year , String speciality) {
        this.qualification_id = qualification_id;
        this.college_name = college_name;
        this.passing_year = passing_year;
        this.qualification_name = qualification_name;
        this.speciality=speciality;
    }

    public Qualification() {
    }

    public Qualification(ArrayList<Qualification> qualification) {
    }

    public String getQualification_id() {
        return qualification_id;
    }

    public void setQualification_id(String qualification_id) {
        this.qualification_id = qualification_id;
    }

    public String getCollege_name() {
        return college_name;
    }

    public void setCollege_name(String college_name) {
        this.college_name = college_name;
    }

    public String getPassing_year() {
        return passing_year;
    }

    public void setPassing_year(String passing_year) {
        this.passing_year = passing_year;
    }

    private String qualification_id;
    private String college_name;
    private String quafication;

    public String getQuafication() {
        return quafication;
    }

    public void setQuafication(String quafication) {
        this.quafication = quafication;
    }

    private String passing_year;
    private String speciality;
    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }


    public String getQualification_name() {
        return qualification_name;
    }

    public void setQualification_name(String qualification_name) {
        this.qualification_name = qualification_name;
    }

    private String qualification_name;
}
