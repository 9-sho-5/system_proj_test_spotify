package api;

import java.io.Writer;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mashape.unirest.http.exceptions.UnirestException;

@WebServlet("/api/callback")
public class Callback extends HttpServlet {

	private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        HttpSession session = request.getSession();	
        Cookie cookie = null;
        
        String code = (request.getParameter("code") == null) ? "" : (String) request.getParameter("code");
        if (code != null){
            Spotify spotify = Spotify.getInstance();
            spotify.setCode(code);
            cookie = new Cookie("loginStatus", "loggedIn");
        } else {
            cookie = new Cookie("loginStatus", "notLoggedIn");
        }

        Spotify spotify = Spotify.getInstance();
        try {
            spotify.crateAccessToken();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        
        response.addCookie(cookie);
        request.setCharacterEncoding("UTF-8");
		response.sendRedirect("index.html");
		
	}
    
}
