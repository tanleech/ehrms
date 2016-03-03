/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sapuraglobal.hrms.ejb;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
/**
 *
 * @author sapura-mac-pro-cto-C02PC1MWG3QT
 */
public class DaoDelegate {
    
    private Session session=null;
    private Configuration cfg = null;
   
    //private Transaction txn = null;
    
    private static DaoDelegate instance = null;
    private DaoDelegate()
    {
        cfg=new Configuration();  
        //populate the hibernate configuration
        //cfg.configure("hibernate.cfg.xml");
         cfg.configure();
    }
    
    public static DaoDelegate getInstance()
    {
        if(instance==null)
        {
            instance = new DaoDelegate();
        }
        return instance;
    }
    
    public Session create()
    {
        //creating seession factory object  
        StandardServiceRegistryBuilder ssrb = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties());
        SessionFactory sessionFactory = cfg.buildSessionFactory(ssrb.build());
        session = sessionFactory.openSession(); 
        //txn = session.beginTransaction();
        return session;
    }
    
    public void close()
    {

        if(session!=null)
        {
            session.close();
            //System.out.println("close");
        }
        
    }

    
}
