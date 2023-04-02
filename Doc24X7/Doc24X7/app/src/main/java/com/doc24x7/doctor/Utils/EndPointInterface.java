package com.doc24x7.doctor.Utils;

import com.doc24x7.doctor.Dashbord.dto.GalleryListResponse;
import com.doc24x7.doctor.Login.dto.secureLoginResponse;

import java.util.ArrayList;
import java.util.Date;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface EndPointInterface {

//    @Multipart
//    @POST("/api/Preciption/SavePreciption")
//    Call<secureLoginResponse> SavePreciption(@Header("X-API-KEY") String authorization,
//                                             @Part("doctor_id") Integer doctor_id,
//                                             @Part("description") RequestBody description,
//                                             @Part("userId") RequestBody userId,
//                                             @Part MultipartBody.Part file5,
//                                             @Part("medicine_name[]") ArrayList<String> medicine_name,
//                                             @Part("appointment_id") RequestBody appointment_id);
//

@Multipart
@POST("/api/GetAccessToken")
Call<secureLoginResponse> GetAccessToken(@Header("X-API-KEY") String authorization,
                                         @Part("doctor_id") Integer doctor_id,
                                         @Part("patient_id") Integer patient_id,
                                         @Part("noti_type") Integer noti_type,
                                         @Part("activityType") Integer activitytype
);

@Multipart
@POST("/api//Recording/StartRecording")
Call<secureLoginResponse> startRecording(@Header("X-API-KEY") String authorization,
                                         @Part("uid") Integer uid,
                                         @Part("cname") String cname
);    @Multipart
    @POST("/api//Recording/StopRecording")
    Call<secureLoginResponse> stopRecording(@Header("X-API-KEY") String authorization,
                                            @Part("uid") Integer uid,
                                            @Part("cname") String cname,
                                            @Part("resourceId") String resourceId,
                                            @Part("sid") String sid
            );

    @Multipart
    @POST("/api/GetRTMAccessToken")
    Call<secureLoginResponse> GetRTMAccessToken(@Header("X-API-KEY") String authorization,
                                             @Part("doctor_id") Integer doctor_id,
                                             @Part("patient_id") Integer patient_id,
                                             @Part("noti_type") Integer noti_type,
                                             @Part("activityType") Integer activitytype
    );
    @Multipart
    @POST("/api/CopyPreciption/SavePreciption")
    Call<secureLoginResponse> SavePreciption(@Header("X-API-KEY") String authorization,
                                             @Part("doctor_id") Integer doctor_id,
                                             @Part("description") RequestBody description,
                                             @Part("userId") RequestBody userId,
                                             @Part MultipartBody.Part file5,
                                             @Part("medicine_name[]") ArrayList<String> medicine_name,
                                             @Part("medicine_details") RequestBody medicine_details,
                                             @Part("appointment_id") RequestBody appointment_id);

    @Multipart
    @POST("api/Doctors/SaveDrQualificationDetails")
        Call<QualificationResponse> SaveDrQualificationDetails(@Header("X-API-KEY") String authorization,
                                                         @Part("doctor_id") Integer doctor_id,
                                                         @Part("qualification_id[]") ArrayList<Integer> qualification_id,
                                                         @Part("college_name[]") ArrayList<String> college_name,
                                                         @Part("passing_year[]") ArrayList<Integer> passing_year,
                                                         @Part("speciality[]") ArrayList<String> speciality);
    @Multipart
    @POST("api/Doctors/Leave")
        Call<QualificationResponse> Leave(@Header("X-API-KEY") String authorization,
                                                         @Part("doctor_id") Integer doctor_id,
                                                         @Part("leave_date[]") ArrayList<String> speciality);
    @Multipart
    @POST("api/Doctors/LeaveOffline")
    Call<QualificationResponse> LeaveOffline(@Header("X-API-KEY") String authorization,
                                      @Part("doctor_id") Integer doctor_id,
                                      @Part("leave_date[]") ArrayList<String> speciality);
    @Multipart
    @POST("api/Doctors/LeaveBySchedule")
        Call<QualificationResponse>  LeaveBySchedule(@Header("X-API-KEY") String authorization,
                                                         @Part("doctor_id") Integer doctor_id,
                                                         @Part("leave_date") String leave_date,
                                                         @Part("slot_id[]") ArrayList<Integer> slot_id);
    @Multipart
    @POST("api/Doctors/LeaveBySchedule")
    Call<QualificationResponse>  LeaveByScheduleOffline(@Header("X-API-KEY") String authorization,
                                                 @Part("doctor_id") Integer doctor_id,
                                                 @Part("leave_date") String leave_date,
                                                 @Part("slot_id[]") ArrayList<Integer> slot_id);
    @Multipart
    @POST("api/Doctors/SaveDrAccountDetails")
    Call<QualificationResponse> SaveDrAccountDetails(@Header("X-API-KEY") String authorization,
                                                         @Part("doctor_id") Integer doctor_id,
                                                         @Part("account_holder_name")String account_holder_name,
                                                         @Part("bank_name") String college_name,
                                                         @Part("account_no") String account_no,
                                                         @Part("ifsc_code") String ifsc_code,
                                                         @Part("branch_name") String branch_name,
                                                         @Part("upi") String upi);

    @Multipart
    @POST("/api/Doctors/UpdateProfile")
    Call<secureLoginResponse> DoctorRegister(@Header("X-API-KEY") String authorization,
                                             @Part("mobile") RequestBody mobile,
                                             @Part("name") RequestBody name,
                                             @Part("email") RequestBody Emailval,
                                             @Part("type_id") RequestBody type_id,
                                             @Part("experience") RequestBody experience,
                                             @Part("pin") RequestBody pin,
                                             @Part("district_id") RequestBody district,
                                             @Part("state_id") RequestBody state,
                                             @Part("address") RequestBody address,
                                             @Part("clinic_name") RequestBody clinic_name,
                                             @Part("assistant_name") RequestBody assistant_name,
                                             @Part("assistant_mobile") RequestBody assistant_mobile,
                                             @Part("doctor_fees") RequestBody doctor_fees,
                                             @Part("firebase_token") RequestBody firebase_token,
                                             @Part("latitude") RequestBody latitude,
                                             @Part("longitude") RequestBody longitude,
                                             @Part("doctor_id") RequestBody doctor_id,
                                             @Part("registration_no") RequestBody registrationval,
                                             @Part("consult_for") RequestBody consult_forval,
                                             @Part("clinic_fees") RequestBody clinic_fees,
                                             @Part("fees_duration") RequestBody fees_duration,
                                             @Part MultipartBody.Part file5);

    @FormUrlEncoded
    @POST("/api/DoctorLogin")
    Call<secureLoginResponse> DoctorLogin(@Header("X-API-KEY") String authorization,
                                          @Field("mobile") String mobile);
    @FormUrlEncoded
    @POST("/api/SaveToken")
    Call<secureLoginResponse> SaveToken(@Header("X-API-KEY") String authorization,
                                          @Field("userId") String userId,
                                          @Field("firbase_token") String firbase_token,
                                          @Field("userType") String userType
                                        );

    @FormUrlEncoded
    @POST("/api/DoctorLogin/DoctorOtpVerify")
    Call<secureLoginResponse> DoctorOtpVerify(@Header("X-API-KEY") String authorization,
                                              @Field("mobile") String mobile,
                                              @Field("otp") String otp);


    @GET("/api/States")
    Call<GalleryListResponse> States(@Header("X-API-KEY") String authorization);

    @GET("/api/Districts")
    Call<GalleryListResponse> Districts(@Header("X-API-KEY") String authorization, @Query("state_id") String state_id);


    @GET("/api/Doctors")
    Call<GalleryListResponse> Doctors(@Header("X-API-KEY") String authorization,
                                      @Query("id") String id);

    @GET("/api/Patient/DrPatientList")
    Call<GalleryListResponse> DrPatientList(@Header("X-API-KEY") String authorization,
                                            @Query("doctor_id") String doctor_id);

    @GET("/api/Appointment/GetAllDoctorAppointment")
    Call<GalleryListResponse> GetAllDoctorAppointment(@Header("X-API-KEY") String authorization,
                                                      @Query("date") String date,
                                                      @Query("doctor_id") String doctor_id);

    @GET("/api/Appointment/GetAllDoctorAppointment")
    Call<GalleryListResponse> GetAllDoctorAppointment(@Header("X-API-KEY") String authorization,
                                                      @Query("doctor_id") Integer doctor_id);

    @GET("/api/OfflineBookAppointment/GetAllDoctorAppointment")
    Call<GalleryListResponse> GetAllDoctorAppointmentoffline(@Header("X-API-KEY") String authorization,
                                                      @Query("doctor_id") Integer doctor_id);
        @GET("/api/Patient/DrPatientDetails")
        Call<GalleryListResponse> DrPatientDetails(@Header("X-API-KEY") String authorization,
                                                   @Query("userId") Integer userId,
                                                   @Query("doctor_id") Integer doctor_id
                                                   );
    @GET("/api/Patient/DrPatientDetailsOffline")
    Call<GalleryListResponse> DrPatientDetailsoffline(@Header("X-API-KEY") String authorization,
                                               @Query("userId") Integer userId,
                                               @Query("doctor_id") Integer doctor_id
    );

    @GET("api/Appointment/GetTodayDoctorAppointment")
    Call<GalleryListResponse> GetTodayDoctorAppointment(@Header("X-API-KEY") String authorization,
                                               @Query("date") String date,
                                               @Query("doctor_id") Integer doctor_id
    );
    @GET("api/Appointment/appointmentDueList")
    Call<GalleryListResponse> GetDueDoctorAppointment(@Header("X-API-KEY") String authorization,
                                                        @Query("date") String date,
                                                        @Query("doctor_id") Integer doctor_id
    );
    @GET("api/Appointment/patentUnderValidityList")
    Call<GalleryListResponse> GetUnderValidityDoctorAppointment(@Header("X-API-KEY") String authorization,
                                                      @Query("date") String date,
                                                      @Query("doctor_id") Integer doctor_id
    );
    @GET("api/Appointment/appointmentUpcommmingList")
    Call<GalleryListResponse> GetUpcommingDoctorAppointment(@Header("X-API-KEY") String authorization,
                                                      @Query("date") String date,
                                                      @Query("doctor_id") Integer doctor_id
    );
    @GET("api/OfflineBookAppointment/GetTodayDoctorAppointment")
    Call<GalleryListResponse> GetTodayDoctorAppointmentoffline(@Header("X-API-KEY") String authorization,
                                                        @Query("date") String date,
                                                        @Query("doctor_id") Integer doctor_id
    );
    @GET("/api/Availability/GetAllAvailability")
    Call<GalleryListResponse> GetAvailability(@Header("X-API-KEY") String authorization,
                                              @Query("doctor_id") String id,
                                              @Query("day") String day);
    @GET("/api/AvailabilityOffline/GetAllAvailability")
    Call<GalleryListResponse> GetAvailabilityOffline(@Header("X-API-KEY") String authorization,
                                                     @Query("doctor_id") String id,
                                                     @Query("day") String day);


    @FormUrlEncoded
    @POST("/api/Availability/UpdateAvailability")
    Call<GalleryListResponse> UpdateAvailability(@Header("X-API-KEY") String authorization,
                                                 @Field("start_time") String start_time,
                                                 @Field("no_of_patient") String no_of_patient,
                                                 @Field("end_time") String end_timeid,
                                                 @Field("day") String day,
                                                 @Field("status") String status,
                                                 @Field("availability_id") String availability_id);
    @FormUrlEncoded
    @POST("/api/AvailabilityOffline/UpdateAvailability")
    Call<GalleryListResponse> UpdateAvailabilityOffline(@Header("X-API-KEY") String authorization,
                                                        @Field("start_time") String start_time,
                                                        @Field("no_of_patient") String no_of_patient,
                                                        @Field("end_time") String end_timeid,
                                                        @Field("day") String day,
                                                        @Field("status") String status,
                                                        @Field("availability_id") String availability_id);


    @GET("/api/Preciption/GetMedicine")
    Call<GalleryListResponse> GetMedicine(@Header("X-API-KEY") String authorization,
                                          @Query("doctor_id") String id);

    @GET("/api/Preciption/GetPreciption")
    Call<GalleryListResponse> GetPreciption(@Header("X-API-KEY") String authorization,
                                            @Query("doctor_id") Integer id,
                                            @Query("appointment_id") Integer appointment_id);


    @GET("/api/Preciption/GetMedicineById")
    Call<GalleryListResponse> GetMedicineById(@Header("X-API-KEY") String authorization,
                                              @Query("medicine_id") String medicine_id);


    @DELETE("/api/Availability/DeleteAvailability")
    Call<secureLoginResponse> DeleteAvailability(@Header("X-API-KEY") String authorization,
                                                 @Query("id") String id);
    @DELETE("/api/AvailabilityOffline/DeleteAvailability")
    Call<secureLoginResponse> DeleteAvailabilityOffline(@Header("X-API-KEY") String authorization,
                                                        @Query("id") String id);



    @GET("/api/Payment/chkpayment")
    Call<secureLoginResponse> chkpayment(@Header("X-API-KEY") String authorization,
                                         @Query("doctor_id") String doctor_id,
                                         @Query("user_id") String user_id);


    @GET("/api/Dashboard")
    Call<secureLoginResponse> Dashboard(@Header("X-API-KEY") String authorization,
                                        @Query("doctor_id") String doctor_id,
                                        @Query("date") String date);

    @GET("/api/Appointment/GetAllPatientAppointment")
    Call<GalleryListResponse> GetAllPatientAppointment(@Header("X-API-KEY") String authorization,
                                                       @Query("userId") String id);
    @FormUrlEncoded
    @POST("/api/Availability/SetAvailability")
    Call<secureLoginResponse> SetAvailability(@Header("X-API-KEY") String authorization,
                                              @Field("doctor_id") String id,
                                              @Field("start_time") String start_time,
                                              @Field("end_time") String end_time,
                                              @Field("day") String day,
                                              @Field("no_of_patient") String no_of_patient,
                                              @Field("date") String date);
    @FormUrlEncoded
    @POST("/api/AvailabilityOffline/SetAvailability")
    Call<secureLoginResponse> SetAvailabilityOffline(@Header("X-API-KEY") String authorization,
                                                     @Field("doctor_id") String id,
                                                     @Field("start_time") String start_time,
                                                     @Field("end_time") String end_time,
                                                     @Field("day") String day,
                                                     @Field("no_of_patient") String no_of_patient,
                                                     @Field("date") String date);

    @GET("/api/Banner")
    Call<GalleryListResponse> getbanner(@Header("X-API-KEY") String authorization);

    @GET("/api/Doctors/GetAllQualification")
    Call<GalleryListResponse> GetAllQualification(@Header("X-API-KEY") String authorization);

    //http://patient.globalforex.in/api/Doctors/GetDrProfileData?doctor_id=31
 @GET("/api/Doctors/GetDrProfileData")
    Call<secureLoginResponse> GetDrProfileData(@Header("X-API-KEY") String authorization,
                                                  @Query("doctor_id") String id);
    @GET("/api/IncomeDetails/DrIncome")
    Call<GalleryListResponse> DrIncome(@Header("X-API-KEY") String authorization,
                                               @Query("doctor_id") String id);

    @GET("/api/AllTypes")
    Call<GalleryListResponse> AllTypes(@Header("X-API-KEY") String authorization);
    @GET("/api/DoctorOnline/getOnlineStatus?doctor_id")
    Call<GalleryListResponse> Getonlinestatus(@Header("X-API-KEY") String authorization,
                                              @Query("doctor_id") String id);

    @Multipart
    @POST("/api/UploadRecordFile")
     Call<secureLoginResponse> UploadRecordFile(@Header("X-API-KEY") String authorization,
                                                @Part("userId") Integer userid,
                                                @Part("doctor_id") Integer id,
                                                @Part("appointment_id") Integer Appointment_id,
                                                @Part MultipartBody.Part file5

    );
}
