/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sapuraglobal.hrms.ejb;

import com.sapuraglobal.hrms.dto.LeaveEntDTO;
import com.sapuraglobal.hrms.dto.LeaveTxnDTO;
import com.sapuraglobal.hrms.dto.LeaveTypeDTO;
import com.sapuraglobal.hrms.dto.UserDTO;
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

    
    @Override
    public void applyLeave(LeaveTxnDTO leaveTxn) {
        Session session=null;
        Transaction txn = null;
        java.util.Date current = new java.util.Date();
        try
        {
            session = DaoDelegate.getInstance().create();
            txn = session.beginTransaction();
            leaveTxn.setCreated(current);
            leaveTxn.setModified(current);
            session.persist(leaveTxn);            
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
    public double getLeaveBalance(LeaveTypeDTO leaveType, UserDTO user) {
        Session session=null;
        LeaveEntDTO result = null;
        double bal = -1;
        try
        {
            session = DaoDelegate.getInstance().create();
            Query qry =  session.createQuery("FROM com.sapuraglobal.hrms.dto.LeaveEntDTO ent WHERE ent.user.User_id = :userId");
            qry.setParameter("userId",user.getId());
            List<LeaveEntDTO> entList = qry.list();
            if(entList==null||entList.isEmpty())
            {
                bal = 0;
            }
            else
            {
                result = (LeaveEntDTO)qry.list().get(0);
                bal = result.getBalance();
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            DaoDelegate.getInstance().close(session);
        }
        return bal;

    }

    @Override
    public List<LeaveEntDTO> getLeaveEntList(String loginId) {
        
        Session session=null;
        List<LeaveEntDTO> result = null;
        try
        {
            session = DaoDelegate.getInstance().create();
            Query qry =  session.createQuery("SELECT ent FROM com.sapuraglobal.hrms.dto.LeaveEntDTO ent left join fetch ent.leaveType WHERE ent.user.login = :loginId");
            qry.setParameter("loginId", loginId);
            result = qry.list();
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
    public void addLeaveEnt(LeaveEntDTO leaveEnt) {

        Session session=null;
        java.util.Date current = new java.util.Date();
        Transaction txn = null;

        try
        {
            session = DaoDelegate.getInstance().create();
            txn = session.beginTransaction();
            leaveEnt.setCreated(current);
            leaveEnt.setModified(current);
            session.persist(leaveEnt);
            txn.commit();
        }
        catch(Exception ex)
        {
            txn.rollback();
            ex.printStackTrace();
        }
        finally
        {
            DaoDelegate.getInstance().close(session);
        }
    }

    @Override
    public LeaveTypeDTO getLeaveType(String leaveType) {
       Session session=null;
        List<LeaveTypeDTO> results = null;
        try
        {
            session = DaoDelegate.getInstance().create();
            Query qry =  session.createQuery("FROM com.sapuraglobal.hrms.dto.LeaveTypeDTO type WHERE type.description = :descr");
            qry.setParameter("descr", leaveType);
            results = qry.list();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            DaoDelegate.getInstance().close(session);
        }
        if(results!=null&&!results.isEmpty())
        {
            return (LeaveTypeDTO)results.get(0); 
        }
        else
        {
            return null;
        }
    }

    @Override
    public List<LeaveTypeDTO> getLeaveSettings(boolean mandatory) {
        Session session=null;
        List<LeaveTypeDTO> results = null;
        String flag="N";
        try
        {
            if(mandatory)
            {
                flag="Y";
            }
            session = DaoDelegate.getInstance().create();
            Query qry =  session.createQuery("FROM com.sapuraglobal.hrms.dto.LeaveTypeDTO WHERE mandatory = :man");
            qry.setParameter("man", flag);
            results = qry.list();
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
    
    
        
    
}
