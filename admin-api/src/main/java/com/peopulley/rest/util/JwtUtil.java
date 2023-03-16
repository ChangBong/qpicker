package com.peopulley.rest.util;

import com.peopulley.rest.common.constants.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

@Component
public class JwtUtil {

    /* 로그인 토큰은 7일로 정한다. */
    public final static long TOKEN_VALIDATION_SECOND = 60 * 60 * 24 * 7;

    /* EMAIL 발송은 30 분으로 일단 설정 */
    public final static long TOKEN_MAIL_VALIDATION_SECOND = 60 * 30;

    @Value("${spring.jwt.secret}")
    private String SECRET_KEY;

    private Key getSigningKey(String secretKey) {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Claims extractAllClaims(String token) throws ExpiredJwtException {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey(SECRET_KEY))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getMembername(String token) {
        return extractAllClaims(token).get("membername", String.class);
    }

    public Object getClaim(String token, String claimName, Class o){
        return extractAllClaims(token).get(claimName, o);
    }

    public Boolean isTokenExpired(String token) {
        final Date expiration = extractAllClaims(token).getExpiration();
        return expiration.before(new Date());
    }

    public Boolean isTodayIssuedAt(String token) throws ParseException {
        final Date issuedAt = extractAllClaims(token).getIssuedAt();
        return DateUtil.getBetweenDates(DateUtil.dateToString(issuedAt), DateUtil.dateToString(new Date())) == 0;
    }
/*

    public String generateToken(Members member) {
        return doGenerateToken(member.getMembername(), TOKEN_VALIDATION_SECOND);
    }

    public String generateRefreshToken(Members member) {
        return doGenerateToken(member.getMembername(), REFRESH_TOKEN_VALIDATION_SECOND);
    }
*/

    public String doGenerateToken(String membername, long expireTime) {
        Claims claims = Jwts.claims();
        claims.put("membername", membername);

        return buildJwt(claims, expireTime);
    }

    public String doGenerateTokenFromMap(HashMap<Object, Object> claimMap, long expireTime) {
        Claims claims = Jwts.claims();
        Iterator<Object> claimsList = claimMap.keySet().iterator();

        while (claimsList.hasNext()){
            String key = String.valueOf(claimsList.next());
            claims.put(key, claimMap.get(key));
        }

        return buildJwt(claims, expireTime);
    }

    private String buildJwt(Claims claims, long expireTime){
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + (expireTime * 1000)))
                .signWith(getSigningKey(SECRET_KEY), SignatureAlgorithm.HS256)
                .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) throws ParseException {
        final String membername = getMembername(token);

        return (membername.equals(userDetails.getUsername()) && !isTokenExpired(token) && isTodayIssuedAt(token));
    }

    public String getAccessTokenFromHeader(HttpServletRequest request, String headerName){
        String authorization = request.getHeader(headerName);
        return authorization != null ? authorization.replace(Constants.TOKEN_TYPE, "") : null;
    }

    public void setAccessTokenToHeader(HttpServletResponse response, String accessToken){
        response.addHeader(Constants.AUTHORIZATION, Constants.TOKEN_TYPE + " " + accessToken);
    }
}
