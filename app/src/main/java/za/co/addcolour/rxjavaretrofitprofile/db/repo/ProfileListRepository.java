package za.co.addcolour.rxjavaretrofitprofile.db.repo;

import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import za.co.addcolour.rxjavaretrofitprofile.model.Profile;
import za.co.addcolour.rxjavaretrofitprofile.network.ApiFactory;
import za.co.addcolour.rxjavaretrofitprofile.network.ApiInterface;

public class ProfileListRepository {
    private static final String TAG = ProfileListRepository.class.getSimpleName();

    private ApiInterface apiInterface;

    public ProfileListRepository() {
        apiInterface = ApiFactory.getClient().create(ApiInterface.class);
    }

    public MutableLiveData<List<Profile>> getProfile() {
        final MutableLiveData<List<Profile>> liveData = new MutableLiveData<>();

        Call<List<Profile>> call = apiInterface.fetchAllProfiles();
        call.enqueue(new Callback<List<Profile>>() {
            @Override
            public void onResponse(@NonNull Call<List<Profile>> call, @NonNull Response<List<Profile>> response) {
                liveData.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<List<Profile>> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
        return liveData;
    }
}