package expertostech.jwt.auth0.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import expertostech.jwt.auth0.data.UserDetailsData;
import expertostech.jwt.auth0.model.UserModel;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTAttributes jwtAttributes;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTAttributes jwtAttributes) {
        this.authenticationManager = authenticationManager;
        this.jwtAttributes = jwtAttributes;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        try {
            UserModel user = new ObjectMapper()
                    .readValue(req.getInputStream(), UserModel.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getLogin(),
                            user.getPassword(),
                            new ArrayList<>())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException {

        UserDetailsData user = (UserDetailsData) auth.getPrincipal();

        String token = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + jwtAttributes.getExpirationTime()))
                .sign(Algorithm.HMAC512(jwtAttributes.getSecret().getBytes()));

        String body = user.getUsername() + " " + token;
        res.getWriter().write(body);
        res.getWriter().flush();
    }

}
