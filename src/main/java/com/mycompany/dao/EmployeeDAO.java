/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.dao;

import com.mycompany.model.Employee;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class EmployeeDAO {

    private final SessionFactory factory;
    
    public EmployeeDAO() {
        factory = new Configuration().configure("hibernate.cfg.xml")
                                     .addAnnotatedClass(Employee.class)
                                     .buildSessionFactory();
    }

    public void saveEmployee(Employee employee) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        session.save(employee);
        session.getTransaction().commit();
    }
    
    public List<Employee> getAllEmployees() {
        Session session = factory.getCurrentSession();
        session.beginTransaction();
        List<Employee> employees = session.createCriteria(Employee.class).list();
        session.getTransaction().commit();
        return employees;
    }
   
   public JasperPrint generateEmployeeReport(){
        List<Employee> employeeList = getAllEmployees();
        try {
            InputStream reportStream = getClass().getClassLoader().getResourceAsStream("employee_report.jrxml");
            if (reportStream == null) {
                throw new FileNotFoundException("Report file 'employee_report.jrxml' not found in classpath.");
            }
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap<>(), new JRBeanCollectionDataSource(employeeList));
            return jasperPrint;
        } catch (JRException | FileNotFoundException ex) {
            Logger.getLogger(EmployeeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
   }
}
