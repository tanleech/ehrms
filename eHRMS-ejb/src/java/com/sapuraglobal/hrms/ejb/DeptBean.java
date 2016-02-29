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
    public void addEmployee(UserDTO userDTO, DeptDTO deptDTO) 
    {
        java.util.Date current = new java.util.Date();
        Session session = null;
        Transaction txn = null;
        try
        {
            
            session =  DaoDelegate.getInstance().create();
            txn = session.beginTransaction();
            UserDeptDTO userDept = new UserDeptDTO();
            if(userDTO.isIsManager())
            {
                  userDept.setManager("Y");
            }
            userDept.setCreated(current);
            userDept.setModified(current);
            userDept.setDept(deptDTO);
            userDept.setUser(userDTO);
            session.persist(userDept);
            txn.commit();
        }
        catch (Exception ex)
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

    
    @Override
    public UserDeptDTO getUserDept(int userId, int deptId) {
        Session session = null;
        Transaction txn = null;        
        UserDeptDTO data = null;
        try
        {
            session =  DaoDelegate.getInstance().create();
            txn = session.beginTransaction();
            //retrieve the full data from db.
            //retrieve from UserDept
            String hql = "FROM com.sapuraglobal.hrms.dto.UserDeptDTO WHERE User_id=:userId AND Dept_id=:deptId";
            
            Query qry = session.createQuery(hql);
            qry.setParameter("userId", userId);
            qry.setParameter("deptId", deptId);
            
            List results = qry.list();
            
            if(results!=null && !results.isEmpty())
            {
               data = (UserDeptDTO) results.get(0);
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
        
        return data;
    }

    @Override
    public void unassignManager(int deptId) {
        Session session = null;
        Transaction txn = null;        
        try
        {
            session =  DaoDelegate.getInstance().create();
            txn = session.beginTransaction();
            //retrieve the full data from db.
            //retrieve from UserDept
            String hql = "DELETE FROM com.sapuraglobal.hrms.dto.UserDeptDTO WHERE Dept_id=:deptId AND manager = 'Y'";
            Query qry = session.createQuery(hql);
            qry.setParameter("deptId", deptId);
            qry.executeUpdate();
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
    public void unassignEmployee(int userId, int deptId) {
        Session session = null;
        Transaction txn = null;        
        try
        {
            session =  DaoDelegate.getInstance().create();
            txn = session.beginTransaction();
            //retrieve the full data from db.
            //retrieve from UserDept
            String hql = "DELETE FROM com.sapuraglobal.hrms.dto.UserDeptDTO WHERE Dept_id=:deptId AND User_id = :userId ";
            Query qry = session.createQuery(hql);
            qry.setParameter("userId",userId);
            qry.setParameter("deptId", deptId);
            qry.executeUpdate();
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
    
    
     
}
