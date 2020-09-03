package com.shantery.result2;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "user")
public class User implements Serializable {
	private static final long serialVersionUID = 3453583737318640866L;
	@Id
	@NotBlank
	@Size(min = 1, max = 20)
	private String loginid;
	@NotBlank
	@Size(min = 1, max = 20)
	private String password;
	@NotBlank
	@Size(min = 1, max = 20)
	private String username;
	@NotBlank
	private String profile;
	@NotBlank
	private String icon;


	public String getLoginid() {
		return loginid;
	}

	public void setLoginid(String loginid) {
		this.loginid = loginid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

}
