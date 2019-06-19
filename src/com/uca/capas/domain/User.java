package com.uca.capas.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(schema = "public", name = "usuario")
public class User {

	@Id
	@GeneratedValue(generator = "user_code_gen", strategy = GenerationType.AUTO)
	@SequenceGenerator(name = "user_code_gen", sequenceName = "public.usuario_codigo_seq", allocationSize = 1)
	@Column(name = "codigo")
	private Integer code;
	
	@Column(name = "usuario")
	private String username;
	
	@Column(name = "clave")
	private String password;

	public User() {
	}
	
	public User(Integer code, String username, String password) {
		super();
		this.code = code;
		this.username = username;
		this.password = password;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
