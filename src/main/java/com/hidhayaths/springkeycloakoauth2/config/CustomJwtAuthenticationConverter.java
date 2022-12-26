package com.hidhayaths.springkeycloakoauth2.config;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * to extract resource_access.spring-auth-client.roles to spring security authorities
 */

@RequiredArgsConstructor
public class CustomJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private final String resourceId;


    private Collection<? extends GrantedAuthority> extractResourceRoles(final Jwt jwt)
    {
        Map<String, Object> resourceAccess = jwt.getClaim("resource_access");

        if(Objects.isNull(resourceAccess)) return Collections.emptySet();

        Map<String, Object> resource = (Map<String, Object>) resourceAccess.get(resourceId);

        if(Objects.isNull(resource)) return Collections.emptySet();

        Collection<String> resourceRoles = (Collection<String>) resource.get("roles");

        if(Objects.isNull(resourceRoles)) return Collections.emptySet();

        return resourceRoles.stream()
                    .map(x -> new SimpleGrantedAuthority("ROLE_" + x))
                    .collect(Collectors.toSet());

    }


    @Override
    public AbstractAuthenticationToken convert(Jwt source) {
        //the below commented would concatenate authorities to existing scopes, commented to have only roles in authorities
        /*Collection<GrantedAuthority> authorities = Stream.concat(new JwtGrantedAuthoritiesConverter().convert(source)
                                .stream(),
                        extractResourceRoles(source).stream())*/
                //.collect(Collectors.toSet());
        return new JwtAuthenticationToken(source, extractResourceRoles(source), source.getClaimAsString("preferred_username"));

    }
}
