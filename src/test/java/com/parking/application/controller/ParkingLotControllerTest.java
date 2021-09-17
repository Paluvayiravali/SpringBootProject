package com.parking.application.controller;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.PostMapping;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parking.application.classes.Car;
import com.parking.application.classes.Slot;
import com.parking.application.classes.Token;
import com.parking.application.services.ParkingLot;



@RunWith(SpringRunner.class)
@WebMvcTest(value = ParkingLotController.class)

class ParkingLotControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ParkingLot parkingLot;
	
	@Test
    public void testInitializeSlot() throws Exception
    {
        Slot slot = new Slot(1);
        ArrayList<Slot> slotList = new ArrayList<Slot>();
        slotList.add(slot);
		 String slotNumber = "{\"slotNumber\":\"10\"}";

        when(parkingLot.initializeSlot(10)).thenReturn(slotList);

        mockMvc.perform(post("/initiateLot")
        		.contentType(MediaType.APPLICATION_JSON)
                .content(slotNumber) 
                .accept(MediaType.APPLICATION_JSON)) 
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].slotNumber").value("1"))
                .andExpect(jsonPath("$[0].slotFree").value("true"));
        verify(parkingLot, times(1)).initializeSlot(10);
        verifyNoMoreInteractions(parkingLot);

    }
   
	   
	@Test
	public void testParkCar() throws Exception
	{
		String car = "{\"carColor\":\"red\",\"carNumber\":\"123\"}";
		Token token = new Token("12345666",new Slot(1),new Car("red","123"));
		when(parkingLot.parkTheCar("red","123")).thenReturn(token);
		mockMvc.perform(post("/parkTheCar")
		.contentType(MediaType.APPLICATION_JSON)
		.content(car)
		.accept(MediaType.APPLICATION_JSON)
		)
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
	
		.andExpect(jsonPath("$['carDetails'].carColor").value("red"))
		.andExpect(jsonPath("$['carDetails'].carNumber").value("123"))
		.andExpect(jsonPath("$.tokenNumber").value("12345666"));
		verify(parkingLot, times(1)).parkTheCar("red","123");
		verifyNoMoreInteractions(parkingLot);

	}
	@Test
	public void testUnPark() throws Exception
	{

		Token token = new Token("1234566",new Slot(1),new Car("red","123"));
	
	
		when(parkingLot.unParkTheCar("1234566")).thenReturn(token);
	
		mockMvc.perform(delete("/unPark/")
		.param("token","1234566"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$['carDetails'].carColor").value("red"))
		.andExpect(jsonPath("$['carDetails'].carNumber").value("123"))
		.andExpect(jsonPath("$.tokenNumber").value("1234566"));
	
		verify(parkingLot, times(1)).unParkTheCar("1234566");
		verifyNoMoreInteractions(parkingLot);

	}

	@Test
    public void getCarByNumber() throws Exception 
    {
        Token token = new Token("123456",new Slot(123),new Car("red","Ap123"));

        when(parkingLot.searchCarNumber("Ap123")).thenReturn(token);

        mockMvc.perform(get("/searchCarNumber/{carNumber}", "Ap123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tokenNumber", is("123456")))
                .andExpect(jsonPath("$['carDetails'].carColor", is("red")))
                .andExpect(jsonPath("$['carDetails'].carNumber", is("Ap123"))
                );

        verify(parkingLot, times(1)).searchCarNumber("Ap123");
        verifyNoMoreInteractions(parkingLot);

    }
	
	

	
	


	
	
	
	


}
