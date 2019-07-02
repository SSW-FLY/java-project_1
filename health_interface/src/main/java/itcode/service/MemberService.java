package itcode.service;

import itcode.pojo.Member;

public interface MemberService {
    Member findMemberByTelephone(String telephone);

    void add(Member member);
}
