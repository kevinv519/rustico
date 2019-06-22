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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(schema = "public", name = "empleado")
public class Employee {

	@Id
	@GeneratedValue(generator = "emp_code_gen", strategy = GenerationType.AUTO)
	@SequenceGenerator(name = "emp_code_gen", sequenceName = "public.empleado_codigo_seq", allocationSize = 1)
	@Column(name = "codigo")
	private Integer code;
	
	@NotBlank(message = "Nombre del empleado obligatorio")
	@Size(min = 3, max = 200)
	@Column(name = "nombre")
	private String name;
	
	@NotNull
	@Column(name = "edad")
	private Integer age;
	
	@NotBlank(message = "Debe especificar el sexo del empleado")
	@Pattern(regexp = "^[M|F]{1}$", message = "Debe ser M o F")
	@Column(name = "sexo")
	private String gender;
	
	@NotNull
	@Column(name = "estado")
	private boolean status;
	
	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "sucursal_id")
	private Store store;

	public Employee(Integer code, String name, Integer age, String gender, boolean status, Store store) {
		super();
		this.code = code;
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.status = status;
		this.store = store;
	}
	
	public Employee() {}

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

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}
	
	public String getStatusDelegate() {
		return status? "Activo" : "Inactivo";
	}
}
