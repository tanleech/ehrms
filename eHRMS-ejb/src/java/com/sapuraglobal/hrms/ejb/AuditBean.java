/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sapuraglobal.hrms.ejb;

import com.sapuraglobal.hrms.dto.AuditDTO;
import com.sapuraglobal.hrms.dto.UserDTO;
import java.util.Date;
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
public class AuditBean implements AuditBeanLocal {

    @Override
    public void log(String descr,UserDTO author) {
        
        AuditDTO audit = create(descr,author);
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
    private AuditDTO create(String descr,UserDTO author)
    {
            AuditDTO audit = new AuditDTO();
            audit.setDescr(descr);
            audit.setLogin(author);
            return audit;
    }

    @Override
    public List<AuditDTO> getAuditLog(Date from, Date to) {
        
        List<AuditDTO> results = null;
        String hql = "FROM com.sapuraglobal.hrms.dto.AuditDTO U left join fetch U.login WHERE U.created BETWEEN :stDate "
                +    "AND :edDate";
        Session session = null;
        try
        {
            session = DaoDelegate.getInstance().create();
            Query query = session.createQuery(hql);
            query.setParameter("stDate", from);
            query.setParameter("edDate", to);

            results = query.list();
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
