import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class Testing {

	@Test
	void testBenefitsDiscount() {
		assertFalse(BenefitDetails.getsDiscount("Bob", "Smith"));
		assertFalse(BenefitDetails.getsDiscount("Bob", "smith"));
		assertFalse(BenefitDetails.getsDiscount("bob", "Smith"));
		assertTrue(BenefitDetails.getsDiscount("Alex", "James"));
		assertTrue(BenefitDetails.getsDiscount("alex", "James"));
		assertTrue(BenefitDetails.getsDiscount("James", "Alexander"));
		assertTrue(BenefitDetails.getsDiscount("James", "alexander"));
		assertTrue(BenefitDetails.getsDiscount("james", "Alexander"));
	}
	
	@Test
	void testEmployee() {
		Employee e = new Employee("Bob", "Smith", 55000);
		assertTrue(Math.abs(e.personalBenefitCost-1000) < 0.001);
		
		Employee f = new Employee("Bob", "Alexander", 55000);
		assertTrue(Math.abs(f.personalBenefitCost-900) < 0.001);
		
		Employee g = new Employee("Alex", "Smith", 55000);
		assertTrue(Math.abs(g.personalBenefitCost-900) < 0.001);
	}
	
	@Test
	void testDependent() {
		Employee par = new Employee("Bob", "Smith", 55000);
		
		Dependent e = new Dependent("Bob", "Smith", par);
		assertTrue(Math.abs(e.yearlyBenefitCost-500) < 0.001);
		
		Dependent f = new Dependent("Bob", "Alexander", par);
		assertTrue(Math.abs(f.yearlyBenefitCost-450) < 0.001);
		
		Dependent g = new Dependent("Alex", "Smith", par);
		assertTrue(Math.abs(g.yearlyBenefitCost-450) < 0.001);
		
		par.addDependent(e);
		par.addDependent(f);
		par.addDependent(g);
		
		assertTrue(Math.abs(par.totalBenefitCost-2400) < 0.001);
	}

}
