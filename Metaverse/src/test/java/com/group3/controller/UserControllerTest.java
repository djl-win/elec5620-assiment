package com.group3.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//open virtual MVC call
@AutoConfigureMockMvc
public class UserControllerTest {
    @Test
    public void testLogin(@Autowired MockMvc mvc) throws Exception {

    }
}
