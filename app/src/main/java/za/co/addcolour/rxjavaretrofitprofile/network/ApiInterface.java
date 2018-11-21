package za.co.addcolour.rxjavaretrofitprofile.network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import za.co.addcolour.rxjavaretrofitprofile.model.Profile;

public interface ApiInterface {

    @GET("profile.json/")
    Call<List<Profile>> fetchAllProfiles();
}