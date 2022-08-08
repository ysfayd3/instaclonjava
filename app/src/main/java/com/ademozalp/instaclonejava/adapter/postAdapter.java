package com.ademozalp.instaclonejava.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ademozalp.instaclonejava.databinding.RcyclerrowBinding;
import com.ademozalp.instaclonejava.model.Post;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class postAdapter extends RecyclerView.Adapter<postAdapter.postHolder> {

    private ArrayList<Post> postArrayList;
    public postAdapter(ArrayList<Post> postArrayList){
        this.postArrayList = postArrayList;
    }

    @NonNull
    @Override
    public postHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RcyclerrowBinding rcyclerrowBinding = RcyclerrowBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new postHolder(rcyclerrowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull postHolder holder, int position) {
        holder.rcyclerrowBinding.rcyclerViewEmail.setText(postArrayList.get(position).email);
        holder.rcyclerrowBinding.rcyclerViewComment.setText(postArrayList.get(position).comment);
        Picasso.get().load(postArrayList.get(position).downloadUrl).into(holder.rcyclerrowBinding.rcyclerViewImage);
    }

    @Override
    public int getItemCount() {
        return postArrayList.size();
    }

    public class postHolder extends RecyclerView.ViewHolder{

        RcyclerrowBinding rcyclerrowBinding;
        public postHolder(RcyclerrowBinding rcyclerrowBinding) {
            super(rcyclerrowBinding.getRoot());
            this.rcyclerrowBinding = rcyclerrowBinding;
        }
    }
}
