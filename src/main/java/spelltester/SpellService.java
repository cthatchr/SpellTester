package spelltester;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Specified endpoints used to query the external API
 */


public interface SpellService {
    @GET("spells/{spell}")
    Call<Spell> getSpell(@Path("spell") String spell);
}
