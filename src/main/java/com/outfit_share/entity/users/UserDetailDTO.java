package com.outfit_share.entity.users;

import java.util.Date;

public class UserDetailDTO {

    private Integer userId;
    private String userEmail;
    private String permissions;
    private String userName;
    private String realName;
    private String address;
    private String phone;
    private Date createdTime;
    private Date updatedTime;
    private String userPhoto;
    private Integer discountPoints;

    public UserDetailDTO() {
    }

    public UserDetailDTO(Integer userId, String userEmail, String permissions, String userName, String realName,
            String address, String phone, Date createdTime, Date updatedTime, String userPhoto,
            Integer discountPoints) {
        this.userId = userId;
        this.userEmail = userEmail;
        this.permissions = permissions;
        this.userName = userName;
        this.realName = realName;
        this.address = address;
        this.phone = phone;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
        this.userPhoto = userPhoto;
        this.discountPoints = discountPoints;
    }

    public UserDetailDTO(String userEmail, String permissions, String userName, String realName, String address,
            String phone, Date createdTime, Date updatedTime, String userPhoto, Integer discountPoints) {
        this.userEmail = userEmail;
        this.permissions = permissions;
        this.userName = userName;
        this.realName = realName;
        this.address = address;
        this.phone = phone;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
        this.userPhoto = userPhoto;
        this.discountPoints = discountPoints;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public Integer getDiscountPoints() {
        return discountPoints;
    }

    public void setDiscountPoints(Integer discountPoints) {
        this.discountPoints = discountPoints;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

}
