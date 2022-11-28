package com.example.demo.security.controller;



import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.security.jwt.util.JwtUtil;
import com.example.demo.security.service.UserService;
import com.example.demo.security.vo.AuthRequestVo;
import com.example.demo.security.vo.AuthenticationCodeVo;
import com.example.demo.security.vo.EmailVo;
import com.example.demo.security.vo.ReturnUserVo;
import com.example.demo.security.vo.UserInfoSearchVo;
import com.example.demo.security.vo.UserVo;

@RestController
public class SecurityController {
	@Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService UserService;
 
    @GetMapping("/api/v1/sample")
    public String sample(HttpServletRequest httpServletRequest, Model model){
        return "list1";       
    }
    //회원가입
    @PostMapping("/api/v1/signup")
    public String signup(@RequestBody UserVo request) {
    	int result = UserService.UserSignUp(request);
    	String resstr = "";
    	if(result == 1) {
    		resstr = "ok";
    	}else {
    		resstr = "fail";
    	}
    	return resstr;
    }
//    @PostMapping("/api/v1/mail")
//	public void mail(@RequestBody EmailVo Request , HttpServletResponse response) {
//		
//		response.setContentType("text/html; charset=UTF-8");
//		PrintWriter out=null;
//		
//		SimpleEmail email1 = new SimpleEmail();
//		email1.setHostName("smtp.naver.com");
//		email1.setSmtpPort(465);
//		email1.setAuthentication("clothpick", "sodhtwkd5%");
//		
//		// 보안연결 SSL, TLS 사용 설정
//		email1.setSSLOnConnect(true);
//		email1.setStartTLSEnabled(true);
//		
//		String response1 = "fail";
//		
//		try {
//			// 보내는 사람 설정 (SMTP 서비스 로그인 계정 아이디와 동일하게 해야함에 주의!)
//			email1.setFrom("clothpick@naver.com", "옷픽", "utf-8");			
//			// 받는 사람 설정
//			email1.addTo(Request.getEmail(), "이름", "utf-8");			
//			// 제목 설정
//			email1.setSubject("제목입니다");			
//			email1.setMsg("메시지입니다");			
//			// 메일 전송하기
//			try {
//				out = response.getWriter();
//				response1 = email1.send();
//				out.println("ok");				
//			} catch (IOException e) {			
//				e.printStackTrace();
//				out.println("err");	
//			}			
//		} catch (EmailException e) {
//			e.printStackTrace();
//			out.println("fail");	
//		}
//		
//	}
    
    
    //인증 메일 보냄
    @PostMapping("/api/v1/mail/send")
 	public String mails(@RequestBody EmailVo Request) throws MessagingException {
    	int result = UserService.sendMail(Request);
    	  if(result == 1) {
    	      return "ok";
    	  }else if(result ==2){
    		  return "email send fail";
    	  }else {
    	      return "fail";
    	 }
 	}
    //메일 코드 체크
    @PostMapping("/api/v1/mail/check")
 	public String mails(@RequestBody AuthenticationCodeVo Request){
    	String result = UserService.checkMail(Request);
    	return result;
 	}
    
    
    
    //유저 정보 리턴
    @GetMapping("/api/v1/userinfo")
    public ReturnUserVo userinfo(HttpServletRequest httpServletRequest){
    	String authorizationHeader = httpServletRequest.getHeader("Authorization");
		String token = authorizationHeader.substring(7);
		ReturnUserVo list = UserService.ReturnUserInfo(token);
        return list;       
    }
    
    //로그인
    @PostMapping("/api/v1/login")
    public String login(@RequestBody AuthRequestVo authRequest) {
    	String AccessToken = UserService.logintoken(authRequest);
    	System.out.println("*******************ch  " + AccessToken);
    	return AccessToken;
    }
    //아이디 중복 체크
    @PostMapping("/api/v1/signup/idch")
    public String signupidch(@RequestBody AuthRequestVo authRequest) {
    	int result = UserService.UserSignUp_idserch(authRequest);
    	String resstr = "";
    	if(result == 1) {
    		resstr = "fail";
    	}else {
    		resstr = "ok";
    	}
    	return resstr;
    }
    
    //이메일 중복 체크
    @PostMapping("/api/v1/signup/emailch")
    public String signupemailch(@RequestBody UserVo authRequest) {
    	int result = UserService.UserSignUp_emailserch(authRequest);
    	String resstr = "";
    	if(result == 1) {
    		resstr = "fail";
    	}else {
    		resstr = "ok";
    	}
    	return resstr;
    }
    
    
    //닉네임 중복체크
    @PostMapping("/api/v1/signup/nickch")
    public String signupnickch(@RequestBody UserVo authRequest) {
    	int result = UserService.UserSignUp_nicknameserch(authRequest);
    	String resstr = "";
    	if(result == 1) {
    		resstr = "fail";
    	}else {
    		resstr = "ok";
    	}
    	return resstr;
    }
    
    @PostMapping("/api/v1/search/username")
    public String searchusername(@RequestBody UserInfoSearchVo request) throws MessagingException {
    	String result = UserService.usernameSearch(request);
    	return result;
    }
    
    @PostMapping("/api/v1/search/password")
    public String searchpassword(@RequestBody UserInfoSearchVo request) throws MessagingException {
    	String result = UserService.passwordReset(request);
    	return result;
    }
    @PostMapping("/api/v1/reset/password")
    public String resetpassword(HttpServletRequest httpServletRequest, @RequestBody AuthRequestVo request){
    	String authorizationHeader = httpServletRequest.getHeader("Authorization");
		String token = authorizationHeader.substring(7);
    	String result = UserService.loginuserpasswordReset(request, token);
    	return result;
    }
}