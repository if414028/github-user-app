package com.example.githubuserapp.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.githubuserapp.R;
import com.example.githubuserapp.databinding.ActivityDetailUserBinding;
import com.squareup.picasso.Picasso;

public class DetailUserActivity extends AppCompatActivity {

    public static final String ARG_USER_MODEL = "user_model";

    private ActivityDetailUserBinding binding;
    private User model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_user);

        getIntentData();
        initLayout();

    }

    private void getIntentData() {
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra(ARG_USER_MODEL)) {
                this.model = intent.getParcelableExtra(ARG_USER_MODEL);
            } else this.model = new User();
        } else this.model = new User();
    }

    private void initLayout() {
        binding.setModel(model);
        Picasso.with(getApplicationContext()).load(model.getAvatarUrl()).into(binding.ivUser);
    }
}