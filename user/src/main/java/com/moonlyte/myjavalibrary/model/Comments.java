package com.moonlyte.myjavalibrary.model;

import com.google.gson.annotations.SerializedName;

public class Comments {

    @SerializedName("postId")
    public Integer postId;
    @SerializedName("id")
    public Integer id;
    @SerializedName("email")
    public String email;
    @SerializedName("body")
    public String body;

}
