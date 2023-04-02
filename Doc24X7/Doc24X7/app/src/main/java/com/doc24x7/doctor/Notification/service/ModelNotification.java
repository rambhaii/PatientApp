package com.doc24x7.doctor.Notification.service;

public class ModelNotification {
    public ModelNotification(String userId, String request_id) {
        this.userId = userId;
        this.request_id = request_id;
    }

    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    private String request_id;


}
