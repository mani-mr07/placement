package com.placement.placement.Service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.function.Function;

@Service
public class OtpService {

    @Autowired
    private StudentRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(OtpService.class);

    @Autowired
    public OtpService(UserRepository userRepository) {
        this.userRepository = userRepository;}

    @Scheduled(fixedRate = 120000) // Run every minute
    public void deleteExpiredOtps() {
        LocalDateTime expiryTime = LocalDateTime.now().minusMinutes(1);
        userRepository.deleteExpiredOtps(expiryTime);
        logger.info("Expired OTPs deleted at {}", LocalDateTime.now());
    }




    //create Secret Key
    private Key getSiginKey()
    {
        byte[] key= Decoders.BASE64.decode("413F442847284862506553685660597033733676397924422645294840406351");
        return Keys.hmacShaKeyFor(key);

    }

    public String extractUserName(String token)
    {
        return extractClaim(token, Claims::getSubject);
    }


    private <T> T extractClaim(String token, Function<Claims,T> claimsResolvers)
    {
        final Claims claims=extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder().setSigningKey(getSiginKey()).build().parseClaimsJws(token).getBody();
    }



    private boolean isTokenExpired(String token){
        return extractClaim(token,Claims::getExpiration).before(new Date());
    }

    //Method to Generate Access Token
    public String generateAccessToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSiginKey(),SignatureAlgorithm.HS256)
                .compact();
    }
    //Method to Generate Refresh Token
    public String generateRefreshToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setExpiration(new Date(System.currentTimeMillis() + 604800000))
                .signWith(getSiginKey(),SignatureAlgorithm.HS256)
                .compact();

    }




}
