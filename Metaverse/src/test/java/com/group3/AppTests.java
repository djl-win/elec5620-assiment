package com.group3;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class AppTests {

    @Test
    void contextLoads() {
        double count = (double) 43 / (double) 4;
        double ceil = Math.ceil(count);

        System.out.println((int)ceil);
    }

}
