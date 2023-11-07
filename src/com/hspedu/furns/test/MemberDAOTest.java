package com.hspedu.furns.test;

import com.hspedu.furns.dao.MemberDAO;
import com.hspedu.furns.dao.impl.MemberDAOImpl;
import com.hspedu.furns.entity.Member;
import org.junit.jupiter.api.Test;

public class MemberDAOTest {
    private MemberDAO memberDAO = new MemberDAOImpl();

    @Test
    public void queryMemberByName() {
        if (memberDAO.queryMemberByName("admin") == null) {
            System.out.println("该用户名不存在");
        } else {
            System.out.println("该用户名存在");
        }
    }

    @Test
    public void saveMember() {
        Member member =
                new Member(null, "king1", "king1", "king1@sohu.com");
        if (memberDAO.saveMember(member) == 1) {
            System.out.println("添加OK");
        } else {
            System.out.println("添加失败...");
        }
    }

    @Test
    public void queryMemberByUsernameAndPassword() {
        Member member = memberDAO.queryMemberByUsernameAndPassword("adminx", "admin");
        System.out.println("member= " + member);
    }

}
