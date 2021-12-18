package microservice.test;

import microservice.SpellTester;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.mockito.Mockito.when;

/**
 * Configuration for testing the microservice
 */


@Configuration
public class ConfigTest {

    @Mock
    SpellTester mockSpellTester;

    // premade queries
    static Map<String,Integer> tests = new HashMap<>() {{
        put("acid-arrow", 2);
        put("call-lightning", 3);
        put("bless", 1);
        put("power-word-kill", 9);
        put("delayed-blast-fireball", 7);
        put("magnificent-mansion", 7);
        put("freedom-of-movement",4);
    }};

    @Bean
    SpellTester getMockSpellTester() {

        MockitoAnnotations.openMocks(this);

        for (String v : tests.keySet())
            when(mockSpellTester.checkLevel(v)).thenReturn(tests.get(v));

        return mockSpellTester;
    }


    @Bean(name = "spellsToTest")
    @Primary
    public static Set<String> getTests() {
        return tests.keySet();
    }
}
