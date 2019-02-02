package com.dev.mygatedemo;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dev.mygatedemo.model.UserModel;
import com.dev.mygatedemo.utils.AppUtils;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private Context context;
    private List<UserModel> smsList;

    public UserAdapter(Context context, List<UserModel> smsList) {
        this.context = context;
        this.smsList = smsList;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recycler_view_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        UserModel model = smsList.get(position);
        String name = AppUtils.getValueFromData(model.getName() +" " + (position + 1)).toString();
        String passcode = AppUtils.getValueFromData(model.getPasscode()).toString();
        holder.userName.setText(name);
        holder.userPasscode.setText(passcode);
//        Glide.with(context).load(new File(model.getImg())).into(holder.userImageView);
    }

    @Override
    public int getItemCount() {
        return smsList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.userImageView)
        ImageView userImageView;
        @BindView(R.id.userName)
        TextView userName;
        @BindView(R.id.userPasscode)
        TextView userPasscode;
        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, itemView);
        }
    }
}
