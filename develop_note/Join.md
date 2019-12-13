# 회원가입/로그인 구현하기

## 로그인 프로세스 확인하기

<https://drive.google.com/file/d/10MfSWlB6t9WROkIM3FjbT9zfCwNsKo0u/view?usp=drivesdk>

<img src = ../images/join/1.png width = 900>

- 서버단에서는 로그인 success를 판단하는 부분을 구현
- 앱에서 SNS로그인 성공 시, 해당 로그인 플랫폼과 토큰을 전달
- 서버에서는 전달받은 토큰으로 해당 플렛폼 API를 통해 사용자 검색
- 검색한 사용자의 정보로 회원 가입을 시킨다.
- 회원가입을 성공하거나, 이미 가입된 사용자일경우에도 사용자의 토큰을 전달한다.



## 데이터 모델 서버 적용하기

<https://drive.google.com/file/d/1ml_gAsWWsfW9-0-s-kasotQdr4YfWb9C/view?usp=drivesdk>

<img src = ../images/join/2.png width = 500>

사전에 작업해놓은 데이터모델 문서를 바탕으로 아래와 같이 적용하였다.

<img src = ../images/join/3.png width = 900>



## 회원가입 구현하기

### User 모델

```
public class User {
    int user_id;
    String kakao_id;
    String name;
    String platform;
    String email;
    String img_url;
    String nation_id;
    String age;
    String sex;
    String city_id;
    String facebook_link;
    String instagram_link;

    public int getId() {
        return user_id;
    }

    public void setId(int user_id) {
        this.user_id = user_id;
    }

    public String getKakao_id() {
        return kakao_id;
    }

    public void setKakao_id(String kakao_id) {
        this.kakao_id = kakao_id;
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
}
```



### User Mapper 구현

```
@Mapper
public interface UserMapper {

    @Select("Select * from User")
    ArrayList<User> getAllUsers();

    /*@Insert("INSERT into " +
            "User(kakao_id, name, platform, email, img_url, nation_id, age, sex, city_id, facebook_link, instagram_link) " +
            "VALUES(#{kakao_id}, #{name}, #{platform}, #{email}, #{img_url}, #{nation_id}, #{age}, #{sex}, #{city_id}, #{facebook_link}, #{instagram_link})")
    void insertUser(@Param("kakao_id") String kakao_id,
                    @Param("name") String name,
                    @Param("platform") String platform,
                    @Param("email") String email,
                    @Param("img_url") String img_url,
                    @Param("nation_id") String nation_id,
                    @Param("age") String age,
                    @Param("sex") String sex,
                    @Param("city_id") String city_id,
                    @Param("facebook_link") String facebook_link,
                    @Param("instagram_link") String instagram_link);*/

    @Insert("INSERT into " +
            "User(kakao_id, name, platform, email, img_url, nation_id, age, sex, city_id, facebook_link, instagram_link) " +
            "VALUES(#{kakao_id}, #{name}, #{platform}, #{email}, #{img_url}, #{nation_id}, #{age}, #{sex}, #{city_id}, #{facebook_link}, #{instagram_link})")
    void insertUser(User user);

    @Select("Select * from User where kakao_id = #{kakao_id}")
    User findUser(@Param("kakao_id") String kakao_id);

}
```



### 컨트롤러에 회원 가입 구현

```
@PostMapping("/join")
    public @ResponseBody
    String join(@RequestParam("platform") String platform, @RequestParam("token") String token) {
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
            travelBuddyResponse.error_msg = e.toString();
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
```

