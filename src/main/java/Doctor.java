

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Doctor {
	private Connection con;	
	
	public Doctor(Connection con) 
	{
		this.con = con;		
	}	
	
	public void viewDoctors()
	{
		String query = "select * from Doctors";
		try {
			PreparedStatement pstmt = con.prepareStatement(query);
			ResultSet resultSet = pstmt.executeQuery();
			
			System.out.println("Doctors :");
			System.out.println("+-----------+------------------------+--------------------+");
			System.out.println("| Doctor Id | Name                   | Specialization     |");
			System.out.println("+-----------+------------------------+--------------------+");
			while(resultSet.next())
			{
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				String specialization = resultSet.getString("specialization");
				
				System.out.printf("| %-10s| %-23s| %-19s|\n",id,name,specialization);
				System.out.println("+-----------+------------------------+--------------------+");
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public boolean getDoctorById(int id)
	{
		String query = "select * from Doctors where id = ?";
		try 
		{
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setInt(1, id);
			ResultSet resultSet = pstmt.executeQuery();
			if(resultSet.next())
			{
				return true;
			}
			else {
				return false;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
}
