package com.example.githubuserapp.activity.main;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.example.githubuserapp.activity.User;
import com.example.githubuserapp.network.GitHubApiClient;
import com.example.githubuserapp.network.GitHubNetworkInterface;
import com.example.githubuserapp.utility.SingleLiveEvent;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GithubUserListViewModel extends ViewModel {

    private final SingleLiveEvent<List<User>> onSuccessGetUserList = new SingleLiveEvent<>();
    private final SingleLiveEvent<Void> onErrorGetUserList = new SingleLiveEvent<>();
    private final SingleLiveEvent<Void> onSearchUserNotFound = new SingleLiveEvent<>();
    private final SingleLiveEvent<List<User>> onSuccessSearchUserList = new SingleLiveEvent<>();

    public void fetchGithubUserList() {
        GitHubNetworkInterface networkInterface = GitHubApiClient.createService(GitHubNetworkInterface.class);
        Call<List<User>> call = networkInterface.getAllUsers();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(@NonNull Call<List<User>> call, @NonNull Response<List<User>> response) {
                if (response.isSuccessful()) {
                    onSuccessGetUserList.postValue(response.body());
                } else onErrorGetUserList.callFromBackgroundThread();
            }

            @Override
            public void onFailure(@NonNull Call<List<User>> call, @NonNull Throwable t) {
                onErrorGetUserList.callFromBackgroundThread();
            }
        });
    }

    public void searchUser(String username) {
        GitHubNetworkInterface networkInterface = GitHubApiClient.createService(GitHubNetworkInterface.class);
        Call<List<User>> call = networkInterface.searchUser(username);
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(@NonNull Call<List<User>> call, @NonNull Response<List<User>> response) {
                if (response.isSuccessful()) {
                    onSuccessSearchUserList.postValue(response.body());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<User>> call, @NonNull Throwable t) {

            }
        });
    }

    public SingleLiveEvent<List<User>> getOnSuccessGetUserList() {
        return onSuccessGetUserList;
    }

    public SingleLiveEvent<Void> getOnErrorGetUserList() {
        return onErrorGetUserList;
    }

    public SingleLiveEvent<Void> getOnSearchUserNotFound() {
        return onSearchUserNotFound;
    }

    public SingleLiveEvent<List<User>> getOnSuccessSearchUserList() {
        return onSuccessSearchUserList;
    }
}
