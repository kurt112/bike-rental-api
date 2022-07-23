package com.thesis.bikerental.utils;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

@Service
public class Jwt implements Serializable {

//    @Value("${secret.key}")
//    private String SECRET_KEY;
//    public final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
//
//    public String getUsername(String token) {
//        T t = extractClaim(token, Claims::getSubject);
//        return t;
//    }
//
//    public Date getExpiration(String token) {
//        return extractClaim(token, Claims::getExpiration);
//    }
//
//    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
//        final Claims claims = extractAllClaims(token);
//        return claimsResolver.apply(claims);
//    }
//
//    private Claims extractAllClaims(String token) {
//
//        return Jwts.parser()
//                .setSigningKey(SECRET_KEY)
//                .parseClaimsJws(token)
//                .getBody();
//    }
//
//    public Boolean isTokenExpired(String token) {
//        return getExpiration(token).before(new Date());
//    }
//
//    public String generateToken(UserDetails userDetails, boolean expiry) {
//        Map<String, Object> claims = new HashMap<>();
//        if(expiry) return createToken(claims, userDetails.getUsername());
//        else return createTokenNoExpiry(claims,userDetails.getUsername());
//    }
//
//    public String generateTokenResetPassword(UserDetails userDetails) {
//        Map<String, Object> claims = new HashMap<>();
//
//        return createTokenReset(claims,userDetails.getUsername());
//    }
//
//
//    private String createToken(Map<String, Object> claims, String subject) {
//        return Jwts.builder()
//                .setClaims(claims)
//                .setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
//                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
//    }
//
//    private String createTokenReset(Map<String, Object> claims, String subject) {
//        return Jwts.builder()
//                .setClaims(claims)
//                .setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 15))
//                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
//    }
//
//    private String createTokenNoExpiry(Map<String, Object> claims, String subject) {
//        return Jwts.builder()
//                .setClaims(claims)
//                .setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
//                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
//    }
//
//    public Boolean validateToken(String token, UserDetails userDetails) {
//        final String username = getUsername(token);
//        return (username.equals(userDetails.getUsername()));
//    }
//
//    public Boolean removeToken(String token) {
//        Claims claims = extractAllClaims(token);
//        claims.setSubject(null);
//        claims.clear();
//        return true;
//    }
//
//    public Date extendDay(int day) {
//        Calendar calendar = new GregorianCalendar();
//        calendar.add(Calendar.DATE, day);
//
//
//        return calendar.getTime();
//    }
//
//    private Date modifyDateToday(int day) {
//        final Calendar cal = Calendar.getInstance();
//        cal.add(Calendar.DATE, day);
//        return cal.getTime();
//    }
//
//    private void removeToken( ) {
////        OAuth2Authentication authentication = super.readAuthentication(token.getValue());
////        String username = authentication.getUserAuthentication().getName();
////        User user = userRepository.findByEmail(username);
////        user.setToken(null);
////        userRepository.save(user);
//    }
}