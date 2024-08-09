package kr.co.ict.jwtdemo.member.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import kr.co.ict.jwtdemo.member.jwt.AuthenticationRequest;
import kr.co.ict.jwtdemo.member.jwt.AuthenticationResponse;
import kr.co.ict.jwtdemo.member.jwt.JwtTokenProvider;
import kr.co.ict.jwtdemo.member.login.MemberLoginService;
import kr.co.ict.jwtdemo.vo.MemberVO;


@RestController
@CrossOrigin 
@RequestMapping("/membership")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private MemberLoginService memberLoginService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(
            @RequestBody AuthenticationRequest authenticationRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),
                        authenticationRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenProvider.createToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        MemberVO member = memberLoginService.findByEmail(userDetails.getUsername());
        if (member == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not found");
        }
    
        int memno = member.getMemno(); 
        String rolecd = member.getRolecd(); 
        return ResponseEntity.ok(new AuthenticationResponse(jwt, memno, rolecd));
    }
    
    
    @GetMapping("/logout")
    public ResponseEntity<?> logoutUser() {
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok("Logout successful");
    }

   @PostMapping("/register")
   public ResponseEntity<?> registerUser(@RequestBody MemberVO member) {
       if (memberLoginService.findByEmail(member.getEmail()) != null) {
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("사용중인 이메일");
       }

       member.setPassword(memberLoginService.encodePassword(member.getPassword()));
       try {
           memberLoginService.registerMember(member);
           return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
       } catch (Exception e) {
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while registering the user");
       }
   }

   @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(@RequestBody MemberVO member) {
        memberLoginService.updateProfile(member);
        return ResponseEntity.ok("Profile updated successfully");
    }

}