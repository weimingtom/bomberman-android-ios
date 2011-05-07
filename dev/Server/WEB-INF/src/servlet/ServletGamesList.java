package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import flexjson.JSONSerializer;
import game.Engines;

public class ServletGamesList extends HttpServlet{
	
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException{
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		out.println("<h1>Servlet listant les parties en cour</h1>");
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		BufferedReader req=new BufferedReader(new InputStreamReader(request.getInputStream()));
			String message =req.readLine();
			if(message != null){
				System.out.println(message);
				
				response.setContentType("text/html");
				PrintWriter out = response.getWriter();
				
				JSONSerializer jsonSerializer = new JSONSerializer();				
				Engines engines = (Engines) getServletContext().getAttribute("engines");
          	  	jsonSerializer.serialize(engines.gamesList(),out);

			}
	}

}
