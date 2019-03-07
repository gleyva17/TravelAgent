package Travel;

public abstract class Section {
	private Seat[][] seats;
	private SeatClass seatClass;
	private char layout;
	
	protected Section(SeatClass seatClass, int numRows, int numCols, char layoutCode) {
		if(seatClass == null || numRows <= 0 || numCols <= 0)
			throw new IllegalArgumentException("Invalid name, class, number of rows or number of columns received");
		
		this.layout = layoutCode;
		seats = new Seat[numRows][];
		for(int i = 0; i<numRows; i++) {
			seats[i] = new Seat[numCols];
			for(int j = 0; j < numCols; j++) {
				seats[i][j] = new Seat(i, j);
			}
		}
		this.seatClass = seatClass;	
	}

	protected final boolean hasAvailableSeats() {
		for(Seat[] seat : seats) {
			for(Seat seat1 : seat) {
				if(!seat1.isOccupied()) {
					return true;
				}
			}
		}
		return false;
	}
	
	protected final boolean bookSeat() {
		for(Seat[] seat : seats) {
			for(Seat seat1 : seat) {
				if(!seat1.isOccupied()) {
					seat1.fill();
					return true;
				}
			}
		}
		return false;
	}
	
	protected boolean bookSeatByColumn(int[] columns) {
		for(int column : columns) {
			for(Seat[] seat : seats) {
				if(!seat[column].isOccupied()) {
					seat[column].fill();
					return true;
				}
			}
		}
		return bookSeat();
	}
	
	protected final boolean bookSeat(int row, int column) {
		if(row < 0 || row >= seats.length || column < 0 || column >= seats[row].length) {
			return false;
		}
		if(!seats[row][column].isOccupied()) {
			seats[row][column].fill();
			return true;
		}
		return false;
	}
	
	protected final void display(double price) {
		String seat = "\t\t" + seatClass.toString();
		String seats = getOccupiedSeats();
		System.out.printf("%-15s$%-10.2f", seat, price);
		if(!seats.isEmpty()) {
			System.out.printf("[%s]", seats);
		}
	}

	private String getOccupiedSeats() {
		boolean first = true;
		String str = "";
		for(Seat[] seat : seats) {
			for(Seat seat1 : seat) {
				if(seat1.isOccupied()) {
					if(first)
						first = false;
					else
						str += ", ";
					str += seat1.getName();
				}
			}
		}
		
		return str;
	}
	
	protected final SeatClass getSeatClass() {
		return this.seatClass;
	}
	
	String getAMSmemento(double price) {
		return String.format("%c:%.2f:%c:%d", seatClassToChar(seatClass), price, layout, seats.length);
	}

	private Object seatClassToChar(SeatClass s) {
		if(s == SeatClass.first)
			return 'F';
		if(s == SeatClass.business)
			return 'B';
		
		return 'E';
	}
	
}//end of flightSection
