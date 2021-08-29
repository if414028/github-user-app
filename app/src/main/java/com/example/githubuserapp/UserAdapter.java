package com.example.githubuserapp;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.githubuserapp.databinding.ItemUserBinding;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private List<User> userList;
    private UserListListener listener;
    private Context context;

    public UserAdapter(Context context, List<User> userList, UserListListener listener) {
        this.userList = userList;
        this.listener = listener;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
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
        holder.itemBinding.ivUser.setImageDrawable(getAvatarAsDrawable(user));
        holder.itemBinding.getRoot().setOnClickListener(v -> listener.onItemClicked(user));
    }

    private Drawable getAvatarAsDrawable(User model) {
        String name = model.getAvatar();
        int id = context.getResources().getIdentifier(name, "drawable", context.getPackageName());
        return context.getResources().getDrawable(id);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
}
