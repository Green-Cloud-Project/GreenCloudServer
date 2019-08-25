package com.example.springbootpractice;

import com.example.springbootpractice.model.FBLoginModel;
import com.example.springbootpractice.model.LoginModel;
import com.google.gson.Gson;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.MultiValueMap;

public class User {
    private int user_id;
    private String kakao_id;
    private String fb_id;
    private String name;
    private String platform;
    private String email;
    private String img_url;
    private String nation_id;
    private String age;
    private String sex;
    private String city_id;
    private String facebook_link;
    private String instagram_link;
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getKakao_id() {
        return kakao_id;
    }

    public void setKakao_id(String kakao_id) {
        this.kakao_id = kakao_id;
    }

    public void setFb_id(String fb_id) {
        this.fb_id = fb_id;
    }

    public String getFb_id() {
        return fb_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getNation_id() {
        return nation_id;
    }

    public void setNation_id(String nation_id) {
        this.nation_id = nation_id;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getFacebook_link() {
        return facebook_link;
    }

    public void setFacebook_link(String facebook_link) {
        this.facebook_link = facebook_link;
    }

    public String getInstagram_link() {
        return instagram_link;
    }

    public void setInstagram_link(String instagram_link) {
        this.instagram_link = instagram_link;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public void requestInfo() throws Exception {
        if (platform.equals("KAKAO")) {
            getKakaoProfile();
        } else if (platform.equals("FB")) {
            getFaceBookProfile();
        } else
            throw new Exception(platform + " 플렛폼 로그인은 준비중입니다");
    }

    private void getFaceBookProfile() throws Exception {
        final String uri = "https://graph.facebook.com/v3.2/me";
        String s = connSnsApi(uri, null);
        FBLoginModel fbLoginModel = new Gson().fromJson(s, FBLoginModel.class);
        if (fbLoginModel.getError() != null)
            throw new Exception(fbLoginModel.getError().getMessage());

        //페이스북 아이디를 넣어줘야함
        setFb_id(fbLoginModel.getId());
    }

    private void getKakaoProfile() throws Exception {
        final String uri = "https://kapi.kakao.com/v2/user/me";
        String s = connSnsApi(uri, null);
        LoginModel loginModel = new Gson().fromJson(s, LoginModel.class);
        if (loginModel.getId() == null)
            throw new Exception(s);

        //카카오 정보 가져옴
        setKakao_id(loginModel.getId());
    }

    private String connSnsApi(String uri, MultiValueMap<String, String> parameters) {
        //헤더 추가
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + token);
        com.hexlant.tb.wallet.common.TBLog.d(token);
        //파라미터 추가
        HttpEntity requestEntity = new HttpEntity("parameters", headers);
        ResponseEntity<String> response = new TBRestTemplete() {
            @Override
            void onError(ClientHttpResponse response) {

            }
        }.exchange(uri, HttpMethod.GET, requestEntity, String.class);
        String result = response.getBody();
        com.hexlant.tb.wallet.common.TBLog.d(result);
        return result;
    }

    public void getUser(UserMapper userMapper) throws Exception {
        try {
            userMapper.insertUser(this);
            //아이디 삽입 성공 시 토큰 생성
        } catch (Exception e) {
            //삽입 실패 이미 가입되어있는 회원인지 찾아보기
            User user = null;
            if (platform.equals("KAKAO"))
                user = userMapper.findUser(kakao_id);
            if (platform.equals("FB"))
                user = userMapper.findUserFB(fb_id);

            if (user == null) {
                new Exception("회원가입은 안되고 회원 가입도 안되어있는 회원? 아니면 로그인 플렛폼 설정오류");
            }
            setUser_id(user.getUser_id());
        }

        try {
            createToken(userMapper);
        } catch (Exception e) {
            new Exception("토큰 생성 실패");
        }
    }

    public void createToken(UserMapper userMapper) throws Exception {

        try {
            userMapper.createToken(user_id, "" + System.currentTimeMillis());
            String t = userMapper.findToken(user_id);
            token = t;
            if (t == null) {
                new Exception("토큰 없음");
            }
        } catch (Exception e) {
            new Exception("토큰 생성 실패");
        }

    }

    public void loadToken(UserMapper userMapper) {
        try {
            token = userMapper.findToken(user_id);
        } catch (Exception e) {
            new Exception("토큰 검색 실패");
        }
    }

    public boolean hasSnsId() {
        return fb_id != null || kakao_id != null;
    }
}
