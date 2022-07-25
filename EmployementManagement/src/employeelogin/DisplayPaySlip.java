package employeelogin;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class DisplayPaySlip
 */
@WebServlet("/DisplayPaySlip")
public class DisplayPaySlip extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DisplayPaySlip() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.print("<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC' crossorigin='anonymous'>\n"
				+ "        <script src='https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js' integrity='sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM' crossorigin='anonymous'></script>");

		HttpSession session = request.getSession();
		Employee em = (Employee)session.getAttribute("empObj");
		response.setContentType("text/html");
		// Welcome on right
					out.print("<div class='row'>\n"
							+ "            <div class='col'></div>\n"
							+ "            <div class='col'><p style='float: right;padding-right: 30px;font-size: 25px'>Welcome!!!<b> "+em.geteName()+"</b></p></div>\n"
							+ "        </div>");
		// !Welcome on Right
		int month = (int)session.getAttribute("Month");
		int year = (int)session.getAttribute("Year");
		int daysWorked = 0;
		try {
			if(getWorkedDays(em.getEmpNo(), month, year) > 0)
				daysWorked = getWorkedDays(em.getEmpNo(), month, year);
			else
				out.print("Record Not Found");
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		float dailySalary = em.getSalary() / 30;
		int numberOfleaves = 30 - daysWorked;
		float monthlySalary = daysWorked * dailySalary;
		int commission = Integer.parseInt(em.getCommission());
		float deductionCommission = 3500 + commission;
		float basicpay = monthlySalary - deductionCommission;
		
		
		
		
		//Payslip
		out.print("<div class='row'>\n"
				+ "          <div class='col'></div>\n"
				+ "          <div class='col-6'>\n"
				+ "            <table style='border-collapse: separate;border-spacing: 0px 10px;padding:10px;margin-left: 10%'>\n"
				+ "              <tr>\n"
				+ "                <td colspan='4' style='text-align: center;'>Payslip</td>\n"
				+ "              </tr>\n"
				+ "              <tr>\n"
				+ "                <td colspan='4'style='text-align: center;'>National Payment Corporation Of India</td>\n"
				+ "              </tr>\n"
				+ "              <tr>\n"
				+ "                <td colspan='4'style='text-align: center;'>Mumbai - 400053 <br>Maharashtra, India</td>\n"
				+ "              </tr>\n"
				+ "              <tr>\n"
				+ "                <td colspan='4'style='text-align: center;'>&nbsp;</td>\n"
				+ "              </tr>\n"
				+ "              <tr>\n"
				+ "                <td><b>Date of Joining</b></td>\n"
				+ "                <td>"+em.getHireDate()+"</td>\n"
				+ "                <td><b>Employee Name : </b></td>\n"
				+ "                <td>"+em.geteName()+"</td>\n"
				+ "              </tr>\n"
				+ "              <tr>\n"
				+ "                <td><b>Pay Period</b></td>\n"
				+ "                <td>"+session.getAttribute("Month")+"/"+session.getAttribute("Year")+"</td>\n"
				+ "                <td><b>Designation</b></td>\n"
				+ "                <td>"+em.getJob()+"</td>\n"
				+ "              </tr>\n"
				+ "              <tr>\n"
				+ "                <td><b>Worked days</b></td>\n"
				+ "                <td>"+daysWorked+"</td>\n"
				+ "                <td><b>Leaves Taken : </b></td>\n"
				+ "                <td>"+numberOfleaves+"</td>\n"
				+ "              </tr>\n"
				+ "              <tr>\n"
				+ "                <td>&nbsp;</td>\n"
				+ "                <td>&nbsp;</td>\n"
				+ "                <td><b>Department ID : </b></td>\n"
				+ "                <td>"+em.getDeptNo()+"</td>\n"
				+ "              </tr>\n"
				+ "              <tr>\n"
				+ "                <td colspan='4'style='text-align: center;'>&nbsp;</td>\n"
				+ "              </tr>\n"
				+ "              \n"
				+ "              <tr>\n"
				+ "                <th>Earnings</th>\n"
				+ "                <th style='text-align: right'>Amount</th>\n"
				+ "                <th style='padding-left: 12px'>Deductions</th>\n"
				+ "                <th style='text-align: right'>Amount</th>\n"
				+ "              </tr>\n"
				+ "              <tr>\n"
				+ "                <td>Basic Salary</td>\n"
				+ "                <td style='float: right;'>"+basicpay+"</td>\n"
				+ "                <td style='padding-left: 12px'>Provident Fund</td>\n"
				+ "                <td style='float: right;'>1500</td>\n"
				+ "              </tr>\n"
				+ "              <tr>\n"
				+ "                  <td>HRA</td>\n"
				+ "                  <td style='float: right;'>3000</td>\n"
				+ "                  \n"
				+ "              </tr>\n"
				+ "              <tr>\n"
				+ "                <td>Meal Allowance</td>\n"
				+ "                <td style='float: right;'>2000</td>\n"
				+ "              </tr>\n"
				+ "				 <tr>\n"
				+ "                <td>Commission</td>\n"
				+ "                <td style='float: right;'>"+commission+"</td>\n"
				+ "              </tr>\n"
				+ "              <tr>\n"
				+ "                <td>Total Earnings</td>\n"
				+ "                <td style='float: right;'>"+(basicpay+commission+3000+2000)+"</td>\n"
				+ "                <td style='padding-left: 12px'>Total Deduction</td>\n"
				+ "                <td style='float: right;'>"+1500+"</td>\n"
				+ "              </tr>\n"
				+ "              <tr>\n"
				+ "                <td colspan='4'style='text-align: center;'>&nbsp;</td>\n"
				+ "              </tr>\n"
				+ "              <tr>\n"
				+ "                <td >&nbsp;</td>\n"
				+ "                <td >&nbsp;</td>\n"
				+ "                <td >Net Pay</td>\n"
				+ "                <td >"+((basicpay+commission+3000+2000)-(1500))+"</td>\n"
				+ "              </tr>\n"
				+ "             </table>\n"
				+ "          </div>\n"
				+ "          <div class='col'></div>\n"
				+ "      </div>\n"
				+ "");
		//!Payslip
		out.print("<div class='row'>\n"
				+ "            <div class='col'></div>\n"
				+ "            <div class='col'><a href='Logout' class='btn btn-primary' style='margin-left: 35%; margin-top: 50px; width: 100px;'>Log Out</a></div>\n"
				+ "            <div class='col'></div>\n"
				+ "        </div>");
	}
	private int getWorkedDays(int empId, int month, int year) throws ClassNotFoundException, SQLException
	{
		Connection connect = getConnection();
		PreparedStatement p2 = connect.prepareStatement("select workingDays from timesheet where empNo = ? and month = ? and year =?");
		p2.setInt(1, empId);
		p2.setInt(2, month);
		p2.setInt(3, year);
		Statement stmt = connect.createStatement();
		ResultSet r = p2.executeQuery();
		if(r.next())
		{
			return r.getInt(1);
		}
		return 0;
		
	}
	
	
	public Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");

		return DriverManager.getConnection("jdbc:mysql://localhost:3306/homework","root","Rishabh123");
			
	}

}
