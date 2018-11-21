package za.co.addcolour.rxjavaretrofitprofile.ui.main;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import za.co.addcolour.rxjavaretrofitprofile.db.repo.ProfileListRepository;
import za.co.addcolour.rxjavaretrofitprofile.model.Profile;

public class MainViewModel extends ViewModel {

    private ProfileListRepository mRepository;
    private MutableLiveData<List<Profile>> mProfileList;

    public MainViewModel() {
        mRepository = new ProfileListRepository();
    }

    LiveData<List<Profile>> getProfile() {
        if (mProfileList == null) {
            mProfileList = new MutableLiveData<>();
            mProfileList = mRepository.getProfile();
        }
        return mProfileList;
    }
}