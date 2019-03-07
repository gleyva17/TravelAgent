package Travel;

import java.util.*;

public abstract class Company {
	
	private Collection<Trip> trips;
	private String name;
	PricingManager pricingManager;
	
	protected Company(String name) {
		if(isValidName(name)) {
			this.name= name.toUpperCase();
		}
		
		this.trips = new ArrayList<>();
		this.pricingManager = new PricingManager();
	}

	protected void display() {
		if(trips.isEmpty()) {
			System.out.println("\tEmpty");
		}
		else {
			for(Trip tempTrip : trips) {
				tempTrip.display(pricingManager);
			}
		}
	}
	
	protected boolean bookSeat(String trip, SeatClass seat, int row, int col) {
		Trip tempTrip = getTripFromName(trip);
		return tempTrip != null && tempTrip.bookSeat(seat, row, col);
	}
	
	private Trip getTripFromName(String tripName) {
		for(Trip tempTrip : trips) {
			if(tempTrip.getName().equals(tripName)) {
				return tempTrip;
			}
		}
		return null;
	}

	protected final String[] getTrips(String source, String destination) {
		Collection<String> selectedTrips = new ArrayList<>();
		for(Trip t : this.trips) {
			if(t.getSource().equals(source) && t.getDestination().equals(destination)) {
				selectedTrips.add(t.getName());
			}
		}
		return castObjectToStringArray(selectedTrips.toArray());
	}
	
	protected final String[] getTrips(String source, String destination, int year, int month, int day) {
		
		Calendar departure = new GregorianCalendar(year, month, day);
		Collection<String> selectedTrips = new ArrayList<>();
		for(Trip tempTrip : this.trips) {
			Calendar cal = tempTrip.getDeparture();
			if(tempTrip.getSource().equals(source) && tempTrip.getDestination().equals(destination) && isSameDay(departure, cal)) {
				selectedTrips.add(tempTrip.getName());
			}
		}
		return castObjectToStringArray(selectedTrips.toArray());
	}
	
	private boolean isSameDay(Calendar departure, Calendar cal) {
		if(departure == null || cal == null){
			throw new IllegalArgumentException("The date can not be null!");
		}
		
		return (departure.get(Calendar.ERA)== cal.get(Calendar.ERA) && departure.get(Calendar.YEAR) == cal.get(Calendar.YEAR)
				&& departure.get(Calendar.DAY_OF_YEAR) == cal.get(Calendar.DAY_OF_YEAR));
	}

	private String[] castObjectToStringArray(Object[] array) {
		String[] str = new String[array.length];
		for(int i =0; i < array.length; i++) {
			str[i] = (String) array[i];
		}
		return str;
	}

	public final String getName() {
		return this.name;
	}
	
	private boolean isValidName(String name2) {
		String upperName = name2.toUpperCase();
		if(upperName.length() < 7 && upperName.length() > 0) {
			char[] chars = upperName.toCharArray();
			for(char c : chars) {
				if(!Character.isLetterOrDigit(c)) {
					throw new IllegalArgumentException();
				}
			}
			return true;
		}
		else {
			throw new IllegalArgumentException("Name is not valid");
		}
	}
	
	protected final boolean addTrip(Trip trip) {
		this.trips.add(trip);
		return true;
	}
	
	protected final boolean addSection(String trip, Section sect){
		if(trip == null || sect == null) {
			return false;
		}
		Trip newTrip = getTripFromName(trip);
		return newTrip != null && newTrip.addSection(sect);
		
	}
	public final double getPricing(String origin, String destination, SeatClass seatClass) {
		return this.pricingManager.getPricing(origin, destination, seatClass);
	}

	public final boolean setPricing(String origin, String destination, SeatClass seatClass, double price) {
		try {
			this.pricingManager.setPricing(origin, destination, seatClass, price);
			return true;
		}
		catch(IllegalArgumentException ignored){
		}
		return false;
	}

	protected Trip getTripByName(String name) {
		for(Trip tempTrip : trips) {
			if(tempTrip.getName().equals(name)) {
				return tempTrip;
			}
		}
		return null;
	}
	
	public final String getAMSmemento() {
		return String.format("%s[%s]", this.name, getFlightAMS());
	}

	private String getFlightAMS() {
		boolean first = true;
		String returns = "";
		for(Trip tempTrip : trips) {
			if(first) {
				first = false;
			}
			else {
				returns +=",";
			}
			returns += tempTrip.getAMSmemento(pricingManager);
		}
		return returns;
	}
}//end of Company Class
