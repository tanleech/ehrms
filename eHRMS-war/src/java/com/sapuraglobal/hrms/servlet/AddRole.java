/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sapuraglobal.hrms.servlet;

import com.sapuraglobal.hrms.dto.DeptDTO;
import com.sapuraglobal.hrms.dto.UserDTO;
import com.sapuraglobal.hrms.ejb.AccessBeanLocal;
import com.sapuraglobal.hrms.ejb.DeptBeanLocal;
import java.io.IOException;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.json.Json;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author sapura-mac-pro-cto-C02PC1MWG3QT
 */
@WebServlet(
    urlPatterns = {"/addRole"}
)

public class AddRole extends HttpServlet {
    @EJB
    private AccessBeanLocal userBean;
    @EJB
    private DeptBeanLocal deptBean;
    


    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            response.setContentType("text/html;charset=UTF-8");
            String action = request.getParameter("action");
            String dept = request.getParameter("dept");
            String page = "/deptEdit.jsp";
            
            if(action==null||action.isEmpty()||!action.equals("A"))
            {   
                if(dept!=null && !dept.isEmpty())
                {
                try
                {
                    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

                    Date from = formatter.parse("01/01/0000");
                    Date to   = formatter.parse("12/31/9999");

                    List<UserDTO> userList = null;//userBean.getAllRoles();
                    request.setAttribute("usrList", userList);
                    request.setAttribute("dept",dept);
                    //RequestDispatcher view = getServletContext().getRequestDispatcher("/deptEdit.jsp"); 
                    //view.forward(request,response);           
                }
                catch(Exception ex)
                {
                   ex.printStackTrace();
                }
                }
            }
            else if (action!=null&&action.equals("A"))
            {
                //deptBean.addEmployee(userDTO, true);
                String mgr = request.getParameter("mgr");
                System.out.println("json mgr: "+mgr);
                String emp = request.getParameter("emp");
                System.out.println("json emp: "+emp);
                /*
                JsonParser parser = Json.createParser(new StringReader(mgr));
                while (parser.hasNext()) 
                {
                    Event event = parser.next();
                    System.out.println("event: "+event.toString());
                    if(event.VALUE_STRING.equals(event))
                    {
                        System.out.println("value: "+parser.getString());
                    }
    
                }
                parser.close();
                */
                List<UserDTO> mgrList = parseObj(mgr);
                UserDTO mgrDTO = mgrList.get(0);
                mgrDTO.setIsManager(true);
                List<UserDTO>empList = parseObj(emp);
                empList.add(mgrDTO);
                //get dept name
                String deptName = (String)request.getSession().getAttribute("dept");
                DeptDTO deptDTO = new DeptDTO();
                deptDTO.setDescription(deptName);
                deptBean.addEmployees(empList, deptDTO);
                
                page = "/deptList";
                
            }
        RequestDispatcher view = getServletContext().getRequestDispatcher(page); 
        view.forward(request,response);     
            

    }
    
    private List<UserDTO> parseObj(String jsonData)
    {   
        List<UserDTO> results = new ArrayList();
        JsonParser parser = Json.createParser(new StringReader(jsonData));
        while (parser.hasNext()) 
        {
            Event event = parser.next();
            System.out.println("event: "+event.toString());
            if(event.VALUE_STRING.equals(event))
            {
               System.out.println("value: "+parser.getString());
               UserDTO entry = new UserDTO();
               entry.setId(Integer.parseInt(parser.getString()));
               results.add(entry);
            }
    
        }
        parser.close();
        
        return results;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
