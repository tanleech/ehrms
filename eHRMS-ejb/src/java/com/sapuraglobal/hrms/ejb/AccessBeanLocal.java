/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sapuraglobal.hrms.ejb;

import com.sapuraglobal.hrms.dto.AccessDTO;
import com.sapuraglobal.hrms.dto.RoleDTO;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author sapura-mac-pro-cto-C02PC1MWG3QT
 */
@Local
public interface AccessBeanLocal {

    List<RoleDTO> getAllRoles();

    void addRole(RoleDTO roleDTO);

    RoleDTO getRole(String descr);

    void update(int roleId, List<AccessDTO>accessList);

    List<AccessDTO> getAccessRights(int roleId);


    
}
