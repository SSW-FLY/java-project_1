package itcode.dao;

import itcode.pojo.Member;
import org.apache.ibatis.annotations.Param;

public interface MemberDao {
    Member findMemberByTelephone(@Param("telephone") String telephone);

    void add(Member member);
}
