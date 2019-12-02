


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*; 

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import user.DbUtils;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		HttpSession h= request.getSession();
		
    	 Connection conn = null;
		 PreparedStatement  st= null;
		 ResultSet rs= null; 
		 try 
		 {    
			String username= request.getParameter("username");
			String password= request.getParameter("password");
			

			 
			conn = DriverManager.getConnection("jdbc:mysql://google/FareChecker?cloudSqlInstance=farechecker-258720:us-west1:finalproject&socketFactory=com.google.cloud.sql.mysql.SocketFactory&useSSL=false&user=hassib&password=rangeen");
			st= conn.prepareStatement("SELECT * FROM logins WHERE username=?");
			st.setString(1, username);
			rs= st.executeQuery();
			if(rs.next())//if user name exists check for password
			{
				st= conn.prepareStatement("SELECT password FROM logins WHERE username=?");
				st.setString(1, username);
				rs= st.executeQuery();
				
				if(rs.next())//password exists
				{
					String pass= rs.getString("password");
					

					if(pass.equals(password))//correct pass
					{
						out.write("");
						//helpful when keeping track of who is logged in 
						h.setAttribute("username", username);
						
					}
					else//incorrect password 
					{

						out.write("Incorrect password");

						
					}

				}
				else//password doesn't exist
				{

					out.write("Incorrect password");

					
				}
				

				
			}
			else
			{
				out.write("Username doesn't exist");

			}
		 }
		 catch (SQLException sqle) 
		 {    
			
			 System.out.println(sqle.getMessage());
		 }
		 finally
		 {
			 if(rs!=null)
			 {
				 try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 }
			 if(conn!=null)
			 {
				 try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 }
			 if(st!=null)
			 {
				 try {
					st.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 }
			 out.flush();
			 out.close();
		 }
				
	}

}
