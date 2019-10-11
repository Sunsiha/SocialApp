package com.moonlyte.myjavalibrary.model;

import com.google.gson.annotations.SerializedName;

public class Todos {

    @SerializedName("userId")
    public Integer userId;
    @SerializedName("id")
    public Integer id;
    @SerializedName("title")
    public String title;
    @SerializedName("completed")
    public Boolean completed;
}
