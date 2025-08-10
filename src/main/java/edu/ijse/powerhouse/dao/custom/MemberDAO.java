package edu.ijse.powerhouse.dao.custom;

import edu.ijse.powerhouse.dao.CrudDAO;
import edu.ijse.powerhouse.entity.Member;

public interface MemberDAO extends CrudDAO<Member> {
    public boolean isDuplicateMember(String contact) throws Exception;

    public boolean isDuplicateMemberForUpdate(String memberId, String contact) throws Exception;

}
