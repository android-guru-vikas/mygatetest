package com.dev.mygatedemo;


import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dev.mygatedemo.Glide.GlideController;
import com.dev.mygatedemo.model.UserModel;
import com.dev.mygatedemo.utils.AppUtils;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

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
        String name = AppUtils.getValueFromData("User" + " " + (position + 1)).toString();
        String passcode = AppUtils.getValueFromData(model.getPasscode()).toString();
        holder.userName.setText(name);
        holder.userPasscode.setText(passcode);
        Uri uri = Uri.fromFile(new File(model.getImg()));
        GlideController.loadImage(holder.userImageView, uri, context);
    }

    @Override
    public int getItemCount() {
        return smsList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.userImageView)
        CircleImageView userImageView;
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
