package microservice.test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import microservice.Response;
import microservice.SpellTester;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testing for the microservice
 */

@ExtendWith(MockitoExtension.class)
public class SpellMicroserviceTest {

    static Endpoints endpoints;
    static final String BaseUrl = "http://127.0.0.1:8080/"; // Location of the web api
    static SpellTester mockSpellTester;
    static Set<String> spellsToTest;

    @BeforeAll
    static void init() {

        Gson gson = new GsonBuilder().
                create();

        Retrofit client = new Retrofit
                .Builder()
                .baseUrl(BaseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        endpoints = client.create(Endpoints.class);

        ApplicationContext context = new AnnotationConfigApplicationContext(ConfigTest.class);

        mockSpellTester = context.getBean(SpellTester.class);
        spellsToTest = (Set<String>) context.getBean("spellsToTest");


    }

    // test microservice
    @Test
    void spellChecks() {
        try {

            for (String spell : spellsToTest) {

                // call our api
                Call<Response> spellTestResponse = endpoints.getSpellTestResponse(spell);
                Response resp = spellTestResponse.execute().body();
                System.out.println(spell + ':' + resp.getLevel());
                assertNotNull(resp);

                assertEquals(resp.getSpell(), spell);
                // compare results
                assertEquals(resp.getLevel(), mockSpellTester.checkLevel(spell));
            }
        } catch (Exception e) {
            assertTrue(false);
        }
    }
}
