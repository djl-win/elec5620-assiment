package com.group3.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.ContentResultMatchers;
import org.springframework.test.web.servlet.result.HeaderResultMatchers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.result.StatusResultMatchers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//open virtual MVC call
@AutoConfigureMockMvc
public class UserControllerTest {
    @Test
    public void testLogin(@Autowired MockMvc mvc) throws Exception {
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.get("/pictures/1/10?pictureStatus=700");
        ResultActions perform = mvc.perform(mockHttpServletRequestBuilder);

        //validate the status
        StatusResultMatchers status = MockMvcResultMatchers.status();
        ResultMatcher ok = status.isOk(); //200 status code
        perform.andExpect(ok); //match the 200 and return status

        //validate the json data
        ContentResultMatchers content = MockMvcResultMatchers.content();
        ResultMatcher json = content.json("{\"data\":[{\"pictureId\":13,\"pictureName\":\"testInsertPicture\",\"pictureDate\":\"2022-07-21T14:00:00.000+00:00\",\"pictureStatus\":700,\"pictureDeleted\":0,\"pictureVersion\":1},{\"pictureId\":21,\"pictureName\":\"testInsertPicture\",\"pictureDate\":\"2022-07-26T14:00:00.000+00:00\",\"pictureStatus\":700,\"pictureDeleted\":0,\"pictureVersion\":1}],\"code\":40011,\"msg\":\"SELECT SUCCESSFUL\"}");
        perform.andExpect(json);

        //validate the content type
        HeaderResultMatchers header = MockMvcResultMatchers.header();
        ResultMatcher contentType = header.string("Content-Type","application/json");
        perform.andExpect(contentType);


    }
}
