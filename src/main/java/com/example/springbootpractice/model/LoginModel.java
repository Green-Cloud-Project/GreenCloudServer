package com.example.springbootpractice.model;

/**
 * {
 * "id": 1066779850,
 * "properties": {
 * "nickname": "김진흥",
 * "profile_image": "http://k.kakaocdn.net/dn/dgTNYk/btquE19LsNm/qar5eQCoz1wUAqfl97fpAK/profile_640x640s.jpg",
 * "thumbnail_image": "http://k.kakaocdn.net/dn/dgTNYk/btquE19LsNm/qar5eQCoz1wUAqfl97fpAK/profile_110x110c.jpg"
 * },
 * "kakao_account": {
 * "has_email": true,
 * "email_needs_agreement": true
 * }
 * }
 */
public class LoginModel {
    String id;
    String name;
    Properties properties;
    KakaoAccount kakao_account;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public KakaoAccount getKakao_account() {
        return kakao_account;
    }

    public void setKakao_account(KakaoAccount kakao_account) {
        this.kakao_account = kakao_account;
    }

    class Properties {
        String nickname;
        String profile_image;
        String thumbnail_image;

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getProfile_image() {
            return profile_image;
        }

        public void setProfile_image(String profile_image) {
            this.profile_image = profile_image;
        }

        public String getThumbnail_image() {
            return thumbnail_image;
        }

        public void setThumbnail_image(String thumbnail_image) {
            this.thumbnail_image = thumbnail_image;
        }
    }

    class KakaoAccount {
        boolean has_email;
        boolean email_needs_agreement;

        public boolean isHas_email() {
            return has_email;
        }

        public void setHas_email(boolean has_email) {
            this.has_email = has_email;
        }

        public boolean isEmail_needs_agreement() {
            return email_needs_agreement;
        }

        public void setEmail_needs_agreement(boolean email_needs_agreement) {
            this.email_needs_agreement = email_needs_agreement;
        }
    }

}
