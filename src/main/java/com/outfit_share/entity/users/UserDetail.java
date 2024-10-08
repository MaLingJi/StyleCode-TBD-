package com.outfit_share.entity.users;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.outfit_share.entity.orders.Orders;
import com.outfit_share.entity.post.Comment;
import com.outfit_share.entity.post.Post;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "userDetail")
public class UserDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "real_name")
    private String realName;

    @Column(name = "user_address")
    private String address;

    @Column(name = "user_phone")
    private String phone;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedTime;

    @Column(name = "user_photo")
    private String userPhoto;

    @Column(name = "discount_points")
    private Integer discountPoints;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @MapsId
    private Users users;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "userDetail")
    private List<CreditCards> creditCards;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "userDetail")
    private List<Notifications> notifications;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "userDetail")
    @JsonManagedReference
    private List<Post> post;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "userDetail")
    @JsonManagedReference
    private List<Comment> comment;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "userDetail")
    private List<Orders> orders;

    @PrePersist
    public void onCreate() {
        if (createdTime == null) {
            createdTime = new Date();
        }
        if (updatedTime == null) {
            updatedTime = new Date();
        }
    }

    public UserDetail() {
    }

    public UserDetail(Integer id, String userName, String realName, String address, String phone, Date createdTime,
            Date updatedTime, String userPhoto, Integer discountPoints, Users users, List<CreditCards> creditCards,
            List<Notifications> notifications, List<Post> post, List<Comment> comment, List<Orders> orders) {
        this.id = id;
        this.userName = userName;
        this.realName = realName;
        this.address = address;
        this.phone = phone;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
        this.userPhoto = userPhoto;
        this.discountPoints = discountPoints;
        this.users = users;
        this.creditCards = creditCards;
        this.notifications = notifications;
        this.post = post;
        this.comment = comment;
        this.orders = orders;
    }

    public UserDetail(String userName, String realName, String address, String phone, Date createdTime,
            Date updatedTime, String userPhoto, Integer discountPoints, Users users, List<CreditCards> creditCards,
            List<Notifications> notifications, List<Post> post, List<Comment> comment, List<Orders> orders) {
        this.userName = userName;
        this.realName = realName;
        this.address = address;
        this.phone = phone;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
        this.userPhoto = userPhoto;
        this.discountPoints = discountPoints;
        this.users = users;
        this.creditCards = creditCards;
        this.notifications = notifications;
        this.post = post;
        this.comment = comment;
        this.orders = orders;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public List<CreditCards> getCreditCards() {
        return creditCards;
    }

    public void setCreditCards(List<CreditCards> creditCards) {
        this.creditCards = creditCards;
    }

    public List<Notifications> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notifications> notifications) {
        this.notifications = notifications;
    }

    public List<Post> getPost() {
        return post;
    }

    public void setPost(List<Post> post) {
        this.post = post;
    }

    public List<Comment> getComment() {
        return comment;
    }

    public void setComment(List<Comment> comment) {
        this.comment = comment;
    }

    public List<Orders> getOrders() {
        return orders;
    }

    public void setOrders(List<Orders> orders) {
        this.orders = orders;
    }

}
