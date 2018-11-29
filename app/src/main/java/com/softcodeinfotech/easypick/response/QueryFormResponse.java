package com.softcodeinfotech.easypick.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class QueryFormResponse {



    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("Information")
    @Expose
    private Information information;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Information getInformation() {
        return information;
    }

    public void setInformation(Information information) {
        this.information = information;
    }


    public class Information {

        @SerializedName("category")
        @Expose
        private String category;
        @SerializedName("picklocation")
        @Expose
        private String picklocation;
        @SerializedName("droplocation")
        @Expose
        private String droplocation;
        @SerializedName("comment")
        @Expose
        private String comment;

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getPicklocation() {
            return picklocation;
        }

        public void setPicklocation(String picklocation) {
            this.picklocation = picklocation;
        }

        public String getDroplocation() {
            return droplocation;
        }

        public void setDroplocation(String droplocation) {
            this.droplocation = droplocation;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

    }
}
