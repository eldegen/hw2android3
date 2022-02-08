package com.example.hw2android3.data.remote;

import com.example.hw2android3.data.models.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PostApi {

    @GET("/posts")
    Call<List<Post>> getPosts();

    @POST("/posts")
    Call<Post> createPost(
            @Body Post post
    );

    @DELETE("/posts/{id}")
    Call<Void> deletePost(@Path("id") int id);

    @FormUrlEncoded
    @PUT("/posts/{id}")
    Call<Post> putPost(@Path("id") int id,
                       @Field("title") String title,
                       @Field("content") String content,
                       @Field("group") int groupId,
                       @Field("user") int userId);
}
