package com.repositories.view;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.SearchManager;
import android.content.Context;
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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.repositories.R;
import com.repositories.model.Repository;
import com.repositories.view.adapter.RepoListAdapter;
import com.repositories.viewmodel.HomeActivityViewModel;

import java.util.List;

public class RepoListFragment extends Fragment implements RepoListAdapter.OnRepoListener {
    private static final String TAG = "RepoListFragment";

    private HomeActivityViewModel mHomeActivityViewModel;
    private RepoListAdapter mRepoListAdapter;
    private RepoListAdapter.OnRepoListener mRepoListener = this;

    @BindView(R.id.recyclerView)
    protected RecyclerView mRecyclerView;

    private Toolbar mToolbar;

    // Lifecycle methods
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_repo_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        view.setTag(TAG);
        ButterKnife.bind(this, view);
        initRecyclerView();
        initToolbar();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViewModel();
    }

    private void initViewModel() {
        mHomeActivityViewModel = ViewModelProviders.of(this).get(HomeActivityViewModel.class);

        final Observer<List<Repository>> reposObserver = repositories -> {
            if(mRepoListAdapter == null){
                mRepoListAdapter = new RepoListAdapter(mRepoListener);
                mRepoListAdapter.setRepos(repositories);
                mRecyclerView.setAdapter(mRepoListAdapter);
            } else {
                mRepoListAdapter.setRepos(repositories);
                mRepoListAdapter.notifyDataSetChanged();
            }
        };
        mHomeActivityViewModel.mRepositories.observe(this, reposObserver);
    }

    private void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mRepoListAdapter);
    }

    private void initToolbar() {
        mToolbar = getActivity().findViewById(R.id.toolbar);
        mToolbar.setTitle("Repositories");
        mToolbar.setNavigationIcon(null);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.options_menu, menu);

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView mSearchView = (SearchView) menu.findItem(R.id.search).getActionView();
        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

        // Set SearchView text color
        EditText searchEditText = mSearchView.findViewById(androidx.appcompat.R.id.search_src_text);
        searchEditText.setHint("Enter username");
        searchEditText.setHintTextColor(getResources().getColor(R.color.white));
        searchEditText.setTextColor(getResources().getColor(R.color.white));

        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mHomeActivityViewModel.deleteAllRepositories();
                mHomeActivityViewModel.getRepositoriesFromWebService(query);
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
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.search:
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

    public void onRepoClick(int position){
        Log.d(TAG, "--- onRepoClick: ");
        Bundle bundle = new Bundle();
        bundle.putInt("id", mRepoListAdapter.getRepoAtPosition(position).getId());
        bundle.putBoolean("is_favorite", mRepoListAdapter.getRepoAtPosition(position).getIsFavorite());
        Navigation.findNavController(getActivity(), R.id.my_nav_host_fragment).navigate(R.id.action_repoListFragment_to_repoDetailFragment, bundle);
    }

}
