package ua.nix.web.servlet;

import ua.nix.web.model.UserAgent;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "UserAgent", value = "/user")
public class MainServlet extends HttpServlet {
    private List<UserAgent> list = new ArrayList<>();

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String ip = req.getRemoteAddr();
        String user = req.getRemoteUser();
        LocalDateTime time = LocalDateTime.now();
        UserAgent userAgent = new UserAgent(ip, user, time);
        list.add(userAgent);
        req.setAttribute("users", list);
        req.getRequestDispatcher("/user.jsp").forward(req,resp);
    }
}
