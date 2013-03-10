package uk.ac.cardiff.comsc.kis.peeps.vendor.database.results;

import java.util.ArrayList;
import java.util.Comparator;

public class Result implements Comparable {
	
	// private int prefrank;
	
	
	// Information about the result that mirrors the database structure
	private int id;
	private ArrayList<String> attributes;
	private double price;
	
	// private int prefRank;
	
	/** Static methods */
	public static Comparator PriceComparator = new Comparator() {
		public int compare(Object obj1, Object obj2) {
			//return Integer.parseInt((((Double)(((Result)obj1).getPrice() -	((Result)obj2).getPrice())).toString()));
			Double priceOfObj1 = ((Result)obj1).getPrice();
			Double priceOfObj2 = ((Result)obj2).getPrice();
			return ((Double)(priceOfObj2 - priceOfObj1)).intValue();
		}
	};
	
	public static Comparator StockComparator = new Comparator() {
		public int compare(Object obj1, Object obj2) {
			return 0;
		}
	};

	
	/** Constructors */
	public Result() {
		
	}
	
	public Result(int id, ArrayList<String> attributes, double price) {
		this.id = id;
		this.attributes = attributes;
		this.price = price;
	}	

	/** Getters and setters */
	
	// public void setPrefrank(int _prefrank) {
	//  	this.prefrank = _prefrank;
	// }
	
	// public int getPrefrank() {
	//  	return prefrank;
	// }
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}
	
	public ArrayList<String> getAttributes() {
		return attributes;
	}

	
	/** Main methods */
	
	public int compareTo(Object obj) throws ClassCastException {
		if (!(obj instanceof Result)) {
			throw new ClassCastException("A Result object expected");
		}
		return Integer.parseInt(((Double)(this.price - ((Result)obj).getPrice())).toString());
	}
	
	
	/** Object output methods */
	
	public String toString() {
		String out = new String();
		
		out += id + " ";
		for (String attribute : attributes) {
			out += attribute + " ";
		}
		
		out += price;
		
		return out;

	}
}
