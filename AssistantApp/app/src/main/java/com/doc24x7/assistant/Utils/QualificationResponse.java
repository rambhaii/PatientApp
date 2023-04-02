package com.doc24x7.assistant.Utils;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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
