package com.moonlyte.myjavalibrary.api;


import com.moonlyte.myjavalibrary.model.Albums;
import com.moonlyte.myjavalibrary.model.Comments;
import com.moonlyte.myjavalibrary.model.Photos;
import com.moonlyte.myjavalibrary.model.Posts;
import com.moonlyte.myjavalibrary.model.Todos;
import com.moonlyte.myjavalibrary.model.Users;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface API {

    @GET("/users")
    Call<List<Users>> getUsersOld();

    @GET("/users")
    Call<List<Users>> getUsers();

    @GET("/posts?")
    Call<List<Posts>> getUserPosts(@Query("userId") Integer userId);

    @GET("/comments?")
    Call<List<Comments>> getUserComments(@Query("postId") Integer postId);

    @GET("/albums?")
    Call<List<Albums>> getUserAlbums(@Query("userId") Integer userId);

    @GET("/photos?")
    Call<List<Photos>> getAlbumPhotos(@Query("albumId") Integer albumId);

    @GET("/todos?")
    Call<List<Todos>> getUserTodos(@Query("userId") Integer userId);
}
