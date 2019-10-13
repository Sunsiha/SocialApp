package com.moonlyte.myjavalibrary.model;

import com.google.gson.annotations.SerializedName;

public class Albums {

    @SerializedName("userId")
    public Integer userId;
    @SerializedName("id")
    public Integer id;
    @SerializedName("title")
    public String title;

}
