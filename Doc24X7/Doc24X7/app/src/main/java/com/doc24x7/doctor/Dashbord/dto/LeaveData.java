package com.doc24x7.doctor.Dashbord.dto;

public class    LeaveData {


    private String leaveid;
           private String leavedate;

    public String getAll_appointment() {
        return all_appointment;
    }

    public void setAll_appointment(String all_appointment) {
        this.all_appointment = all_appointment;
    }

    private String all_appointment;

    public LeaveData(String leaveid, String leavedate, String createddate, String startdate, String enddate, String all_appointment) {
        this.leaveid = leaveid;
        this.leavedate = leavedate;
        this.createddate = createddate;
        this.startdate = startdate;
        this.enddate = enddate;
        this.all_appointment=all_appointment;
    }

    private String createddate;
           private String startdate;
           private String enddate;

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public LeaveData(String leaveid, String leavedate, String createddate, String all_appointment) {
        this.leaveid = leaveid;
        this.leavedate = leavedate;
        this.createddate = createddate;
        this.all_appointment = all_appointment;
    }

    public String getLeaveid() {
        return leaveid;
    }

    public void setLeaveid(String leaveid) {
        this.leaveid = leaveid;
    }

    public String getLeavedate() {
        return leavedate;
    }

    public void setLeavedate(String leavedate) {
        this.leavedate = leavedate;
    }

    public String getCreateddate() {
        return createddate;
    }

    public void setCreateddate(String createddate) {
        this.createddate = createddate;
    }
}
