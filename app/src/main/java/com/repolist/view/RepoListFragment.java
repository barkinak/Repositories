package com.repolist.view;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.repolist.R;
import com.repolist.model.Repo;
import com.repolist.model.Repository;
import com.repolist.view.adapter.RepoListAdapter;
import com.repolist.viewmodel.HomeActivityViewModel;

import java.util.List;

/**
 * RepoList will use RecyclerView
 */

public class RepoListFragment extends Fragment implements RepoListAdapter.OnRepoListener {

    private static final String TAG = "RepoListFragment";
    private HomeActivityViewModel mViewModel;

    protected RecyclerView mRecyclerView;
    protected RepoListAdapter mRepoListAdapter;
    protected RecyclerView.LayoutManager mLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView called");

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.repo_list_fragment, container, false);
        rootView.setTag(TAG);

        mRecyclerView = rootView.findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRepoListAdapter = new RepoListAdapter(this);

        // Set CustomAdapter as the adapter for RecyclerView.
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mRepoListAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated called");
        mViewModel = ViewModelProviders.of(this).get(com.repolist.viewmodel.HomeActivityViewModel.class);
        mViewModel.init();
        mRepoListAdapter.setRepos(mViewModel.getRepos().getValue());

        mViewModel.getRepos().observe(this, new Observer<List<Repository>>() {
            @Override
            public void onChanged(@Nullable List<Repository> repos) {
                Log.d(TAG, "**** ---> ");
                mRepoListAdapter.notifyDataSetChanged();
            }
        });
    }

    public void onRepoClick(int position){
        Log.d(TAG, "clicked on pos " + position);
    }

}
