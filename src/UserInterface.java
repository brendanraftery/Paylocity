import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.*;

public class UserInterface {
	
	private Employee curEmployee;
	
	private JFrame frame;

	// Buttons in UI
	private JButton newEmployee;
	private JButton addDependent;
	//private JButton generateReport;
	
	// Labels of UI
	private JLabel currentEmployee;
	private JLabel currentEmployeeData;
	private JLabel dependents;
	private JLabel dependentsData;
	private JLabel employeeInfo;
	private JLabel employeeInfoAttributes;
	private JLabel employeeData;
	private JLabel tableCol;
	private JLabel tableRow;
	private JLabel tableData;
	private JLabel perPaychecks;
	private JLabel perYears;
	
	// Alters betweeen using default or custom salary
	private JCheckBox takeSalary;
	
	// Default Strings used when cleared
	private String currentEmployeeBase = "Current Employee:";
	private String dependentsBase = "Dependents:";
	private String employeeInfoBase = "Employee Info:";
	private String employeeInfoLabelBase = "Salary:             Paycheck Amount (before costs)";
	private String tableAttributes = "Yearly                    Per Paycheck";
	// html tags used to allow new lines in JLabels
	private String tableDesc = "<html>Personal Benefits Cost<br />"
			+ "Dependent Benefits Cost<br />"
			+ "Total Benefits Cost<br /><br />"
			+ "Salary After Benefits Cost</html>";

	// Sets number formatting for proper decimal output
	DecimalFormat df = new DecimalFormat("###,###,###.##");
	
	public static void main(String[] args) {
		UserInterface ui = new UserInterface();
	}
	
	public UserInterface() {
		frame = new JFrame();
		
		newEmployee = new JButton("New Employee");
		newEmployee.setBounds(475,60,200,35);
		// Add listener to hear button press
		newEmployee.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getEmployeeDialog();
				updateLabels();
			}
		});
		
		addDependent = new JButton("Add Dependent");
		addDependent.setBounds(475,120,200,35);
		// Add listener to hear button press
		addDependent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getDependentDialog();
				updateLabels();
			}
		});
		
		currentEmployee = new JLabel(currentEmployeeBase);
		currentEmployee.setBounds(25, 60, 200, 100);
		currentEmployee.setVerticalAlignment(JLabel.TOP);

		currentEmployeeData = new JLabel();
		currentEmployeeData.setBounds(25, 80, 200, 100);
		currentEmployeeData.setVerticalAlignment(JLabel.TOP);
		
		dependents = new JLabel(dependentsBase);
		dependents.setBounds(225,60,200,300);
		dependents.setVerticalAlignment(JLabel.TOP);

		dependentsData = new JLabel();
		dependentsData.setBounds(225,80,300,300);
		dependentsData.setVerticalAlignment(JLabel.TOP);
		
		employeeInfo = new JLabel(employeeInfoBase);
		employeeInfo.setBounds(25,350,400,100);
		employeeInfo.setVerticalAlignment(JLabel.TOP);

		employeeInfoAttributes = new JLabel(employeeInfoLabelBase);
		employeeInfoAttributes.setBounds(25,370,400,100);
		employeeInfoAttributes.setVerticalAlignment(JLabel.TOP);

		employeeData = new JLabel();
		employeeData.setBounds(25,390,400,100);
		employeeData.setVerticalAlignment(JLabel.TOP);
		
		tableRow = new JLabel(tableDesc);
		tableRow.setBounds(25,500,400,200);
		tableRow.setVerticalAlignment(JLabel.TOP);
		
		tableCol = new JLabel(tableAttributes);
		tableCol.setBounds(230,480,300,200);
		tableCol.setVerticalAlignment(JLabel.TOP);

		perYears = new JLabel();
		perYears.setBounds(230,500,300,200);
		perYears.setVerticalAlignment(JLabel.TOP);

		perPaychecks = new JLabel();
		perPaychecks.setBounds(330,500,300,200);
		perPaychecks.setVerticalAlignment(JLabel.TOP);
		
		takeSalary = new JCheckBox("Custom Salary", false);
		takeSalary.setBounds(475,170,200,50);
		takeSalary.setVerticalAlignment(JCheckBox.TOP);
		
		frame.add(newEmployee);
		frame.add(addDependent);
		frame.add(currentEmployee);
		frame.add(currentEmployeeData);
		frame.add(dependents);
		frame.add(dependentsData);
		frame.add(employeeInfo);
		frame.add(employeeInfoAttributes);
		frame.add(employeeData);
		frame.add(tableRow);
		frame.add(tableCol);
		frame.add(perYears);
		frame.add(perPaychecks);
		frame.add(takeSalary);
				
		clear();
		frame.setSize(700,700);
		frame.setTitle("Paylocity Benefits Calculator");
		frame.setLayout(null);
		frame.setVisible(true);
	}
	
	public void updateLabels() {
		updateEmployee();
		updateDependents();
		updateEmployeeData();
		updateTableData();
	}
	
	public void updateEmployee() {
		if (curEmployee == null)
			return;
		
		String discount = "";
		
		if (BenefitDetails.getsDiscount(curEmployee.fname, curEmployee.lname))
			discount = " (Discounted)";
		
		currentEmployeeData.setText(curEmployee.fname+" "+curEmployee.lname+discount);
	}
	
	public void updateDependents() {
		if (curEmployee == null)
			return;
		
		String discount = "";	
		String temp = "<html>";
		for (Dependent d : curEmployee.dependents) {
			if (BenefitDetails.getsDiscount(d.fname, d.lname))
				discount = " (Discounted)";
			temp += d.fname+" "+d.lname+discount+"<br />";
			discount = ""; // Reset discount flag	
		}
		
		temp += "</html>";
		
		dependentsData.setText(temp);
	}
	
	public void updateEmployeeData() {
		if (curEmployee == null)
			return;
		
		employeeData.setText("$"+df.format(curEmployee.salary)+""
				+ "         $"+df.format(curEmployee.getPaycheckAmount()));
	}
	
	public void updateTableData() {
		if (curEmployee == null)
			return;
		
		String paycheck = "<html>$";
		String years = "<html>$";
		
		// Yearly
		years += df.format(curEmployee.personalBenefitCost)+"<br />$";
		years += df.format((curEmployee.totalBenefitCost-curEmployee.personalBenefitCost))+"<br />$";
		years += df.format(curEmployee.totalBenefitCost)+"<br /><br />$";
		years += df.format(curEmployee.salary-curEmployee.totalBenefitCost);
		
		// Per Paycheck
		paycheck += df.format(curEmployee.personalBenefitCost/BenefitDetails.paymentCount)+"<br />$";
		paycheck += df.format((curEmployee.totalBenefitCost-curEmployee.personalBenefitCost)/BenefitDetails.paymentCount)+"<br />$";
		paycheck += df.format(curEmployee.totalBenefitCost/BenefitDetails.paymentCount)+"<br /><br />$";
		paycheck += df.format((curEmployee.salary-curEmployee.totalBenefitCost)/BenefitDetails.paymentCount);
		
		perYears.setText(years);
		perPaychecks.setText(paycheck);
	}
	
	public void getEmployeeDialog() {
		String name = (String) JOptionPane.showInputDialog(frame, "Enter Employee Name");
		
		// Cut into first and last name
		String[] names = name.split(" ");
		
		if (names == null || names.length != 2) {
			JOptionPane.showMessageDialog(frame, "Improper Name Format");
			return;
		}
	
		double salaryValue;
		
		if (takeSalary.isSelected()) {
		
			String salary = (String) JOptionPane.showInputDialog(frame, "Enter Employee Salary");
			
			// check for input that might mess with values
			salary.replaceAll(",", "");
			salary.replaceAll("$", "");
		
			// Check if parse fails
			try {
				salaryValue = Double.parseDouble(salary.trim());
			} catch (Exception e) {
				JOptionPane.showMessageDialog(frame, "Invalid Salary Input");
				return;
			}
		} else {
			salaryValue = BenefitDetails.defaultSalary; // Use the default salary
		}
		
		curEmployee = new Employee(names[0], names[1], salaryValue);
	}
	
	public void getDependentDialog() {
		if (curEmployee == null) {
			JOptionPane.showMessageDialog(frame, "Must Have Employee Created");
			return;
		}
		
		String name = (String) JOptionPane.showInputDialog("Enter Dependent Name");
		
		// Cut into first and last name
		String[] names = name.split(" ");
		
		if (names == null || names.length != 2)
			return;
		
		Dependent dep = new Dependent(names[0], names[1], curEmployee);
		curEmployee.addDependent(dep);
	}
		
	public void clear() {
		dependentsData.setText("");
		currentEmployeeData.setText("");
		perYears.setText("");
		perPaychecks.setText("");
		employeeData.setText("");
	}
}
