package edu.tus.car.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import edu.tus.car.model.Car;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CarAppIT {

	@Value(value="${local.server.port}")
	private int port;
	
	TestRestTemplate restTemplate;
	HttpHeaders headers;
	
	@BeforeEach
	public void setup() {
		restTemplate =new TestRestTemplate();
		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
	}

	/*
	 * @Test
	 * 
	 * @Sql({"/testdata.sql"}) public void addCarSuccessInTest() { HttpEntity<Car>
	 * request = new HttpEntity<Car>(buildCar(),headers); ResponseEntity<String>
	 * response = restTemplate.postForEntity("http://localhost:"+port+"/api/cars",
	 * request, String.class);
	 * assertEquals(HttpStatus.CREATED,response.getStatusCode()); }
	 */
		 
	
	@Test
	@Sql({"/testdata.sql"})
	public void addCarEmptyFieldInTest()
	{
		Car car=buildCar();
		car.setModel("");
		HttpEntity<Car> request = new HttpEntity<Car>(car,headers);
		ResponseEntity<String>	response =	restTemplate.postForEntity("http://localhost:"+port+"/api/cars", request, String.class);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());;
	}
	
	@Test
	@Sql({"/testdata.sql"})
	public void addCarlAlreadyExistsInTest()
	{
		HttpEntity<Car> request = new HttpEntity<Car>(buildCar(),headers);
		ResponseEntity<String>	response =	restTemplate.postForEntity("http://localhost:"+port+"/api/cars", request, String.class);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}
	
	@Test
	@Sql({"/testdata.sql"})
	public void addYearInvalidInTest()
	{
		Car car=buildCar();
		car.setYear(2023);
		HttpEntity<Car> request = new HttpEntity<Car>(car,headers);
		ResponseEntity response =	restTemplate.postForEntity("http://localhost:"+port+"/api/cars", request, String.class);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}
	
	Car buildCar() {
		//make", "model", "year", "color"
		Car car = new Car();
		car.setMake("Mercedes");
		car.setModel("X5");
		car.setYear(2020);
		car.setColor("Black");
		return car;
	}

}
