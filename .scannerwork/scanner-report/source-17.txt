package edu.tus.car.controller;

import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@AutoConfigureJsonTesters
@SpringBootTest
@AutoConfigureMockMvc
class CarControllerMockTest {

	/*
	 * @Autowired private MockMvc mvc;
	 * 
	 * @Mock private CarRepository mockCarRepository;
	 * 
	 * 
	 * private final List<Car> mockCars = List.of(new Car(),new Car());
	 * 
	 * 
	 * @Nested
	 * 
	 * @DisplayName("/car endpoint case") class QueryForAllCars{
	 * 
	 * @Test
	 * 
	 * @DisplayName("should return 200") public void testQueryForAllCars() throws
	 * Exception { when(mockCarRepository.findAll()).thenReturn(mockCars);
	 * 
	 * ((JsonPathResultMatchers)
	 * mvc.perform(get("/api/cars")).andExpect(status().isOk()).andExpect((
	 * ResultMatcher) jsonPath("$.length"))).value(2); } }
	 */
}
