package org.example.unibelltask;

import org.example.unibelltask.annotation.PostgresqlTestContainer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@PostgresqlTestContainer
@SpringBootTest(useMainMethod = SpringBootTest.UseMainMethod.ALWAYS)
class UnibellTaskApplicationTests {

    @Test
    void contextLoads() {

    }
}
