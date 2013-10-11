/**
 *
 */
package com.PMSystems.util;

import java.security.SecureRandom;
import java.security.NoSuchAlgorithmException;
import sun.misc.BASE64Encoder;

public final class TokenGenerator {

	public static String generateRandomToken(String prng, int length) throws NoSuchAlgorithmException {

		SecureRandom sr = SecureRandom.getInstance(prng);
		byte[] bytes = new byte[length];

		sr.nextBytes(bytes);
		return new BASE64Encoder().encode(bytes);
	}

        public static String generateCSRFToken(String prng, int length) throws NoSuchAlgorithmException {
                String s = generateRandomToken(prng, length);
                return normalize(s, prng, true);
        }

	public static String generateCSRFToken(int length) throws NoSuchAlgorithmException {
                String prng = "SHA1PRNG";
		String s = generateRandomToken(prng, length);
		return normalizeNew(s, prng, false);
	}

	/**
	 * Make sure the token only has letters and numbers
	 * @param s
	 * @return
	 */
	public static String normalize(String s, String prng) throws NoSuchAlgorithmException {
		return normalize(s, prng, false);
	}

        public static String normalize(String s) throws NoSuchAlgorithmException {
            String prng = "SHA1PRNG";
            return normalize(s, prng, false);
        }

	/**
	 * Make sure the token only has letters and numbers.
	 * If we catch a bad char, we optionally replace it
	 * with an 'a'
	 * @param s
	 * @param replace
	 * @return
	 */
        public static String normalize(String s, String prng, boolean replace) throws NoSuchAlgorithmException {

            StringBuffer sb = new StringBuffer();
            int len = (s == null ? -1 : s.length());

            for(int i=0; i<len; i++) {
                char c = s.charAt(i);
                if((c >= 'a' && c <='z') || (c >= 'A' && c <='Z') || (c >= '0' && c <= '9')) {
                    sb.append(c);

                } else if(replace) {
                    char rnd = generateRandomChar(prng);
                    sb.append(rnd);
                }
            }
            return sb.toString();
        }

        public static String normalizeNew(String s, String prng, boolean replace) throws NoSuchAlgorithmException {

            StringBuffer sb = new StringBuffer();
            int len = (s == null ? -1 : s.length());

            for(int i=0; i<len; i++) {
                char c = s.charAt(i);
                if((c >= 'a' && c <='z') || (c >= 'A' && c <='Z') || (c >= '0' && c <= '9')) {
                    sb.append(c);

                } else if(replace) {
                    sb.append('x');
                }
            }
            return sb.toString();
        }

        /**
         *
         * @param prng
         * @return
         * @throws NoSuchAlgorithmException
         */
	public static char generateRandomChar(String prng) throws NoSuchAlgorithmException {

            SecureRandom sr = SecureRandom.getInstance(prng);
            int i = sr.nextInt();
            char c = (char)((i%26)+97);
            return c;
        }
}
