/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sapuraglobal.hrms.ejb;

import com.sapuraglobal.hrms.dto.LeaveEntDTO;
import com.sapuraglobal.hrms.dto.LeaveTxnDTO;
import com.sapuraglobal.hrms.dto.LeaveTypeDTO;
import com.sapuraglobal.hrms.dto.StatusDTO;
import com.sapuraglobal.hrms.dto.UserDTO;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author michael-PC
 */
@Local
public interface LeaveBeanLocal extends BaseBeanLocal{

    void saveLeaveSetting(LeaveTypeDTO leaveType);

    List<LeaveTypeDTO> getAllLeaveSettings();

    void deleteLeaveSetting(int typeId);

    LeaveTypeDTO getLeaveSetting(int id);

    void updateLeaveSetting(LeaveTypeDTO type);

    void applyLeave(LeaveTxnDTO leaveTxn);

    double getLeaveBalance(LeaveTypeDTO leaveType, UserDTO user);

    List<LeaveEntDTO> getLeaveEntList(String loginId);

    void addLeaveEnt(LeaveEntDTO leaveEnt);

    LeaveTypeDTO getLeaveType(String leaveType);

    List<LeaveTypeDTO> getLeaveSettings(boolean mandatory);

    void deleteLeaveEnt(int entId, int userId);

    List<LeaveTxnDTO> getAllTxn();

    List<LeaveTxnDTO> getTxnForApprover(int approver);

    List<LeaveTxnDTO> getLeaveRecords(int userId);

    StatusDTO getStatus(String descr);

    void updateLeaveEnt(int leaveTypeId, int userId, double days);
    
    void updateLeaveEntitlement(LeaveEntDTO entDo, int userId);

    void approveLeave(int txnId, int status);

    LeaveTxnDTO getTxn(int txnId);

    
}
