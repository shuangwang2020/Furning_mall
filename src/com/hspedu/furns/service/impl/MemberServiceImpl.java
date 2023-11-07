package com.hspedu.furns.service.impl;

import com.hspedu.furns.dao.MemberDAO;
import com.hspedu.furns.dao.impl.MemberDAOImpl;
import com.hspedu.furns.entity.Member;
import com.hspedu.furns.service.MemberService;

public class MemberServiceImpl implements MemberService {
    private MemberDAO memberDAO = new MemberDAOImpl();

    @Override
    public boolean registerMember(Member member) {
        return memberDAO.saveMember(member) == 1 ? true : false;
    }

    @Override
    public boolean isExistsUsername(String userName) {
        // ctrl + alt + b 定位到实现类的方法中
        return memberDAO.queryMemberByName(userName) != null;
    }

    @Override
    public Member login(Member member) {
        return memberDAO.
                queryMemberByUsernameAndPassword(member.getUsername(), member.getPassword());
    }
}
