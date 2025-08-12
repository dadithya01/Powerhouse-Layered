package edu.ijse.powerhouse.bo.custom.impl;

import edu.ijse.powerhouse.bo.custom.MemberBO;
import edu.ijse.powerhouse.dao.DAOFactory;
import edu.ijse.powerhouse.dao.custom.MemberDAO;
import edu.ijse.powerhouse.dto.MemberDTO;
import edu.ijse.powerhouse.entity.Member;

import java.sql.SQLException;
import java.util.ArrayList;

public class MemberBOImpl implements MemberBO {

    MemberDAO memberDAO = (MemberDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.MEMBER);

    @Override
    public ArrayList<MemberDTO> getAllMembers() throws SQLException, ClassNotFoundException {
        ArrayList<Member> entity = memberDAO.getAll();
        ArrayList<MemberDTO> memberDTOS = new ArrayList<>();
        for (Member member : entity) {
            memberDTOS.add(new MemberDTO(
                    member.getMember_id(),
                    member.getName(),
                    member.getWeight(),
                    member.getHeight(),
                    member.getAge(),
                    member.getContact(),
                    member.getEmergency_contact(),
                    member.getMedical_conditions(),
                    member.getFitness_goals(),
                    member.getRegister_date(),
                    member.getMembership_status(),
                    member.getAdded_by()));
        }
        return memberDTOS;
    }

    @Override
    public boolean saveMember(MemberDTO memberDTO) throws SQLException, ClassNotFoundException {
        return memberDAO.save(new Member(
                memberDTO.getMember_id(),
                memberDTO.getName(),
                memberDTO.getWeight(),
                memberDTO.getHeight(),
                memberDTO.getAge(),
                memberDTO.getContact(),
                memberDTO.getEmergency_contact(),
                memberDTO.getMedical_conditions(),
                memberDTO.getFitness_goals(),
                memberDTO.getRegister_date(),
                memberDTO.getMembership_status(),
                memberDTO.getAdded_by()));
    }

    @Override
    public boolean updateMember(MemberDTO memberDTO) throws SQLException, ClassNotFoundException {
        return memberDAO.update(new Member(
                memberDTO.getMember_id(),
                memberDTO.getName(),
                memberDTO.getWeight(),
                memberDTO.getHeight(),
                memberDTO.getAge(),
                memberDTO.getContact(),
                memberDTO.getEmergency_contact(),
                memberDTO.getMedical_conditions(),
                memberDTO.getFitness_goals(),
                memberDTO.getRegister_date(),
                memberDTO.getMembership_status(),
                memberDTO.getAdded_by()));
    }

    @Override
    public boolean deleteMember(String memberId) throws SQLException, ClassNotFoundException {
        return memberDAO.delete(memberId);
    }

    @Override
    public boolean existMember(String memberId) throws SQLException, ClassNotFoundException {
        return memberDAO.exist(memberId);
    }

    @Override
    public String generateNewMemberId() throws SQLException, ClassNotFoundException {
        return memberDAO.generateNewId();
    }

    @Override
    public boolean isDuplicateMember(String contact) throws Exception {
        return memberDAO.isDuplicateMember(contact);
    }

    @Override
    public boolean isDuplicateMemberForUpdate(String memberId, String contact) throws Exception {
        return memberDAO.isDuplicateMemberForUpdate(memberId, contact);
    }
}
