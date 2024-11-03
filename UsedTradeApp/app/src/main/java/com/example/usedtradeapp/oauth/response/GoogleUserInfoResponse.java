package com.example.usedtradeapp.oauth.response;

import com.google.gson.annotations.SerializedName;

public class GoogleUserInfoResponse {

    @SerializedName("sub")
    private String id; // 사용자의 고유 ID

    @SerializedName("name")
    private String name; // 사용자의 이름

    @SerializedName("given_name")
    private String givenName; // 사용자의 이름 (성)

    @SerializedName("family_name")
    private String familyName; // 사용자의 성

    @SerializedName("picture")
    private String pictureUrl; // 프로필 사진 URL

    @SerializedName("email")
    private String email; // 사용자의 이메일 주소

    @SerializedName("email_verified")
    private boolean emailVerified; // 이메일 확인 여부

    @SerializedName("locale")
    private String locale; // 사용자의 지역

    // Getters 및 Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    @Override
    public String toString() {
        return "GoogleUserInfoResponse{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", givenName='" + givenName + '\'' +
                ", familyName='" + familyName + '\'' +
                ", pictureUrl='" + pictureUrl + '\'' +
                ", email='" + email + '\'' +
                ", emailVerified=" + emailVerified +
                ", locale='" + locale + '\'' +
                '}';
    }
}
