package com.hspedu.furns.dao;

import com.hspedu.furns.entity.Member;

public interface MemberDAO {
    // 根据用户名返回对应member
    public Member queryMemberByName(String userName);

    // 保存member
    public int saveMember(Member member);

    Member queryMemberByUsernameAndPassword(String username, String password);
}
