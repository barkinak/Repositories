package com.repolist.view;

import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.repolist.R;
import com.repolist.model.Repository;
import com.repolist.view.adapter.RepoListAdapter;
import com.repolist.viewmodel.HomeActivityViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * RepoList will use RecyclerView
 */

public class RepoListFragment extends Fragment implements RepoListAdapter.OnRepoListener {
    private static final String TAG = "RepoListFragment";

    private HomeActivityViewModel mHomeActivityViewModel;
    private RepoListAdapter mRepoListAdapter;

    @BindView(R.id.recyclerView)
    protected RecyclerView mRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        setHasOptionsMenu(true);

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.repo_list_fragment, container, false);
        rootView.setTag(TAG);

        ButterKnife.bind(this, rootView);
        initRecyclerView();

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated: ");
        initViewModel();
    }

    private void initViewModel() {
        mHomeActivityViewModel = ViewModelProviders.of(this).get(com.repolist.viewmodel.HomeActivityViewModel.class);
        //mRepoListAdapter.setRepos(mHomeActivityViewModel.getRepos().getValue());
        final Observer<List<Repository>> reposObserver = new Observer<List<Repository>>() {
            @Override
            public void onChanged(List<Repository> repositories) {
                if(mRepoListAdapter == null){
                    mRepoListAdapter = new RepoListAdapter();
                    mRepoListAdapter.setRepos(repositories);
                } else {
                    mRepoListAdapter.notifyDataSetChanged();
                }
            }
        };
    }

    private void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration divider = new DividerItemDecoration(mRecyclerView.getContext(), layoutManager.getOrientation());
        mRecyclerView.addItemDecoration(divider);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.options_menu, menu);

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView mSearchView = (SearchView) menu.findItem(R.id.search).getActionView();
        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d(TAG, "onQueryTextSubmit: " + query);
                mHomeActivityViewModel.query(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        };
        mSearchView.setOnQueryTextListener(queryTextListener);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                Log.d(TAG, "onOptionsItemSelected: Search");
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

    public void onRepoClick(int position){
        Log.d(TAG, "clicked on pos " + position);
        Navigation.findNavController(getActivity(), R.id.my_nav_host_fragment).navigate(R.id.repoDetailFragment);
    }

}
