package com.repolist.view.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.repolist.R;
import com.repolist.model.Repo;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;


public class RepoListAdapter extends RecyclerView.Adapter<RepoListAdapter.MyViewHolder> {
    private static final String TAG = "RepoListAdapter";
    private List<Repo> repos;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView textView;

        public MyViewHolder(View v) {
            super(v);
            textView = v.findViewById(R.id.repo_name);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public RepoListAdapter() {
        Log.d(TAG, "**** RepoListAdapter ");
    }

    public void setRepos(List<Repo> repos){
        Log.d(TAG, "**** setRepos() ");
        this.repos = repos;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RepoListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "**** onCreateViewHolder() ");
        // create a new view
        RelativeLayout v = (RelativeLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.repo_layout, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Log.d(TAG, "**** onBindViewHolder() ");
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Log.d(TAG, "**** repos.get(position).getRepoName() " + repos.get(position).getRepoName());
        holder.textView.setText(repos.get(position).getRepoName());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return repos.size();
    }
}