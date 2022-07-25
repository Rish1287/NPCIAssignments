package employeelogin;

import java.time.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class PaySlip
 */
@WebServlet("/PaySlip2")
public class PaySlip extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PaySlip() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		//Payslip Calender
		out.print("<div class='row'>\n"
				+ "            <div class='col'></div>\n"
				+ "            <div class='col-6'><form action=''>\n"
				+ "                <label for='Month'>Choose Month:</label>\n"
				+ "    \n"
				+ "                <select name='month' id='month' class='form-select' >\n"
				+ "                    <option value='1'>January</option>\n"
				+ "                    <option value='2'>February</option>\n"
				+ "                    <option value='3'>March</option>\n"
				+ "                    <option value='4'>April</option>\n"
				+ "                    <option value='5'>May</option>\n"
				+ "                    <option value='6'>June</option>\n"
				+ "                    <option value='7'>July</option>\n"
				+ "                    <option value='8'>August</option>\n"
				+ "                    <option value='9'>September</option>\n"
				+ "                    <option value='10'>October</option>\n"
				+ "                    <option value='11'>November</option>\n"
				+ "                    <option value='12'>December</option>\n"
				+ "                </select>\n"
				+ "                <br>\n"
				+ "                &nbsp;\n"
				+ "                <label for='Month'>Choose Year:</label>\n"
				+ "    \n"
				+ "                <select name='year' id='year' class='form-select'>\n"
				+ "                    <option value='2019'>2019</option>\n"
				+ "                    <option value='2020'>2020</option>\n"
				+ "                    <option value='2021'>2021</option>\n"
				+ "                    <option value='2022'>2022</option>\n"
				+ "                </select>\n"
				+ "                <br>\n"
				+ "                <input type='submit' value='Print Payslip' onclick = 'isEmployeeTillDate()' class='btn btn-outline-primary' style='margin-top: 10px; margin-left: 40%;'>\n"
				+ "            </form></div>\n"
				+ "            <div class='col'></div>\n"
				+ "            </div>\n"
				+ "        </div>");
		//Buttons for performing Actions
		out.print("<div class='row'>\n"
				+ "            <div class='col'>\n"
				+ "                <a href='Details' class='btn btn-primary' style='margin-left: 30%;'>Employee Details</a>\n"
				+ "            </div>\n"
				+ "            <div class='col'>\n"
				+ "                <a href='PaySlip' class='btn btn-primary' style='margin-left: 29%;'>Time Sheet</a>\n"
				+ "            </div>\n"
				+ "            <div class='col'>\n"
				+ "                <a href='Logout' class='btn btn-primary' style='margin-left: 29%;'>Log Out</a>\n"
				+ "            </div>\n"
				+ "          </div>");
		//!Buttons for performing Actions
		//!PaYslip Calender
		int valueMonth = Integer.parseInt(request.getParameter("month"));
		int valueYear = Integer.parseInt(request.getParameter("year"));
		session.setAttribute("Month", valueMonth);
		session.setAttribute("Year", valueYear);
		int empId = em.getEmpNo();
		try {
			
			if(isPresentTimeSheet(empId, valueMonth, valueYear))
			{
				response.sendRedirect("DisplayPaySlip");
			}
			else {
				out.print("<html><body><div style = 'color :red; margin-left: 35%; margin-top: 50px; margin-bottom: 50px'><h4>Employee does not exist on the required month or year</h4></div></body></html>");
				RequestDispatcher dispatcher = request.getRequestDispatcher("PaySlip2");
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
		
	}
	private boolean isEmployeeTillDate(int empId,int valueMonth, int valueYear) throws SQLException, ClassNotFoundException
	{
		Connection connect = getConnection();
		PreparedStatement p2 = connect.prepareStatement("select HireDate from Employee where empNo = ?");
		p2.setInt(1, empId);
		Statement stmt = connect.createStatement();
		ResultSet r = p2.executeQuery();
		int year1 = 0;
		if(r.next())
		{
			LocalDate local = LocalDate.parse(r.getString(1));
			int yearfromsql = local.getYear();
			Month objMonth = local.getMonth();
			YearMonth firstYearMonth = YearMonth.of(yearfromsql, objMonth);
	        YearMonth secondYearMonth = YearMonth.of(valueYear, valueMonth);
			year1 = firstYearMonth.compareTo(secondYearMonth);
			
		}
		if(year1 > 0)
			return false;
		else if(year1 == 0 )
			return true;
		else
			return true;
	}
	
	private boolean isPresentTimeSheet(int empId, int valueMonth, int valueYear) throws ClassNotFoundException, SQLException {
		Connection connect = getConnection();
		PreparedStatement p2 = connect.prepareStatement("select * from timesheet where empNo = ? and month = ? and year =?");
		p2.setInt(1, empId);
		p2.setInt(2, valueMonth);
		p2.setInt(3, valueYear);
		Statement stmt = connect.createStatement();
		ResultSet r;
		if(isEmployeeTillDate(empId, valueMonth, valueYear)) {
			r = p2.executeQuery();
			if(r.next())
			{
				return true;
			}
		}
		return false;
	}
	
	
	
	public Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");

		return DriverManager.getConnection("jdbc:mysql://localhost:3306/homework","root","Rishabh123");
		
	}
	

}