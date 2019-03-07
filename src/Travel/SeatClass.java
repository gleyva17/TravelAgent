package Travel;

public enum SeatClass {
	first, business, economy;
	
	public static SeatClass getFomName(String type) {
		
		if(type.equals("first")) {
			return SeatClass.first;
		}
		
		if(type.equals("business")) {
			return SeatClass.business;
		}
		
		if(type.equals("economy")) {
			return SeatClass.economy;
		}
		
		return null;
	}
	
}
