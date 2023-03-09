package org.ngsoft.TaskAppAPI.services;

import org.ngsoft.TaskAppAPI.entities.TaskAppAPIUserDetails;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.stereotype.Service;

@Service
public class OidcUserInfoService {
    private final TaskAppAPIUserDetailsService userDetailsService;

    public OidcUserInfoService(TaskAppAPIUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public OidcUserInfo loadUser(String username) {
        TaskAppAPIUserDetails user = (TaskAppAPIUserDetails) userDetailsService.loadUserByUsername(username);
        return OidcUserInfo.builder()
                .subject(user.getId().toString())
                .name(user.getUsername())
                .name(user.getName())
                .email(user.getEmail())
                .claim("roles", user.getAuthorities())
                .build();
    }
}
