import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.security.interfaces.RSAPublicKey;

public class CustomAuthenticationManager implements AuthenticationManager {
    public static final String ROLE_ADMIN = "ADMIN";

    @Value("${spring.security.oauth2.resourceserver.jwt.key-value}")
    private RSAPublicKey secretKey;

    @Value("${spring.security.jwt.token.prefix}")
    private String tokenPrefix;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = authentication.getName();

        if (token == null || token.isEmpty()) {
            throw new BadCredentialsException("JWT not found");
        }

        try {
            String user = JWT.require(Algorithm.HMAC512(secretKey.getEncoded())).build()
                    .verify(token.replace(tokenPrefix, "")).getSubject();
            Jwt jwt = Jwt.withTokenValue(token).header(PublicClaims.ALGORITHM, "HS512")
                    .claim(PublicClaims.SUBJECT, user).build();

            UserDetails users = build(user);
            return new JwtAuthenticationToken(jwt, users.getAuthorities());
        } catch (Exception e) {
            //log.error("BadCredentialsException {}", e.getMessage());
            throw new BadCredentialsException(e.getMessage());
        }

    }


    private UserDetails build(String user) {
        User.UserBuilder builder = null;
        builder = User.withUsername(user);
        builder.password(user);
        builder.roles(ROLE_ADMIN);
        return builder.build();
    }


}