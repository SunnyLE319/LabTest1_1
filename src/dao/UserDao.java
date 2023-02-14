package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utils.DBUtils;


import domain.User;

public class UserDao {

	public User selectByUsername(String username) {
		Connection con = DBUtils.getConnection();
		List<User> list = new ArrayList<>();
		String sql = "select * from user where username=?";
		PreparedStatement pstmt =null;
		ResultSet rs;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, username);
			rs = pstmt.executeQuery();
			list=resultSetToBean(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			DBUtils.close(con,pstmt);
        }
		return list.isEmpty()?null:list.get(0);
	}

	public boolean insert(User user) {
		Connection con = DBUtils.getConnection();
		PreparedStatement pstmt =null;
		String sql = "INSERT INTO user(username,password) VALUES(?,?)";
		boolean res = false;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1,user.getUsername());
			pstmt.setString(2,user.getPassword());
		
			System.out.println(user.getUsername());
			
			System.out.println(res);
			res = (pstmt.executeUpdate()==1);
			System.out.println(res);
		}catch (SQLException e) {
			if(!e.getMessage().contains("PRIMARY")){
				e.printStackTrace();
			}else {
				System.out.println("该用户已存在");
				return false;
			}
		}finally {
			DBUtils.close(con,pstmt);
		}
		return res;
	}
	
	
	public boolean delete(Integer userid) {
		Connection con = DBUtils.getConnection();
		PreparedStatement pstmt =null;
		String sql = "delete from user where userid= ?";
		boolean res = false;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1,userid);
			res = (pstmt.executeUpdate()==1);
		}catch (SQLException e) {
			e.printStackTrace();
			return false;
		}finally {
			DBUtils.close(con,pstmt);
        }
		return res;
	}	
	public boolean update(User user) {
		Connection con = DBUtils.getConnection();
		PreparedStatement pstmt =null;
		String sql = "update user set username=?,nickname=?,password=?,gender=?,phone=?,email=?,address=? where userid= ?";
		boolean res = false;
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1,user.getUsername());
			pstmt.setString(3,user.getPassword());
			res = (pstmt.executeUpdate()==1);
		}catch (SQLException e) {
			e.printStackTrace();
			return false;
		}finally {
			DBUtils.close(con,pstmt);
		}
		return res;
	}
		private static List<User> resultSetToBean(ResultSet rs) throws SQLException {
			List<User> list = new ArrayList<>();
			while (rs.next()){
				User user = new User();
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("password"));
				list.add(user);
			}
			return list;
		}
	


	
}
