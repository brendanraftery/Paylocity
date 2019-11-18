
public class BenefitDetails {
	public static double employeeRate = 1000;
	public static double dependentRate = 500;
	public static double discount = 0.9; // 10% discount
	public static int paymentCount = 26;
	public static double defaultSalary = 52000;
	
	// Determines whether the benefits should be applied to this person 
	public static boolean getsDiscount(String fname, String lname) {
		
		// Ensure valid name
		if (fname == null || lname == null || fname.contentEquals("") || lname.contentEquals(""))
			return false;
		
		// Check if there is an A at the beginning of one of the names
		if (fname.toLowerCase().charAt(0) == 'a' || lname.toLowerCase().charAt(0) == 'a') {
			return true;
		}
		
		return false;
	}
}
