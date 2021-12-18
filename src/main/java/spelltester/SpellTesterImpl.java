package spelltester;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import microservice.SpellTester;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



/**
 * Implementation of SpellTester
 * Spells are stored in the persistent db. If the queried spell is not in the db, we call the external api to check.
 */

@Component
public class SpellTesterImpl implements SpellTester {

    private JdbcTemplate jdbcTemplate;
    static SpellService endpoints;

    @Autowired
    public SpellTesterImpl(JdbcTemplate jdbcTemplate) {

        Gson gson = new GsonBuilder()
                .create();

        //the external web api being used to check a spell's level
        Retrofit client = new Retrofit.Builder()
                .baseUrl("https://www.dnd5eapi.co/api/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        endpoints = client.create(SpellService.class);

        this.jdbcTemplate = jdbcTemplate;
        jdbcTemplate.execute("DROP TABLE spelltests IF EXISTS");
        jdbcTemplate.execute(
                "CREATE TABLE spelltests(" +
                        "id SERIAL, " +
                        "spell VARCHAR(255), " +
                        " level INT)"
        );
    }


    public int checkLevel(String s) {

        String querystr = String.format("SELECT level FROM spelltests WHERE spell = '%s'", s);

        // check if spell is in db
        try {

            Integer level = jdbcTemplate.queryForObject(querystr, Integer.class); // Integer level
            return level;

        } catch (Exception e) {

            // not in the db; search web api, then add to db
            Call<Spell> call = endpoints.getSpell(s);

            try {
                // store the spell information into an object
                Spell sp = call.execute().body();
                // System.out.println("Not in db");
                querystr = String.format("INSERT into spelltests (spell,level) VALUES ('%s',%d)", s, sp.getLevel());
                jdbcTemplate.execute(querystr);
                return sp.getLevel();

            } catch (Exception e1) {
                return 999;
            }

        }
    }

}