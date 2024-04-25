package com.ohgiraffers.sessionsecurity.user.model.dto;

import com.ohgiraffers.sessionsecurity.common.UserRole;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LoginUserDTO {

    private int userCode;
    private String userID;
    private String userName;
    private String password;
    private UserRole userRole;

    public LoginUserDTO() { }

    public LoginUserDTO(int userCode, String userID, String userName, String password, UserRole userRole) {
        this.userCode = userCode;
        this.userID = userID;
        this.userName = userName;
        this.password = password;
        this.userRole = userRole;
    }

    // 가중 권한
    public List<String> getRole() {

        if(this.userRole.getRole().length()>0) {

            return Arrays.asList(this.userRole.getRole().split(","));

        }

        return new ArrayList<>();

    }

    public int getUserCode() {
        return userCode;
    }

    public void setUserCode(int userCode) {
        this.userCode = userCode;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    @Override
    public String toString() {
        return "LoginUserDTO{" +
                "userCode=" + userCode +
                ", userID='" + userID + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", userRole=" + userRole +
                '}';
    }
}
