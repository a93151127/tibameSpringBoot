package com.tibame.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


import com.tibame.domain.Engine;
import com.tibame.domain.Transmission;

@Component
public class Car {
	private Engine engine;
	private Transmission transmission;
	
	
	//建構子注入
	@Autowired
	public Car(Engine engine, Transmission transmission) {
		System.out.println(engine);
        this.engine = engine;
        this.transmission = transmission;
    }
	public Engine getEngine() {
		return engine;
	}
	public Transmission getTransmission() {
		return transmission;
	}
	public void setEngine(Engine engine) {
		this.engine = engine;
	}
	

}
