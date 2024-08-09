package kr.co.ict.jwtdemo.member.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import kr.co.ict.jwtdemo.vo.MemberVO;

@Service
public class MemberLoginService {
    @Autowired
    private MemberLoginDAO memberLoginDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    public void registerMember(MemberVO member) {
        memberLoginDAO.registerMember(member);
    }

    public MemberVO findByEmail(String email) {
        return memberLoginDAO.findByEmail(email);
    }

    public boolean verifyPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public void updateProfile(MemberVO member) {
        if (member.getPassword() != null && !member.getPassword().isEmpty()) {
            member.setPassword(passwordEncoder.encode(member.getPassword()));
        }

        memberLoginDAO.updateProfile(member);
    }
}
