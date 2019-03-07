package AirTravel;

import Travel.Company;
import Travel.SeatClass;
import Travel.Section;
import Travel.Trip;

public class Airline extends Company {

	Airline(String name) throws IllegalArgumentException {
		super(name);
	}
	
	boolean createFlightSection(String flight, SeatClass seatClass, int rows, int columns) {
		Section section;
		
		try
        {
            section = new FlightSection(seatClass, rows, columns);
        }
        catch (IllegalArgumentException e)
        {
            return false;
        }

        return super.addSection(flight, section);
    }

    boolean addFlight(Trip trip)
    {
        return super.addTrip(trip);
    }

    boolean addFlightSection(String flight, Section section)
    {
        return super.addSection(flight, section);
    }

    protected void display()
    {
        System.out.println("Airline: " + super.getName());
        super.display();
        System.out.println("");
    }

    String[] getFlight(String origin, String destination)
    {
        return super.getTrips(origin, destination);
    }

    String[] getFlight(String origin, String destination, int year, int month, int day)
    {
        return super.getTrips(origin, destination, year, month, day);
    }

    boolean addFlight(String name, String source, String destination, int year, int month, int day)
    {
        if (name == null)
        {
            System.out.println("Null is not a valid flight ID!");
            return false;
        }
        try
        {
            Trip trip = new Flight(name, source, destination, year, month, day);
            super.addTrip(trip);
            return true;
        }
        catch (Exception ignore)
        {
        }
        return false;
    }

    boolean bookSeat(String flightID, SeatClass seatClass, int row, char column)
    {
        char upperColumn = Character.toUpperCase(column);
        int columnInt = ((int) upperColumn) - 65;
        if (columnInt < 0 || columnInt > 9)
        {
            System.out.println("index out of range.");
            return false;
        }
        else
        {
            return super.bookSeat(flightID, seatClass, row, columnInt);
        }
    }

    
    boolean bookSeat(String flightID, SeatClass seat, boolean windowSeat, boolean aisleSeat)
    {
        Trip trip = getTripByName(flightID);
        if (trip == null)
        {
            return false;
        }
        Section sect = trip.getSectionBySeatClass(seat);
        FlightSection section = (FlightSection) sect;
        return section.bookSeat(windowSeat, aisleSeat);
    }
}
