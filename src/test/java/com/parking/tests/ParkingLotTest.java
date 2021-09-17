package com.parking.tests;

import com.parking.application.classes.Slot;
import com.parking.application.classes.Token;
import com.parking.application.exceptions.CustomException;
import com.parking.application.exceptions.ResourceNotFoundException;
import com.parking.application.services.ParkingLot;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.springframework.stereotype.Component;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

@Component
public class ParkingLotTest {
	private ParkingLot parkinglot = new ParkingLot();
	
	
	

	@Test
	public void testToInitializeASlotWithTwoSlots(){
		ArrayList<Slot> availableSlot = parkinglot.initializeSlot(5);
		assertTrue(availableSlot.size()>0);
	}
	
	@Test
	public void testParkACar(){
		parkinglot.initializeSlot(1);
		Token token = parkinglot.parkTheCar("red","123");
		assertFalse(token.getTokenNumber().isBlank());
	}

	@Test
	public void testUnParkACar(){
		parkinglot.initializeSlot(1);
		Token Token = parkinglot.parkTheCar("red","123");
		Token unParkMessage = parkinglot.unParkTheCar(Token.getTokenNumber());
		assertEquals(unParkMessage,Token);
	}



	@Test
	public void testForColorSearch(){
		parkinglot.initializeSlot(1);
		parkinglot.parkTheCar("red","123");
		List<Token> searchToken = parkinglot.searchCarColor("red");
		assertNotNull(searchToken);
	}

	@Test
	public void testForCarSearch(){

		parkinglot.initializeSlot(1);
		parkinglot.parkTheCar("red","12345");
		Token searchvalue = parkinglot.searchCarNumber("12345");
		assertNotNull(searchvalue);
	}

	@Test
	public void testToListAllCar(){
		parkinglot.initializeSlot(1);
		parkinglot.parkTheCar("red","123");
		List<Token> searchToken = parkinglot.showListOfCarDetails();
		assertNotNull(searchToken);
	}

	@Test
    public void testListEmpty(){
    	   Exception exception = assertThrows(CustomException.class, () -> {

    	 parkinglot.initializeSlot(10); 
	    parkinglot.showListOfCarDetails();
    	   });
	    String expectedMessage = "Parking lot is Empty";
	    String actualMessage = exception.getMessage();
	    assertTrue(actualMessage.contains(expectedMessage));
    }
	
	
    @Test
    public void testForColorSearchWithInvalid() {
    	   Exception exception = assertThrows(CustomException.class, () -> {
    		   parkinglot.initializeSlot(1);
    			parkinglot.parkTheCar("red","123");
    			List<Token> searchToken = parkinglot.searchCarColor("blue");
    	    });

    	    String expectedMessage = "No car is found with the given car color";
    	    String actualMessage = exception.getMessage();

    	    assertTrue(actualMessage.contains(expectedMessage));
    }
    
    @Test
    public void testForCarSearchWithInvalid() {
    	   Exception exception = assertThrows(CustomException.class, () -> {
    		   parkinglot.initializeSlot(1);
    			parkinglot.parkTheCar("red","123");
    			Token searchvalue = parkinglot.searchCarNumber("12345");
    	    });

    	    String expectedMessage = "No car is found with the given car number";
    	    String actualMessage = exception.getMessage();

    	    assertTrue(actualMessage.contains(expectedMessage));
    }
    
    @Test
    public void testParkCarWithoutInitializingSlot() {
    	   Exception exception = assertThrows(CustomException.class, () -> {
    		   //parkinglot.initializeSlot(1);
    			parkinglot.parkTheCar("red","123");
    			Token searchvalue = parkinglot.parkTheCar("red","123");
    	    });

    	    String expectedMessage = "Initiate Slot.";
    	    String actualMessage = exception.getMessage();

    	    assertTrue(actualMessage.contains(expectedMessage));
    }
    
    @Test
    public void testParkCarWhenSlotIsFull() {
    	   Exception exception = assertThrows(CustomException.class, () -> {
    		   parkinglot.initializeSlot(2);
    			parkinglot.parkTheCar("red","123");
    			Token searchvalue = parkinglot.parkTheCar("red","123");
    			parkinglot.parkTheCar("blue", "12345");
    			Token searchvalue1 = parkinglot.parkTheCar("blue","12345");
    			parkinglot.parkTheCar("blue", "12345");
    			Token searchvalue2 = parkinglot.parkTheCar("blue","12345");
    	    });

    	    String expectedMessage = "Slot is full";
    	    String actualMessage = exception.getMessage();

    	    assertTrue(actualMessage.contains(expectedMessage));
    }
    
    
    
	
	
	
	
	
}
