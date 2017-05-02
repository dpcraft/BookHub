package com.dpcraft.bookhub.DataClass;

import com.google.gson.annotations.SerializedName;

/**
 * Created by DPC on 2017/5/2.
 */
public class RequestBookInfo {

    @SerializedName("bwid")
    private String requestId;
    @SerializedName("bwtitle")
    private String requestTitle;
    @SerializedName("bwuserid")
    private String bwUserId;
    @SerializedName("bwbody")
    private String requestBody;
    @SerializedName("create_date")
    private String date;
    public String getRequestTitle() {
        return requestTitle;
    }
    public void setRequestTitle(String requestTitle) {
        this.requestTitle = requestTitle;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }
    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }




}
