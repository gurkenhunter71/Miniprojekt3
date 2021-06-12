package server;

public enum Prio {
	
	Low, Medium, High;
	
	public boolean contains(String searchString) {
		return (this.name().contains(searchString));
	}
	
	public boolean equals(String searchString) {
		return (this.name().equals(searchString));
	}

}
