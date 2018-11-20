package za.co.addcolour.rxjavaretrofitprofile.network;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import za.co.addcolour.rxjavaretrofitprofile.model.Profile;

public interface ApiInterface {

    @GET("profile.json/")
    Single<List<Profile>> fetchAllProfiles();
}