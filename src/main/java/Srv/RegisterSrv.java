//package Srv;
//
//import Beans.UserBean;
//import Service_Implement.UserServiceImpl;
//import java.io.IOException;
//
//import jakarta.servlet.RequestDispatcher;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//
///**
// * Servlet implementation class RegisterSrv
// */
//@WebServlet("/RegisterSrv")
//public class RegisterSrv extends HttpServlet {
//	private static final long serialVersionUID = 1L;
//
//        @Override
//	protected void doGet(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//
//		response.setContentType("text/html");
//		String userName = request.getParameter("username");
//		Long mobileNo = Long.valueOf(request.getParameter("mobile"));
//		String emailId = request.getParameter("email");
//		String address = request.getParameter("address");
//		int pinCode = Integer.parseInt(request.getParameter("pincode"));
//		String password = request.getParameter("password");
//		String confirmPassword = request.getParameter("confirmPassword");
//		String status = "";
//		if (password != null && password.equals(confirmPassword)) {
//			UserBean user = new UserBean(userName, mobileNo, emailId, address, pinCode, password);
//
//			UserServiceImpl dao = new UserServiceImpl();
//
//			status = dao.registerUser(user);
//		} else {
//			status = "Password not matching!";
//		}
//
//		RequestDispatcher rd = request.getRequestDispatcher("register.jsp?message=" + status);
//
//		rd.forward(request, response);
//	}
//
//        @Override
//	protected void doPost(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//
//		doGet(request, response);
//	}
//
//}
package Srv;

import Beans.UserBean;
import Service_Implement.UserServiceImpl;
import Utility.JavaMailUtil;

import jakarta.mail.MessagingException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/RegisterSrv")
public class RegisterSrv extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");

        String userName = request.getParameter("username");
        Long mobileNo = Long.valueOf(request.getParameter("mobile"));
        String emailId = request.getParameter("email");
        String address = request.getParameter("address");
        int pinCode = Integer.parseInt(request.getParameter("pincode"));
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        if (password != null && password.equals(confirmPassword)) {

            UserBean user = new UserBean(userName, mobileNo, emailId, address, pinCode, password);
            UserServiceImpl dao = new UserServiceImpl();

            String status = dao.registerUser(user); // This returns a String

            if (status.equals("User Registered Successfully!")) {
                try {
                    JavaMailUtil.sendMail(emailId); // Send welcome email
                } catch (MessagingException e) {
                    e.printStackTrace(); // Log email issue (optional)
                }
                // Redirect to login.jsp
                response.sendRedirect("login.jsp?message=Registration Successful! Please log in.");
            } else {
                // Registration failed or email already exists
                request.setAttribute("error", status); // Pass actual failure reason
                RequestDispatcher rd = request.getRequestDispatcher("register.jsp");
                rd.forward(request, response);
            }
        } else {
            // Passwords do not match
            request.setAttribute("error", "Passwords do not match!");
            RequestDispatcher rd = request.getRequestDispatcher("register.jsp");
            rd.forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response); // Handle GET as POST if needed
    }
}
