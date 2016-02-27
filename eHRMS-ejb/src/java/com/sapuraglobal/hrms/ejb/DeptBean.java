/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sapuraglobal.hrms.ejb;

import com.sapuraglobal.hrms.dto.DeptDTO;
import com.sapuraglobal.hrms.dto.UserDTO;
import com.sapuraglobal.hrms.dto.UserDeptDTO;
import java.util.List;
import javax.ejb.Stateless;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author sapura-mac-pro-cto-C02PC1MWG3QT
 */
@Stateless
public class DeptBean implements DeptBeanLocal {

    @Override
    public List<DeptDTO> getAllDepts() {
        List results=null;
        Session session=null;
        try
        {
            session = DaoDelegate.getInstance().create();
            results =  session.createQuery("SELECT DISTINCT dept FROM com.sapuraglobal.hrms.dto.DeptDTO dept left join fetch dept.employees").list();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            DaoDelegate.getInstance().close(session);
        }
        
        return results;
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    @Override
    public void addDept(DeptDTO deptDTO) {
        
        java.util.Date current = new java.util.Date();
        deptDTO.setCreated(current);
        deptDTO.setModified(current);
        Session session = null;
        Transaction txn = null;
        try
        {
            session =  DaoDelegate.getInstance().create();
            txn = session.beginTransaction();
            session.persist(deptDTO);
            txn.commit();
            
        }catch (Exception ex)
        {
            if(txn!=null)
            {
                txn.rollback();
            }
            ex.printStackTrace();
        }
        finally
        {
            DaoDelegate.getInstance().close(session);
        }
    }
    
    @Override
    public void addEmployees(List<UserDTO> userList, DeptDTO deptDTO) 
    {
        java.util.Date current = new java.util.Date();
        Session session = null;
        Transaction txn = null;
        try
        {
            
            session =  DaoDelegate.getInstance().create();
            txn = session.beginTransaction();
            //retrieve the full data from db.
            String deptl = "SELECT dept FROM com.sapuraglobal.hrms.dto.DeptDTO dept WHERE dept.description = :descr";
            Query deptQuery = session.createQuery(deptl);
            System.out.println("descr: "+deptDTO.getDescription());
            deptQuery.setParameter("descr", deptDTO.getDescription());
            
            List deptResults = deptQuery.list();
            DeptDTO deptData = null;
            if(deptResults!=null && !deptResults.isEmpty())
            {
               deptData = (DeptDTO) deptResults.get(0);
               System.out.println("Dept id: "+deptData.getDescription());
            }


            //session.persist(deptDTO);
            //check whether there are records for the departments
            
                //delete existing user from UserDept table
            String delHql = "delete com.sapuraglobal.hrms.dto.UserDeptDTO userDept WHERE userDept.dept.id = :id";  
            Query delQry = session.createQuery(delHql);
            System.out.println("deptId: "+deptData.getId());
            delQry.setParameter("id", deptData.getId());
            delQry.executeUpdate();
            //txn.commit();
            //retrieve the Manager
            //txn.begin();
            for(int i=0; i<userList.size();i++)
            {    
                UserDTO dto = userList.get(i);
                //retrieve the full data from db.
                String hql = "SELECT User FROM com.sapuraglobal.hrms.dto.UserDTO User WHERE User.id = :id";
                Query query = session.createQuery(hql);
                query.setParameter("id", dto.getId());
                
                List results = query.list();
                UserDTO data = null;
                if(results!=null && !results.isEmpty())
                {
                   data = (UserDTO) results.get(0);
                }
                UserDeptDTO userDept = new UserDeptDTO();
                if(dto.isIsManager())
                {
                  userDept.setManager("Y");
                }
                userDept.setCreated(current);
                userDept.setModified(current);
                userDept.setDept(deptData);
                userDept.setUser(data);
                session.persist(userDept);
            }    
            txn.commit();
        }catch (Exception ex)
        {
            if(txn!=null)
            {
                txn.rollback();
            }
            ex.printStackTrace();
        }
        finally
        {
            DaoDelegate.getInstance().close(session);
        }

    }

    @Override
    public DeptDTO getDepartment(String deptDescr) {
        Session session = null;
        Transaction txn = null;        
        DeptDTO deptData = null;
        try
        {
            session =  DaoDelegate.getInstance().create();
            txn = session.beginTransaction();
            //retrieve the full data from db.
            String deptl = "SELECT DISTINCT dept FROM com.sapuraglobal.hrms.dto.DeptDTO dept left join fetch dept.employees WHERE dept.description = :descr";
            Query deptQuery = session.createQuery(deptl);
            deptQuery.setParameter("descr", deptDescr);
            
            List deptResults = deptQuery.list();
            
            if(deptResults!=null && !deptResults.isEmpty())
            {
               deptData = (DeptDTO) deptResults.get(0);
            }
            
        }catch (Exception ex)
        {
            if(txn!=null)
            {
                txn.rollback();
            }
            ex.printStackTrace();
        }
        finally
        {
            DaoDelegate.getInstance().close(session);
        }
        
        return deptData;
    }
    
    
     
}
