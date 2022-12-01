package api;

import java.io.IOException;
import java.io.Writer;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mashape.unirest.http.exceptions.UnirestException;

/**
 * Spotify Serch Servlet
 */
@WebServlet("/api/search")
public class SearchServlet extends HttpServlet  {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String keyword = (request.getParameter("keyword") == null) ? "" : (String) request.getParameter("keyword");
        
		Spotify spotify = Spotify.getInstance();

		if(spotify.getAccessToken() == null){
			try {
				spotify.crateAccessToken();
			} catch (UnirestException e) {
			}
		}

		String json = spotify.serch(keyword);
		response.setContentType("application/json");
		request.setCharacterEncoding("UTF-8");
		System.out.println(json);
		Writer writer = response.getWriter();
		writer.append(json);
		writer.flush();
	}
}
