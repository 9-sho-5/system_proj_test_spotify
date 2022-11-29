package api;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
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
        String code = (request.getParameter("code") == null) ? "" : (String) request.getParameter("code");
        
		Spotify spotify = new Spotify();
        // token所持の確認
        spotify.setCode(code);
        try {
            spotify.crateAccessToken();
        } catch (UnirestException e) {
        }

        String data = spotify.serch(keyword);

		StringBuilder builder = new StringBuilder();
		builder.append('{');
		builder.append("\"data\":\"").append(data).append("\"");
		builder.append('}');
		String json = builder.toString();
		
		System.out.println(json);
		Writer writer = response.getWriter();
		writer.append(json);
		writer.flush();
	}
}
