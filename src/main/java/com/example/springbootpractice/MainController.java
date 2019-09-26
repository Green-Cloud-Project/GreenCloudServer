package com.example.springbootpractice;

import com.example.springbootpractice.model.RentalOffice;
import com.google.gson.Gson;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.hexlant.tb.wallet.common.TBLog;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;

@Controller
public class MainController {

    private final UserMapper userMapper;

    public MainController(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @RequestMapping("/")
    public @ResponseBody
    String init() {
        return "init";
    }

    @RequestMapping("/user")
    public @ResponseBody
    String user() {
        ArrayList<User> list = userMapper.getAllUsers();
        return "" + list.size();
    }

    @RequestMapping("/findRentalOffice")
    public @ResponseBody
//    ArrayList<RentalOffice> findRentalOffice(@RequestParam("lat") String lat, @RequestParam("lon") String lon) {
////        ArrayList<RentalOffice> list = userMapper.findReatalOffice(lat, lon);
//        ArrayList<RentalOffice> list = userMapper.findReatalOffice();
//        return list;
//    }
    ArrayList<RentalOffice> findRentalOffice() {
        ArrayList<RentalOffice> list = userMapper.findReatalOffice();
        return list;
    }

    @PostMapping("/join")
    public @ResponseBody
    String join(@RequestParam("platform") String platform, @RequestParam("token") String token) {
        org.apache.ibatis.logging.LogFactory.useSlf4jLogging();
        com.hexlant.tb.wallet.common.TBLog.d(token);
        User user = new User();
        user.setToken(token);
        user.setPlatform(platform);
        TravelBuddyResponse<User> travelBuddyResponse = new TravelBuddyResponse<>();

        //SNS 회원 정보 요청하기
        try {
            user.requestInfo();
        } catch (Exception e) {
            travelBuddyResponse.response = 500;
            travelBuddyResponse.error_msg = e.getMessage();
            return response(travelBuddyResponse);
        }

        //내부서버 회원 정보 가져오기
        try {
            user.getUser(userMapper);
        } catch (Exception e) {
            travelBuddyResponse.response = 500;
            travelBuddyResponse.error_msg = e.toString();
            return response(travelBuddyResponse);
        }

        //토큰 로드
        try {
            user.loadToken(userMapper);
        } catch (Exception e) {
            travelBuddyResponse.response = 500;
            travelBuddyResponse.error_msg = e.toString();
            return response(travelBuddyResponse);
        }

        travelBuddyResponse.data = user;
        return response(travelBuddyResponse);
    }

    public static String response(Object o) {
        String s = new Gson().toJson(o);
        TBLog.d(s);
        return s;
    }

    @RequestMapping("restTempleteTest")
    public @ResponseBody
    String restTempleteTest() {
        TBRestTemplete restTemplate = new TBRestTemplete() {
            @Override
            void onError(ClientHttpResponse response) {

            }
        };

        final String uri = "https://kapi.kakao.com/v2/user/me";
        //final String uri = "https://graph.facebook.com/v3.2/me";
        return restTemplate.postForObject(uri, null, String.class);
    }


}
