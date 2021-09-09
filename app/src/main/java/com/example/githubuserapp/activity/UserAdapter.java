package com.example.githubuserapp.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.githubuserapp.R;
import com.example.githubuserapp.databinding.ItemUserBinding;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private List<User> userList = new ArrayList<>();
    private UserListListener listener;
    private Context context;

    public UserAdapter(Context context, List<User> userList, UserListListener listener) {
        this.userList = userList;
        this.listener = listener;
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ItemUserBinding itemBinding;

        public ViewHolder(ItemUserBinding itemBinding) {
            super(itemBinding.getRoot());
            this.itemBinding = itemBinding;
        }
    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        ItemUserBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_user, parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull UserAdapter.ViewHolder holder, int position) {
        User user = userList.get(position);
        holder.itemBinding.setModel(user);
        Picasso.with(context).load(user.getAvatarUrl()).into(holder.itemBinding.ivUser);
        holder.itemBinding.getRoot().setOnClickListener(v -> listener.onItemClicked(user));
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
}
