/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.servlet;

import com.mycompany.dao.EmployeeDAO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;


public class EmployeeReportServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //String firstName = request.getParameter("firstName");
        //String lastName = request.getParameter("lastName");
        //String email = request.getParameter("email");
        String action = request.getParameter("download");
        try {
            
            EmployeeDAO emDAO = new EmployeeDAO();
            JasperPrint jasperPrint = emDAO.generateEmployeeReport();
            if(action!=null){
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "attachment; filename=employee_report.pdf");
                JasperExportManager.exportReportToPdfStream(jasperPrint, response.getOutputStream());
            } else if(request.getParameter("preview")!=null){
                displayReportThruHTML(response, jasperPrint);
            }
 
            
        } catch (JRException | IOException e) {
            e.printStackTrace();
            // Display the exception on the web page
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            out.println("<html><body>");
            out.println("<h3>Error occurred: " + e.getMessage() + "</h3>");
            out.println("<pre>");
            e.printStackTrace(out);
            out.println("</pre>");
            out.println("</body></html>");


        }
       //response.sendRedirect("success.jsp");
    }
    
    
    
    public void displayReportThruHTML(HttpServletResponse response, JasperPrint jasperPrint) throws IOException, JRException{
            // Create an exporter instance for HTML
            HtmlExporter exporter = new HtmlExporter();
            // Set the export parameters
            response.setContentType("text/html");
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleHtmlExporterOutput(response.getWriter()));
            exporter.exportReport();  // This writes the HTML to the response output stream
    }

}
