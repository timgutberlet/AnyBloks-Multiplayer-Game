package token;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import game.model.Debug;
import org.junit.jupiter.api.Test;

/**
 * Testing the generation and verification of tokens
 *
 * @author tbuscher
 */
public class TokenGenerationVerification {

  @Test
  public void tokenGenerationVerification() {
    String token = "";
    boolean passed = true;
    try {
      Algorithm algorithm = Algorithm.HMAC256("notblocks3");
      token = JWT.create()
          .withIssuer("server")
          .sign(algorithm);

    } catch (JWTCreationException exception) {
      passed = false;
    }

    Debug.printMessage(token);

    try {
      Algorithm algorithm = Algorithm.HMAC256("notblocks3");
      JWTVerifier verifier = JWT.require(algorithm)
          .withIssuer("server")
          .build();
      DecodedJWT jwt = verifier.verify(token);

    } catch (JWTVerificationException exception) {
      passed = false;
    }
    assertTrue(passed);

  }
}
