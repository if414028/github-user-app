package com.example.githubuserapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.githubuserapp.databinding.ActivityMainBinding;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements UserListListener {

    private ActivityMainBinding binding;
    private UserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.rvUser.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        adapter = new UserAdapter(getApplicationContext(), getUserList(), this);
        binding.rvUser.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private List<User> getUserList() {
        List<User> users = new ArrayList<>();
        try {
            JSONObject userJsonObject = new JSONObject(getJsonFromResources());
            JSONArray userJsonArray = userJsonObject.getJSONArray("users");
            if (userJsonArray.length() > 0) {
                for (int i = 0; i < userJsonArray.length(); i++) {
                    User user = new Gson().fromJson(userJsonArray.get(i).toString(), User.class);
                    users.add(user);
                }
            }
        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    private String getJsonFromResources() throws IOException {
        InputStream is = getResources().openRawResource(R.raw.githubuser);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } finally {
            is.close();
        }

        return writer.toString();
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