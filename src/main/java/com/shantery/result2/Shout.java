package com.shantery.result2;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="shout")
public class Shout implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long shout_id;
	private String shout;
	private String shout_username;
	private String icon;

	public Long getShout_id() {
		return shout_id;
	}
	public void setShout_id(Long shout_id) {
		this.shout_id = shout_id;
	}
	public String getShout() {
		return shout;
	}
	public void setShout(String shout) {
		this.shout = shout;
	}


	public String getShout_username() {
		return shout_username;
	}
	public void setShout_username(String shout_username) {
		this.shout_username = shout_username;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}


}
