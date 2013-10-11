package com.PMSystems.util;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
import com.PMSystems.logger.*;
import java.security.MessageDigest;
import java.security.*;

public class KeyGenerator {


  private KeyGenerator() {
  }

  /**
   *
   * @param Key String
   * @param Code int
   * @return String
   */
  private static String placeDigitalCode(String Key, int Code){
    int location1 = (int) (Math.random() * 100) % 6;
    int location2 = (int) (Math.random() * 100) % 6;
    if (location1 == location2) {
      location2 += 1;
    }
    int tensLocation = location1 < location2 ? location1 : location2;
    int unitLocation = location1 < location2 ? location2 : location1;
    int tens = Code / 10;
    int units = Code % 10;
    int charLocation = 0;
    StringBuffer sb = new StringBuffer();

    for (int i = 0; i < 8; i++) {
      if (i == tensLocation) {
        sb.append(tens);
      } else if (i == unitLocation) {
        sb.append(units);
      } else {
        sb.append(Key.charAt(charLocation++));
      }
    }
    return  sb.toString();
  }

  /**
   *
   * @param userName String
   * @return int
   */
  private static int getDigitalCode(String userName) {
    int key = 0;
    for (int i = 0; i < userName.length(); i++) {
      int character = userName.charAt(i);
      key += process1(character, i);
//      System.out.println("Key :" + key);
    }
    key = process4(key);
//    System.out.println("Key :" + key);
    return key;
  }

  /**
   *
   * @param character int
   * @param place int
   * @return int
   */
  private static int process1(int character, int place) {
    int result = character << (place / 2);
    result = result & 0xFF;
    result = result + process2(character, place);
    return result;
  }

  /**
   *
   * @param character int
   * @param place int
   * @return int
   */
  private static int process2(int character, int place) {
    int result = character >> 0x2;
    result = result * (place + 102);
    result = result & 0xAA;
    result = result + process3(character, place);
    return result;
  }

  /**
   *
   * @param character int
   * @param place int
   * @return int
   */
  private static int process3(int character, int place) {
    int result = character << 0x8;
    result = result / (place + 1);
    result = result & 0x88;
    return result;
  }

  /**
   *
   * @param value int
   * @return int
   */
  private static int process4(int value) {
    int result = (value >> 3)%100;
    if(result<10){
      result *=0x9;
    }
    return result;
  }

  /**
   *
   * @param userId String
   * @return String
   */
  public static String getActivationCode(String userId) {
    try {

      int digitalCode = getDigitalCode(userId);
      String key = getUniqueID(userId);
      key = placeDigitalCode(key, digitalCode);
      return key.trim();

    } catch (Throwable th) {
        WebServerLogger.getLogger().log(th);
    }
    return null;
  }

  /**
   *
   * @param inputfeed String
   * @return String
   */
  private static String getUniqueID(String inputfeed) {

    String resultOutput = "";
    try {

      MessageDigest sha = MessageDigest.getInstance("SHA-1");
      byte[] result = sha.digest(inputfeed.getBytes());
      resultOutput = hexEncode(result);

    } catch (NoSuchAlgorithmException nsae) {
      nsae.printStackTrace();
    }
    return resultOutput;
  }

  /**
   *
   * @param aInput byte[]
   * @return String
   */
  private static String hexEncode(byte[] aInput) {
    StringBuffer result = new StringBuffer();
    char[] digits = {
                    'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
                    'm', 'n',
                    'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                    'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
                    'M', 'N',
                    'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    int idx;
    for (idx = 0; idx < aInput.length; ++idx) {
      byte b = aInput[idx];
      int digitLocation = (b & 0xCF) >> 2;
      //      System.out.print(digitLocation+", ");
      result.append(digits[digitLocation]);
      if (idx == 3) {
        idx = 17;
      }
    }
    //     System.out.println();
    return result.toString();
  }
}
