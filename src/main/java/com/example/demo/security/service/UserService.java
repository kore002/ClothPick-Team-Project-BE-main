package com.example.demo.security.service;


import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.mail.MessagingException;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.security.dao.AuthenticationDao;
import com.example.demo.security.dao.UserDao;
import com.example.demo.security.jwt.util.JwtUtil;
import com.example.demo.security.vo.AuthRequestVo;
import com.example.demo.security.vo.AuthenticationCodeVo;
import com.example.demo.security.vo.EmailVo;
import com.example.demo.security.vo.ReturnUserVo;
import com.example.demo.security.vo.UserInfoSearchVo;
import com.example.demo.security.vo.UserVo;

@Service
public class UserService implements UserDetailsService {
	@Autowired
	UserDao UserDao;
	@Autowired
	AuthenticationDao AuthenticationDao;
	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	JavaMailSender javaMailSender;
	
	
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	Map<String, String> paramMap = new HashMap<String,String>();
		paramMap.put("username", username);
        UserVo user = UserDao.UserSearchDao(paramMap);
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
    }
    
    //아이디 비번 찾기 이메일 보내기 코드
    public String send_ID_PASSWORD_mail(int codeType, String userEmail, String result) throws MessagingException {
    	SimpleMailMessage message = new SimpleMailMessage();
    	if(codeType == 1) {
			message.setFrom("clothpickmoble@gmail.com");
			message.setTo(userEmail);
	        message.setSubject("옷픽 아이디");
	        message.setText("옷픽 아이디 : " + result);
	        javaMailSender.send(message);
	    	
	        return "success";
		}else if(codeType == 2 ) {
			message.setFrom("clothpickmoble@gmail.com");
			message.setTo(userEmail);
	        message.setSubject("옷픽 임시 비밀번호");
	        message.setText("임시 비밀번호 : " + result);
	        javaMailSender.send(message);
	    	
	        return "success";
		}else {
			return "err : email send fail";
		}
    }
    
    //메일 인증 보내기
    public int sendMail(EmailVo request) throws MessagingException {
    	SimpleMailMessage message = new SimpleMailMessage();
    	
    	UUID uuid = UUID.randomUUID();
		String uuids[] = uuid.toString().split("-");
		String emailCode = uuids[0];
		
		if(request.getCodeType() == 0) {
			Map<String, String> userMap = new HashMap<String, String>();
			userMap.put("codeType",  Integer.toString(request.getCodeType()));
			userMap.put("emailCode",  emailCode);
			userMap.put("authenticationStatus",  "0");
			userMap.put("userEmail", request.getUserEmail());
			
			int result = AuthenticationDao.AuthenticationInfoPost(userMap);
			
//	    	message.setFrom("2kwon2lee@gmail.com");
			message.setFrom("clothpickmoble@gmail.com");
			message.setTo(request.getUserEmail());
	        message.setSubject("옷픽 회원 가입을 위한 이메일 인증");
	        message.setText("이메일 인증 코드 : " + emailCode);
	        javaMailSender.send(message);
	    	
	        return result;
		}else if(request.getCodeType() == 1) {
			Map<String, String> userMap = new HashMap<String, String>();
			userMap.put("codeType",  Integer.toString(request.getCodeType()));
			userMap.put("emailCode",  emailCode);
			userMap.put("authenticationStatus",  "0");
			userMap.put("userEmail", request.getUserEmail());
			
			int result = AuthenticationDao.AuthenticationInfoPost(userMap);
			
//	    	message.setFrom("2kwon2lee@gmail.com");
			message.setFrom("clothpickmoble@gmail.com");
			message.setTo(request.getUserEmail());
	        message.setSubject("옷픽 아이디 찾기를 위한 이메일 인증");
	        message.setText("이메일 인증 코드 : " + emailCode);
	        javaMailSender.send(message);
	    	
	        return result;
		}else if(request.getCodeType() == 2 ) {
			Map<String, String> userMap = new HashMap<String, String>();
			userMap.put("codeType",  Integer.toString(request.getCodeType()));
			userMap.put("emailCode",  emailCode);
			userMap.put("authenticationStatus",  "0");
			userMap.put("userEmail", request.getUserEmail());
			
			int result = AuthenticationDao.AuthenticationInfoPost(userMap);
			
//	    	message.setFrom("2kwon2lee@gmail.com");
			message.setFrom("clothpickmoble@gmail.com");
			message.setTo(request.getUserEmail());
	        message.setSubject("옷픽 비밀번호 초기화를 위한 이메일 인증");
	        message.setText("이메일 인증 코드 : " + emailCode);
	        javaMailSender.send(message);
	    	
	        return result;
		}else {
			return 2;
		}
		


    }
    //메일코드 체크
    //메일 인증 체크
    public String checkMail( AuthenticationCodeVo request ){
    
    	UUID uuid = UUID.randomUUID();
		String uuids[] = uuid.toString().split("-");
		String emailCode = uuids[0];
		
		Map<String, String> userMap = new HashMap<String, String>();
		userMap.put("emailCode",  request.getEmailCode());
		
		
		AuthenticationCodeVo result = AuthenticationDao.AuthenticationStatusCheck(userMap);
		if(result == null) {
			return"wrong code";
		}
		else if(request.getCodeType() == result.getCodeType()) {
			if(request.getUserEmail().equals(result.getUserEmail())) {
				Map<String, String> authenticationMap = new HashMap<String, String>();
				
				authenticationMap.put("emailCode",  request.getEmailCode());
				authenticationMap.put("authenticationStatus",  "1");
				
				int change = AuthenticationDao.AuthenticationInfoPut(authenticationMap);
				
				if(change == 1) {
					return "ok";
				}else {
					return "fail";
				}
			}else {
				return "fail";
			}
		}else {
			return "fail";
		}
    }
    //로그인 토큰
    //로그인 토큰 발급
    public String logintoken(AuthRequestVo authrequestvo) {
    	Map<String, String> userMap = new HashMap<String, String>();
    	userMap.put("username", authrequestvo.getUsername());
    	System.out.println("***** username : "+authrequestvo.getUsername());
    	
    	UserVo result = UserDao.UserSearchDao(userMap);
    	
    	JSONObject userjsonObject = new JSONObject();
    	
    	BCryptPasswordEncoder Encoder = new BCryptPasswordEncoder();
    	if(result == null) {
    		String iderr = "No ID Info";
    		return iderr;
    	}
    	else if( ! Encoder.matches(authrequestvo.getPassword(), result.getPassword())) {
    		System.out.println("***** password : false");
    		String pwerr = "No Coincide Passsword";
    		return pwerr;
    	}else {
    		System.out.println("***** password : true");
    		userjsonObject.put("username", result.getUsername());
        	userjsonObject.put("userRole", result.getUserRole());
        	userjsonObject.put("userNickName", result.getUserNickName());
        	userjsonObject.put("userGender", result.getUserGender());
        	String userinfo = userjsonObject.toJSONString();
        	String usertoken = jwtUtil.generateToken(result, result.getUsername());
        	return usertoken;
    	}
    }
    //아이디 중복 체크
    //아이디 중복
    public int UserSignUp_idserch(AuthRequestVo authrequestvo) {
    	Map<String, String> userMap = new HashMap<String,String>();
    	userMap.put("username", authrequestvo.getUsername());

    	int result = UserDao.UsernameSearchDao(userMap);
    	return result;
    }
    //이메일 중복 체크
    //이메일 중복
    public int UserSignUp_emailserch(UserVo requestvo) {
    	Map<String, String> userMap = new HashMap<String,String>();
    	userMap.put("userEmail", requestvo.getUserEmail());
    	System.out.println("*************************** email log : "+requestvo.getUserEmail());
    	int result = UserDao.UserEmailSearchDao(userMap);
    	return result;
    }
    //닉네임 중복 체크
    //닉네임 중복
    public int UserSignUp_nicknameserch(UserVo requestvo) {
    	Map<String, String> userMap = new HashMap<String,String>();
    	userMap.put("userNickName", requestvo.getUserNickName());

    	int result = UserDao.UserNicknameSearchDao(userMap);
    	return result;
    }
    //회원가입
    //회원가입
    public int UserSignUp(UserVo request) {
    	
    	Map<String, String> authenticationMap = new HashMap<String, String>();
		authenticationMap.put("emailCode",  request.getEmailCode());
    	
		AuthenticationCodeVo resultlist = AuthenticationDao.AuthenticationStatusCheck(authenticationMap);
		if(resultlist.getAuthenticationStatus() != 1 || request.getEmailCode() == null) {
			return 0;
		}else {
			
	    	Map<String, String> userMap = new HashMap<String,String>();
	    	userMap.put("username", request.getUsername());
	    	//패스워드 암호화 
	    	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	    	String resultpassword = encoder.encode(request.getPassword());
	    	userMap.put("password", resultpassword);
	    	
	    	//user, admin 구분 예정
	    	userMap.put("userRole", "user");
	    	userMap.put("userEmail", request.getUserEmail());
	    	
	    	//유저 특수정보
	    	userMap.put("userNickName", request.getUserNickName());
	    	userMap.put("userGender", request.getUserGender());
	    	userMap.put("userDOB", request.getUserDOB());
	    	
	    	
	    	ZoneOffset seoulZoneOffset = ZoneOffset.of("+09:00");
			String currentout = ZonedDateTime.now(seoulZoneOffset).toString();
	    	//가입일, 비밀 번호 수정일
	    	userMap.put("createAT", currentout.substring(0, 19));
	    	userMap.put("updateAT", currentout.substring(0, 19));
	    	
	    	
	    	int result = UserDao.UserSignUpDao(userMap);
	    	return result;
		}
    }
    //유저 정보 조회
    //유저 정보 리턴
    public ReturnUserVo ReturnUserInfo(String token) {
    	
    	int userId = Integer.parseInt(jwtUtil.getUserIdFromToken(token));
    	String username =  jwtUtil.getUsernameFromToken(token);
    	String userRole  = jwtUtil.getuserRoleFromToken(token);
    	String userNickName = jwtUtil.getuserNickNameFromToken(token);
    	String userGender = jwtUtil.getuserGenderFromToken(token);
    	String userDOB = jwtUtil.getuserDOBFromToken(token);
    	String userupdateAT = jwtUtil.getuserUserupdateATFromToken(token);
    			
    	ReturnUserVo userinfo = new ReturnUserVo();
    	userinfo.setUserId(userId);
    	userinfo.setUsername(username);
    	userinfo.setUserRole(userRole);
    	userinfo.setUserNickName(userNickName);
    	userinfo.setUserGender(userGender);
    	userinfo.setUserDOB(userDOB);
    	userinfo.setUserupdateAT(userupdateAT);
    	
    	return userinfo;
    }

    //아이디 찾기
    public String usernameSearch(UserInfoSearchVo UserInfoSearchVo) throws MessagingException  {
    	//인증체크 확인을 위한 테이블 조회
    	Map<String, String> authenticationMap = new HashMap<String, String>();
		authenticationMap.put("emailCode",  UserInfoSearchVo.getEmailCode());
    	
		AuthenticationCodeVo resultlist = AuthenticationDao.AuthenticationStatusCheck(authenticationMap);
		if ( resultlist == null ) { return "fail"; }
		else if(resultlist.getAuthenticationStatus() != 1 || UserInfoSearchVo.getEmailCode() == null) {
			return "wrong code";
		}else {
			Map<String, String> userMap = new HashMap<String, String>();
			userMap.put("userEmail",  UserInfoSearchVo.getUserEmail());
			UserVo result = UserDao.SearchusernameDao(userMap);
			
			String result_username = result.getUsername();
			String sendusername = result_username.substring(0, result_username.length()-3) + "***";
			int codetype = UserInfoSearchVo.getCodeType();
			String useremail = UserInfoSearchVo.getUserEmail();
			String resstr = "fail";
			resstr = send_ID_PASSWORD_mail(codetype, useremail, sendusername);
			
			return resstr;
		}
    }
    //비로그인 상태에서 비밀 번호 초기화
    public String passwordReset(UserInfoSearchVo UserInfoSearchVo) throws MessagingException {
    	//인증체크 확인
    	Map<String, String> authenticationMap = new HashMap<String, String>();
		authenticationMap.put("emailCode",  UserInfoSearchVo.getEmailCode());
    	
		AuthenticationCodeVo resultlist = AuthenticationDao.AuthenticationStatusCheck(authenticationMap);
		UUID uuid = UUID.randomUUID();
		String uuids[] = uuid.toString().split("-");
		String resetpassword = uuids[0]+uuids[1];
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    	String resultpassword = encoder.encode(resetpassword);
    	
    	
    	String resstr = "fail";
    	
    	if ( resultlist == null ) { return resstr; }
    	else if(resultlist.getAuthenticationStatus() != 1 || UserInfoSearchVo.getEmailCode() == null) {
			return "wrong code";
		}else {
			Map<String, String> userMap = new HashMap<String, String>();
			userMap.put("username",  UserInfoSearchVo.getUsername());
			userMap.put("password",  resultpassword);
			userMap.put("userEmail",  UserInfoSearchVo.getUserEmail());
			
			int result = UserDao.ResetpasswordDao(userMap);
			if(result == 1) {
				int codetype = UserInfoSearchVo.getCodeType();
				String useremail = UserInfoSearchVo.getUserEmail();
				
				resstr = send_ID_PASSWORD_mail(codetype, useremail, resetpassword);

				return resstr;
			}else {
				return resstr;
			}
		}
    }
    //로그인 상태에서 비밀번호 초기화
    public String loginuserpasswordReset(AuthRequestVo authrequestvo , String token) {
    	
    	String username = jwtUtil.extractUsername(token);
    	String useremail = jwtUtil.getuserUserEmailFromToken(token);
    	String newpassword = authrequestvo.getPassword();
    	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    	String resultpassword = encoder.encode(newpassword);
    	
    	Map<String, String> userMap = new HashMap<String, String>();
		userMap.put("username",  username);
		userMap.put("password",  resultpassword);
		userMap.put("userEmail",  useremail);
		
		int result = UserDao.ResetpasswordDao(userMap);
		
		if(result == 1) {
			return "ok";
		}else {
			return "fail";
		}
    }

}