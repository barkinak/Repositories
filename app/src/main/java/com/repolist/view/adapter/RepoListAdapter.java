package com.repolist.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.repolist.R;
import com.repolist.model.Repo;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class RepoListAdapter extends RecyclerView.Adapter<RepoListAdapter.MyViewHolder> {
    private static final String TAG = "RepoListAdapter";
    private List<Repo> repos;
    private OnRepoListener mOnRepoListener;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public TextView textView;
        OnRepoListener onRepoListener;

        public MyViewHolder(View v, OnRepoListener onRepoListener) {
            super(v);
            textView = v.findViewById(R.id.repo_name);
            this.onRepoListener = onRepoListener;

            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onRepoListener.onRepoClick(getAdapterPosition());
        }
    }

    public interface OnRepoListener {
        void onRepoClick (int position);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public RepoListAdapter(OnRepoListener mOnRepoListener) {
        this.mOnRepoListener = mOnRepoListener;
    }

    public void setRepos(List<Repo> repos){ this.repos = repos; }

    // Create new views
    @Override
    public RepoListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.repo_layout, parent, false);
        MyViewHolder vh = new MyViewHolder(view, mOnRepoListener);
        return vh;
    }

    // Replace the contents of a view
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.textView.setText(repos.get(position).getRepoName());
    }

    // Return the size of your data set
    @Override
    public int getItemCount() {
        return repos.size();
    }
}