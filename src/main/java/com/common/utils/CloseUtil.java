package com.common.utils;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CloseUtil {
	public static void close(Closeable obj){
		try {
			if(obj!=null) obj.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void close(Connection conn){
		try {
			if(conn!=null) conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void close(PreparedStatement pstat){
		try {
			if(pstat!=null) pstat.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void close(ResultSet rs){
		try {
			if(rs!=null) rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}