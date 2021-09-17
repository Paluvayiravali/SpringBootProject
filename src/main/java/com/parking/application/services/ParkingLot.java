package com.parking.application.services;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import com.parking.application.classes.Car;
import com.parking.application.classes.Slot;
import com.parking.application.classes.Token;
import com.parking.application.exceptions.CustomException;
import com.parking.application.exceptions.ResourceNotFoundException;


@Service

public class ParkingLot {
	private int totalNumberOfSlots;
	ArrayList<Slot> availableSlotList;
	private List<Token> tokenForLot;
	private List<Token> historyOfParking;
	private ArrayList<Slot> totalSlots;
	

	public ParkingLot(){
		
		this.tokenForLot = new ArrayList<>();
		this.historyOfParking = new ArrayList<>();

	}
	public ArrayList<Slot> initializeSlot(int totalNumberOfSlot){

		this.totalSlots = new ArrayList<Slot>() {};
		for (int i=1; i<= totalNumberOfSlot; i++) {
			Slot getSlotAssignment = new Slot(i);
			totalSlots.add(getSlotAssignment);
		}
		return this.availableSlotList = totalSlots;

	}


	public Token parkTheCar(String carColor, String carNumber) {
		Car car = new Car(carColor,carNumber);
			if(isSlotAvailable()){
				Slot availableSlot = getTheNextFreeSlot();
				Token parkingToken = new Token(String.valueOf(System.currentTimeMillis()),availableSlot,car);
				this.tokenForLot.add(parkingToken);
				return parkingToken;
			}else {
				throw new CustomException("Slot is full.");
			}
		}

	public Token unParkTheCar(String token){
		for(Token tokenInLot:tokenForLot){
			if(tokenInLot.getTokenNumber().equals(token)){
				tokenForLot.remove(tokenInLot);
				Slot slot = tokenInLot.getSlotDetails();
				int slotNumber = slot.getSlotNumber();
				return removeCarFromSlot(tokenInLot,slotNumber);
			}
			throw new CustomException("No Token found.");
		}
		return null;
		
	}

	private Token removeCarFromSlot(Token token, int slotNumber) {
		for (Slot removeEntry:availableSlotList){

			if(removeEntry.getSlotNumber() == slotNumber){
				removeEntry.makeSlotFree();
				Token historyToken = token.updateCheckOutTime();
				historyOfParking.add(historyToken);
				return token;
			}

		}
		return null;
	}

	private Slot getTheNextFreeSlot() {
		for(Slot slot : availableSlotList){
			if(slot.isSlotFree()){
				slot.makeSlotOccupied();
				return slot;
			}
		}
		throw new CustomException("slot is full");
	}
	
	
	public Token searchCarNumber(String carNumber){
		for(Token tokenSearch:tokenForLot){
			String carDetails = tokenSearch.getCarDetails().getCarNumber();
			if(carDetails.equalsIgnoreCase(carNumber)){
				return tokenSearch;
			}
		}
		throw new CustomException("No car is found with the given car number");
	}
	

	public List<Token> searchCarColor(String carColor){
		
		List<Token> carColorList = new ArrayList<Token>(){};
		for(Token tokenSearch:tokenForLot){
			String carDetails = tokenSearch.getCarDetails().getCarColor();
			if(carDetails.equalsIgnoreCase(carColor)){
				
				carColorList.add(tokenSearch);
				
			}
		}
		if(carColorList.size() == 0)
			throw new CustomException("No car is found with the given car color");
		else
			return carColorList;
	}
	
	
	private boolean isSlotAvailable() {
		if(availableSlotList !=null) {
		boolean isSlotAvailable = false;

		for(Slot slot:availableSlotList){
			if(slot.isSlotFree()){
				isSlotAvailable = true;
				break;
			}
		}
		return isSlotAvailable;
		}else {
			throw new CustomException("Initiate Slot.");
		}
	}
	

	
	public List<Token> showListOfCarDetails() {
		if(tokenForLot.isEmpty()) {
			throw new CustomException("Parking lot is Empty.");
		}else {
			for (Token i : tokenForLot) {
			System.out.println("Token Number: " + i.getTokenNumber());
			System.out.println("Slot Number: " + i.getSlotDetails().getSlotNumber());
			System.out.println("car color: " + i.getCarDetails().getCarColor());
			System.out.println("Car Number: " + i.getCarDetails().getCarNumber());
			}
		return tokenForLot;

		}
	}

	
}
