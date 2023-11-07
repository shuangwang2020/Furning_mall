package com.hspedu.furns.service;

import com.hspedu.furns.entity.Member;

public interface MemberService {
    // 注册用户
    boolean registerMember(Member member);

    // 判断用户名是否存在
    boolean isExistsUsername(String userName);

    Member login(Member member);
}
