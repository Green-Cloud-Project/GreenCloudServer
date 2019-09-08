package com.example.springbootpractice;

import com.example.springbootpractice.model.RentalOffice;
import jdk.nashorn.internal.runtime.logging.Logger;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

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
    ArrayList<RentalOffice> findReatalOffice(@Param("lat") String lat, @Param("lon") String lon);
}
