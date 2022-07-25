package employeelogin;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class EmployeeDetails
 */
@WebServlet("/Details")
public class EmployeeDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EmployeeDetails() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		PrintWriter out = response.getWriter();
		out.print("<link href='https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css' rel='stylesheet' integrity='sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC' crossorigin='anonymous'>\n"
				+ "        <script src='https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js' integrity='sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM' crossorigin='anonymous'></script>");
		
		
		HttpSession session = request.getSession();
		String email = (String)session.getAttribute("email");
		response.setContentType("text/html");
		
		try {
			Employee e = getEmployeeDetails(email);
			session.setAttribute("empObj", e);
			// Welcome on right
			out.print("<div class='row'>\n"
					+ "            <div class='col'></div>\n"
					+ "            <div class='col'><p style='float: right;padding-right: 30px;font-size: 25px'>Welcome!!!<b> "+e.geteName()+"</b></p></div>\n"
					+ "        </div>");
			// !Welcome on Right
			
			
			//Employee Details
			out.print("<table class='table table-success'>\n"
					+ "            <thead>\n"
					+ "              <tr>\n"
					+ "                <th scope='col'>Serial No.</th>\n"
					+ "                <th scope='col'>Field</th>\n"
					+ "                <th scope='col'>Data</th>\n"
					+ "              </tr>\n"
					+ "            </thead>\n"
					+ "            <tbody>\n"
					+ "              <tr>\n"
					+ "                <th scope='row'>1</th>\n"
					+ "                <td>Employee ID</td>\n"
					+ "                <td>"+e.getEmpNo()+"</td>\n"
					+ "              </tr>\n"
					+ "              <tr>\n"
					+ "                <th scope='row'>2</th>\n"
					+ "                <td>Employee Name</td>\n"
					+ "                <td>"+e.geteName()+"</td>\n"
					+ "              </tr>\n"
					+ "              <tr>\n"
					+ "                <th scope='row'>3</th>\n"
					+ "                <td>Designation</td>\n"
					+ "                <td>"+e.getJob()+"</td>\n"
					+ "              </tr>\n"
					+ "              <tr>\n"
					+ "                <th scope='row'>4</th>\n"
					+ "                <td>Hire Date</td>\n"
					+ "                <td>"+e.getHireDate()+"</td>\n"
					+ "              </tr>\n"
					+ "              <tr>\n"
					+ "                <th scope='row'>5</th>\n"
					+ "                <td>Manager ID</td>\n"
					+ "                <td>"+e.getManagerID()+"</td>\n"
					+ "              </tr>\n"
					+ "              <tr>\n"
					+ "                <th scope='row'>6</th>\n"
					+ "                <td>Salary</td>\n"
					+ "                <td>"+e.getSalary()+"</td>\n"
					+ "              </tr>\n"
					+ "              <tr>\n"
					+ "                <th scope='row'>7</th>\n"
					+ "                <td>Commission</td>\n"
					+ "                <td>"+e.getCommission()+"</td>\n"
					+ "              </tr>\n"
					+ "              <tr>\n"
					+ "                <th scope='row'>8</th>\n"
					+ "                <td>Department Number</td>\n"
					+ "                <td>"+e.getDeptNo()+"</td>\n"
					+ "              </tr>              \n"
					+ "            </tbody>\n"
					+ "          </table>");
			//!Employee Details
			
			//Buttons for performing Actions
			out.print("<div class='row'>\n"
					+ "            <div class='col'>\n"
					+ "                <a href='PaySlip2' class='btn btn-primary' style='margin-left: 30%;'>Payslip</a>\n"
					+ "            </div>\n"
					+ "            <div class='col'>\n"
					+ "                <a href='PaySlip' class='btn btn-primary' style='margin-left: 29%;'>Time Sheet</a>\n"
					+ "            </div>\n"
					+ "            <div class='col'>\n"
					+ "                <a href='Logout' class='btn btn-primary' style='margin-left: 29%;'>Log Out</a>\n"
					+ "            </div>\n"
					+ "          </div>");
			//!Buttons for performing Actions
			
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	
	}
	
	

	private Employee getEmployeeDetails(String email) throws ClassNotFoundException, SQLException {
		
		Connection connect = getConnection();
		PreparedStatement p = connect.prepareStatement("select * from Employee where EmpNo = (select EmpNo from LoginCredentials where emailID = ?)");
		p.setString(1, email);
		
		
		ResultSet r = p.executeQuery();
		if(r.next())
			return new Employee(r.getInt(1),r.getString(2),r.getString(3),r.getString(4),r.getInt(5),r.getInt(6),r.getString(7),r.getInt(8));
		
		return null;
	}

	public Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");

		return DriverManager.getConnection("jdbc:mysql://localhost:3306/homework","root","Rishabh123");
		
		
		
		
		
	}

	
}