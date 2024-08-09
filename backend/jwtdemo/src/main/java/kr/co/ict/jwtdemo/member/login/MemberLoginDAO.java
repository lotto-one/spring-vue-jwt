package kr.co.ict.jwtdemo.member.login;

import org.apache.ibatis.annotations.Mapper;

import kr.co.ict.jwtdemo.vo.MemberVO;

@Mapper
public interface MemberLoginDAO {
    MemberVO findByEmail(String email);
    int registerMember(MemberVO member);
    void updateProfile(MemberVO member);
} 
