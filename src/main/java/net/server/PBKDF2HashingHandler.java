package net.server;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 * Class to handle the hashing of passwords.
 *
 * @author tbuscher
 */

public class PBKDF2HashingHandler {

  /**
   * size of salt in bytes.
   */
  private static final int saltSize = 16;
  /**
   * size of hashed data.
   */
  private static final int hashBytes = 32;
  /**
   * Number of runs of algorithm. Determines strength.
   */
  private static final int iterations = 131072;
  /**
   * Base64 base64encoder. https://levelup.gitconnected.com/what-is-base64-encoding-4b5ed1eb58a4.
   */
  private static final Encoder base64encoder = Base64.getEncoder();

  /**
   * Base64 decoder. https://levelup.gitconnected.com/what-is-base64-encoding-4b5ed1eb58a4.
   */
  private static final Decoder decoder = Base64.getDecoder();

  /**
   * secure version of random out of java.util.random
   */
  private static final SecureRandom random = new SecureRandom();


  /**
   * Hashes Plaintext using PBKDF2.
   *
   * @param passwordPlain plaintext password between 5 and 128 characters.
   * @return base64 encoded hashed password
   * @throws Exception                in case hashing or encoding fails
   * @throws IllegalArgumentException in case plaintext password is under 5 or over 128 characters.
   */
  public static String hashPassword(String passwordPlain) throws Exception {
    //Check length
    if ((passwordPlain.length() < 5) || (passwordPlain.length() > 128)) {
      throw new IllegalArgumentException(
          "Illegal Password length! Choose a password between 5 and 128 characters.");
    }

    try {
      // Building a key
      SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
      byte[] salt = new byte[saltSize];
      random.nextBytes(salt);
      KeySpec keySpec = new PBEKeySpec(passwordPlain.toCharArray(), salt, iterations, hashBytes);

      // Hashing + encoding
      byte[] passwordHash = secretKeyFactory.generateSecret(keySpec).getEncoded();
      return iterations + "$" + new String(base64encoder.encode(salt), StandardCharsets.UTF_8)
          + "$" + new String(base64encoder.encode(passwordHash), StandardCharsets.UTF_8);

    } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
      e.printStackTrace();
    }
    throw new Exception("The password could not be hashed & encoded");
  }

  /**
   * Checks a password.
   *
   * @param passwordPlain plainText password.
   * @param passwordHash  password already hashed.
   */

  public static boolean checkPassword(String passwordPlain, String passwordHash) {
    //Decompose the String passwordHash into the parts, as constructed in return of hashPassword()
    int numberOfIterations = Integer.parseInt(
        passwordHash.split("\\$")[0]); //Escaping $ as it is a meta character
    byte[] salt = decoder.decode(
        passwordHash.split("\\$")[1]); //Escaping $ as it is a meta character
    byte[] hash = decoder.decode(
        passwordHash.split("\\$")[2]); //Escaping $ as it is a meta character
    PBEKeySpec pbeKeySpec = new PBEKeySpec(passwordPlain.toCharArray(), salt, numberOfIterations,
        hash.length * 8);
    boolean result = false;
    try {
      SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
      byte[] compareToHash = secretKeyFactory.generateSecret(pbeKeySpec).getEncoded();

      result = MessageDigest.isEqual(compareToHash, hash);

    } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
      e.printStackTrace();
      return false;
    }

    // Letting thread sleep randomly to prevent success of any attacks using time measurements.
    try {
      Thread.sleep(random.nextInt(25, 150));
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    return result;


  }

  /**
   * Checks a password.
   *
   * @param passwordHashA a hashed password.
   * @param passwordHashB another passwordHash.
   */

  public static boolean checkPasswordHashes(String passwordHashA, String passwordHashB) {
    //Decompose the String passwordHash into the parts, as constructed in return of hashPassword()
    Integer numberOfIterationsA = Integer.parseInt(
        passwordHashA.split("\\$")[0]); //Escaping $ as it is a meta character
    byte[] saltA = decoder.decode(
        passwordHashA.split("\\$")[1]); //Escaping $ as it is a meta character
    byte[] hashA = decoder.decode(
        passwordHashA.split("\\$")[2]); //Escaping $ as it is a meta character

    Integer numberOfIterationsB = Integer.parseInt(
        passwordHashB.split("\\$")[0]); //Escaping $ as it is a meta character
    byte[] saltB = decoder.decode(
        passwordHashB.split("\\$")[1]); //Escaping $ as it is a meta character
    byte[] hashB = decoder.decode(
        passwordHashB.split("\\$")[2]); //Escaping $ as it is a meta character

    boolean result = false;

    result = MessageDigest.isEqual(hashA, hashB);

    // Letting thread sleep randomly to prevent success of any attacks using time measurements.
    try {
      Thread.sleep(random.nextInt(25, 150));
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    return result;


  }


}

