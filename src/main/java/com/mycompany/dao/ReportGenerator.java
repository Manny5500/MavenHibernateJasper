/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dao;
import com.mycompany.model.Employee;
import java.util.ArrayList;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.HashMap;

public class ReportGenerator {

    public static void generateEmployeeReport() {
        try {
            // Fetch data from the database using Hibernate
            EmployeeDAO employeeDAO = new EmployeeDAO();
            List<Employee> employees = new ArrayList<>();
            employees.add(new Employee(1L, "Sample"));
            

            // Compile the JRXML to Jasper file
            JasperReport jasperReport = JasperCompileManager.compileReport("employee_report.jrxml");

            // Convert List<Employee> to JRDataSource (JasperReports' data source)
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(employees);

            // Fill the report with data
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap<>(), dataSource);

            // Export the report to a PDF file
            JasperExportManager.exportReportToPdfFile(jasperPrint, "employee_report.pdf");

            System.out.println("Report generated successfully!");
        } catch (JRException e) {
            e.printStackTrace();
        }
    }

    
}
