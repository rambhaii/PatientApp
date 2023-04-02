package com.doc24x7.doctor.Login.dto;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class secureLoginResponse {

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("OTP")
    @Expose
    private String OTP;


    public String getOTP() {
        return OTP;
    }

    public void setOTP(String OTP) {
        this.OTP = OTP;
    }

    @SerializedName("message")
    @Expose
    private String message;


    @SerializedName("Data")
    @Expose
    private Data data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {

        this.status = status;
    }

    public String getMessage() {

        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

 }
