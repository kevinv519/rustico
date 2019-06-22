package com.uca.capas.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


public class EmployeeDTO {
	
	private Integer code;
	
	@NotBlank(message = "Nombre del empleado obligatorio")
	@Size(min = 3, max = 200, message = "La longitud del nombre debe estar entre 3 y 200 caracteres")
	private String name;
	
	@NotNull(message = "Edad del empleado obligatoria")
	@Min(value = 18, message = "El empleado debe ser mayor de 18 de años")
	private Integer age;
	
	@NotBlank(message = "Debe especificar el sexo del empleado")
	@Pattern(regexp = "^[M|F]{1}$", message = "Debe ser M o F")
	private String gender;
	
	@NotNull(message = "Debe seleccionar si el empleado está activo o no")
	private boolean status;
	
	@NotNull(message = "Debe seleccionar la sucursal en la que trabaja el empleado")
	private Integer storeId;
	
	public EmployeeDTO() {}

	public EmployeeDTO(Integer code, String name, Integer age, String gender,
			boolean status, Integer storeId) {
		super();
		this.code = code;
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.status = status;
		this.storeId = storeId;
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

	public Integer getStoreId() {
		return storeId;
	}

	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}
	
}
