package Travel;
import java.util.*;

public class PricingManager {
	
	private HashMap<String, Double> pricing;

	PricingManager() {
		this.pricing = new HashMap<>();
	}

	public double getPricing(String origin, String destination, SeatClass seatClass) {
		String key = encode(origin, destination, seatClass);
		if(pricing.containsKey(key)) {
			return pricing.get(key);
		}
		return 0;
	}

	private String encode(String origin, String destination, SeatClass seatClass) {
		return origin + "|" + destination + "|" + seatClass;
	}

	public void setPricing(String origin, String destination, SeatClass seatClass, double price) {
		if(origin == null || origin.isEmpty() || destination == null || destination.isEmpty() || seatClass == null || price < 0) {
			throw new IllegalArgumentException("wrong parameters passed");
		}
		
		String key = encode(origin, destination, seatClass);
		pricing.put(key, price);
		
	}

}
