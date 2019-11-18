package com.repolist.view.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.repolist.R;
import com.repolist.model.Repo;

import java.util.ArrayList;
import java.util.List;

public class RepoListAdapter extends BaseAdapter {
    private static final String TAG = "RepoListAdapter";

    private Context context;
    private List<Repo> repos;

    public RepoListAdapter(Context context, List<Repo> repos){
        this.context = context;
        this.repos = repos;
        //this.favList = favList;
    }

    public int getCount(){ return repos.size(); }
    public Object getItem(int arg0) { return repos.get(arg0); }
    public long getItemId(int arg0) { return arg0; }

    public View getView(int position, View view, ViewGroup parent){
        View v = view;
        ViewHolder viewHolder;

        if(v == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert inflater != null;
            v = inflater.inflate(R.layout.repo_layout, parent, false);
            viewHolder.repoName = v.findViewById(R.id.repo_name);
            v.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) v.getTag();
        }

        viewHolder.repoName.setText(repos.get(position).getRepoName());
        return v;
    }

    public class ViewHolder{
        TextView repoName;
        ImageView starImage;
    }
}
