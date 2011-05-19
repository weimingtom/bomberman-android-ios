package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import flexjson.JSONSerializer;
import game.Engines;

/**
 * Servlet implementation class ServletGamesList
 */
public class ServletGamesList extends HttpServlet implements Servlet {
	private static final long serialVersionUID = 1L;
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletGamesList() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		out.println("<h1>Servlet listant les parties en cour</h1>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BufferedReader req=new BufferedReader(new InputStreamReader(request.getInputStream()));
		OutputStreamWriter writer = new OutputStreamWriter(response.getOutputStream());
			String message =req.readLine();
			System.out.println("gl => " + message);
			if(message.length() > 0){
				
				// vérification que l'utilisateur s'est connecté
//				HashMap<String, String> users = (HashMap<String, String>) getServletContext().getAttribute("usersOnline");
//				if(users.containsKey(message)){
									
					System.out.println(message);
					
					response.setContentType("text/html");
					
					JSONSerializer jsonSerializer = new JSONSerializer();				
					Engines engines = (Engines) getServletContext().getAttribute("engines");
	          	  	jsonSerializer.serialize(engines.gamesList(),writer);
	          	  	writer.flush();
//				}
//				else{
//					System.out.println(":: "+ message.length());
//					writer.write("ERROR");
//					writer.flush();
//				}
			}
	}

}
