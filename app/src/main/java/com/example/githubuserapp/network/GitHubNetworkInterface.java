package com.example.githubuserapp.network;

import com.example.githubuserapp.activity.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GitHubNetworkInterface {

    @GET("users")
    Call<List<User>> getAllUsers();

    @GET("search/users?q={username}")
    Call<List<User>> searchUser(@Path("username") String username);

    @GET("users/{username}")
    Call<User> getDetailUser(@Path("username") String username);

    @GET("users/{username}/followers")
    Call<List<User>> getUserFollowers(@Path("username") String username);

    @GET("users/{username}/following")
    Call<List<User>> getUserFollowing(@Path("username") String username);

}
