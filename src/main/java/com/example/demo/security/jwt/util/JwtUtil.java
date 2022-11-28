package com.example.demo.security.jwt.util;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.cglib.core.internal.Function;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.demo.security.vo.UserVo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtUtil {
	private String secret = "javatechie";
	 
	
	//서브잭 가져오기
    public String extractUsername(String token) {
    	
//    	JSONParser parser = new JSONParser();
//    	JSONObject info = new JSONObject();
//    	
//    	System.out.println();
//    	System.out.println("***** info : "+info.get("userNickName"));
//    	return Jwts.parser().setSigningKey(secret).parseClaimsJwt(token).getBody();
//		
    	
//    	try {
//			info = (JSONObject)parser.parse( userinfo );
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		 return "aaa";
//    	extractClaim(token, Claims::get)
        return extractClaim(token, Claims::getSubject);
		
    }
 
    /**
     * Claim 에서 각 항목 정보 가져오기
     */
    public String getUserIdFromToken(String token) {
        String userId  = String.valueOf(extractAllClaims(token).get("userId"));
//        System.out.println("getUsernameFormToken subject = {}"+ userId);
        return userId;
    }
    public String getUsernameFromToken(String token) {
        String username = String.valueOf(extractAllClaims(token).get("username"));
//        System.out.println("getUsernameFormToken subject = {}"+ username);
        return username;
    }
    public String getuserGenderFromToken(String token) {
        String username = String.valueOf(extractAllClaims(token).get("userGender"));
//        System.out.println("getUsernameFormToken subject = {}"+ username);
        return username;
    }
    public String getuserDOBFromToken(String token) {
        String username = String.valueOf(extractAllClaims(token).get("userDOB"));
//        System.out.println("getUsernameFormToken subject = {}"+ username);
        return username;
    }
    public String getuserNickNameFromToken(String token) {
        String username = String.valueOf(extractAllClaims(token).get("userNickName"));
//        System.out.println("getUsernameFormToken subject = {}"+ username);
        return username;
    }
    public String getuserRoleFromToken(String token) {
        String username = String.valueOf(extractAllClaims(token).get("userRole"));
//        System.out.println("getUsernameFormToken subject = {}"+ username);
        return username;
    }
    public String getuserUserupdateATFromToken(String token) {
    	String username = String.valueOf(extractAllClaims(token).get("userupdateAT"));
//        System.out.println("getUsernameFormToken subject = {}"+ username);
        return username;
    }
    public String getuserUserEmailFromToken(String token) {
    	String username = String.valueOf(extractAllClaims(token).get("userEmail"));
//        System.out.println("getUsernameFormToken subject = {}"+ username);
        return username;
    }
    
    //------------------------------------------------------------------------
  
    
    
    // claim정보 가져오기
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    // body 정보 가저오기
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }
 
    
    
    // 토큰 유효시간 추출
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    // 토큰 유효시간 확인
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
 
    
    
    
    
    // 유저 정보 작성
    public String generateToken(UserVo username, String user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", username.getUserId());
        claims.put("username", username.getUsername());
        claims.put("userEmail", username.getUserEmail());
        claims.put("userGender", username.getUserGender());
        claims.put("userDOB", username.getUserDOB());
        claims.put("userNickName", username.getUserNickName());
        claims.put("userRole", username.getUserRole());
        claims.put("userupdateAT", username.getUserupdateAT());
        
        return createToken(claims, user);
    }
    
    // 토큰 빌드
    private String createToken(Map<String, Object> claims, String subject) {
 
        return Jwts.builder()
        		.setClaims(claims)
        		.setSubject(subject)
        		.setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }
 
    
    
    
    // 토큰 유저 정보 확인
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}