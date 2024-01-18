package com.springbasic.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.Test;

public class ConnectionTest {
	private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String URL = "jdbc:mysql://localhost:3306/hay?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Seoul&characterEncoding=UTF-8";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "1234";
	
	@Test // 아래의 메서드가 JUNit 4 라이브러리에의해 동작되는 테스트 메서드임을 알림
	public void testConnection() throws ClassNotFoundException {
		Class.forName(DRIVER);
		
		try(Connection con = DriverManager.getConnection(URL, USERNAME, PASSWORD)){
			if(con!=null) {
				System.out.println(con.toString());
			}
		} catch(SQLException s) {
			s.printStackTrace();
		}
	}
	
}
