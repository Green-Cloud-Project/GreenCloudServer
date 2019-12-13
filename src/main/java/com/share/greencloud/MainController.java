package com.share.greencloud;

import com.share.greencloud.model.GreenCloudRestResponse;
import com.share.greencloud.model.RentalHistory;
import com.share.greencloud.model.RentalOffice;
import com.share.greencloud.model.Stock;
import com.google.gson.Gson;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

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
        TBLog.d(token);
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

    //즐겨찾기 추가
    @RequestMapping("/addFavority")
    public @ResponseBody
    GreenCloudRestResponse addFavority(@RequestHeader Map<String, String> headers, @RequestParam("office_id") int office_id) {
        GreenCloudRestResponse greenCloudRestResponse = new GreenCloudRestResponse();
        try {
            User user = userMapper.getUser(headers.get("token"));
            userMapper.addFavority(user.getUser_id(), office_id);
        } catch (Exception e) {
            greenCloudRestResponse.setResult(-1);
            greenCloudRestResponse.setErrorMessage("즐겨찾기 추가 실패: " + e.toString());
        }
        return greenCloudRestResponse;
    }

    //줄겨찾기 제거
    @RequestMapping("/deleteFavority")
    public @ResponseBody
    GreenCloudRestResponse deleteFavority(@RequestHeader Map<String, String> headers, @RequestParam("office_id") int office_id) {
        GreenCloudRestResponse greenCloudRestResponse = new GreenCloudRestResponse();
        try {
            User user = userMapper.getUser(headers.get("token"));
            boolean b = userMapper.deleteFavority(user.getUser_id(), office_id);
            greenCloudRestResponse.setResult(b ? 0 : -1);
        } catch (Exception e) {
            greenCloudRestResponse.setResult(-1);
            greenCloudRestResponse.setErrorMessage("즐겨찾기 삭제 실패: " + e.toString());
        }
        return greenCloudRestResponse;
    }

    //즐겨찾기 목록
    @RequestMapping("/listFavority")
    public @ResponseBody
    GreenCloudRestResponse<ArrayList<RentalOffice>> listFavority(@RequestHeader Map<String, String> headers) {
        GreenCloudRestResponse<ArrayList<RentalOffice>> greenCloudRestResponse = new GreenCloudRestResponse<>();
        try {
            User user = userMapper.getUser(headers.get("token"));
            greenCloudRestResponse.setModel(userMapper.listFavority(user.getUser_id()));
            //예약번호로 다시 조회하여 결과를 리턴
        } catch (Exception e) {
            greenCloudRestResponse.setResult(-1);
            greenCloudRestResponse.setErrorMessage("즐겨찾기 가져오기 실패: " + e.toString());
            System.out.println(e.toString());
        }
        return greenCloudRestResponse;
    }

    //우산 빌리기
    @RequestMapping("/rentUmbrella")
    public @ResponseBody
    GreenCloudRestResponse rentUmbrella(@RequestHeader Map<String, String> headers, @RequestParam("qr_code") String qr_code) {
        GreenCloudRestResponse greenCloudRestResponse = new GreenCloudRestResponse<>();
        User user = userMapper.getUser(headers.get("token"));

        if (user == null) {
            greenCloudRestResponse.setResult(-1);
            greenCloudRestResponse.setErrorMessage("토큰만료");
            return greenCloudRestResponse;
        }

        Stock stock = userMapper.getStock(qr_code);
        //재고 상태 바꿔주고
        if (stock == null) {
            greenCloudRestResponse.setResult(-1);
            greenCloudRestResponse.setErrorMessage("대여 실패: 존재하지 않는 우산 코드 입니다.");
            return greenCloudRestResponse;
        }


        try {
            if (stock.getStatus().equals("ready")) {
                //대여기록에 넣어주기
                userMapper.rentStock(qr_code);
                SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd_hh:mm:ss");
                String startTime = format.format(new Date(System.currentTimeMillis()));
                userMapper.rentUmbrella(startTime, stock.getOffice_id(), stock.getUmbrella_id(), user.getUser_id(), "rented");
            } else {
                greenCloudRestResponse.setResult(-1);
                greenCloudRestResponse.setErrorMessage("대여 실패: 이미 대여중입니다.");
            }
            //예약번호로 다시 조회하여 결과를 리턴
        } catch (Exception e) {
            greenCloudRestResponse.setResult(-1);
            greenCloudRestResponse.setErrorMessage("대여 실패: " + e.toString());
            System.out.println(e.toString());
        }
        return greenCloudRestResponse;
    }


    //우산 빌리기
    @RequestMapping("/getRentalHistory")
    public @ResponseBody
    GreenCloudRestResponse getRentalHistory(@RequestHeader Map<String, String> headers) {
        GreenCloudRestResponse<ArrayList<RentalHistory>> greenCloudRestResponse = new GreenCloudRestResponse<>();
        User user = userMapper.getUser(headers.get("token"));

        if (user == null) {
            greenCloudRestResponse.setResult(-1);
            greenCloudRestResponse.setErrorMessage("토큰만료");
            return greenCloudRestResponse;
        }

        try {
            greenCloudRestResponse.setModel(userMapper.getRentalHistory(user.getUser_id()));
            //예약번호로 다시 조회하여 결과를 리턴
        } catch (Exception e) {
            greenCloudRestResponse.setResult(-1);
            greenCloudRestResponse.setErrorMessage("대여 실패: " + e.toString());
            System.out.println(e.toString());
        }
        return greenCloudRestResponse;
    }
}
