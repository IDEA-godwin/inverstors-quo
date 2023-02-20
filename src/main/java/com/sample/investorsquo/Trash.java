//package com.bw.dentaldoor.service;
//
//
//import com.bw.commons.starter.SettingService;
//import com.bw.dentaldoor.AuthUserDto;
//import com.bw.dentaldoor.KeycloakConfigurationProperties;
//import com.bw.dentaldoor.LoginDto;
//import com.bw.dentaldoor.domain.PortalUserDto;
//import com.bw.dentaldoor.domain.TokenPojo;
//import com.bw.dentaldoor.domain.account.SignUpResponse;
//import com.bw.dentaldoor.domain.auth.Scope;
//import com.bw.dentaldoor.entity.PortalUser;
//import com.bw.exception.ErrorResponse;
//import com.google.gson.Gson;
//import com.google.gson.annotations.SerializedName;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import okhttp3.MediaType;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.RequestBody;
//import okhttp3.logging.HttpLoggingInterceptor;
//import org.apache.commons.lang3.StringUtils;
//import org.keycloak.admin.client.CreatedResponseUtil;
//import org.keycloak.admin.client.Keycloak;
//import org.keycloak.admin.client.KeycloakBuilder;
//import org.keycloak.admin.client.resource.ClientResource;
//import org.keycloak.admin.client.resource.RealmResource;
//import org.keycloak.admin.client.resource.UserResource;
//import org.keycloak.admin.client.resource.UsersResource;
//import org.keycloak.representations.idm.*;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import javax.inject.Inject;
//import javax.inject.Named;
//import javax.validation.constraints.NotNull;
//import javax.ws.rs.core.Response;
//import java.io.IOException;
//import java.util.List;
//
///**
// * @author Jeremiah Udoh
// * email: judoh@byteworks.com.ng
// * July, 2022
// **/
//@Slf4j
//@Named
//@RequiredArgsConstructor
//public class Trash  {
//    @Inject
//    private RealmResource realmResource;
//    private final KeycloakConfigurationProperties keycloakConfigurationProperties;
//    private final PhoneNumberService phoneNumberService;
//    private final SettingService settingService;
//    public static final String KEYCLOAK_CLIENT_SECRET = "KEYCLOAK_MOBILE_CLIENT_SECRET";
//
//    @Override
//    public AuthUserDto createNewUser(PortalUserDto user) {
//
//        System.out.println("creating new email3====" + user.getEmail());
//        List<UserRepresentation> userRepresentations = realmResource.users().search(user.getEmail(), true);
//        if (StringUtils.isNotBlank(user.getEmail())) {
//            userRepresentations.addAll(realmResource.users().search(null, null, null, user.getEmail(), 0, 1));
//        }
//        if (userRepresentations.isEmpty()) {
//            UserRepresentation userRepresentation = new UserRepresentation();
//            userRepresentation.setFirstName(user.getFirstName());
//            userRepresentation.setLastName(user.getLastName());
//            userRepresentation.setEmail(user.getEmail());
//            userRepresentation.setUsername(user.getEmail());
//            userRepresentation.setEnabled(false);
//            userRepresentation.setEmailVerified(false);
//
//            Response response = realmResource.users().create(userRepresentation);
//            if (response == null) {
//                return null;
//            }
//
//            System.out.printf("Response: %s %s%n", response.getStatus(), response.getStatusInfo());
//            if (response.getStatus() > 300) {
//                return null;
//            }
//            String userId = CreatedResponseUtil.getCreatedId(response);
//
//            UserResource userResource = realmResource.users().get(userId);
//            if (StringUtils.isNotBlank(user.getPassword())) {
//                CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
//                credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
//                credentialRepresentation.setValue(user.getPassword());
//                credentialRepresentation.setTemporary(Boolean.FALSE);
//                userResource.resetPassword(credentialRepresentation);
//            }
//            UserRepresentation userRep = userResource.toRepresentation();
//            AuthUserDto authUserDto = new AuthUserDto();
//            authUserDto.setId(userRep.getId());
//            authUserDto.setUserName(userRep.getUsername());
//            return authUserDto;
//        }
//
//        UserRepresentation userRepresentation = userRepresentations.get(0);
//        AuthUserDto authUserDto = new AuthUserDto();
//        authUserDto.setId(userRepresentation.getId());
//        authUserDto.setUserName(userRepresentation.getUsername());
//        return authUserDto;
//    }
//
//    @Override
//    public AuthUserDto getUserByUsername(String username) {
//        List<UserRepresentation> userRepresentations = realmResource.users().search(username, true);
//        if (userRepresentations.isEmpty()) {
//            return null;
//        }
//        UserRepresentation userRepresentation = userRepresentations.get(0);
//        AuthUserDto authUserDto = new AuthUserDto();
//        authUserDto.setId(userRepresentation.getId());
//        authUserDto.setUserName(userRepresentation.getUsername());
//        authUserDto.setFirstName(userRepresentation.getFirstName());
//        authUserDto.setLastName(userRepresentation.getLastName());
//        return authUserDto;
//    }
//
//    @Override
//    public void activateUser(PortalUser portalUser) {
//        UserRepresentation user = realmResource.users().get(portalUser.getUserId()).toRepresentation();
//        user.setEnabled(true);
//        user.setEmailVerified(true);
//        realmResource.users().get(portalUser.getUserId()).update(user);
//    }
//
//    @Override
//    public void changePassword(String newPassword, PortalUser portalUser) {
//        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
//        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
//        credentialRepresentation.setValue(newPassword);
//        credentialRepresentation.setTemporary(false);
//        UsersResource users = realmResource.users();
//        portalUser.getUserId();
//        users.count();
//        UserResource userResource = users.get(portalUser.getUserId());
//        userResource.resetPassword(credentialRepresentation);
//    }
//
//    @Override
//    public void resetPassword(String newPassword, PortalUser portalUser) {
//        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
//        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
//        credentialRepresentation.setValue(newPassword);
//        credentialRepresentation.setTemporary(true);
//        UsersResource users = realmResource.users();
//        portalUser.getUserId();
//        users.count();
//        UserResource userResource = users.get(portalUser.getUserId());
//        userResource.resetPassword(credentialRepresentation);
//    }
//
//    @Override
//    public void logout(PortalUser portalUser) {
//        realmResource.users().get(portalUser.getUserId()).logout();
//    }
//
//    @Override
//    public String generateAccessToken(String username, String password) {
//        try (Keycloak keycloak = Keycloak.getInstance(keycloakConfigurationProperties.getAuthUrl(),// keycloak address
//                "dentaldoor",
//                username,
//                password, "dentaldoor-core")) {
//
//            return keycloak.tokenManager().getAccessToken().getToken();
//        } catch (Exception e) {
//            throw new ErrorResponse(400, "Invalid Username or Password");
//        }
//    }
//
//    public void checkSession(){
//        try (Keycloak keycloak = Keycloak.getInstance(keycloakConfigurationProperties.getAuthUrl(),// keycloak address
//                "dentaldoor",
//                "admin",
//                "admin", "dentaldoor-frontend")) {
//            List<ClientRepresentation> cr = keycloak.realm("dentaldoor").clients().findByClientId("dentaldoor-frontend");
//            ClientResource resource = keycloak.realm("dentaldoor").clients().get(cr.get(0).getId());
//
//            List<UserSessionRepresentation> srp = resource.getUserSessions(0, 1000);
//            log.info("session count: "+srp.size());
//        } catch (Exception e) {
//            throw new ErrorResponse(400, "Invalid Username or Password");
//        }
//    }
//    @Override
//    public String generateKeycloakToken(String username, String password,String clientId) {
//        try (Keycloak keycloak = Keycloak.getInstance(keycloakConfigurationProperties.getAuthUrl(),// keycloak address
//                "dentaldoor",
//                username,
//                password, clientId)) {
//
//            return keycloak.tokenManager().getAccessToken().getToken();
//        } catch (Exception e) {
//            throw new ErrorResponse(400, "Invalid Username or Password");
//        }
//    }
//
//    @Override
//    public void createDefaultScopes() {
//        List<ClientScopeRepresentation> clientScopeRepresentations = realmResource.clientScopes().findAll();
//        for (Scope value : Scope.values()) {
//            if (clientScopeRepresentations.stream().noneMatch(clientScopeRepresentation -> clientScopeRepresentation.getId().equalsIgnoreCase(value.getCode()))) {
//                ClientScopeRepresentation clientScopeRepresentation = new ClientScopeRepresentation();
//                clientScopeRepresentation.setId(value.getCode());
//                clientScopeRepresentation.setName(value.name());
//                clientScopeRepresentation.setDescription(value.getDescription());
//
//                realmResource.clientScopes().create(clientScopeRepresentation);
//            }
//        }
//    }
//
//    @Override
//    public SignUpResponse createNewUser(PortalUser portalUser, String password) {
//        PortalUserDto newUserDto = new PortalUserDto();
//        newUserDto.setPassword(password);
//        newUserDto.setEmail(portalUser.getEmail());
//        newUserDto.setPhoneNumber(portalUser.getPhoneNumber());
//        newUserDto.setFirstName(portalUser.getFirstName());
//        newUserDto.setLastName(portalUser.getLastName());
//        AuthUserDto newUser = null;
//        try {
//            newUser = createNewUser(newUserDto);
//        } catch (Exception e) {
//            log.info("keycloak error ==> {}", e.getMessage());
//        }
//        SignUpResponse signUpResponse = new SignUpResponse();
//        signUpResponse.setUserId(newUser != null ? newUser.getId() : null);
//        signUpResponse.setAuthToken(null);
//        signUpResponse.setPortalUser(portalUser);
//        return signUpResponse;
//    }
//
//    @Override
//    public void updateUserDetails(PortalUser user) {
//        List<UserRepresentation> userRepresentations = realmResource.users().search(user.getUsername(), true);
//        if (userRepresentations.isEmpty()) {
//            throw new ErrorResponse(400, "User not found");
//        }
//        UserRepresentation userRepresentation = userRepresentations.get(0);
//        userRepresentation.setFirstName(user.getFirstName());
//        userRepresentation.setLastName(user.getLastName());
//    }
//
//    @Override
//    public String getUserCurrentSessionId(@NotNull String userId) throws IndexOutOfBoundsException{
//        UserResource ur = realmResource.users().get(userId);
//        List<UserSessionRepresentation> usrList = ur.getUserSessions();
//        UserSessionRepresentation usr;
//
//        //A user can only have one session. This is configured on keycloak.
//        usr = usrList.get(0);
//        return usr.getId();
//    }
//
//    public TokenPojo generateClientLoginToken(LoginDto dto, String clientId) {
//        String SECRET = settingService.getString(KEYCLOAK_CLIENT_SECRET, "108b470e-8198-42ca-998a-6b7b03bfa999");;
//        String USERNAME =dto.getUsername().trim();
//        String PASSWORD = dto.getPassword();
//        String authUrl = keycloakConfigurationProperties.getAuthUrl();
//        if (!authUrl.endsWith("/")) {
//            authUrl = authUrl + "/";
//        }
//        String TOKEN_URL = authUrl + "realms/dentaldoor/protocol/openid-connect/token";
//        String GRANT_TYPE = "password";
//        String SCOPE = "openid";
//        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
//        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//        OkHttpClient client = new OkHttpClient().newBuilder()
//                .addInterceptor(httpLoggingInterceptor)
//                .build();
//        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
//        RequestBody body =
//                RequestBody.create(mediaType, "client_id=" + clientId
//                        + "&client_secret=" + SECRET
//                        + "&grant_type=" + GRANT_TYPE
//                        + "&username=" + USERNAME
//                        + "&password=" + PASSWORD
//                        + "&scope=" + SCOPE);
//        Request request = new Request.Builder()
//                .url(TOKEN_URL)
//                .method("POST", body)
//                .addHeader("Content-Type", "application/x-www-form-urlencoded")
//                .build();
//        okhttp3.Response response = null;
//        try {
//            response = client.newCall(request).execute();
//            if (!response.isSuccessful()) {
//                throw new ErrorResponse(400, "Invalid User Credentials");
//            }
//            TokenResponse tokenResponse = new Gson().fromJson(response.body().string(), TokenResponse.class);
//            return new TokenPojo(tokenResponse.accessToken, tokenResponse.refreshToken);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        throw new ErrorResponse(403, "Something went wrong");
//    }
//
////    public void enableUserAccount(String userId){
////        // connect to keycloak server
////        Keycloak keycloak = KeycloakBuilder.builder()
////                .serverUrl(AUTHURL)
////                .realm(REALM)
////                .grantType(OAuth2Constants.PASSWORD) // "password"
////                .clientId(CLIENTID)
////                .clientSecret(SECRETKEY)
////                .username(ADMIN_USERNAME)
////                .password(ADMIN_PASSWORD)
////                .build();
////
////// get user resource
////        RealmResource realmResource = keycloak.realm(REALM);
////        UsersResource userRessource = realmResource.users();
////
////// fetch an existing user
////        UserRepresentation user = userRessource.get(userId).toRepresentation();
////
////// change user
////        user.setEnabled(true);
////
////// update
////        userRessource.get(userId).update(user);
////    }
//
//
//    public static class TokenResponse {
//        @SerializedName("access_token")
//        public String accessToken;
//        @SerializedName("refresh_token")
//        public String refreshToken;
//    }
//}
