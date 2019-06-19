package com.uca.capas.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(schema = "public", name = "empleado")
public class Employee {

	@Id
	@GeneratedValue(generator = "emp_code_gen", strategy = GenerationType.AUTO)
	@SequenceGenerator(name = "emp_code_gen", sequenceName = "public.empleado_codigo_seq", allocationSize = 1)
	@Column(name = "codigo")
	private Integer code;
	
	@Column(name = "nombre")
	private String name;
	
	@Column(name = "edad")
	private Integer age;
	
	@Column(name = "sexo")
	private char gender;
	
	@Column(name = "estado")
	private boolean status;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "sucursal_id")
	private Store store;

	public Employee(Integer code, String name, Integer age, char gender, boolean status, Store store) {
		super();
		this.code = code;
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.status = status;
		this.store = store;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public char getGender() {
		return gender;
	}

	public void setGender(char gender) {
		this.gender = gender;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Store getStoreId() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}
	
	public String getStatusDelegate() {
		return status? "Activo" : "Inactivo";
	}
}
