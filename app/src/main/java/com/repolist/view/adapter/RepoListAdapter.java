package com.repolist.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.repolist.R;
import com.repolist.model.Repository;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RepoListAdapter extends RecyclerView.Adapter<RepoListAdapter.MyViewHolder> {
    private static final String TAG = "RepoListAdapter";
    private List<Repository> mRepos;
    private OnRepoListener mOnRepoListener;

    // Provide a reference to the views for each data item Complex data items may need more than one
    // view per item, and you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.repo_name)
        TextView mRepoName;
        @BindView(R.id.repo_description)
        TextView mRepoDescription;
        @BindView(R.id.stargazers_count)
        TextView mStargazersCount;
        @BindView(R.id.watchers_count)
        TextView mWatchersCount;
        @BindView(R.id.language)
        TextView mLanguage;

        OnRepoListener onRepoListener;

        public MyViewHolder(View v, OnRepoListener onRepoListener) {
            super(v);
            ButterKnife.bind(this, v);

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

    public RepoListAdapter(OnRepoListener onRepoListener){
        mOnRepoListener = onRepoListener;
    }

    public void setRepos(List<Repository> repos){
        mRepos = repos;
        notifyDataSetChanged();
    }

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
        final Repository repository = mRepos.get(position);
        holder.mRepoName.setText        (repository.getName());
        holder.mStargazersCount.setText (Integer.toString(repository.getStargazersCount()));
        holder.mWatchersCount.setText   (Integer.toString(repository.getWatchersCount()));
        holder.mLanguage.setText        (repository.getLanguage());
        holder.mRepoDescription.setText (repository.getDescription());
    }

    // getItemCount() is called many times, and when it is first called,
    // repos has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mRepos != null)
            return mRepos.size();
        else return 0;
    }
}