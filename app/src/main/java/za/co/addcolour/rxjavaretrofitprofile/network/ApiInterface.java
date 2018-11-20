package za.co.addcolour.rxjavaretrofitprofile.network;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import za.co.addcolour.rxjavaretrofitprofile.model.Profile;

public interface ApiInterface {

    @GET("profile.json/{key}")
    Single<List<Profile>> fetchAllProfiles(@Path("key") int key);
}