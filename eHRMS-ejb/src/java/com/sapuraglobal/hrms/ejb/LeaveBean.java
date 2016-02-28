/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sapuraglobal.hrms.ejb;

import com.sapuraglobal.hrms.dto.LeaveTypeDTO;
import java.util.List;
import javax.ejb.Stateless;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author michael-PC
 */
@Stateless
public class LeaveBean implements LeaveBeanLocal {

    @Override
    public void saveLeaveSetting(LeaveTypeDTO leaveType) {
        
        java.util.Date current = new java.util.Date();
        Session session = null;
        Transaction txn = null;
        leaveType.setCreated(current);
        leaveType.setModified(current);
        try
        {
            
            session =  DaoDelegate.getInstance().create();
            txn = session.beginTransaction();
            session.persist(leaveType);
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

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    @Override
    public List<LeaveTypeDTO> getAllLeaveSettings() {
        List results=null;
        Session session=null;
        try
        {
            session = DaoDelegate.getInstance().create();
            results =  session.createQuery("FROM com.sapuraglobal.hrms.dto.LeaveTypeDTO leaveType").list();
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

    @Override
    public void deleteLeaveSetting(int typeId) {
        
        List results=null;
        Session session=null;
        Transaction txn = null;
        try
        {
            session = DaoDelegate.getInstance().create();
            txn = session.beginTransaction();
            Query delQry = session.createQuery("DELETE FROM com.sapuraglobal.hrms.dto.LeaveTypeDTO where id = :typeId");
            delQry.setParameter("typeId", typeId);
            delQry.executeUpdate();
            txn.commit();
            
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            if(txn!=null)
            {
                txn.rollback();
            }
        }
        finally
        {
            DaoDelegate.getInstance().close(session);
        }

    }

    @Override
    public LeaveTypeDTO getLeaveSetting(int id) {
        Session session=null;
        LeaveTypeDTO result = null;
        try
        {
            session = DaoDelegate.getInstance().create();
            Query qry =  session.createQuery("FROM com.sapuraglobal.hrms.dto.LeaveTypeDTO WHERE id = :typeId");
            qry.setParameter("typeId", id);
            result = (LeaveTypeDTO)qry.list().get(0);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            DaoDelegate.getInstance().close(session);
        }
        return result;
    }

    @Override
    public void updateLeaveSetting(LeaveTypeDTO type) {
        
        List results=null;
        Session session=null;
        Transaction txn = null;
        java.util.Date current = new java.util.Date();
        try
        {
            session = DaoDelegate.getInstance().create();
            txn = session.beginTransaction();
            Query updateQry = session.createQuery("UPDATE com.sapuraglobal.hrms.dto.LeaveTypeDTO "
                    + " set description = :descr, days = :ent, mandatory = :option, annualIncre = :annual, cf = :cf, modified =:modified"
                    + " where id = :typeId");
            updateQry.setParameter("typeId", type.getId());
            updateQry.setParameter("descr", type.getDescription());
            System.out.println("days: "+type.getDays());
            updateQry.setParameter("ent", type.getDays());
            updateQry.setParameter("option", type.getMandatory());
            updateQry.setParameter("annual", type.getAnnualIncre());
            updateQry.setParameter("cf", type.getCarriedForward());
            updateQry.setParameter("modified", current);
            updateQry.executeUpdate();
            txn.commit();
            
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            if(txn!=null)
            {
                txn.rollback();
            }
        }
        finally
        {
            DaoDelegate.getInstance().close(session);
        }

        
        
    }
    
    
}
