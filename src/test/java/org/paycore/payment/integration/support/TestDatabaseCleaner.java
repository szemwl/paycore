package org.paycore.payment.integration.support;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TestDatabaseCleaner {

    private final JdbcTemplate jdbcTemplate;

    public void clean() {
        jdbcTemplate.execute("TRUNCATE TABLE orders CASCADE");
    }
}