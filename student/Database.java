package lab13.student;

import java.sql.*;

public class Database {
	private final String JDBC_DRIVE=
			"oracle.jdbc.driver.OracleDriver";
	private final String DB_URL=
			"jdbc:oracle:thin:@DESKTOP-B0Q1348:1521:orcle";
	private final String user="jExper";
	private final String password="1234";
	
	private String sql;
	private Connection conn;
	private Statement stmt;
	private ResultSet result;
	
	public Database(){
		try{
			Class.forName(JDBC_DRIVE);
			//通过驱动建立与数据库的连接
			conn=DriverManager.getConnection(DB_URL,user,password);
			stmt=conn.createStatement();
		}
		catch(SQLException se){
			se.printStackTrace();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void setSql(String sql){
		this.sql=sql;
	}
									//查询操作
	public ResultSet getResult(){
		try{
			result=stmt.executeQuery(sql);
		}
		catch(SQLException se){
			se.printStackTrace();
		}
		return result;
	}
	//本身带sql脚本
	public ResultSet getResult(String sql){
		try{
			result=stmt.executeQuery(sql);
		}
		catch(SQLException se){
			se.printStackTrace();
		}
		
		return result;
	}
						//更新（修改，插入，删除数据）
	public void Update(){
		try{
			stmt.executeUpdate(sql);
		}
		catch(SQLException se){
			se.printStackTrace();
		}
	}
	
	public void Update(String sql){
		try{
			stmt.executeUpdate(sql);
		}
		catch(SQLException se){
			se.printStackTrace();
		}
	}
	
	public void close(){
		try{
			if(result!=null){
				result.close();
			}
			stmt.close();
			conn.close();
		}
		catch(SQLException se){
			se.printStackTrace();
		}	
	}
}
