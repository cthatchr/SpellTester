package microservice.test;


/**
 * Endpoints supported by our api
 */

import microservice.Response;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Endpoints {
    @GET("spell")
    Call<Response> getSpellTestResponse(@Query("s") String s);

}
