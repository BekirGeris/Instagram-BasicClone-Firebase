package com.begers.instagramclone.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.begers.instagramclone.databinding.RecyclerRowBinding;
import com.begers.instagramclone.model.Post;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostHolder> {

    List<Post> postList;

    public PostAdapter(List<Post> postList) {
        this.postList = postList;
    }

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerRowBinding recyclerRowBinding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new PostHolder(recyclerRowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, int position) {
        holder.recyclerRowBinding.RecyclerViewEmailText.setText(postList.get(position).getEmail());
        holder.recyclerRowBinding.RecyclerViewCommentText.setText(postList.get(position).getComment());
        Picasso.get().load(postList.get(position).getDownloadUrl()).into(holder.recyclerRowBinding.RecyclerViewImageView);
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    class PostHolder extends RecyclerView.ViewHolder{

        RecyclerRowBinding recyclerRowBinding;

        public PostHolder(RecyclerRowBinding recyclerRowBinding) {
            super(recyclerRowBinding.getRoot());
            this.recyclerRowBinding = recyclerRowBinding;
        }
    }
}
