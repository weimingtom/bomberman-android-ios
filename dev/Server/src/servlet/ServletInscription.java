package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import flexjson.JSONDeserializer;


/**
 * Servlet implementation class ServletInscription
 */
public class ServletInscription extends HttpServlet implements Servlet {
	private static final long serialVersionUID = 1L;
	private String username, password;
	
	private HttpSession session;
	private Connection co ;
	private String bdLink;
	
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletInscription() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		out.println("<h1>Servlet d'inscription des membres</h1>");	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BufferedReader req = new BufferedReader(new InputStreamReader(request.getInputStream()));
		OutputStreamWriter writer = new OutputStreamWriter(response.getOutputStream());
		String message = req.readLine();
		
		if (message != null) {
			System.out.println("inscription "+message);

			response.setContentType("text/html");
			session = request.getSession();
			
			 // désérialisation des infos envoyé par l'utilisateur dans une arraylist
			JSONDeserializer<ArrayList<String>> jsonDeserializer = new JSONDeserializer<ArrayList<String>>();
			ArrayList<String> identifiers;
			identifiers = jsonDeserializer.deserialize(message);
			
			username = identifiers.get(0);
			password = identifiers.get(1);
			
			
			 // récupération de l'objet de connexion à la bdd présent dans le contexte
			co = (Connection) getServletContext().getAttribute("connectionData");
			bdLink = (String) getServletContext().getAttribute("bdLink");
			
			
			 // si la connexion n'est pas valide on la réétablie
			if( !this.isValid(co)){
				String dbClassName = "com.mysql.jdbc.Driver";
				
				// FIXME moyen le root quand même un user avec simple droits serait mieux
				try {
					Class.forName(dbClassName);
					Properties p = new Properties();
					p.put("user", "root");
					p.put("password", "root");
					co = DriverManager.getConnection(bdLink, p);
				} catch (ClassNotFoundException e) {
					writer.write("ERROR");
					System.out.println("Connection is not valid to bdd");
					e.printStackTrace();
				} catch (SQLException e) {
					writer.write("ERROR");
					System.out.println("Connection is not valid to bdd");
					e.printStackTrace();
				}
			}
			try {
				/** TODO ERROR sera pour une erreur système à gérer plus bas **/ 
				if( inscription() ){
					writer.write("OK");
				}
				else{
					writer.write("BU");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				co.close(); // fermeture de l'accès à la bdd
			} catch (SQLException e) {
				e.printStackTrace();
			}
			writer.flush();
		}
	}
	
	
	/**
	 * méthode permettant d'être sûr que la bd est accessible et la connexion valide
	 * @param connection
	 * @return boolean
	 */
	public static boolean isValid(Connection connection){
		   if(connection==null){
		      return false;
		   }
		   ResultSet ping = null;
		   try{
		      if(connection.isClosed()){return false;}
		      ping = connection.createStatement().executeQuery("SELECT 1");
		      return ping.next();
		   }catch(SQLException sqle){
		      return false;
		   }
		   finally{
		      if(ping!=null){try{ping.close();}catch(Exception e){}}
		   }  
		}
	
	/**
	 * méthode d'inscription avec vérification de l'existance de l'userName 
	 * @return boolean
	 * @throws SQLException
	 */
	public boolean inscription() throws SQLException{
		boolean result = false;
		
		Statement theStatement = co.createStatement();

		// récupère les entrées avec le même userName
		ResultSet theResult = theStatement.executeQuery("select userName from Users where username='"+ username +"'"); 
		if(!theResult.next()){
    		boolean insert = theStatement.execute("INSERT into Users VALUES ('"+ username +"','"+ password +"')"); 
    		System.out.println("INSERTION "+ theStatement.getUpdateCount());
    		
    		// une fois l'ajoût fait, on ajoute aussi le userKey dans la liste des usersOnline
    		HashMap<String, String> users = (HashMap<String, String>) getServletContext().getAttribute("usersOnline");
			users.put(session.getId(), username);
    		result = true;
    		
    		System.out.println("Users online: "+users.toString());
		}		
		return result;
	}
	

}
