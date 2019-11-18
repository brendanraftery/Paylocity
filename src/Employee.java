import java.util.ArrayList;

public class Employee {
	public String fname;
	public String lname;
	public double salary;
	public double personalBenefitCost; // Cost for the employee
	public double totalBenefitCost; // Cost for employee and dependents
	public ArrayList<Dependent> dependents = new ArrayList<Dependent>();
	
	public Employee(String fname, String lname, double salary) {
		this.fname = fname.trim();
		this.lname = lname.trim();
		this.salary = salary;
		
		// If the employee qualifies, apply the discount
		if (BenefitDetails.getsDiscount(fname, lname)) {
			personalBenefitCost = BenefitDetails.employeeRate * BenefitDetails.discount;
		} else {
			personalBenefitCost = BenefitDetails.employeeRate;
		}
		
		totalBenefitCost = personalBenefitCost;
	}
		
	public void addDependent(Dependent dependent) {
		// Make sure dependent not already added
		if (dependents.contains(dependent))
			return;
		
		dependents.add(dependent);
		totalBenefitCost += dependent.yearlyBenefitCost;
	}
	
	public void removeDependent(Dependent dependent) {
		dependents.remove(dependent);
		totalBenefitCost -= dependent.yearlyBenefitCost;
	}
	
	public double getPaycheckAmount() {
		return salary / BenefitDetails.paymentCount;
	}
	
	public double getPaycheckBenefitCost() {
		return totalBenefitCost / BenefitDetails.paymentCount;
	}
	
	public double updateCosts( ) {
		// Update personal in case any benefit details changed
		if (BenefitDetails.getsDiscount(fname, lname)) {
			personalBenefitCost = BenefitDetails.employeeRate * BenefitDetails.discount;
		} else {
			personalBenefitCost = BenefitDetails.employeeRate;
		}
		
		totalBenefitCost = personalBenefitCost;
		
		for (Dependent dep : dependents) {
			totalBenefitCost += dep.updateCosts();
		}
		
		return totalBenefitCost;
	}
}
