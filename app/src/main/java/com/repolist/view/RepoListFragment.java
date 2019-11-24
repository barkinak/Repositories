package com.repolist.view;

import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.repolist.R;
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

    @BindView(R.id.recyclerView)
    protected RecyclerView mRecyclerView;
    protected RepoListAdapter mRepoListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView called");

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.repo_list_fragment, container, false);
        rootView.setTag(TAG);

        ButterKnife.bind(this, rootView);
        initRecyclerView();
        initViewModel();

        return rootView;
    }

    private void initViewModel() {
    }

    private void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);

        mRepoListAdapter = new RepoListAdapter(this);
        mRecyclerView.setAdapter(mRepoListAdapter);
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
