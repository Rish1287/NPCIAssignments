package employeelogin;

import java.time.*;
import java.util.*;
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
@WebServlet("/PaySlip")
public class Timesheet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Timesheet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
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
		// Employee Chart
					out.print("<form action=''>\n"
				+ "            <div class='row'>\n"
				+ "                <div class='col'></div>\n"
				+ "                <div class='col'>\n"
				+ "                    <table class= 'form-control' method = 'Post' style='margin:15px;border-collapse: separate;\n"
				+ "    border-spacing:0 20px;'>\n"
				+ "                        <tbody>\n"
				+ "                          <tr>\n"
				+ "                            <td>Employee ID : </td>\n"
				+ "                            <td>"+em.getEmpNo()+"</td>\n"
				+ "                          </tr>\n"
				+ "                          <tr>\n"
				+ "                            <td>Current Month : </td>\n<td>"+LocalDate.now().getMonthValue()+"</td>\n"
				+ "                          </tr>\n"
				+ "							<tr>\n"
				+ "							<td>Current Year : </td>\n<td>" + LocalDate.now().getYear()+"</td>\n"
				+ "							</tr>\n"
				+ "                          <tr>\n"
				+ "                            <td>Number of Working days : </td>\n"
				+ "                            <td><input type='number' name='working_days' id='' class='form-control'></td>\n"
				+ "                          </tr>\n"
				+ "                        </tbody>\n"
				+ "                      </table>\n"
				+ "                </div>\n"
				+ "                <div class='col'></div>\n"
				+ "            </div>\n"
				+ "        \n"
				+ "        </form>");
		//!Employee Chart
		//Buttons for performing Actions
		out.print("<div class='row'>\n"
				+ "            <div class='col'>\n"
				+ "                <a href='PaySlip2' class='btn btn-primary' style='margin-left: 30%;'>Payslip</a>\n"
				+ "            </div>\n"
				+ "            <div class='col'>\n"
				+ "                <a href='Details' class='btn btn-primary' style='margin-left: 29%;'>Employee Details</a>\n"
				+ "            </div>\n"
				+ "            <div class='col'>\n"
				+ "                <a href='Logout' class='btn btn-primary' style='margin-left: 29%;'>Log Out</a>\n"
				+ "            </div>\n"
				+ "          </div>");
		//!Buttons for performing Actions
		Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH) + 1;
		int empNo = em.getEmpNo();
		String month2 = String.valueOf(month);
		int year = LocalDate.now().getYear();
		String unwanted = request.getParameter("working_days");
		int daysCount = Integer.valueOf(unwanted);
		if(daysCount <= 31 && daysCount > 0) 
		{
			
			try {
				String msg = InsertTimeSheet(empNo, month2, daysCount,year);
				session.setAttribute("daysWorked", daysCount);
				out.print("<div class='row'>\n"
						+ "        <div class='col'></div>\n"
						+ "        <div class='col' style = 'color :red;'><h4>"+msg+"</h4></div>\n"
						+ "        <div class='col'></div>\n"
						+ "       </div>");
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			out.print("<div class='row'>\n"
					+ "        <div class='col'></div>\n"
					+ "        <div class='col'><div style = 'color :red;'><h4>Invalid Days Entered</h4></div></div>\n"
					+ "        <div class='col'></div>\n"
					+ "       </div>");
			RequestDispatcher dispatcher = request.getRequestDispatcher("PaySlip");
		}

	}
	private String InsertTimeSheet(int empNo, String month2,int daysCount, int year) throws SQLException, ClassNotFoundException {
		
		Connection connect = getConnection();
		PreparedStatement p = connect.prepareStatement("insert into timesheet values(?,?,?,?);");
		String msg="";
		p.setInt(1, empNo);
		p.setString(2, month2);
		p.setInt(3, year);
		p.setInt(4, daysCount);
		if(isPresentInRecord(empNo, month2,year))
		{
			int i = p.executeUpdate();
			msg = "Number of Working days recorded";
		}
		else{
			msg = "Record Already Present";
		}
		return msg;
		
	}
	private boolean isPresentInRecord(int empNo, String month, int year) throws ClassNotFoundException, SQLException
	{
		Connection connect = getConnection();
		PreparedStatement p2 = connect.prepareStatement("select * from timesheet where empNo = ? and month = ? and year = ?");
		p2.setInt(1, empNo);
		p2.setString(2, month);
		p2.setInt(3, year);
		Statement stmt = connect.createStatement();
		ResultSet r = p2.executeQuery();
		if(!(r.next()))
		{
			return true;
		}
		return false;
	}

	
	
	
	public Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");

		return DriverManager.getConnection("jdbc:mysql://localhost:3306/homework","root","Rishabh123");
		
	}
	

}