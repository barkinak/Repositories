package com.repolist.view.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.repolist.R;
import com.repolist.model.Repository;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class RepoListAdapter extends RecyclerView.Adapter<RepoListAdapter.MyViewHolder> {
    private static final String TAG = "RepoListAdapter";
    private List<Repository> repos;
    private OnRepoListener mOnRepoListener;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public TextView mRepoName, mRepoDescription, mStargazersCount, mWatchersCount, mLanguage;
        OnRepoListener onRepoListener;

        public MyViewHolder(View v, OnRepoListener onRepoListener) {
            super(v);
            mRepoName = v.findViewById(R.id.repo_name);
            mRepoDescription = v.findViewById(R.id.repo_description);
            mStargazersCount = v.findViewById(R.id.stargazers_count);
            mWatchersCount = v.findViewById(R.id.watchers_count);
            mLanguage = v.findViewById(R.id.language);

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

    public void setRepos(List<Repository> repos){ this.repos = repos; }

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
        holder.mRepoName.setText(repos.get(position).getName());
        holder.mStargazersCount.setText("" + repos.get(position).getStargazersCount());
        holder.mWatchersCount.setText("" + repos.get(position).getWatchersCount());
        holder.mLanguage.setText("" + repos.get(position).getLanguage());

        if(repos.get(position).getDescription() == "null")
            holder.mRepoDescription.setText("No description");
        else
            holder.mRepoDescription.setText(repos.get(position).getDescription());
    }

    // Return the size of your data set
    @Override
    public int getItemCount() {
        return repos.size();
    }
}