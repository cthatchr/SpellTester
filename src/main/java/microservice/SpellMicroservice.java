package microservice;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import retrofit2.http.Query;


/**
 * Implementation of the microservice
 */

@Service
@RestController
public class SpellMicroservice {

    SpellTester spellTester;

    @Autowired
    public SpellMicroservice(SpellTester spellTester) {
        this.spellTester = spellTester;
    }

    @GetMapping("/info")
    public String info() {
        return "Checks the level of a spell from DnD 5th Edition. Use '/spell/?s=acid-arrow'. Use dashes (-) instead of spaces. Ex. Acid Arrow -> acid-arrow";
    }

    @GetMapping("/spell")
    public String spelltest(@Query("s") String s) {
        return new Gson().toJson((new Response(s, spellTester.checkLevel(s))));
    }
}