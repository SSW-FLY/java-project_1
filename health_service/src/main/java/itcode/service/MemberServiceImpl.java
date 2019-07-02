package itcode.service;

import com.alibaba.dubbo.config.annotation.Service;
import itcode.dao.MemberDao;
import itcode.pojo.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


@Service(interfaceClass = MemberService.class)
@Transactional
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberDao memberDao;

    @Override
    public Member findMemberByTelephone(String telephone) {
        Member member = memberDao.findMemberByTelephone(telephone);
        return member;
    }

    @Override
    public void add(Member member) {
        memberDao.add(member);
    }
}
