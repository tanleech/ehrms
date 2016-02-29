/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sapuraglobal.hrms.ejb;

import com.sapuraglobal.hrms.dto.UserDTO;
import com.sapuraglobal.hrms.dto.UserDeptDTO;
import com.sapuraglobal.hrms.dto.UserRoleDTO;
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

    void createUser(UserDTO parameter, UserDeptDTO userDept, UserRoleDTO userRole);

    List<UserDTO> getAllUsers(Date from, Date to);
    
}
