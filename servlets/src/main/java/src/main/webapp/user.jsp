<%@ page import="ua.nix.web.model.UserAgent" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="X-UA-Compatible" content="ie=edge">
        <title>User-Agent</title>
        <style>
            table,td{
                border: 1px solid black;
            }
        </style>
    </head>
    <body>
        <section>
            <h3>User-Agent List</h3>
            <table>
                <thead>
                <tr>
                    <th>IP Address</th><th>User-Agent</th><th>Time</th>
                </tr>
                </thead>
                <tbody>
                <% List<UserAgent> users = (ArrayList<UserAgent>) request.getAttribute("users");
                for (UserAgent user : users) {
                    %>
                <tr>
                    <td><strong><%=user.getIpAdress()%></strong></td>
                    <td><strong><%=user.getUserAgent()%></strong></td>
                    <td><%=user.getTime()%></td>
                </tr>
                <%}%>
                </tbody>
            </table>
        </section>
    </body>
</html>
