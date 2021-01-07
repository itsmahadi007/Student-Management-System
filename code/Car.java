package car;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import helper.Helper;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * This is the car class. <br>
 * It has all the necessary methods to deal with a single car.
 * 
 * @author jielun
 *
 */
public class Car implements Serializable {
	private String id;
	private String description;
	private String colour;
	private double price; 
	private CarStatus status;
//	private String customerName; // dont think this is needed
//	private String fromDate;
//	private String toDate;
	
	private List<Appoinment> appoinments;
	
	private int num;
	private String msg;
	
	// imgPath for the image
	private String imgPath;

	/**
	 * Constructor for the car class, it initialises its status to available. <br>
	 * And it also has a null value of its bookingDate and customerName.
	 * 
	 * @param id
	 * @param description
	 * @param colour
	 * @param price
	 * @throws CarException
	 * If the first character of registration number is not a letter <b>or</b> 
	 * its length is not equal to 8
	 */
	public Car(String id, String description, 
			String colour, double price) throws CarException {
		if(Character.isDigit(id.charAt(0)) || id.length() != 8)
			throw new CarException("The first character of registration number should be a letter and "
					+ "its length should equal to 8");
		if(description.length() > 25)
			throw new CarException("Length for description should be less than equal to 25");
		this.id = id;
		this.description = description;
		this.colour = colour;
		this.price = price;
		status = CarStatus.AVAILALBE;
		appoinments = new ArrayList<>();
		this.num = 1;
		this.msg = "\nRecords\n" + num + ". This car has been added at " + Helper.getTime() + "\n";
	}

	public Car(String id, String description, 
			String colour, double price, String imgPath) throws CarException {
		this(id,description, colour, price);
		this.setImgPath(imgPath);
	}
	
//	/**
//	 * Constructor for reading files
//	 * 
//	 * @param id
//	 * @param description
//	 * @param colour
//	 * @param price
//	 * @param status
//	 * @param customerName
//	 * @param fromDate
//	 * @param toDate
//	 * @param num
//	 * @param msg
//	 * @throws CarException
//	 */
//	public Car(String id, String description, String colour, double price, CarStatus status,
//			String customerName, String fromDate, String toDate, int num, String msg) throws CarException {
//		this(id,description, colour, price);
//		this.status = status;
//		this.customerName = customerName;
//		this.fromDate = fromDate;
//		this.toDate = toDate;
//		this.num = num;
//		this.msg = msg;
//	}
	
	public Car(String id, String description, String colour, double price, CarStatus status,
			List<Appoinment> appoinments, int num, String msg) throws CarException {
		this(id,description, colour, price);
		this.status = status;
		this.appoinments = appoinments;
		this.num = num;
		this.msg = msg;
	}
	
//	/**
//	 * 
//	 * 
//	 * @param id
//	 * @param description
//	 * @param colour
//	 * @param price
//	 * @param status
//	 * @param customerName
//	 * @param fromDate
//	 * @param toDate
//	 * @param imgPath
//	 * @param num
//	 * @param msg
//	 * @throws CarException
//	 */
//	public Car(String id, String description, String colour, double price, CarStatus status,
//			String customerName, String fromDate, String toDate, String imgPath, int num, String msg) throws CarException {
//		this(id,description, colour, price);
//		this.status = status;
//		this.customerName = customerName;
//		this.fromDate = fromDate;
//		this.toDate = toDate;
//		if(!imgPath.isEmpty()) {
//			this.setImgPath(imgPath);			
//		}
//		this.num = num;
//		this.msg = msg;
//	}
	
	public Car(String id, String description, String colour, double price, CarStatus status,
			List<Appoinment> appoinments, String imgPath, int num, String msg) throws CarException {
		this(id,description, colour, price);
		this.status = status;
		this.appoinments = appoinments;
		if(!imgPath.isEmpty()) {
			this.setImgPath(imgPath);			
		}
		this.num = num;
		this.msg = msg;
	}
	
	/**
	 * Returns appointment object
	 * @return
	 */
	public List<Appoinment> getAppoinments(){
		return appoinments;
	}

	// necessary getter and setter methods
	public String getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getColour() {
		return colour;
	}

	public void setColour(String colour) {
		this.colour = colour;
	}

	public CarStatus getStatus() {
		return status;
	}

	public void setStatus(CarStatus status) {
		this.status = status;
	}

//	public String getCustomerName() {
//		return customerName;
//	}
//
//	public void setCustomerName(String customerName) {
//		this.customerName = customerName;
//	}

	public double getPrice() {
		return price;
//		return Math.round(price*100.00)/100.00;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	//
	public void addMessage(String msg) {
		this.msg += msg + "\n";
	}

	public String getMessage() {
		return msg;
	}

	public int getNum() {
		num++;
		return num;
	}
	
//	public String getFromDate() {
//		return fromDate;
//	}
//	
//	public void setFromDate(String fromDate) {
//		this.fromDate = fromDate;
//	}
//	
//	public String getReturnDate() {
//		return toDate;
//	}
//	
//	public void setReturnDate(String toDate) {
//		this.toDate = toDate;
//	}

	public String getImgPath() {
		return imgPath;
	}
	
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	
//	/**
//	 * toString method prints out all the details of a car object
//	 */
//	@Override
//	public String toString() {
//		return "Car [id=" + id + ", description=" + description + ", colour=" + colour
//				+ ", price=" + price + ", status=" + status + ", customerName=" + customerName + ",\nfromDate=" + fromDate + ", toDate=" + toDate + ", num=" + num + ", msg=" + msg + "]";
//	}
	
}
