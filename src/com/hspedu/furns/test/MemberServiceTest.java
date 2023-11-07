package com.hspedu.furns.test;

import com.hspedu.furns.entity.Member;
import com.hspedu.furns.service.MemberService;
import com.hspedu.furns.service.impl.MemberServiceImpl;
import org.junit.jupiter.api.Test;

public class MemberServiceTest {
    private MemberService memberService = new MemberServiceImpl();

    @Test
    public void isExistsUsername() {
        if (memberService.isExistsUsername("king")) {
            System.out.println("用户名存在...");
        } else {
            System.out.println("用户名不存在...");
        }
    }

    @Test
    public void registerMember() {
        Member member = new Member(null, "mary", "mary", "mary@qq.com");
        if (memberService.registerMember(member)) {
            System.out.println("注册用户成功...");
        } else {
            System.out.println("注册用户失败...");
        }
    }

    @Test
    public void login() {
        Member member = new Member(null, "adminx", "admin", null);
        System.out.println(memberService.login(member));
    }
}
