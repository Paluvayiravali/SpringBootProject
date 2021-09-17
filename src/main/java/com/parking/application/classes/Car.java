package com.parking.application.classes;

public class Car {
    private String carColor;
    private String carNumber;
    
    public Car() {
    	
    }

    public Car(String carColor, String carNumber){
        this.carColor = carColor;
        this.carNumber = carNumber;
    }

    public String getCarColor() {
        return carColor;
    }
    
    public void setCarColor(String carcolor) {
		this.carColor = carcolor;
	}

    public String getCarNumber() {
        return carNumber;
    }
    
    public void setCarNumber(String CarNumber) {
		this.carNumber = CarNumber;
	}

	
}
