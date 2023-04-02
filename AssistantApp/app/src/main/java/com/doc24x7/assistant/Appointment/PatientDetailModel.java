package com.doc24x7.assistant.Appointment;

import java.util.ArrayList;

public class PatientDetailModel {
   private String PatientName;
    private String PatientMobile;
    private String Clientemail;
    private String PatientSymption;
    private  String Type;
    private String Gender;
    private String Weight;
    private String Height;
    private String Bp;
    private String Sugar;

    ArrayList<String> ReportUrl;
    public PatientDetailModel(String patientName, String patientMobile, String clientemail, String patientAge, String PatientSymptom, String gender, ArrayList<String> reporturl,String type, String weight,String height,String bp,String sugar) {
        PatientName = patientName;
        PatientMobile = patientMobile;
        Type=type;
        Clientemail = clientemail;
        PatientAge = patientAge;
        PatientSymption=PatientSymptom;
        Gender=gender;
        ReportUrl=reporturl;
        Weight=weight;
        Height=height;
        Bp=bp;
        Sugar=sugar;
    }

    private String PatientAge;

}
