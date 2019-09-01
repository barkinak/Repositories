package com.repolist.activities;

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

public class RepoListAdapter extends BaseAdapter {
    private static final String TAG = "RepoListAdapter";

    private Context context;
    private ArrayList<Repo> repos;
    private ArrayList<String> favList;

    public RepoListAdapter(Context context, ArrayList<Repo> repos, ArrayList<String> favList){
        this.context = context;
        this.repos = repos;
        this.favList = favList;
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

        if(favList != null) {
            printArray(favList);
            if (favList.contains(repos.get(position))) {
                viewHolder.starImage.setImageResource(R.drawable.baseline_star_24);
            }
        }
        viewHolder.repoName.setText(repos.get(position).getRepoName());
        return v;
    }

    private void printArray(ArrayList<String> list){
        Log.d(TAG, "Printing FavList");
        for(String s: list)
            Log.d(TAG, s + " ");
        Log.d(TAG, "");
    }

    public class ViewHolder{
        TextView repoName;
        ImageView starImage;
    }
}
