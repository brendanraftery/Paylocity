
public class Dependent {
	public String fname;
	public String lname;
	public double yearlyBenefitCost;
	public Employee dependentOf;
	
	public Dependent(String fname, String lname, Employee dependentOf) {
		this.fname = fname.trim();
		this.lname = lname.trim();
		this.dependentOf = dependentOf;
		
		// If the dependent qualifies, apply the discount
		if (BenefitDetails.getsDiscount(fname, lname)) {
			yearlyBenefitCost = BenefitDetails.dependentRate * BenefitDetails.discount;
		} else {
			yearlyBenefitCost = BenefitDetails.dependentRate;
		}
	}
	
	public double updateCosts( ) {
		// Update personal in case any benefit details changed
		if (BenefitDetails.getsDiscount(fname, lname)) {
			yearlyBenefitCost = BenefitDetails.dependentRate * BenefitDetails.discount;
		} else {
			yearlyBenefitCost = BenefitDetails.dependentRate;
		}

		return yearlyBenefitCost;
	}
}
