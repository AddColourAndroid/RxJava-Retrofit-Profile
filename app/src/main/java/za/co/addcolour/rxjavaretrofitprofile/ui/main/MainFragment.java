package za.co.addcolour.rxjavaretrofitprofile.ui.main;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import za.co.addcolour.rxjavaretrofitprofile.R;
import za.co.addcolour.rxjavaretrofitprofile.databinding.MainFragmentBinding;
import za.co.addcolour.rxjavaretrofitprofile.model.Profile;
import za.co.addcolour.rxjavaretrofitprofile.network.ApiFactory;
import za.co.addcolour.rxjavaretrofitprofile.network.ApiInterface;
import za.co.addcolour.rxjavaretrofitprofile.ui.adapter.ProfileAdapter;

public class MainFragment extends Fragment {

    private static final String TAG;

    static {
        TAG = MainFragment.class.getSimpleName();
    }

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    private MainFragmentBinding mBinding;

    private ApiInterface apiInterface;

    private ProfileAdapter mAdapter;
    private List<Profile> mProfileList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false);

        apiInterface = ApiFactory.getClient(getActivity()).create(ApiInterface.class);

        mAdapter = new ProfileAdapter();
        mAdapter.setProfileList(mProfileList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mBinding.recyclerView.setLayoutManager(mLayoutManager);
        mBinding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        mBinding.recyclerView.setAdapter(mAdapter);

        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getProfile(apiInterface).observe(this, profileList -> {
            if (profileList != null) {
                mProfileList.clear();
                mProfileList.addAll(profileList);
                mAdapter.notifyDataSetChanged();
                mBinding.setShowdialog(true);
            }
        });
    }
}