/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sapuraglobal.hrms.ejb;

import com.sapuraglobal.hrms.dto.DeptDTO;
import com.sapuraglobal.hrms.dto.UserDTO;
import com.sapuraglobal.hrms.dto.UserDeptDTO;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author sapura-mac-pro-cto-C02PC1MWG3QT
 */
@Local
public interface DeptBeanLocal extends BaseBeanLocal {

    List<DeptDTO> getAllDepts();

    void addDept(DeptDTO deptDTO);

    DeptDTO getDepartment(String deptDescr);

    UserDeptDTO getUserDept(int userId, int deptId);

    void unassignManager(int deptId);

    void assignEmployee(UserDTO userDTO, DeptDTO deptDTO); 

    void unassignEmployee(int userId, int deptId);

    int assignManager(int userId, int deptId);

    int updateEmployee(int userId, int deptId);

    int updateDept(String oldName, String newName);
    
}
