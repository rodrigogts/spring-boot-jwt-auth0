package expertostech.password.encrypt.security;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class JWTAttributes {

  private final String headerString = "Authorization";

  private final String tokenPrefix = "Bearer ";

  @Value("${token.secret}")
  private String secret;

  @Value("${token.expiration.time}")
  private Long expirationTime;

}
