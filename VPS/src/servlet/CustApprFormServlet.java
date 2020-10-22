package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import data.FileData;
import model.Venue;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;

@WebServlet(urlPatterns = { "/CAC" })
public class CustApprFormServlet extends HttpServlet {
	
	private static final long serialVersionUID = 3927327685349439335L;


	@Override
	    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	            throws ServletException, IOException {
		
		request.getSession().setAttribute("location", null);
		request.getSession().setAttribute("id", null);	 
		  
		  String qrParam = request.getParameter("qrParam");			
		  System.out.println("qrParam is: "+qrParam);
		  
		  if(qrParam!=null && !qrParam.isEmpty()) {
			 
			  String id=null,loc=null;
			  boolean inTheList = false; 
				
			  List<Venue> sheetData = null;
			  ServletContext context = getServletContext();
			  if (context.getAttribute("sheetData") == null
					  || ((Long)context.getAttribute("fileLastModified") != null && (Long)context.getAttribute("fileLastModified") != FileData.getLastModified())) {
				  System.out.println("NULL, get the venue list");
				  sheetData = FileData.getFile();
				  context.setAttribute("sheetData", sheetData);
				  context.setAttribute("fileLastModified", FileData.getLastModified());
			  }else
				  sheetData = (List<Venue>)context.getAttribute("sheetData");   
			  
			  for(Venue cur:sheetData) {
				  if(qrParam.equals(cur.getVenueId())) {
					  id=qrParam;
					  loc=cur.getVenueChHKName()+" "+cur.getVenueEngName();
					  System.out.println("loc is: "+loc);
					  inTheList=true;
					  break;
				  }
			  }

			  if(inTheList) {				
				  request.getSession().setAttribute("location", loc);
				  request.getSession().setAttribute("id", id);
				   RequestDispatcher dispatcher //
	                = this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/custApprForm.jsp");
	 
			        dispatcher.forward(request, response);
			  }else {
			
				  response.setContentType("text/html;charset=utf-8");
				  PrintWriter writer = response.getWriter();

				  String htmlRespone = "<html><h3>";

				  htmlRespone += "Invalid access<br/>";	

				  htmlRespone += "</h3></html>";

				  // return response
				  writer.println(htmlRespone);	
			  }
		 }else {
			  
		        RequestDispatcher dispatcher //
                = this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/custApprForm.jsp");
 
		        dispatcher.forward(request, response);
		 }
	 
}
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		// read form fields

		System.out.println("sloc is: " + request.getParameter("sloc"));

		System.out.println("Search keyword is: " + request.getParameter("keyword"));

		String searchId = request.getParameter("sid");
		System.out.println("Search id is: " + searchId);
			
		if(searchId!=null && !searchId.isEmpty()) {
				  request.getSession().setAttribute("location", request.getParameter("sloc"));
				  request.getSession().setAttribute("id", searchId);			
		}
		String location = (String) request.getSession().getAttribute("location");
		System.out.println("location is: "+location);
		String id = (String) request.getSession().getAttribute("id");
		System.out.println("id is: "+id);
		if(id!=null && !id.isEmpty()) {
			
			String cat[] = request.getParameterValues("category");
			String category = "";

			if (cat != null) {
			
				for (String t : cat) {
//					category += ""+(t.replaceAll("/\"/g", "\\'"))+"\",";
					category += t+",";
				}
				
				category = category.substring(0,category.length()-1);
				System.out.println("category are: "+category);
			}

  
			String feedback = request.getParameter("feedback");  
			System.out.println("feedback is: " + feedback);
//			String username = request.getParameter("username");  
			System.out.println("feedback is: " + feedback);
//			System.out.println("username is: " + username);
			
			FileData.createCsv(id,feedback,location,category);
			  
	        RequestDispatcher dispatcher //
	                = this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/end.jsp");
	 
	        dispatcher.forward(request, response);
		}
	}

}
