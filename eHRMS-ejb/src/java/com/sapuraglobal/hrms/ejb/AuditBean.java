/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sapuraglobal.hrms.ejb;

import com.sapuraglobal.hrms.dto.AuditDTO;
import javax.ejb.Stateless;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author sapura-mac-pro-cto-C02PC1MWG3QT
 */
@Stateless
public class AuditBean implements AuditBeanLocal {

    @Override
    public void log(AuditDTO audit) {
        
        java.util.Date current = new java.util.Date();
        audit.setCreated(current);
        audit.setModified(current);
        Session session = null;
        Transaction txn = null;
        try
        {
            session = DaoDelegate.getInstance().create();
            txn = session.beginTransaction();
            session.persist(audit);
            txn.commit(); 

        }
        catch (Exception ex)
        {
            txn.rollback();
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
