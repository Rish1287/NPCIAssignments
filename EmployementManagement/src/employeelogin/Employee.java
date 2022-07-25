package employeelogin;


public class Employee {
	
	 private int empNo; 
	 private String eName;
	 private String  job; 
	 private String hireDate; 
	 private int managerID; 
	 private int salary ;
	 private String commission ;
	 private int deptNo; 
	 
	 public Employee() {
		// TODO Auto-generated constructor stub
	}

	public Employee(int empNo, String eName, String job, String hireDate, int managerID, int salary, String commission,
			int deptNo) {
		super();
		this.empNo = empNo;
		this.eName = eName;
		this.job = job;
		this.hireDate = hireDate;
		this.managerID = managerID;
		this.salary = salary;
		this.commission = commission;
		this.deptNo = deptNo;
	}

	public int getEmpNo() {
		return empNo;
	}

	public void setEmpNo(int empNo) {
		this.empNo = empNo;
	}

	public String geteName() {
		return eName;
	}

	public void seteName(String eName) {
		this.eName = eName;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getHireDate() {
		return hireDate;
	}

	public void setHireDate(String hireDate) {
		this.hireDate = hireDate;
	}

	public int getManagerID() {
		return managerID;
	}

	public void setManagerID(int managerID) {
		this.managerID = managerID;
	}

	public int getSalary() {
		return salary;
	}
	
	

	@Override
	public String toString() {
		return "Employee [empNo=" + empNo + ", eName=" + eName + ", job=" + job + ", hireDate=" + hireDate
				+ ", managerID=" + managerID + ", salary=" + salary + ", commission=" + commission + ", deptNo="
				+ deptNo + "]";
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public String getCommission() {
		return commission;
	}

	public void setCommission(String commission) {
		this.commission = commission;
	}

	public int getDeptNo() {
		return deptNo;
	}

	public void setDeptNo(int deptNo) {
		this.deptNo = deptNo;
	}
	
	 
	 

}
