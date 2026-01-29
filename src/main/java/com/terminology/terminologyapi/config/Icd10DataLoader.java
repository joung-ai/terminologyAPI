package com.terminology.terminologyapi.config;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@Profile("prod")
public class Icd10DataLoader implements ApplicationRunner {

    private final JdbcTemplate jdbcTemplate;

    public Icd10DataLoader(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM icd10",
                Integer.class
        );

        if (count != null && count > 0) {
            System.out.println("ℹ ICD-10 already exists, skipping import");
            return;
        }

        var resource = new ClassPathResource("db/import/icd10_data.sql");
        String sql = new String(resource.getInputStream().readAllBytes());

        jdbcTemplate.execute(sql);
        System.out.println("ICD-10 IMPORTED SUCCESSFULLY");
    }
}
