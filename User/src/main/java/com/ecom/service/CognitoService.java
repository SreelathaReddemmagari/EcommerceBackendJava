//package com.ecom.service;
//
//import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
//import com.amazonaws.services.cognitoidp.model.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import javax.crypto.Mac;
//import javax.crypto.spec.SecretKeySpec;
//import java.nio.charset.StandardCharsets;
//import java.util.Base64;
//
//@Service
//public class CognitoService {
//    private String calculateSecretHash(String userName, String clientId, String clientSecret) {
//        final String HMAC_SHA256_ALGORITHM = "HmacSHA256";
//        try {
//            SecretKeySpec signingKey = new SecretKeySpec(clientSecret.getBytes(StandardCharsets.UTF_8), HMAC_SHA256_ALGORITHM);
//            Mac mac = Mac.getInstance(HMAC_SHA256_ALGORITHM);
//            mac.init(signingKey);
//            byte[] rawHmac = mac.doFinal((userName + clientId).getBytes(StandardCharsets.UTF_8));
//            return Base64.getEncoder().encodeToString(rawHmac);
//        } catch (Exception e) {
//            throw new RuntimeException("Error while calculating secret hash", e);
//        }
//    }
//
//
//    @Value("${aws.cognito.client-id}")
//    private String clientId;
//
//    @Value("${aws.cognito.client-secret}")
//    private String clientSecret;
//
//    @Value("${aws.cognito.region}")
//    private String region;
//
//    @Value("${aws.cognito.user-pool-id}")
//    private String userPoolId;
//
//    @Autowired
//    private final AWSCognitoIdentityProvider cognitoClient;
//
//    @Autowired
//    public CognitoService(AWSCognitoIdentityProvider cognitoClient) {
//        this.cognitoClient = cognitoClient;
//    }
//
//    public SignUpResult signUpUser(String username, String password, String email, String phoneNumber) {
//        try {
//            SignUpRequest signUpRequest = new SignUpRequest()
//                    .withClientId(clientId)
//                    .withUsername(username)
//                    .withPassword(password)
//                    .withSecretHash(calculateSecretHash(username, clientId, clientSecret))
//                    .withUserAttributes(
//                            new AttributeType().withName("email").withValue(email)
////                            new AttributeType().withName("phone_number").withValue(phoneNumber)
//                    );
//
//            return cognitoClient.signUp(signUpRequest);
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new RuntimeException("Error signing up user", e);
//        }
//    }
//
//    public AdminConfirmSignUpResult confirmUserSignUp(String username) {
//        try {
//            AdminConfirmSignUpRequest confirmRequest = new AdminConfirmSignUpRequest()
//                    .withUserPoolId(userPoolId)
//                    .withUsername(username);
//
//            return cognitoClient.adminConfirmSignUp(confirmRequest);
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new RuntimeException("Error confirming user sign up", e);
//        }
//    }
//}
