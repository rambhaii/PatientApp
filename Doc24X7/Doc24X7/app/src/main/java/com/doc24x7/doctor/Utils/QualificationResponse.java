package com.doc24x7.doctor.Utils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.doc24x7.doctor.Login.dto.Data;

public class QualificationResponse {
    @SerializedName("Data")
    @Expose
    private QData Data;

    public QData getData() {
        return Data;
    }

    public void setData(QData data) {
        this.Data = data;
    }
}
