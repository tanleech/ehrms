/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sapuraglobal.hrms.ejb;

import com.sapuraglobal.hrms.dto.DeptDTO;
import com.sapuraglobal.hrms.dto.UserDTO;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author sapura-mac-pro-cto-C02PC1MWG3QT
 */
@Local
public interface DeptBeanLocal {

    List<DeptDTO> getAllDepts();

    void addDept(DeptDTO deptDTO);

    void addEmployees(List<UserDTO> userList, DeptDTO deptDTO); 

    DeptDTO getDepartment(String deptDescr);
    
}
