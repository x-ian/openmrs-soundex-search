/**
 * Copyright (C) 2011 innoQ Deutschland GmbH
 *
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * @author Arnd Kleinbeck, innoQ Deutschland GmbH, http://www.innoq.com
 */
package org.openmrs.module.soundex.encoder;

/**
 *
 */
public class SoundexEncoder {

  public String encode(String str) {

    if (str == null || str.equals(""))
      return null;

    // Handle blanks
    str = str.toUpperCase();

    //  Drop all punctuation marks and numbers and spaces
    str = str.replaceAll("[^A-Z]", "");

    if (str == null || str.equals(""))
      return null;

    // Words starting with M or N or D followed by another consonant should drop the first letter
    str = str.replaceAll("^M([BDFGJKLMNPQRSTVXZ])", "$1");
    str = str.replaceAll("^N([BCDFGJKLMNPQRSTVXZ])", "$1");
    str = str.replaceAll("^D([BCDFGJKLMNPQRSTVXZ])", "$1");

    // silent R enhancement (Margret, Esnart)
    str = str.replaceAll("ARG", "AG");
    str = str.replaceAll("ART", "AT");
  
    //THY and CH as common phonemes enhancement
    str = str.replaceAll("(THY|CH|TCH)", "9");

    // Retain the first letter of the word
    String initial = String.valueOf(str.charAt(0));
    String tail = str.substring(1, str.length());

    // Initial vowel enhancement
    initial = initial.replaceAll("[AEI]", "E");

    initial = initial.replaceAll("[CK]", "K");
    initial = initial.replaceAll("[JY]", "Y");
    initial = initial.replaceAll("[VF]", "F");
    initial = initial.replaceAll("[LR]", "R");
    initial = initial.replaceAll("[MN]", "N");
    initial = initial.replaceAll("[SZ]", "Z");

    //W followed by a vowel should be treated as a consonant enhancement
    tail = tail.replaceAll("W[AEIOUHY]", "8");

    // Change letters from the following sets into the digit given
    tail = tail.replaceAll("[AEIOUHWY]", "0");
    tail = tail.replaceAll("[BFPV]", "1");
    tail = tail.replaceAll("[CGKQX]", "2");
    tail = tail.replaceAll("[DT]", "3");
    tail = tail.replaceAll("[LR]", "4");
    tail = tail.replaceAll("[MN]", "5");
    tail = tail.replaceAll("[SZ]", "6"); // Originally with CGKQX
    tail = tail.replaceAll("[J]", "7"); // Originally with CGKQX

    // Remove all pairs of digits which occur beside each other from the string
    tail = tail.replaceAll("1+", "1");
    tail = tail.replaceAll("2+", "2");
    tail = tail.replaceAll("3+", "3");
    tail = tail.replaceAll("4+", "4");
    tail = tail.replaceAll("5+", "5");
    tail = tail.replaceAll("6+", "6");
    tail = tail.replaceAll("7+", "7");
    tail = tail.replaceAll("8+", "8");
    tail = tail.replaceAll("9+", "9");

    // Remove all zeros from the string
    tail = tail.replaceAll("0", "");

    // Return only the first four positions
    if (tail.length() < 3)
      return initial + tail;
    else
      return initial + tail.substring(0, 3);
  }
}
