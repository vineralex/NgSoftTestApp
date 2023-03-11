package org.ngsoft.TaskAppAPI.filters;


import com.nimbusds.jose.shaded.gson.internal.LinkedTreeMap;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TaskAppFilter implements Filter {

    private boolean isAdminOnly;

    public TaskAppFilter( boolean isAdminOnly){
        this.isAdminOnly = isAdminOnly;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            Jwt jwt = ((JwtAuthenticationToken) authentication).getToken();
            var rolesObj = jwt.getClaims().get("roles");
            ArrayList<LinkedTreeMap<String, String>> roles = new ArrayList<>();
            if (rolesObj instanceof ArrayList) {
                roles = (ArrayList<LinkedTreeMap<String, String>>) rolesObj;
                List<SimpleGrantedAuthority> authorityList = roles.stream()
                        .map(roleMap -> new SimpleGrantedAuthority(roleMap.get("role")))
                        .collect(Collectors.toList());
                boolean authorityFound = false;
                if (isAdminOnly) {
                    authorityFound = authorityList.stream().anyMatch(a -> a.getAuthority().equals("ADMIN"));
                }
                else {
                    authorityFound = authorityList.stream().anyMatch(a ->
                            a.getAuthority().equals("ADMIN") || a.getAuthority().equals("USER"));
                }
                if (!authorityFound) {
                    HttpServletResponse httpResponse = (HttpServletResponse) response;
                    httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN, "Access denied");
                    return;
                }
            }
        }
        else {
            // handle unauthenticated requests here
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication required");
            return;
        }

        chain.doFilter(request, response);
    }
}
