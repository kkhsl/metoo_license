//package com.abtnetworks.totems.basic.service;
//
//import com.abtnetworks.totems.basic.entity.mysql.UmsUserSsoTokenEntity;
//import com.abtnetworks.totems.basic.service.UserInfoService;
//import com.abtnetworks.totems.common.utils.IdGen;
//import com.abtnetworks.totems.sso.config.ClientDetailsStoreMap;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
//import org.springframework.security.oauth2.common.OAuth2AccessToken;
//import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
//import org.springframework.security.oauth2.provider.ClientDetails;
//import org.springframework.security.oauth2.provider.OAuth2Authentication;
//import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
//import org.springframework.security.oauth2.provider.token.TokenStore;
//import org.springframework.stereotype.Service;
//import org.springframework.web.HttpRequestMethodNotSupportedException;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//
//@Service
//public class InitSsoTokenService {
//    @Value("client4")
//    private String clientId;
//    @Value("third_part_client")
//    private String thirdPartClientId;
//    @Autowired
//    private TokenEndpoint tokenEndpointService;
//    @Autowired
//    private TokenStore tokenStore;
//
//    @Autowired
//    private ClientDetailsStoreMap clientDetailsStoreMap;
//    @Autowired
//    private UserInfoService userInfoService;
//
//    private static final Integer DEFAULT_TOKEN_VALIDITY_SECONDS = 43200;
//
//    public void init(){
//        this.insert();
//    }
//
//    public String creatThirdPartToken(Integer validitySeconds) throws HttpRequestMethodNotSupportedException {
//        String tokenValue = null;
//
//        try {
//            Map<String, String> parameters = new HashMap();
//            ClientDetails clientDetails  = (ClientDetails)this.clientDetailsStoreMap.clientDetailsStore().get(this.thirdPartClientId);
//            parameters.put("client_id", clientDetails.getClientId());
//            parameters.put("client_secret", clientDetails.getClientSecret());
//            parameters.put("grant_type", "client_credentials");
//
//            Authentication authentication = new UsernamePasswordAuthenticationToken(clientDetails.getClientId(), clientDetails.getClientSecret(), clientDetails.getAuthorities());
//            ResponseEntity<OAuth2AccessToken> entity = this.tokenEndpointService.postAccessToken(authentication, parameters);
//            String accessTokenValue = ((OAuth2AccessToken)entity.getBody()).getValue();
//            DefaultOAuth2AccessToken accessToken = (DefaultOAuth2AccessToken)entity.getBody();
//            if (accessToken == null) {
//                throw new InvalidTokenException("Invalid access token: " + accessTokenValue);
//            }
//
//            if (accessToken.isExpired()) {
//                this.tokenStore.removeAccessToken(accessToken);
//                throw new InvalidTokenException("Access token expired: " + accessTokenValue);
//            }
//
//            OAuth2Authentication result = this.tokenStore.readAuthentication(accessToken);
//            if (result == null) {
//                throw new InvalidTokenException("Invalid access token: " + accessTokenValue);
//            }
//
//            accessToken.setExpiration(new Date(System.currentTimeMillis() + (long)validitySeconds * 1000L));
//            this.tokenStore.storeAccessToken(accessToken, result);
//            tokenValue = accessToken.getValue();
//        } catch (Exception var16) {
//
//        }
//        return tokenValue;
//    }
//
//
//    public void insert() {
//        UmsUserSsoTokenEntity entity = new UmsUserSsoTokenEntity();
//        entity.setUserName("DEFAULT_THIRD_PARTY_ORGANIZATION");
//        entity.setUuid(IdGen.uuid());
//        entity.setUmsUserId(entity.getUserName());
//        String tokenValue = null;
//        try {
//            tokenValue = this.creatThirdPartToken(Integer.valueOf("43200"));
//            entity.setTokenValue(tokenValue);
////            this.userInfoService.insertUserSsoTokenEntity(entity);
//
//        } catch (HttpRequestMethodNotSupportedException e) {
//            e.printStackTrace();
//        }
//
//    }
//}
