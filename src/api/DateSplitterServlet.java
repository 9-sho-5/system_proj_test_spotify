package api;

import java.io.Writer;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/api/execute")
public class DateSplitterServlet extends HttpServlet {

	// private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String date = (request.getParameter("date") == null) ? ""
        : (String) request.getParameter("date");

        DateSplitter dateSplitter = new DateSplitter();
        dateSplitter.setDate(date);
        dateSplitter.execute();
        
        StringBuilder builder = new StringBuilder();

		builder.append('{');
		builder.append("\"year\":\"").append(dateSplitter.getYear()).append("\",");
		builder.append("\"month\":\"").append(dateSplitter.getMonth()).append("\",");
		builder.append("\"day\":\"").append(dateSplitter.getDay()).append("\"");
		builder.append('}');
		String json = builder.toString();
		System.out.println(json);
		response.setContentType("application/json");
		Writer writer = response.getWriter();
		writer.append(json);
		writer.flush();
    }

}

