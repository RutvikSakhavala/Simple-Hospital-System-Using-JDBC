

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Patients {
	private Connection con;
	private Scanner sc;
	
	public Patients(Connection con, Scanner sc) 
	{
		this.con = con;
		this.sc = sc;
	}
	
	public void addPatient()
	{
		sc.nextLine();
		System.out.println("Enter Patient Name : ");
		String name = sc.nextLine();
		System.out.println("Enter Patient Age: ");
		int age = sc.nextInt();
		
		sc.nextLine();
		System.out.println("Enter Pateint Gender : ");
		String gender = sc.nextLine();
		
		try
		{
			String query = "insert into patients(name,age,gender)values(?,?,?)";
			PreparedStatement pstmt = con.prepareStatement(query);
			pstmt.setString(1,name);
			pstmt.setInt(2, age);
			pstmt.setString(3,gender);
			int affectedRows = pstmt.executeUpdate();
			
			if(affectedRows>0)
			{
				System.out.println("Patient Added Successfully!!");
			}
			else 
			{
				System.out.println("Failed to add Patient");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void viewPatient()
	{
		String query = "select * from patients";
		try {
			PreparedStatement pstmt = con.prepareStatement(query);
			ResultSet resultSet = pstmt.executeQuery();
			
			System.out.println("Patients :");
			System.out.println("+------------+-------------------------+---------+-----------+");
			System.out.println("| Patient Id | Name                   | Age     | Gender    |");
			System.out.println("+------------+-------------------------+---------+-----------+");
			while(resultSet.next())
			{
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				int age = resultSet.getInt("age");
				String gender  = resultSet.getString("gender");
				System.out.printf("|%-12s|%-24s|%-9s|%-11s\n",id,name,age,gender);
				System.out.println("+------------+-------------------------+---------+-----------+");
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public boolean getPatientById(int id)
	{
		String query = "select * from patients where id = ?";
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
