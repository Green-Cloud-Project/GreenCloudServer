package com.example.springbootpractice;

import com.example.springbootpractice.model.FBLoginModel;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FbLoginTests {

    String tokenExpriedDummy = "{\n" +
            "    \"error\": {\n" +
            "        \"message\": \"Error validating access token: Session has expired on Tuesday, 30-Apr-19 09:00:00 PDT. The current time is Tuesday, 30-Apr-19 23:55:39 PDT.\",\n" +
            "        \"type\": \"OAuthException\",\n" +
            "        \"code\": 190,\n" +
            "        \"error_subcode\": 463,\n" +
            "        \"fbtrace_id\": \"GWEmiZ5RPxE\"\n" +
            "    }\n" +
            "}";

    @Test
    public void test() {
        System.out.println("------ Facebook Login Test ------");

        System.out.println(tokenExpriedDummy);

        FBLoginModel fbLoginTests = new Gson().fromJson(tokenExpriedDummy, FBLoginModel.class);

        //에러가 있다면
        if(fbLoginTests.getError() != null){
            System.out.println(fbLoginTests.getError().getMessage());
        }

    }

}
