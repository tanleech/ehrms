/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sapuraglobal.hrms.servlet;

import com.sapuraglobal.hrms.dto.AccessDTO;
import com.sapuraglobal.hrms.dto.ModuleDTO;
import com.sapuraglobal.hrms.dto.RoleDTO;
import com.sapuraglobal.hrms.ejb.AccessBeanLocal;
import com.sapuraglobal.hrms.ejb.ModuleBeanLocal;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
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
    urlPatterns = {"/roleEdit"}
)

public class RoleEdit extends HttpServlet {
    @EJB
    private ModuleBeanLocal moduleBean;
    @EJB
    private AccessBeanLocal accessBean;
    


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
        System.out.println("action: "+action);
        String page = "/roleEdit.jsp";
        if(action==null||action.isEmpty())
        {
            List<ModuleDTO> moduleList = moduleBean.getAllModules();
            request.getSession().setAttribute("moduleList",moduleList);
        }
        else if(action.equals("A"))
        {   
            List<ModuleDTO> moduleList = (List)request.getSession().getAttribute("moduleList");
            List<AccessDTO> accessList = new ArrayList();
            RoleDTO role = new RoleDTO();
            String roleName = request.getParameter("name");
            role.setDescription(roleName);
            
            //system access
            String acr = request.getParameter("system");
            
            for(int i=0;i<moduleList.size();i++)
            {
                ModuleDTO module = moduleList.get(i);
                AccessDTO access = new AccessDTO();
                if(acr.equals("0"))
                {
                  access.setAccess(0);
                }
                else
                {
                    String rights = request.getParameter(module.getName());
                    System.out.println("rights: "+rights);
                    System.out.println("module name: "+module.getName());
                    access.setAccess(Integer.parseInt(rights));
                }
                accessList.add(access);
            }//end for
            role.setAccessList(accessList);
            //update ejb
            accessBean.addRole(role);
             
        }
        else if(action.equals("U"))
        {
            
        }
        RequestDispatcher view = getServletContext().getRequestDispatcher(page); 
        view.forward(request,response);     
            

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
