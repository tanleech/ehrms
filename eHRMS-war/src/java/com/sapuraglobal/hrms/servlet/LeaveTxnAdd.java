/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sapuraglobal.hrms.servlet;

import com.sapuraglobal.hrms.dto.LeaveTxnDTO;
import com.sapuraglobal.hrms.dto.LeaveTypeDTO;
import com.sapuraglobal.hrms.dto.StatusDTO;
import com.sapuraglobal.hrms.dto.UserDTO;
import com.sapuraglobal.hrms.ejb.LeaveBeanLocal;
import com.sapuraglobal.hrms.ejb.UserBeanLocal;
import com.sapuraglobal.hrms.servlet.helper.Utility;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
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
    urlPatterns = {"/leaveTxnAdd"}
)

public class LeaveTxnAdd extends HttpServlet {
    @EJB
    private LeaveBeanLocal leaveBean;
    @EJB
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
        String page = "/leaveTxn?action=list";

        String lvTypeId = request.getParameter("leaveType");
        String startDate = request.getParameter("startDate");
        Date startDt=null,endDt=null;
        try
        {
           startDt = Utility.format(startDate, "MM/dd/yyyy");
           String endDate = request.getParameter("endDate");
           endDt = Utility.format(endDate, "MM/dd/yyyy");
 
        }catch(ParseException pe)
        {
            pe.printStackTrace();
        }
  
        String startSlot = request.getParameter("start_slot");

        String endSlot = request.getParameter("end_slot");

        String days = request.getParameter("taken");
        
        
        UserDTO user = (UserDTO)request.getSession().getAttribute("User");
        LeaveTxnDTO txn = new LeaveTxnDTO();
        txn.setDays(Double.parseDouble(days));
        
        txn.setStart(startDt);
        txn.setEnd(endDt);
        
        txn.setStart_slot(startSlot);
        txn.setEnd_slot(endSlot);
        
        System.out.println("leaveTypeId="+lvTypeId);
        LeaveTypeDTO typeDTO = leaveBean.getLeaveSetting(Integer.parseInt(lvTypeId));
        txn.setLeaveType(typeDTO);
        
        StatusDTO statusDTO = leaveBean.getStatus("pending");
        
        txn.setStatus(statusDTO);
        txn.setUser(user);
        
        leaveBean.applyLeave(txn);
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
