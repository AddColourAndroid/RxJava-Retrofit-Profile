package za.co.addcolour.rxjavaretrofitprofile.ui.main;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.Collections;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import za.co.addcolour.rxjavaretrofitprofile.model.Profile;
import za.co.addcolour.rxjavaretrofitprofile.network.ApiInterface;

public class MainViewModel extends ViewModel {

    private ApiInterface apiInterface;
    private CompositeDisposable mDisposable = new CompositeDisposable();

    private MutableLiveData<List<Profile>> mProfileList;

    LiveData<List<Profile>> getProfile(ApiInterface apiInterface) {
        this.apiInterface = apiInterface;
        if (mProfileList == null) {
            mProfileList = new MutableLiveData<>();
            loadProfile();
        }
        return mProfileList;
    }

    private void loadProfile() {

        mDisposable
                .add(
                apiInterface.fetchAllProfiles()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .map(profiles -> {
                            Collections.sort(profiles, (n1, n2) -> n2.getId() - n1.getId());
                            return profiles;
                        })
                        .subscribeWith(new DisposableSingleObserver<List<Profile>>() {
                            @Override
                            public void onSuccess(List<Profile> profiles) {
                                mProfileList.postValue(profiles);
                            }

                            @Override
                            public void onError(Throwable e) {
                                // TODO - handle error
                            }
                        }));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mDisposable.clear();
    }
}