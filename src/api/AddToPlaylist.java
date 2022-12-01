package api;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

@WebServlet("/api/add_playlist")
public class AddToPlaylist extends HttpServlet {
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String trackId = (request.getParameter("trackId") == null) ? "" : (String) request.getParameter("trackId");
        
        JSONArray uris = new JSONArray();
        uris.put(trackId);

        Spotify spotify = Spotify.getInstance();
		spotify.addTrack(ENV.getPlaylistId(), uris);

		response.setContentType("application/json");
		request.setCharacterEncoding("UTF-8");
	}
}