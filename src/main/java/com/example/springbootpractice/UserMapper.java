package com.example.springbootpractice;

import com.example.springbootpractice.model.RentalOffice;
import jdk.nashorn.internal.runtime.logging.Logger;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.ArrayList;
import java.util.Map;

@Logger
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

    @Insert("INSERT into " + "User(kakao_id, fb_id, name, platform, email, img_url, nation_id, age, sex, city_id, facebook_link, instagram_link) " + "VALUES(#{kakao_id},#{fb_id}, #{name}, #{platform}, #{email}, #{img_url}, #{nation_id}, #{age}, #{sex}, #{city_id}, #{facebook_link}, #{instagram_link})")
    void insertUser(User user);

    @Select("Select * from User where kakao_id = #{kakao_id}")
    User findUser(@Param("kakao_id") String kakao_id);

    @Select("Select * from User where fb_id = #{fb_id}")
    User findUserFB(@Param("fb_id") String fb_id);

    @Insert("INSERT into Token(user_id, token) VALUES(#{user_id}, #{token})")
    void createToken(@Param("user_id") int user_id, @Param("token") String token);

    @Select("Select token from Token where user_id = #{user_id}")
    String findToken(@Param("user_id") int user_id);

    @Select("Select * from rental_office")
        //    ArrayList<RentalOffice> findReatalOffice(@Param("lat") String lat, @Param("lon") String lon);
    ArrayList<RentalOffice> findReatalOffice();

    @Select("Select * from rental_office where office_name like %#{office_name}%")
    ArrayList<RentalOffice> searchReatalOffice();

    @Select("SELECT rental_office.* FROM favority  JOIN rental_office ON favority.office_id = rental_office.office_id WHERE user_id = #{user_id}")
    ArrayList<RentalOffice> listFavority(@Param("user_id") int user_id);

    @Insert("INSERT INTO favority(user_id, office_id) values(#{user_id}, #{office_id})")
    void addFavority(@Param("user_id") int user_id, @Param("office_id") int office_id);

    @Delete("DELETE FROM favority WHERE user_id=#{user_id} AND office_id=#{office_id}")
    boolean deleteFavority(@Param("user_id") int user_id, @Param("office_id") int office_id);

    /**
     * 토큰으로 사용자 아이디 가져오기
     *
     * @param token
     * @return
     */
    @Select("Select * from Token where token = #{token}")
    User getUser(@Param("token") String token);
}
