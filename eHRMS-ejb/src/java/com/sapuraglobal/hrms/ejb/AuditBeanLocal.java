/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sapuraglobal.hrms.ejb;

import com.sapuraglobal.hrms.dto.AuditDTO;
import javax.ejb.Local;

/**
 *
 * @author sapura-mac-pro-cto-C02PC1MWG3QT
 */
@Local
public interface AuditBeanLocal {

    void log(AuditDTO audit);
    
}
