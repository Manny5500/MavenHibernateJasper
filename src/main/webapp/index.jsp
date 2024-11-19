<%@page import="java.util.List"%>
<%@page import="com.mycompany.model.Employee"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="style.css">
    </head>
    <body>
    
    <h3>Add Employee</h3>
        <form action="AddEmployeeServlet" method="post">
            <input type = "text" name="name" placeholder="Name">
            <input type = "text" name="email" placeholder="sample@gmail.com">
            <input type = "text" name="address" placeholder="Address">
            <input type = "submit" value="Submit">
        </form>
    <h3>Employee List</h3>

    <table border="1">
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Email</th>
            <th>Address</th>
        </tr>

        <% 
            List<Employee> employees = (List<Employee>) request.getAttribute("employees"); 
            for(Employee employee : employees) {
        %>
            <tr>
                <td><%= employee.getId() %></td>
                <td><%= employee.getFirstName() %></td>
                <td><%= employee.getEmail() %></td>
                <td><%= employee.getAddress() %></td>
            </tr>
        <% 
            }
        %>

    </table>

    <form action="EmployeeReportServlet" method="GET">
        <input type="submit" value="Download" name="download">
    </form>
    
    <form action="EmployeeReportServlet" method="GET">
        <input type="submit" value="Preview" name="preview">
    </form>

</body>

</html>
