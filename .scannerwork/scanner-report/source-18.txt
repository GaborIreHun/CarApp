package edu.tus.car.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import edu.tus.car.errors.ErrorMessage;
import edu.tus.car.errors.ErrorMessages;
import edu.tus.car.exception.CarValidationException;
import edu.tus.car.model.Car;
import edu.tus.car.service.CarService;

@SpringBootTest
@AutoConfigureMockMvc
class CarControllerTest {

	@Autowired
	CarController carController;
	
	@MockBean
	CarService carService;
	

	@Test
	public void addCarTestSuccess() throws CarValidationException
	{
		Car car = buildCar();
		Car savedCar = buildCar();
		savedCar.setId(1L);
		when(carService.createCar(car)).thenReturn(savedCar);
		ResponseEntity response	=carController.addCar(car);
		assertEquals(response.getStatusCode(),HttpStatus.CREATED);
		Car carAdded= (Car) response.getBody();
		carAdded.getId();
		assertEquals(1L,carAdded.getId());
		assertTrue(carAdded.equals(savedCar));
	}
	
	@Test
	public void addCarTestFail() throws CarValidationException
	{
		Car car = buildCar();
		Car savedCar = buildCar();
		savedCar.setId(1L);
		when(carService.createCar(car)).thenThrow(new CarValidationException(ErrorMessages.INVALID_YEAR.getMsg()));		
		ResponseEntity response	=carController.addCar(car);
		assertEquals(response.getStatusCode(),HttpStatus.BAD_REQUEST);
		ErrorMessage errorMsg= (ErrorMessage) response.getBody();
		assertEquals(ErrorMessages.INVALID_YEAR.getMsg(),errorMsg.getErrorMessage());
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
