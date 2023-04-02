package com.doc24x7.doctor.Dashbord.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {


String symtom_description;
String preciption_description;
String preciption_img;
String medicine_name;
String medicine_id;
String appointment_id;
String patient_name;

    public String getPatient_details() {
        return patient_details;
    }

    public void setPatient_details(String patient_details) {
        this.patient_details = patient_details;
    }

    String patient_details;
String slot_id;
    private String amount;
    private String txt_date;
    private String txt_id;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTxt_date() {
        return txt_date;
    }

    public void setTxt_date(String txt_date) {
        this.txt_date = txt_date;
    }

    public String getTxt_id() {
        return txt_id;
    }

    public void setTxt_id(String txt_id) {
        this.txt_id = txt_id;
    }

    public String getPayment_status() {
        return payment_status;
    }

    public void setPayment_status(String payment_status) {
        this.payment_status = payment_status;
    }

    private String payment_status;
    private String start_time;
    private String name;
    private String mobile;



    public String getMedicine_details() {
        return medicine_details;
    }

    public void setMedicine_details(String medicine_details) {
        this.medicine_details = medicine_details;
    }

    public String getQualification_name() {
        return qualification_name;
    }

    public void setQualification_name(String qualification_name) {
        this.qualification_name = qualification_name;
    }

    String medicine_details;

    @Override
    public String toString() {
        return qualification_name;
    }

    public String getPatient_name() {
        return patient_name;
    }

    public void setPatient_name(String patient_name) {
        this.patient_name = patient_name;
    }

    public String getSlot_id() {
        return slot_id;
    }

    public void setSlot_id(String slot_id) {
        this.slot_id = slot_id;
    }

    public String getAppointment_id() {
        return appointment_id;
    }

    public void setAppointment_id(String appointment_id) {
        this.appointment_id = appointment_id;
    }

    public String getMedicine_id() {
        return medicine_id;
    }

    public void setMedicine_id(String medicine_id) {
        this.medicine_id = medicine_id;
    }

    public String getMedicine_name() {
        return medicine_name;
    }

    public void setMedicine_name(String medicine_name) {
        this.medicine_name = medicine_name;
    }

    public String getSymtom_description() {
        return symtom_description;
    }

    public void setSymtom_description(String symtom_description) {
        this.symtom_description = symtom_description;
    }

    public String getPreciption_description() {
        return preciption_description;
    }

    public void setPreciption_description(String preciption_description) {
        this.preciption_description = preciption_description;
    }

    public String getPreciption_img() {
        return preciption_img;
    }

    public void setPreciption_img(String preciption_img) {
        this.preciption_img = preciption_img;
    }

    @SerializedName("book_date")
    @Expose
    private String book_date;

    public String getConsult_date() {
        return consult_date;
    }

    public void setConsult_date(String consult_date) {
        this.consult_date = consult_date;
    }

    private String consult_date;

    @SerializedName("doctor_name")
    @Expose
    private String doctor_name;

    public String getBook_date() {
        return book_date;
    }

    public void setBook_date(String book_date) {
        this.book_date = book_date;
    }

    public String getDoctor_name() {
        return doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }

    @SerializedName("symtom")
    @Expose
    private String symtom;

  @SerializedName("doctor_id")
    @Expose
    private String doctor_id;



@SerializedName("day")
    @Expose
    private String day;

@SerializedName("no_of_patient")
    @Expose
    private String no_of_patient;


    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getNo_of_patient() {
        return no_of_patient;
    }

    public void setNo_of_patient(String no_of_patient) {
        this.no_of_patient = no_of_patient;
    }

    @SerializedName("end_time")
    @Expose
    private String end_time;

 @SerializedName("date")
    @Expose
    private String date;


    public String getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(String doctor_id) {
        this.doctor_id = doctor_id;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSymtom() {
        return symtom;
    }

    public void setSymtom(String symtom) {
        this.symtom = symtom;
    }

    @SerializedName("description")
    @Expose
    private String description;





   @SerializedName("created_date")
    @Expose
    private String created_date;

   @SerializedName("id")
    @Expose
    private String id;

   String userId;
   String user_mobile;


    public String getUser_mobile() {
        return user_mobile;
    }

    public void setUser_mobile(String user_mobile) {
        this.user_mobile = user_mobile;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @SerializedName("banner_img")
    @Expose
    private String banner_img;

  @SerializedName("icon")
    @Expose
    private String icon;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("qualification_name")
    @Expose
    private String qualification_name;

    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("district")
    @Expose
    private String district;
    @SerializedName("pin")
    @Expose
    private String pin;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("experience")
    @Expose
    private String experience;
    @SerializedName("clinic_name")
    @Expose
    private String clinicName;
    @SerializedName("profile_pic")
    @Expose
    private String profilePic;
    @SerializedName("type_name")
    @Expose
    private String typeName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQualification() {
        return qualification_name;
    }

    public void setQualification(String qualification) {
        this.qualification_name = qualification;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getBanner_img() {
        return banner_img;
    }

    public void setBanner_img(String banner_img) {
        this.banner_img = banner_img;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }
}
