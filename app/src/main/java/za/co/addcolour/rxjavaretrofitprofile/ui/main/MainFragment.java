package za.co.addcolour.rxjavaretrofitprofile.ui.main;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
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
    private CompositeDisposable disposable = new CompositeDisposable();

    private ProfileAdapter mAdapter;
    private List<Profile> profileList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false);

        apiInterface = ApiFactory.getClient(getActivity()).create(ApiInterface.class);

        mAdapter = new ProfileAdapter();
        mAdapter.setProfileList(profileList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mBinding.recyclerView.setLayoutManager(mLayoutManager);
        mBinding.recyclerView.setItemAnimator(new DefaultItemAnimator());
        mBinding.recyclerView.setAdapter(mAdapter);

        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        disposable.add(
                apiInterface.fetchAllProfiles()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .map(new Function<List<Profile>, List<Profile>>() {
                            @Override
                            public List<Profile> apply(List<Profile> profiles) {
                                Collections.sort(profiles, new Comparator<Profile>() {
                                    @Override
                                    public int compare(Profile n1, Profile n2) {
                                        return n2.getId() - n1.getId();
                                    }
                                });
                                return profiles;
                            }
                        })
                        .subscribeWith(new DisposableSingleObserver<List<Profile>>() {
                            @Override
                            public void onSuccess(List<Profile> profiles) {
                                profileList.clear();
                                profileList.addAll(profiles);
                                mAdapter.notifyDataSetChanged();
                                mBinding.setShowdialog(true);
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e(TAG, "onError: " + e.getMessage());
                                showError(e);
                            }
                        }));
    }

    private void showError(Throwable e) {
        String message = "";
        try {
            if (e instanceof IOException) {
                message = "No internet connection!";
            } else if (e instanceof HttpException) {
                HttpException error = (HttpException) e;
                assert error.response().errorBody() != null;
                String errorBody = error.response().errorBody().string();
                JSONObject jObj = new JSONObject(errorBody);

                message = jObj.getString("error");
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        if (TextUtils.isEmpty(message)) {
            message = "Unknown error occurred! Check LogCat.";
        }

        Snackbar snackbar = Snackbar
                .make(mBinding.coordinatorLayout, message, Snackbar.LENGTH_LONG);

        View sbView = snackbar.getView();
        TextView textView = sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);
        snackbar.show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}