package com.repolist.view;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
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

    private HomeActivityViewModel mHomeActivityViewModel;
    private RepoListAdapter mRepoListAdapter;
    private RepoListAdapter.OnRepoListener mRepoListener = this;

    @BindView(R.id.recyclerView)
    protected RecyclerView mRecyclerView;

    // Lifecycle methods
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach: ");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        return inflater.inflate(R.layout.fragment_repo_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        view.setTag(TAG);
        ButterKnife.bind(this, view);
        initRecyclerView();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViewModel();
        Log.d(TAG, "onActivityCreated: ");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    // Fragment is active

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView: ");
        Log.d(TAG, "**************************************");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach: ");
    }

    // ---------------------------------------------------------------------------------------------

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Log.d(TAG, "onViewStateRestored: ");
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void initViewModel() {
        mHomeActivityViewModel = ViewModelProviders.of(this).get(com.repolist.viewmodel.HomeActivityViewModel.class);

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

        //DividerItemDecoration divider = new DividerItemDecoration(mRecyclerView.getContext(), layoutManager.getOrientation());
        //mRecyclerView.addItemDecoration(divider);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.options_menu, menu);

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        SearchView mSearchView = (SearchView) menu.findItem(R.id.search).getActionView();
        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

        // Set SearchView text color
        EditText searchEditText = mSearchView.findViewById(androidx.appcompat.R.id.search_src_text);
        searchEditText.setHint("Enter repository name");
        searchEditText.setHintTextColor(getResources().getColor(R.color.white));
        searchEditText.setTextColor(getResources().getColor(R.color.white));

        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mHomeActivityViewModel.deleteAllRepositories();
                mHomeActivityViewModel.getRepositories(query);
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
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }

    public void onRepoClick(int position){
        Bundle bundle = new Bundle();
        bundle.putInt("id", mRepoListAdapter.getRepoAtPosition(position).getId());
        bundle.putBoolean("is_favorite", mRepoListAdapter.getRepoAtPosition(position).getIsFavorite());
        Navigation.findNavController(getActivity(), R.id.my_nav_host_fragment).navigate(R.id.repoDetailFragment, bundle);
    }

}
