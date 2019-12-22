package com.repositories.view;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import androidx.navigation.Navigation;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.repositories.R;
import com.repositories.repository.model.Repository;
import com.repositories.viewmodel.DetailFragmentViewModel;

import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RepoDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RepoDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RepoDetailFragment extends Fragment {
    private static final String TAG = "RepoDetailFragment";

    @BindView(R.id.owner)
    TextView mOwner;
    @BindView(R.id.name)
    TextView mName;
    @BindView(R.id.description)
    TextView mDescription;
    @BindView(R.id.avatar)
    ImageView mAvatar;

    //@BindView(R.id.detail_stargazers_count)
    TextView mStargazersCount;
    //@BindView(R.id.detail_watchers_count)
    TextView mWatchersCount;
    //@BindView(R.id.detail_language)
    TextView mLanguage;

    private DetailFragmentViewModel mDetailFragmentViewModel;
    private Repository mRepository;
    private Toolbar mToolbar;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "id";
    private static final String ARG_PARAM2 = "is_favorite";

    // TODO: Rename and change types of parameters
    private int mParam1;
    private boolean mParam2;

    private OnFragmentInteractionListener mListener;

    public RepoDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment RepoDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RepoDetailFragment newInstance(int param1, boolean param2) {
        RepoDetailFragment fragment = new RepoDetailFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        args.putBoolean(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_PARAM1);
            mParam2 = getArguments().getBoolean(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_repo_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setHasOptionsMenu(true);
        ButterKnife.bind(this, view);
        initializeViewModel();

        mStargazersCount = getActivity().findViewById(R.id.detail_stargazers_count);
        mWatchersCount = getActivity().findViewById(R.id.detail_watchers_count);

        mToolbar = getActivity().findViewById(R.id.toolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        mToolbar.setNavigationOnClickListener(v -> Navigation.findNavController(getActivity(),
                R.id.my_nav_host_fragment)
                .navigate(R.id.action_repoDetailFragment_to_repoListFragment));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mDetailFragmentViewModel.getRepositoryById(mParam1);
    }

    private void initializeViewModel() {
        mDetailFragmentViewModel = ViewModelProviders.of(this).get(DetailFragmentViewModel.class);
        mDetailFragmentViewModel.mRepository.observe(this, repository -> {
            if(repository != null){
                mRepository = repository;

                // Set repository name, description and owner
                mOwner.setText(repository.getOwner().getLogin());
                mName.setText(repository.getName());
                mDescription.setText(repository.getDescription());
                mStargazersCount.setText(Integer.toString(repository.getStargazersCount()));
                mWatchersCount.setText(Integer.toString(repository.getWatchersCount()));

                // Set avatar picture
                Picasso.with(getActivity()).load(repository.getOwner().getAvatarUrl()).into(mAvatar);

                mToolbar.setTitle(repository.getName());
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.details_page_menu, menu);
        if(mParam2){
            menu.getItem(0).setIcon(R.drawable.ic_star_white_48dp);
        } else {
            menu.getItem(0).setIcon(R.drawable.ic_star_border_white_48dp);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.favorite_button:
                if(!mRepository.getIsFavorite()){
                    item.setIcon(R.drawable.ic_star_white_48dp);
                    mDetailFragmentViewModel.updateIsFavorite(mParam1, true);
                } else {
                    item.setIcon(R.drawable.ic_star_border_white_48dp);
                    mDetailFragmentViewModel.updateIsFavorite(mParam1, false);
                }
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);
        }
    }
}
