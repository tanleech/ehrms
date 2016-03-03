/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sapuraglobal.hrms.servlet;

import com.sapuraglobal.hrms.dto.DeptDTO;
import com.sapuraglobal.hrms.dto.LeaveEntDTO;
import com.sapuraglobal.hrms.dto.LeaveTypeDTO;
import com.sapuraglobal.hrms.dto.RoleDTO;
import com.sapuraglobal.hrms.dto.TitleDTO;
import com.sapuraglobal.hrms.dto.UserDTO;
import com.sapuraglobal.hrms.dto.UserDeptDTO;
import com.sapuraglobal.hrms.dto.UserRoleDTO;
import com.sapuraglobal.hrms.ejb.AccessBeanLocal;
import com.sapuraglobal.hrms.ejb.DeptBeanLocal;
import com.sapuraglobal.hrms.ejb.LeaveBeanLocal;
import com.sapuraglobal.hrms.ejb.TitleBeanLocal;
import com.sapuraglobal.hrms.ejb.UserBeanLocal;
import com.sapuraglobal.hrms.servlet.helper.BeanHelper;
import com.sapuraglobal.hrms.servlet.helper.Utility;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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
    urlPatterns = {"/employeeEdit"}
)
public class EmployeeEdit extends HttpServlet {
    @EJB
    private UserBeanLocal userBean;
    @EJB
    private TitleBeanLocal titleBean;
    @EJB
    private DeptBeanLocal deptBean;
    @EJB
    private AccessBeanLocal accessBean;
    @EJB
    private LeaveBeanLocal leaveBean;



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
          String page = "/employeeEdit.jsp";
          
          System.out.println("action: "+action);
          
          List<TitleDTO> titleList = titleBean.getAllTitles();
          request.setAttribute("titleList", titleList);
          
          List<DeptDTO>deptList = deptBean.getAllDepts();
          request.setAttribute("deptList",deptList);

          List<RoleDTO>roleList = accessBean.getAllRoles();
          request.setAttribute("roleList",roleList);
          
          List<UserDTO>mgrList = new BeanHelper().getAllUsers(userBean);
          request.setAttribute("mgrList",mgrList);
          if(action!=null)
          {
             if(action.equals("A"))
             {
                 UserDTO userDto = prepare(request);
                 UserDeptDTO deptDto = userDto.getDept();
                 UserRoleDTO userRoleDto = userDto.getRole();
                 //getting annual leave ent
                 LeaveEntDTO entDto = userDto.getLeaveEnt().get(0);
                 userDto.setDept(null);
                 userDto.setRole(null);
                 userDto.setLeaveEnt(null);
                 userBean.createUser(userDto);
                 deptBean.addEmployee(userDto, deptDto.getDept());
                 userBean.assignRole(userDto,userRoleDto.getRole());
                 entDto.setUser(userDto);
                 leaveBean.addLeaveEnt(entDto);
                 page="/employee";
                 
             }
             else if(action.equals("U"))
             {
                 String login = request.getParameter("id");
                 UserDTO user = userBean.getUser(login);
                 List<LeaveEntDTO> entList = leaveBean.getLeaveEntList(login);
                 boolean found = false;
                 int i =0;
                 LeaveEntDTO annualEnt=null;
                 while(!found && i < entList.size())
                 {
                     LeaveEntDTO ent = entList.get(i);
                     if(ent.getLeaveType().getDescription().equals("Annual"))
                     {
                         found=true;
                         //user.setLeaveEnt(entList);
                         annualEnt = ent;
                         request.setAttribute("entAnnual", ent);
                     }
                     i++;
                 }
                 
                 //compute Annual Accured
                 Date now = new Date();
                 Date begin = Utility.getYearBeginTime();
                 double days = Utility.computeDaysBetween(begin, now);
                 System.out.println("days between: "+days);
                 double accured = (days/365.0) * annualEnt.getCurrent();
                 
                 request.setAttribute("typeList", leaveBean.getAllLeaveSettings());
                 request.setAttribute("user", user);
                 request.setAttribute("entList", entList);
                 request.setAttribute("accured", accured);
                 
                 page="/employeeDetl.jsp";
             }
          else if(action.equals("E"))
          {
                 System.out.println("E action");
                 UserDTO userDto = prepare(request);
                 int id = Integer.parseInt(request.getParameter("userId"));
                 userDto.setId(id);
                 UserDeptDTO deptDto = userDto.getDept();
                 UserRoleDTO userRoleDto = userDto.getRole();
                 //getting annual leave ent
                 LeaveEntDTO entDto = userDto.getLeaveEnt().get(0);
                 userDto.setDept(null);
                 userDto.setRole(null);
                 userDto.setLeaveEnt(null);
                 //update user
                 userBean.updateUser(userDto);
                 //update dept
                 //deptBean.addEmployee(userDto, deptDto.getDept());
                 //update role
                 //userBean.assignRole(userDto,userRoleDto.getRole());
                 //update entitlement
                 //entDto.setUser(userDto);
                 //leaveBean.addLeaveEnt(entDto);
                 page="/employee";

          }
             
          }
          
          
          RequestDispatcher view = getServletContext().getRequestDispatcher(page); 
          view.forward(request,response);           
    }
    
    private UserDTO prepare(HttpServletRequest request)
    {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String dept = request.getParameter("dept");
        String mobile = request.getParameter("mobile");
        String title = request.getParameter("title");
        String office = request.getParameter("office");
        String mgr = request.getParameter("mgr");
        String role = request.getParameter("role");
        String login = request.getParameter("login");
        String dateJoin = request.getParameter("dateJoin");
        String probDue = request.getParameter("probDue");
        String base = request.getParameter("base");
        String max  = request.getParameter("max");
        String balance = request.getParameter("balance");
        

        
        UserDTO user = new UserDTO();
        user.setName(name);
        user.setEmail(email);
        user.setPhone(mobile);
        user.setOffice(office);
        user.setLogin(login);
        /*
        user.setMax(Double.parseDouble(max));
        user.setBase(Double.parseDouble(base));
        user.setBalance(Double.parseDouble(balance));
        */
        
        UserDTO mgrDto = new UserDTO();
        mgrDto.setId(Integer.parseInt(mgr));
        user.setApprover(mgrDto.getId());
        try
        {
           Date join = Utility.format(dateJoin,"MM/dd/yyyy");
           Date prob = Utility.format(probDue, "MM/dd/yyyy");
           user.setDateJoin(join);
           user.setProbationDue(prob);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        
        //TitleDTO titleDto = titleBean.getTitle(Integer.parseInt(title));
        TitleDTO titleDTO = new TitleDTO();
        titleDTO.setId(Integer.parseInt(title));
        user.setTitle(titleDTO);
        
        
        DeptDTO deptDto = new DeptDTO();
        deptDto.setId(Integer.parseInt(dept));
        UserDeptDTO userDept = new UserDeptDTO();
        userDept.setDept(deptDto);
        
        RoleDTO roleDto = new RoleDTO();
        roleDto.setId(Integer.parseInt(role));
        UserRoleDTO userRole = new UserRoleDTO();
        userRole.setRole(roleDto);
        
        
        LeaveEntDTO ent = new LeaveEntDTO();
        ent.setCurrent(Double.parseDouble(base));
        ent.setMax(Double.parseDouble(max));
        ent.setBalance(Double.parseDouble(balance));
        
        LeaveTypeDTO leaveType = leaveBean.getLeaveType("Annual");
        ent.setLeaveType(leaveType);
        
        ArrayList<LeaveEntDTO> entList = new ArrayList();
        entList.add(ent);
        user.setLeaveEnt(entList);
         
        user.setDept(userDept);
        user.setRole(userRole);
        
        return user;
        
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
