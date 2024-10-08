package com.outfit_share.entity.users;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class Users {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Integer id;

	@Column(name = "user_email", unique = true)
	private String email;

	@Column(name = "user_password")
	private String pwd;

	@Column(name = "user_permissions")
	private String permissions;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "users")
	@PrimaryKeyJoinColumn
	private UserDetail userDetail;

	public Users() {
		super();
	}

	public Users(Integer id, String email, String pwd, String permissions, UserDetail userDetail) {
		this.id = id;
		this.email = email;
		this.pwd = pwd;
		this.permissions = permissions;
		this.userDetail = userDetail;
	}

	public Users(String email, String pwd, String permissions, UserDetail userDetail) {
		this.email = email;
		this.pwd = pwd;
		this.permissions = permissions;
		this.userDetail = userDetail;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getPermissions() {
		return permissions;
	}

	public void setPermissions(String permissions) {
		this.permissions = permissions;
	}

	public UserDetail getUserDetail() {
		return userDetail;
	}

	public void setUserDetail(UserDetail userDetail) {
		this.userDetail = userDetail;
	}

}
