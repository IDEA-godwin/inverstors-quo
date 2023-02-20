package com.sample.investorsquo.serviceImpl;

import com.sample.investorsquo.customException.KeycloakCreateUserException;
import com.sample.investorsquo.domain.dto.CreateKeycloakUserDto;
import com.sample.investorsquo.service.KeycloakService;
import lombok.AllArgsConstructor;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.ws.rs.core.Response;
import java.util.List;

@Service
@AllArgsConstructor
public class KeycloakServiceImpl implements KeycloakService {

    private final RealmResource realmResource;

    @Override
    public String createNewUser(CreateKeycloakUserDto userDto) throws KeycloakCreateUserException {
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setFirstName(userDto.firstName());
        userRepresentation.setLastName(userDto.lastName());
        userRepresentation.setEmail(userDto.email());
        userRepresentation.setUsername(userDto.email());
        userRepresentation.setEnabled(userDto.enabled());
        userRepresentation.setEmailVerified(userDto.emailVerified());

        try (Response response = realmResource.users().create(userRepresentation)) {

            if (response == null || response.getStatus() > 300) {
                Assert.notNull(response, "No response from keycloak realm");
                throw new KeycloakCreateUserException("Keycloak user creation failed", response.getStatus());
            }

            String userId = CreatedResponseUtil.getCreatedId(response);
            resetUserCredential(userId, userDto.password());
            return userId;

        } catch (IllegalArgumentException e) {
            throw new KeycloakCreateUserException("Response from keycloak realm is null");
        } catch (KeycloakCreateUserException e) {
            throw new KeycloakCreateUserException(e.getMessage(), e.getCode());
        }
    }

    @Override
    public void resetUserCredential(String userId, String password) {
        UserResource userResource = realmResource.users().get(userId);

        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setValue(password);
        credentialRepresentation.setTemporary(Boolean.FALSE);
        userResource.resetPassword(credentialRepresentation);
    }
}
