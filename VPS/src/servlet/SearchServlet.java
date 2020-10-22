package servlet;

import model.Venue;
import model.Venue.venueMap;
import java.io.IOException;
import java.util.List;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import com.google.gson.Gson;
import data.FileData;
import javax.servlet.ServletContext;

@WebServlet(name = "search", urlPatterns = "/search")
public class SearchServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2636503230939791249L;
	private Gson gson = new Gson();

	@Override

	protected void doGet(HttpServletRequest request, HttpServletResponse response)

			throws ServletException, IOException {

		processRequest(request, response);

	}

	@Override

	protected void doPost(HttpServletRequest request, HttpServletResponse response)

			throws ServletException, IOException {

		processRequest(request, response);

	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
		List<Venue> sheetData = null;
		request.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		System.out.println( "search "+request.getParameter("searchKW"));
		ServletContext context = getServletContext();
		if (context.getAttribute("sheetData") == null
				|| ((Long)context.getAttribute("fileLastModified") != null && (Long)context.getAttribute("fileLastModified") != FileData.getLastModified())) {
			System.out.println("NULL, get the venue list");
			sheetData = FileData.getFile();
			context.setAttribute("sheetData", sheetData);
			context.setAttribute("fileLastModified", FileData.getLastModified());

		} else {
//			System.out.println("NOT NULL, reuse old list");
			sheetData = (List<Venue>) context.getAttribute("sheetData");
//	        	
		}
		venueMap[] searchResult = Venue.getSearchResult(request.getParameter("searchKW"), sheetData);
//	        String venueJsonString = this.gson.toJson(sheetData);
		String venueJsonString = this.gson.toJson(searchResult);

		PrintWriter out = response.getWriter();

		out.print(venueJsonString);
		out.flush();
	}

	
}
