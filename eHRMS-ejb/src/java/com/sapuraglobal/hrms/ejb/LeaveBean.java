/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sapuraglobal.hrms.ejb;

import com.sapuraglobal.hrms.dto.LeaveTypeDTO;
import java.util.List;
import javax.ejb.Stateless;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author michael-PC
 */
@Stateless
public class LeaveBean implements LeaveBeanLocal {

    @Override
    public void saveLeaveSetting(List<LeaveTypeDTO> leaveTypes) {
        
        java.util.Date current = new java.util.Date();
        Session session = null;
        Transaction txn = null;
        try
        {
            session =  DaoDelegate.getInstance().create();
            txn = session.beginTransaction();
            for(int i=0;i<leaveTypes.size();i++)
            {
              session.persist((LeaveTypeDTO)leaveTypes.get(i));
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

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    
}
