//package Service_Implement;
//
//import Beans.UserBean;
//import Constants.IUserConstants;
//import Service.UserService;
//import Utility.DBUtil;
//import Utility.MailMessage;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//
//public class UserServiceImpl implements UserService {
//
//	@Override
//	public String registerUser(String userName, Long mobileNo, String emailId, String address, int pinCode,
//			String password) {
//
//		UserBean user = new UserBean(userName, mobileNo, emailId, address, pinCode, password);
//
//		String status = registerUser(user);
//
//		return status;
//	}
//
//	@Override
//	public String registerUser(UserBean user) {
//
//		String status = "User Registration Failed!";
//
//		boolean isRegtd = isRegistered(user.getEmail());
//
//		if (isRegtd) {
//			status = "Email Id Already Registered!";
//			return status;
//		}
//		Connection conn = DBUtil.provideConnection();
//		PreparedStatement ps = null;
//		if (conn != null) {
//			System.out.println("Connected Successfully!");
//		}
//
//		try {
//
//			ps = conn.prepareStatement("insert into " + IUserConstants.TABLE_USER + " values(?,?,?,?,?,?)");
//
//			ps.setString(1, user.getEmail());
//			ps.setString(2, user.getName());
//			ps.setLong(3, user.getMobile());
//			ps.setString(4, user.getAddress());
//			ps.setInt(5, user.getPinCode());
//			ps.setString(6, user.getPassword());
//
//			int k = ps.executeUpdate();
//
//			if (k > 0) {
//				status = "User Registered Successfully!";
//				MailMessage.registrationSuccess(user.getEmail(), user.getName().split(" ")[0]);
//			}
//
//		} catch (SQLException e) {
//			status = "Error: " + e.getMessage();
//			e.printStackTrace();
//		}
//
//		DBUtil.closeConnection(ps);
//		DBUtil.closeConnection(ps);
//
//		return status;
//	}
//
//	@Override
//	public boolean isRegistered(String emailId) {
//		boolean flag = false;
//
//		Connection con = DBUtil.provideConnection();
//
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//
//		try {
//			ps = con.prepareStatement("select * from user where email=?");
//
//			ps.setString(1, emailId);
//
//			rs = ps.executeQuery();
//
//			if (rs.next())
//				flag = true;
//
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		DBUtil.closeConnection(con);
//		DBUtil.closeConnection(ps);
//		DBUtil.closeConnection(rs);
//
//		return flag;
//	}
//
//	@Override
//	public String isValidCredential(String emailId, String password) {
//		String status = "Login Denied! Incorrect Username or Password";
//
//		Connection con = DBUtil.provideConnection();
//
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//
//		try {
//
//			ps = con.prepareStatement("select * from user where email=? and password=?");
//
//			ps.setString(1, emailId);
//			ps.setString(2, password);
//
//			rs = ps.executeQuery();
//
//			if (rs.next())
//				status = "valid";
//
//		} catch (SQLException e) {
//			status = "Error: " + e.getMessage();
//			e.printStackTrace();
//		}
//
//		DBUtil.closeConnection(con);
//		DBUtil.closeConnection(ps);
//		DBUtil.closeConnection(rs);
//		return status;
//	}
//
//	@Override
//	public UserBean getUserDetails(String emailId, String password) {
//
//		UserBean user = null;
//
//		Connection con = DBUtil.provideConnection();
//
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//
//		try {
//			ps = con.prepareStatement("select * from user where email=? and password=?");
//			ps.setString(1, emailId);
//			ps.setString(2, password);
//			rs = ps.executeQuery();
//
//			if (rs.next()) {
//				user = new UserBean();
//				user.setName(rs.getString("name"));
//				user.setMobile(rs.getLong("mobile"));
//				user.setEmail(rs.getString("email"));
//				user.setAddress(rs.getString("address"));
//				user.setPinCode(rs.getInt("pincode"));
//				user.setPassword(rs.getString("password"));
//
//				return user;
//			}
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//
//		DBUtil.closeConnection(con);
//		DBUtil.closeConnection(ps);
//		DBUtil.closeConnection(rs);
//
//		return user;
//	}
//
//	@Override
//	public String getFName(String emailId) {
//		String fname = "";
//
//		Connection con = DBUtil.provideConnection();
//
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//
//		try {
//			ps = con.prepareStatement("select name from user where email=?");
//			ps.setString(1, emailId);
//
//			rs = ps.executeQuery();
//
//			if (rs.next()) {
//				fname = rs.getString(1);
//
//				fname = fname.split(" ")[0];
//
//			}
//
//		} catch (SQLException e) {
//
//			e.printStackTrace();
//		}
//
//		return fname;
//	}
//
//	@Override
//	public String getUserAddr(String userId) {
//		String userAddr = "";
//
//		Connection con = DBUtil.provideConnection();
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//
//		try {
//			ps = con.prepareStatement("select address from user where email=?");
//
//			ps.setString(1, userId);
//
//			rs = ps.executeQuery();
//
//			if (rs.next())
//				userAddr = rs.getString(1);
//
//		} catch (SQLException e) {
//
//			e.printStackTrace();
//		}
//
//		return userAddr;
//	}
//
//}
package Service_Implement;

import Beans.UserBean;
import Constants.IUserConstants;
import Service.UserService;
import Utility.DBUtil;
import Utility.MailMessage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserServiceImpl implements UserService {

    @Override
    public String registerUser(String userName, Long mobileNo, String emailId, String address, int pinCode, String password) {
        UserBean user = new UserBean(userName, mobileNo, emailId, address, pinCode, password);
        return registerUser(user);
    }

    @Override
    public String registerUser(UserBean user) {
        String status = "User Registration Failed!";

        if (isRegistered(user.getEmail())) {
            return "Email Id Already Registered!";
        }

        Connection conn = DBUtil.provideConnection();
        PreparedStatement ps = null;

        try {
            if (conn != null) {
                System.out.println("Connected Successfully!");
                ps = conn.prepareStatement("INSERT INTO " + IUserConstants.TABLE_USER + " VALUES (?, ?, ?, ?, ?, ?)");

                ps.setString(1, user.getEmail());
                ps.setString(2, user.getName());
                ps.setLong(3, user.getMobile());
                ps.setString(4, user.getAddress());
                ps.setInt(5, user.getPinCode());
                ps.setString(6, user.getPassword());

                int k = ps.executeUpdate();
                if (k > 0) {
                    status = "User Registered Successfully!";
                    MailMessage.registrationSuccess(user.getEmail(), user.getName().split(" ")[0]);
                }
            }
        } catch (SQLException e) {
            status = "Error: " + e.getMessage();
            e.printStackTrace();
        } finally {
            DBUtil.closeConnection(ps);
            DBUtil.closeConnection(conn);
        }

        return status;
    }

    @Override
    public boolean isRegistered(String emailId) {
        boolean flag = false;

        Connection con = DBUtil.provideConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = con.prepareStatement("SELECT * FROM user WHERE email=?");
            ps.setString(1, emailId);
            rs = ps.executeQuery();

            if (rs.next()) {
                flag = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeConnection(rs);
            DBUtil.closeConnection(ps);
            DBUtil.closeConnection(con);
        }

        return flag;
    }

    @Override
    public String isValidCredential(String emailId, String password) {
        String status = "Login Denied! Incorrect Username or Password";

        Connection con = DBUtil.provideConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = con.prepareStatement("SELECT * FROM user WHERE email=? AND password=?");
            ps.setString(1, emailId);
            ps.setString(2, password);
            rs = ps.executeQuery();

            if (rs.next()) {
                status = "valid";
            }
        } catch (SQLException e) {
            status = "Error: " + e.getMessage();
            e.printStackTrace();
        } finally {
            DBUtil.closeConnection(rs);
            DBUtil.closeConnection(ps);
            DBUtil.closeConnection(con);
        }

        return status;
    }

    @Override
    public UserBean getUserDetails(String emailId, String password) {
        UserBean user = null;

        Connection con = DBUtil.provideConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = con.prepareStatement("SELECT * FROM user WHERE email=? AND password=?");
            ps.setString(1, emailId);
            ps.setString(2, password);
            rs = ps.executeQuery();

            if (rs.next()) {
                user = new UserBean();
                user.setName(rs.getString("name"));
                user.setMobile(rs.getLong("mobile"));
                user.setEmail(rs.getString("email"));
                user.setAddress(rs.getString("address"));
                user.setPinCode(rs.getInt("pincode"));
                user.setPassword(rs.getString("password"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeConnection(rs);
            DBUtil.closeConnection(ps);
            DBUtil.closeConnection(con);
        }

        return user;
    }

    @Override
    public String getFName(String emailId) {
        String fname = "";

        Connection con = DBUtil.provideConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = con.prepareStatement("SELECT name FROM user WHERE email=?");
            ps.setString(1, emailId);
            rs = ps.executeQuery();

            if (rs.next()) {
                fname = rs.getString("name").split(" ")[0];
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeConnection(rs);
            DBUtil.closeConnection(ps);
            DBUtil.closeConnection(con);
        }

        return fname;
    }

    @Override
    public String getUserAddr(String userId) {
        String userAddr = "";

        Connection con = DBUtil.provideConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = con.prepareStatement("SELECT address FROM user WHERE email=?");
            ps.setString(1, userId);
            rs = ps.executeQuery();

            if (rs.next()) {
                userAddr = rs.getString("address");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.closeConnection(rs);
            DBUtil.closeConnection(ps);
            DBUtil.closeConnection(con);
        }

        return userAddr;
    }
}
