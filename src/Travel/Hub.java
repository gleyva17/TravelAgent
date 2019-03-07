package Travel;

public abstract class Hub {
	private String name;
	
	public Hub(String name) {
		if(isValidName(name)) {
			this.name = name;
		}
	}
	
	public static boolean isValidName(String name) {
		String upperName = name.toUpperCase();
		if(upperName.length() == 3) {
			char[] chars = upperName.toCharArray();
			for(char c : chars ) {
				if(!Character.isLetter(c)) {
					throw new IllegalArgumentException();
				}
			}
			return true;
		}
		else {
			throw new IllegalArgumentException();
		}
	}

	public final String getName() {
		return this.name;
	}
	
	public abstract void display();
	
	protected abstract String getAMSmemento();
	
}// end of HUb class
