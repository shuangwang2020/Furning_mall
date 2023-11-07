package com.hspedu.furns.dao.impl;

import com.hspedu.furns.dao.BasicDAO;
import com.hspedu.furns.dao.MemberDAO;
import com.hspedu.furns.entity.Member;

public class MemberDAOImpl extends BasicDAO<Member> implements MemberDAO {

    @Override
    public Member queryMemberByName(String userName) {
        String sql = "SELECT `id`,`username`,`password`,`email` FROM `member`" +
                " WHERE `username`=?";
        return querySingle(sql, Member.class, userName);
    }

    @Override
    public int saveMember(Member member) {
        // -1 失败 其他数字表示受影响的行数
        String sql = "INSERT INTO member(`username`,`password`,`email`) " +
                "VALUES(?,MD5(?),?)";
        return update(sql, member.getUsername(),
                member.getPassword(), member.getEmail());
    }

    @Override
    public Member queryMemberByUsernameAndPassword(String username, String password) {
        String sql = "SELECT `id`,`username`,`password`,`email` FROM `member`" +
        " WHERE `username`=? AND `password`=md5(?)";
        return querySingle(sql, Member.class, username, password);
    }
}
