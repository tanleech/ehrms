/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sapuraglobal.hrms.ejb;

import com.sapuraglobal.hrms.dto.UserDTO;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author sapura-mac-pro-cto-C02PC1MWG3QT
 */
@Stateless
public class UserBean implements UserBeanLocal {

    @Override
    public UserDTO authenticate(String loginId, String password) {
        
        Context initCtx;
        String url,baseDn;
        boolean isAuth=false;
        UserDTO userData = null;
        try {
            initCtx = new InitialContext();
            Context envCtx = (Context) initCtx.lookup("java:comp/env");
            // Look up our data source
            url = (String)envCtx.lookup("LDAP_URL");
            baseDn = (String)envCtx.lookup("LDAP_BASE");
            
       	    Hashtable<String,String> env = new Hashtable<String,String>();
  	    env.put(Context.INITIAL_CONTEXT_FACTORY, 
	    "com.sun.jndi.ldap.LdapCtxFactory");
	    env.put(Context.PROVIDER_URL, url);
                
            // Authenticate as S. User and password "mysecret"
            env.put(Context.SECURITY_AUTHENTICATION, "simple");
            env.put(Context.SECURITY_PRINCIPAL, "uid="+loginId+","+baseDn);
            env.put(Context.SECURITY_CREDENTIALS, password);

	    // Create initial context
	    DirContext ctx = new InitialDirContext(env);
	    ctx.close();
            isAuth=true;
            //authenticated get UserDTO
            userData = getUser(loginId);
            
            
	} catch (NamingException e) {
	    e.printStackTrace();
            
	}
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        
        return userData;
    }

    private UserDTO getUser(String loginId)
    {
        UserDTO data=null;
        String hql = "FROM com.sapuraglobal.hrms.dto.UserDTO U WHERE U.login = :userLogin";
        Session session=null;
        try{
            session = DaoDelegate.getInstance().create();
            Query query = session.createQuery(hql);
            query.setParameter("userLogin", loginId);
            List results = query.list();
            if(results!=null && !results.isEmpty())
            {
               data = (UserDTO) results.get(0);
            }
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            DaoDelegate.getInstance().close(session);
        }
        return data;
    }


    @Override
    public void createUser(UserDTO user) {
        java.util.Date current = new java.util.Date();
        user.setCreated(current);
        user.setModified(current);
        Session session = null;
        Transaction txn = null;
        try
        {
            session = DaoDelegate.getInstance().create();
            txn = session.beginTransaction();
            session.persist(user);
            txn.commit();
              
        }catch (Exception ex)
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
    public List<UserDTO> getAllUsers(Date from, Date to){
        
        List<UserDTO> results = null;
        String hql = "FROM com.sapuraglobal.hrms.dto.UserDTO U WHERE U.dateJoin BETWEEN :stDate "
                +    "AND :edDate AND U.deleted='N'";
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
