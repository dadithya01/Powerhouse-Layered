package edu.ijse.powerhouse.bo.custom;

import edu.ijse.powerhouse.bo.SuperBO;
import edu.ijse.powerhouse.dto.MemberDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface MemberBO extends SuperBO {
    public ArrayList<MemberDTO> getAllMembers() throws SQLException, ClassNotFoundException;
    public boolean saveMember(MemberDTO memberDTO) throws SQLException, ClassNotFoundException;
    public boolean updateMember(MemberDTO memberDTO) throws SQLException, ClassNotFoundException;
    public boolean deleteMember(String memberId) throws SQLException, ClassNotFoundException;
    public boolean existMember(String memberId) throws SQLException, ClassNotFoundException;
    public String generateNewMemberId() throws SQLException, ClassNotFoundException;
}
