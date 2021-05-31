package expertostech.jwt.auth0.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private final JWTAttributes jwtAttributes;

    public JWTAuthorizationFilter(AuthenticationManager authManager, JWTAttributes jwtAttributes) {
        super(authManager);
        this.jwtAttributes = jwtAttributes;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        String header = req.getHeader(jwtAttributes.getHeaderString());

        if (header == null || !header.startsWith(jwtAttributes.getTokenPrefix())) {
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    // Reads the JWT from the Authorization header, and then uses JWT to validate the token
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(jwtAttributes.getHeaderString());

        if (token == null) {
            return null;
        }

        // Parse the token.
        String username = JWT.require(Algorithm.HMAC512(jwtAttributes.getSecret().getBytes()))
                .build()
                .verify(token.replace(jwtAttributes.getTokenPrefix(), ""))
                .getSubject();

        if (username == null) {
            return null;
        }

        // new arraylist means authorities
        return new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
    }
}