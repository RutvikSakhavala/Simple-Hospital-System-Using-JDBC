import java.sql.*;
import java.util.Scanner;

public class Hospital {
	private static final String url="jdbc:mysql://localhost:3306/Hospital";
	private static final String username="root";
	private static final String password="Rutvik232";
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		    Connection con =  DriverManager.getConnection(url, username, password);
			Patients patient = new Patients(con,sc);
			Doctor doctor = new Doctor(con);
			
			while(true) {
				System.out.println("HOSPITAL MANAGEMENT SYSTEM");
				System.out.println("1. Add Patient");
				System.out.println("2. View Pateint");
				System.out.println("3. View Doctors");
				System.out.println("4. Book Appointment");
				System.out.println("0. Exit");
				System.out.println("Enter your choise: ");
				int choise = sc.nextInt();
				
				switch(choise) {
				case 1:
					patient.addPatient();
					break;
				case 2:
					patient.viewPatient();
					break;
				case 3:
					doctor.viewDoctors();
					break;
				case 4:
					bookAppointment(patient, doctor, con, sc);
					break;
				case 0:
					System.exit(0);
				default:
					System.out.println("wrong choise");
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void bookAppointment(Patients patient, Doctor doctor, Connection con,Scanner sc) {
		System.out.println("Enter Patient Id :");
		int patientId = sc.nextInt();
		System.out.println("Enter Doctor Id :");
		int doctorId = sc.nextInt();
		System.out.println("Enter appoinment date (YYYY-DD-MM)");
		String appoinmentDate = sc.next();
		if(patient.getPatientById(patientId) && doctor.getDoctorById(doctorId))
		{
			if(checkDoctorAvailibility(doctorId,appoinmentDate,con))
			{
				String appoinmentQuery = "insert into Appointments(patient_id,doctor_id,appointment_date) values(?,?,?)";
				try {
					PreparedStatement pstmt = (con).prepareStatement(appoinmentQuery);
					pstmt.setInt(1,patientId);
					pstmt.setInt(2, doctorId);
					pstmt.setString(3, appoinmentDate);
					int affectedRows = pstmt.executeUpdate();
					if(affectedRows>0)
					{
						System.out.println("Appointment Booked");
					}
					else 
					{
						System.out.println("Failed to book appoinment");
					}
				}
				catch(SQLException e) {
					e.printStackTrace();
				}
			}
			else {
				System.out.println("Doctor not available on this Date");
			}
		}
		else 
		{
			System.out.println("Either Doctor ro patient does not exist");
		}
		
	}
	
	public static boolean checkDoctorAvailibility(int doctorId, String appoinmentDate, Connection con) {
		String query = "select count(*) from Appointments where doctor_id = ? AND appointment_date = ?";
		try {
			PreparedStatement pstmt = (con).prepareStatement(query);
			pstmt.setInt(1,doctorId);
			pstmt.setString(2, appoinmentDate);
			ResultSet resultSet = pstmt.executeQuery();
			if(resultSet.next())
			{
				int count = resultSet.getInt(1);
				if(count==0) {
						return true;
				}
				else {
					return false;
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
