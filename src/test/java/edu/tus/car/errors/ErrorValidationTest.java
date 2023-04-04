package edu.tus.car.errors;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.tus.car.model.Car;

class ErrorValidationTest {

	ErrorValidation errorValidation;
	Car car;

	@BeforeEach
	void setUp() {
		errorValidation = new ErrorValidation();
		car = buildCar();
	}

	@Test
	void testYearValid() {
		assertTrue(errorValidation.yearNotOK(car));
	}

	@Test
	void testYearNotOk() {
		car.setYear(2022);
		assertFalse(errorValidation.yearNotOK(car));
	}

	@Test
	void testModel() {
		assertTrue(errorValidation.checkMakeAndModelNotAllowed(car));
	}

	@Test
	void testEmptyFieldModel() {
		car.setModel("");
		assertTrue(errorValidation.checkMakeAndModelNotAllowed(car));
	}
	
	@Test
	void testColor() {
		assertFalse(errorValidation.colorNotOK(car));
	}

	@Test
	void testWrongColor() {
		car.setColor("Yellow");
		assertTrue(errorValidation.colorNotOK(car));
	}

	@Test
	void testMake() {
		assertTrue(errorValidation.checkMakeAndModelNotAllowed(car));
	}

	@Test
	void testEmptyFieldMake() {
		car.setMake("");
		assertTrue(errorValidation.checkMakeAndModelNotAllowed(car));
	}
	
	Car buildCar() {
		//make", "model", "year", "color"
		Car car = new Car();
		car.setMake("Mercedes");
		car.setModel("X5");
		car.setYear(2019);
		car.setColor("Black");
		return car;
	}

}
