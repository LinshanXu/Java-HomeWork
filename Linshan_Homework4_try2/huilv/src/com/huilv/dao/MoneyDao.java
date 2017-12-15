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
			//1��ע������ (ֻ��һ��)
			Class.forName("com.mysql.jdbc.Driver");
			//2����������(Connection) 
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/huilv_db", "root", "123456");
			//3������ִ��SQL��������(Statement)
			stm = conn.createStatement();
			//4��׼����ִ��SQL���
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
			//6���ͷ���Դ(ע��ر�˳��)
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
