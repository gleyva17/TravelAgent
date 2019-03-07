package Travel;

import java.util.*;

public abstract class Trip {

	private String name;
	private Calendar departure;
	private String source;
	private String destination;
	
	private Collection<Section> sections;
	
	public Trip(String name, String src, String destination, int year, int month, int day, int hour, int minute) {
		this.departure = checkDate(year, month, day, hour, minute);
		if(this.departure == null)
			throw new IllegalArgumentException("Bad date was given");
		if(src.equals(destination))
			throw new IllegalArgumentException("origin and destination are the same");
		
		this.source = src;
		this.destination = destination;
		this.name = name;
		sections = new ArrayList<>();
	}
	
	private Calendar checkDate(int year, int month, int day, int hour, int minute) {
		Calendar cal;
		try {
			cal = new GregorianCalendar(year, month, day, hour, minute);
		}
		catch(Exception e) {
			return null;
		}
		Calendar temp = new GregorianCalendar();
		temp.add(Calendar.YEAR, 1);
		
		if(cal.after(temp))
			return null;
		
		temp.add(Calendar.YEAR, -1);
		if(cal.before(temp))
			return null;
		
		return cal;
	}

	protected void display(PricingManager pricingManager) {
		String date = departure.get(Calendar.MONTH) + "/" + departure.get(Calendar.DAY_OF_MONTH) + "/" + departure.get(Calendar.YEAR);
		
		System.out.printf(" ID; %-8s Source: %-5s Destination: %-5s Departure: %-10s\n", name, source, destination, date);
		for(Section s : sections) {
			double price = pricingManager.getPricing(source, destination, s.getSeatClass());
			s.display(price);
			System.out.println("");
		}
		System.out.println("");
		
	}

	public String getDestination() {
		return this.destination;
	}

	public String getSource() {
		return this.source;
	}

	public Calendar getDeparture() {
		return this.departure;
	}

	public String getName() {
		return this.name;
	}


	protected boolean addSection(Section sect) {
		if(sect == null)
			return false;
		
		for(Section section : this.sections) {
			if(sect.getSeatClass() == section.getSeatClass())
				return false;
		}
		
		this.sections.add(sect);
		return true;
	}
	
	protected final Collection<Section> getSection(){
		return this.sections;
	}
	
	public Section getSectionBySeatClass(SeatClass seat) {
		for(Section sect: sections) {
			if(sect.getSeatClass() == seat) 
				return sect;
		}
		return null;
	}

	public String getAMSmemento(PricingManager pricingManager) {
		return String.format("%s|%s|%s|%s[%s]", this.name, getAMSdeparture(), this.source, this.getDestination(), getAMSsections(pricingManager));
	}

	private String getAMSdeparture() {
		return String.format("%d, %d, %d, %d, %d", 
				departure.get(Calendar.YEAR),
				departure.get(Calendar.MONTH),departure.get(Calendar.DAY_OF_MONTH),
				departure.get(Calendar.HOUR_OF_DAY),departure.get(Calendar.MINUTE));
	}

	private String getAMSsections(PricingManager pricingManager) {
		String str = "";
		boolean first = true;
		for(Section sect : sections) {
			if(first)
				first = false;
			else
				str += ", ";
			
			double price = pricingManager.getPricing(source, destination, sect.getSeatClass());
			str += sect.getAMSmemento(price);
		}
		
		return str;
	}

	boolean bookSeat(SeatClass seat, int row, int col) {
		for(Section sect : sections) {
			if(sect.getSeatClass() == seat)
				return sect.bookSeat(row, col);
		}
		return false;
	}

	public abstract boolean equals(Object o);
}
