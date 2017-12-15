package com.huilv.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MoneyDao {
	public double select(String currency){
		Connection conn = null;
		Statement stm = null;
		double huilv = 0;
		try {
			//1、注册驱动 (只做一次)
			Class.forName("com.mysql.jdbc.Driver");
			//2、建立连接(Connection) 
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/huilv_db", "root", "123456");
			//3、创建执行SQL的语句对象(Statement)
			stm = conn.createStatement();
			//4、准备并执行SQL语句
			String sql = "select huilv from huilv where currency=\'"+currency+"\'";
			
			ResultSet rs = stm.executeQuery(sql);
			while(rs.next()) {
				huilv = rs.getDouble("huilv");
			}
			return huilv;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			//6、释放资源(注意关闭顺序)
			try {
				if(null!=stm) {
					stm.close();
					stm = null;
				}
				if(null!=conn) {
					conn.close();
					conn = null;
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		return huilv;
	}
}
