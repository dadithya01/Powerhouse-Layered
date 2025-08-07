package edu.ijse.powerhouse.dao.custom.impl;


import edu.ijse.powerhouse.dao.custom.MemberDAO;
import edu.ijse.powerhouse.entity.Member;
import edu.ijse.powerhouse.util.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MemberDAOImpl implements MemberDAO {
    @Override
    public ArrayList<Member> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.executeQuery("SELECT * FROM Member");
        ArrayList<Member> members = new ArrayList<>();
        while (rst.next()) {
            Member member = new Member(
                    rst.getString("member_id"),
                    rst.getString("name"),
                    rst.getDouble("weight"),
                    rst.getDouble("height"),
                    rst.getInt("age"),
                    rst.getString("contact"),
                    rst.getString("emergency_contact"),
                    rst.getString("medical_conditions"),
                    rst.getString("fitness_goals"),
                    rst.getString("register_date"),
                    rst.getString("membership_status"),
                    rst.getString("added_by")
            );
            members.add(member);
        }
        return members;
    }

    @Override
    public boolean save(Member entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("INSERT INTO Member (member_id, name, weight, height, age, contact, emergency_contact, medical_conditions, fitness_goals, register_date, membership_status, added_by) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                entity.getMember_id(),
                entity.getName(),
                entity.getWeight(),
                entity.getHeight(),
                entity.getAge(),
                entity.getContact(),
                entity.getEmergency_contact(),
                entity.getMedical_conditions(),
                entity.getFitness_goals(),
                entity.getRegister_date(),
                entity.getMembership_status(),
                entity.getAdded_by()
        );
    }

    @Override
    public boolean update(Member entity) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("UPDATE Member SET name = ?, weight = ?, height = ?, age = ?, contact = ?, emergency_contact = ?, medical_conditions = ?, fitness_goals = ?, register_date = ?, membership_status = ?, added_by = ? WHERE member_id = ?",
                entity.getName(),
                entity.getWeight(),
                entity.getHeight(),
                entity.getAge(),
                entity.getContact(),
                entity.getEmergency_contact(),
                entity.getMedical_conditions(),
                entity.getFitness_goals(),
                entity.getRegister_date(),
                entity.getMembership_status(),
                entity.getAdded_by(),
                entity.getMember_id()
        );
    }

    @Override
    public boolean exist(String memberId) throws SQLException, ClassNotFoundException {
        ResultSet rst= SQLUtil.executeQuery("SELECT member_id FROM Member WHERE member_id = ?", memberId);
        return rst.next();
    }

    @Override
    public boolean delete(String memberId) throws SQLException, ClassNotFoundException {
        return SQLUtil.executeUpdate("DELETE FROM Member WHERE member_id = ?", memberId);
    }

    @Override
    public String generateNewId() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.executeQuery("SELECT member_id FROM Member ORDER BY member_id DESC LIMIT 1");
        char tableCharacter = 'M';

        if(resultSet.next()){
            String lastId = resultSet.getString(1);
            String lastIdNumberString = lastId.substring(1);
            int lastIdNumber = Integer.parseInt(lastIdNumberString);
            int nextIdNumber = lastIdNumber + 1;
            String nextIdString = String.format(tableCharacter + "%03d" , nextIdNumber);

            return nextIdString;
        }
        return tableCharacter+ "1";
    }

    @Override
    public boolean isDuplicateMember(String contact) throws Exception {
        ResultSet rst = SQLUtil.executeQuery("SELECT 1 FROM Member WHERE contact = ?", contact);
        return rst.next();
    }

    @Override
    public boolean isDuplicateMemberForUpdate(String memberId, String contact) throws Exception {
        ResultSet rst = SQLUtil.executeQuery("SELECT 1 FROM Member WHERE (contact = ?) AND member_id != ?", memberId, contact);
        return rst.next();
    }
}
