/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sapuraglobal.hrms.servlet;

import com.sapuraglobal.hrms.dto.AccessDTO;
import com.sapuraglobal.hrms.dto.UserDTO;
import com.sapuraglobal.hrms.ejb.AccessBeanLocal;
import com.sapuraglobal.hrms.ejb.UserBeanLocal;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author sapura-mac-pro-cto-C02PC1MWG3QT
 */
@WebServlet(
    urlPatterns = {"/login"}
)
public class Login extends HttpServlet {

    @EJB(beanName="UserBean")
    private UserBeanLocal userBean;

    @EJB(beanName="AccessBean")
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
        String userId = request.getParameter("loginId");
        String password = request.getParameter("password");
        String page="/login.jsp";
        if(userId==null||userId.isEmpty()
           ||password==null||password.isEmpty())
        {
            //redirect to login.jsp
            System.out.println("redirect to login");
            //response.sendRedirect("login.jsp");
        }
        else         
        {

            //authenticate
            UserDTO auth = userBean.authenticate(userId, password, true);
            
            
            if(auth!=null)
            {
                HttpSession session = request.getSession();
                
                
                //auth.getRole().getRole().setAccessList(accessBean.getAccessRights(auth.getRole().getRole().getId()));
                List<AccessDTO> list = accessBean.getAccessRights(auth.getRole().getRole().getId());
                session.setAttribute("access", convertToACRMap(list));
                //determine manager
                UserDTO mgr = userBean.getUserFromId(auth.getApprover());
                if(mgr!=null)
                {
                    auth.setApproverEmail(mgr.getEmail());
                    auth.setApproverName(mgr.getName());
                }
                List<UserDTO> resultList = userBean.getReporteeList(auth.getId());
                System.out.println("auth id: "+auth.getId());
                if(resultList!=null&&resultList.size()>0)
                {
                    auth.setIsManager(true);
                }
                session.setAttribute("User", auth);
                page = "/leaveTxn?action=list";
            }
            else
            {
                request.setAttribute("error","login credentials not match.");

            }
        }
            RequestDispatcher view = getServletContext().getRequestDispatcher(page); 
            view.forward(request,response);    
        
        
    }
    
    private HashMap convertToACRMap(List<AccessDTO>accessList)
    {
        HashMap map = new HashMap();
        for(int i=0;i<accessList.size();i++)
        {
            AccessDTO access = accessList.get(i);
            map.put(access.getModule().getName(), access);
        }
        return map;
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
