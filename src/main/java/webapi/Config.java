package webapi;

import microservice.SpellMicroservice;
import microservice.SpellTester;
import spelltester.SpellTesterImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class Config {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Bean
    public SpellTester getSpellTester() { return new SpellTesterImpl(jdbcTemplate); }

    @Bean
    public SpellMicroservice getSpellMicroservice() {  return new SpellMicroservice(this.getSpellTester()); }
}
