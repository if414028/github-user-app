package com.example.githubuserapp.activity.main;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.githubuserapp.R;
import com.example.githubuserapp.activity.DetailUserActivity;
import com.example.githubuserapp.activity.User;
import com.example.githubuserapp.activity.UserAdapter;
import com.example.githubuserapp.activity.UserListListener;
import com.example.githubuserapp.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements UserListListener {

    private ActivityMainBinding binding;
    private GithubUserListViewModel viewModel;
    private UserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewModel = new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(GithubUserListViewModel.class);

        initLayout();
        initObserver();
    }

    private void initLayout() {
        binding.rvUser.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        adapter = new UserAdapter(getApplicationContext(), new ArrayList<>(), this);
        binding.rvUser.setAdapter(adapter);
    }

    private void initObserver() {
        viewModel.fetchGithubUserList();

        viewModel.getOnSuccessGetUserList().observe(this, users -> {
            adapter.setUserList(users);
            adapter.notifyDataSetChanged();

            binding.setIsSuccess(true);
            binding.setIsEmptySearch(false);
            binding.setIsError(false);
        });

        viewModel.getOnSuccessSearchUserList().observe(this, users -> {
            adapter.setUserList(users);
            adapter.notifyDataSetChanged();

            binding.setIsSuccess(true);
            binding.setIsEmptySearch(false);
            binding.setIsError(false);
        });

        viewModel.getOnSearchUserNotFound().observe(this, unused -> {
            binding.setIsSuccess(false);
            binding.setIsEmptySearch(true);
            binding.setIsError(false);
        });

        viewModel.getOnErrorGetUserList().observe(this, unused -> {
            binding.setIsSuccess(false);
            binding.setIsEmptySearch(false);
            binding.setIsError(true);
        });
    }

    @Override
    public void onItemClicked(User user) {
        Intent intent = new Intent(getApplicationContext(), DetailUserActivity.class);
        intent.putExtra(DetailUserActivity.ARG_USER_MODEL, user);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}