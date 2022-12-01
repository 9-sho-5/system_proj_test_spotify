package api;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/api/search")
public class AddToPlaylist extends HttpServlet {
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String playlistId = (request.getParameter("playlistId") == null) ? "" : (String) request.getParameter("playlistId");
        
		Spotify spotify = Spotify.getInstance();

		response.setContentType("application/json");
		request.setCharacterEncoding("UTF-8");
	}
}