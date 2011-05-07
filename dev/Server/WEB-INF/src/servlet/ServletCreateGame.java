package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletCreateGame extends HttpServlet{
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException{
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		out.println("<h1>Servlet création partie multi</h1>");
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		BufferedReader req=new BufferedReader(new InputStreamReader(request.getInputStream()));
			String message =req.readLine();
			if(message != null){
				//JSONDeserializer<ColorModel> jsonDeserializer = new JSONDeserializer<ColorModel>();
				//ColorModel m = jsonDeserializer.deserialize(message);
				System.out.println(message);

			}
	}

}
