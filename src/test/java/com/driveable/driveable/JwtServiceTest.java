package com.driveable.driveable;

import static org.junit.jupiter.api.Assertions.*;

import io.jsonwebtoken.ExpiredJwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import com.driveable.driveable.Services.JwtService;

import java.util.Collections;

public class JwtServiceTest {

  private JwtService jwtService;
  // A valid base64-encoded secret key. This decodes to "testsecretkeyforJWT".
  // Ensure that the decoded key length is valid for HS256 (typically at least 256
  // bits).
  private final String jwtSecretTest = "70ead43162b2b37b4644535d880f91bc688dbf9f230c698748e7717f87aa65cc1274b33d9485e5984a440c526a07c1b9802b78599942c7f37ac0ffcea60fd750a0fbf260876d1cc165a41eaa99cd8332fef212fdd059bfa035ca2385157087618d36d5d8d176ab99af2a807a954963693ec7926186c7e515700da255dd330824a1c471b0ce2038c30e989339a9008d29884bf9f9e7cc1f9a5704fb135a1a73e4d980f2cb6b67ed340f2fe6453f12a570470b08acfe1ba517b88bc0e5c81ba46751760ea1212bb863bd344b6b0659f4aa9f34754fb7cfe5aca18d37ab8207317f4b68f58da92467151c03f09834429478573806a643e58d7d123a294348b1b098";
  private final long jwtExpirationTest = 3600000;

  @BeforeEach
  public void setUp() {
    jwtService = new JwtService();

    ReflectionTestUtils.setField(jwtService, "secretKey", jwtSecretTest);
    ReflectionTestUtils.setField(jwtService, "jwtExpiration", jwtExpirationTest);
  }

  @Test
  public void testGenerateToken() {
    UserDetails userDetails = new User("testUser", "password", Collections.emptyList());
    String token = jwtService.generateToken(userDetails);

    assertNotNull(token, "The generated token should not be null");
    assertFalse(token.isEmpty(), "The generated token should not be empty");

    // Expecting JWTs to have three parts separated by dots.
    String[] parts = token.split("\\.");
    assertEquals(3, parts.length, "A valid JWT should have three parts");
  }

  @Test
  public void testExtractUsername() {
    UserDetails userDetails = new User("testUser", "password", Collections.emptyList());
    String token = jwtService.generateToken(userDetails);

    String extractedUsername = jwtService.extractUsername(token);
    assertEquals(userDetails.getUsername(), extractedUsername, "Extracted username should match the original");
  }

  @Test
  public void testIsTokenValid() {
    UserDetails userDetails = new User("testUser", "password", Collections.emptyList());
    String token = jwtService.generateToken(userDetails);

    // Validate token with correct username.
    boolean isValid = jwtService.isTokenValid(token, userDetails);
    assertTrue(isValid, "Token should be valid for the given user details");

    // Validate token with incorrect user details.
    UserDetails wrongUser = new User("wrongUser", "password", Collections.emptyList());
    boolean isValidForWrongUser = jwtService.isTokenValid(token, wrongUser);
    assertFalse(isValidForWrongUser, "Token should not be valid for a different user");
  }

  @Test
  public void testTokenExpiration() throws InterruptedException {
    // Set a short expiration time for testing token expiry (e.g., 1 second)
    ReflectionTestUtils.setField(jwtService, "jwtExpiration", 1000L);
    UserDetails userDetails = new User("testUser", "password", Collections.emptyList());
    String token = jwtService.generateToken(userDetails);

    // Sleep for 2 seconds so the token will expire.
    Thread.sleep(2000L);
    // Attempting to extract claims from an expired token should throw an exception.
    assertThrows(ExpiredJwtException.class, () -> {
      jwtService.extractUsername(token);
    }, "Extracting username from an expired token should throw ExpiredJwtException");
  }
}
