package AirTravel;

import Travel.Hub;

public class Airport extends Hub {

	public Airport(String name) throws IllegalArgumentException {
		super(name);
	}

	@Override
	public void display() {
		System.out.println("Airport: " + super.getName());
		
	}

	@Override
	protected String getAMSmemento() {
		return super.getName();
	}

}
