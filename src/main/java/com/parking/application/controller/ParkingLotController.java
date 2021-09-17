package com.parking.application.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.parking.application.classes.Car;
import com.parking.application.classes.Slot;
import com.parking.application.classes.Token;
import com.parking.application.exceptions.CustomException;
import com.parking.application.exceptions.ResourceNotFoundException;
import com.parking.application.services.ParkingLot;

@RestController
public class ParkingLotController
{
	@Autowired
	private ParkingLot parkingLot;
	
	
	
	@GetMapping("/showAllCarParkDetails")
	public ResponseEntity<Object> getAllcars()
	{
		return new ResponseEntity<>(parkingLot.showListOfCarDetails(),HttpStatus.OK);
	}

	@PostMapping("/parkTheCar")
	public ResponseEntity<Object> parkTheCar(@RequestBody Car car)
	{
		return new ResponseEntity<>(parkingLot.parkTheCar(car.getCarColor(),car.getCarNumber()),HttpStatus.OK);
	}
	
	 @PostMapping("/initiateLot")
	    public ResponseEntity<Object> initiateLot(@RequestBody Slot slot){
		 return new ResponseEntity<>(parkingLot.initializeSlot(slot.getSlotNumber()),HttpStatus.OK);
	    }
	
	
	
	@GetMapping("/searchCarColor/{carColor}")
	public List<Token> searchCarColor(@PathVariable("carColor") String carColor)
	{
		List<Token> token = parkingLot.searchCarColor(carColor);
		return token;
	}
	
	@GetMapping("/searchCarNumber/{carNumber}")
	public Token searchCarNumber(@PathVariable("carNumber") String carNumber)
	{
		Token token = parkingLot.searchCarNumber(carNumber);
		return token;
		
	}
	
	@DeleteMapping("/unPark")
	public Token UnParkTheCar(@RequestParam("token") String token)
	{
		Token unparkcar = parkingLot.unParkTheCar(token);
		return unparkcar;
		
	}
	

	 


	

}
