/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sapuraglobal.hrms.servlet;

import com.sapuraglobal.hrms.dto.LeaveEntDTO;
import com.sapuraglobal.hrms.dto.LeaveTypeDTO;
import com.sapuraglobal.hrms.dto.UserDTO;
import com.sapuraglobal.hrms.ejb.LeaveBeanLocal;
import com.sapuraglobal.hrms.ejb.UserBeanLocal;
import java.io.IOException;
import java.io.PrintWriter;
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
    urlPatterns = {"/leaveEntAdd"}
)

public class LeaveEntAdd extends HttpServlet {
    @EJB(beanName="LeaveBean")
    private LeaveBeanLocal leaveBean;
    
    @EJB(beanName="UserBean")
    private UserBeanLocal userBean;

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
            System.out.println("action in LeaveEntAdd: "+action);
            String page = "/employeeLeaveDetl.jsp";
            UserDTO loginUser = (UserDTO)request.getSession().getAttribute("User");
            


            if(action!=null)
            {
                
                if (action.equals("AS"))
                {
                   String dayStr = request.getParameter("days");
                   String typeIdStr = request.getParameter("leaveType");
                   int typeId = (int) Double.parseDouble(typeIdStr);
                   LeaveTypeDTO typeDTO = leaveBean.getLeaveSetting(typeId);
                   String login = request.getParameter("login");
                   System.out.println("login ID in leaveEntAdd: "+login);
                   UserDTO user = userBean.getUser(login);
                   
                   double days = Double.parseDouble(dayStr);
                   LeaveEntDTO entDto = new LeaveEntDTO();
                   entDto.setCurrent(days);
                   entDto.setCarriedOver(0);
                   entDto.setMax(0);
                   entDto.setBalance(days);
                   entDto.setLeaveType(typeDTO);
                   entDto.setUser(user);
                   leaveBean.setAuthor(loginUser);
                   leaveBean.addLeaveEnt(entDto);
                   page="/leaveEnt?action=U&id="+login;
                     
                }
                else if (action.equals("T"))
                {
                     String typeId = request.getParameter("typeId");
                     
                     int id = (int) Double.parseDouble(typeId);
                     response.setContentType("text/html");
                     PrintWriter out = response.getWriter();
                     
                     if(id!=0)
                     {    
                        LeaveTypeDTO type = leaveBean.getLeaveSetting(id);
                        out.write(String.valueOf(type.getDays()));
                     }
                     else
                     {
                        out.write("");
                     }
                     out.flush();
                     
                     page="";
                }
                else if (action.equals("D"))
                {
                   //String id = request.getParameter("id");
                   //leaveBean.deleteLeaveSetting(Integer.parseInt(id));
                    String entId = request.getParameter("entId");
                    String userId = request.getParameter("userId");
                    String login = request.getParameter("loginId");
                    leaveBean.setAuthor(loginUser);
                    leaveBean.deleteLeaveEnt(Integer.parseInt(entId),Integer.parseInt(userId));
                    page="/leaveEnt?action=U&id="+login;
                }

                //request.setAttribute("action", "");
            }

        if(!page.isEmpty())
        {
            RequestDispatcher view = getServletContext().getRequestDispatcher(page); 
            view.forward(request,response);     
        }
     }
    /*
    private LeaveEntDTO prepare(HttpServletRequest request)
    {
        
        String cf = request.getParameter("cf");
        
        LeaveTypeDTO type = new LeaveTypeDTO();
        type.setDescription(leaveType);
        type.setMandatory(mandatory);
        type.setDays(Double.parseDouble(ent));
        type.setAnnualIncre(Double.parseDouble(annualIncre));
        type.setCarriedForward(Double.parseDouble(cf));
        
        return type;
    }
    */
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
