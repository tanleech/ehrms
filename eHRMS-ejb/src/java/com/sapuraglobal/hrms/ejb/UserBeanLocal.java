/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sapuraglobal.hrms.ejb;

import com.sapuraglobal.hrms.dto.RoleDTO;
import com.sapuraglobal.hrms.dto.UserDTO;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author sapura-mac-pro-cto-C02PC1MWG3QT
 */
@Local
public interface UserBeanLocal {

    UserDTO authenticate(String parameter,String password);

    void createUser(UserDTO parameter);

    List<UserDTO> getAllUsers(Date from, Date to);
    
    UserDTO getUser(String loginId);

    void assignRole(UserDTO user, RoleDTO role);
    
}
