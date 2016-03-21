/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sapuraglobal.hrms.servlet;

import com.sapuraglobal.hrms.dto.AuditDTO;
import com.sapuraglobal.hrms.ejb.AuditBeanLocal;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
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
    urlPatterns = {"/auditList"}
)
public class AuditList extends HttpServlet {

    @EJB(beanName="AuditBean")
    private AuditBeanLocal auditBean;

    
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
        String dtRange = request.getParameter("dateRange");
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

        if(dtRange==null||dtRange.isEmpty())
        {
          Date current = new Date();  
          Calendar frmCal = Calendar.getInstance();
          frmCal.add(Calendar.MONTH, -480);
          String frmDate = formatter.format(frmCal.getTime());
          String toDate  = formatter.format(current);
          StringBuilder sb = new StringBuilder();
          //sb.append(frmDate+" - "+toDate);
          sb.append(frmDate).append(" - ").append(toDate);
          
          request.setAttribute("dateRange",sb.toString());
          RequestDispatcher view = getServletContext().getRequestDispatcher("/auditLogList.jsp"); 
          view.forward(request,response);           
        }
        else
        {
          //json
            
          //Get the list 
          StringTokenizer st = new StringTokenizer(dtRange,"-");
          String stDate = st.nextToken().trim();
          String edDate = st.nextToken().trim();
	  try {

		Date fromDate = formatter.parse(stDate);
                Date toDate = formatter.parse(edDate);

                List<AuditDTO> auditList = auditBean.getAuditLog(fromDate,toDate);
                //convert to json array
                String json = convertToJson(auditList);
                response.setContentType("text/html");
                PrintWriter out = response.getWriter();
                System.out.println("json: "+json);
                out.write(json);
                out.flush();
  	  } catch (ParseException e) {
		e.printStackTrace();
	  }
            
            
        }
    }
    
    private String convertToJson(List<AuditDTO> auditList)
    {
        JsonArrayBuilder array = Json.createArrayBuilder();
        for(int i=0;i<auditList.size();i++)
        {
            AuditDTO audit = auditList.get(i);
            Date subDate = audit.getCreated();
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            String subDateStr = formatter.format(subDate);
            array.add(
            Json.createObjectBuilder()
                    .add("date",subDateStr)
                    .add("description",  audit.getDescr())
                    .add("employee", audit.getLogin().getName())
            );
            
        }
        return array.build().toString();
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
