package com.example.demo.services;//package com.example.demo.services;
//
//import com.auth0.jwt.*;
//import com.auth0.jwt.algorithms.Algorithm;
//import com.auth0.jwt.exceptions.JWTVerificationException;
//import com.auth0.jwt.interfaces.DecodedJWT;
//import com.example.demo.exception.InvalidTokenException;
//import com.example.demo.exception.NotLoggedInException;
//import com.example.demo.exception.UnauthorizedUserException;
//import org.apache.logging.log4j.util.Strings;
//
//public class JwtService {
//    private static final String SECRET_KEY = "This_is_My_secret_KEY";
//
//    public static String generateToken(String username, String role) {
//        return JWT.create()
//                .withSubject(username)
//                .withClaim("role", role)
//                .sign(Algorithm.HMAC256(SECRET_KEY));
//    }
//
//    public static DecodedJWT decodeToken(String token) {
//        try {
//            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
//            JWTVerifier verifier = JWT.require(algorithm).build();
//            return verifier.verify(token);
//        } catch (JWTVerificationException ex) {
//            throw new InvalidTokenException("Invalid token");
//        }
//    }
//
//    public static void verifyDoctor(String token) {
//        if (token == null || Strings.isBlank(token)){
//            throw new NotLoggedInException("User not logged in");
//        }
//
//        DecodedJWT decodedJWT = decodeToken(token);
//        if(!decodedJWT.getClaim("role").asString().equals("Specialist") )
//            throw new UnauthorizedUserException("You are not a specialist");
//    }
//}
