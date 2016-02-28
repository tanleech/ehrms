/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sapuraglobal.hrms.ejb;

import com.sapuraglobal.hrms.dto.LeaveTypeDTO;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author michael-PC
 */
@Local
public interface LeaveBeanLocal {

    void saveLeaveSetting(List<LeaveTypeDTO> leaveTypes);

    List<LeaveTypeDTO> getAllLeaveSettings();
    
}
