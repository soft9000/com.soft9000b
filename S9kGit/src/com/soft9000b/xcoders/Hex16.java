/*
 * The MIT License
 *
 * Copyright 2020 - 2024 Randall Nagy.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.soft9000b.xcoders;

/**
 * Classic Hex16 encoder / decoder.
 * 
 * @author Randall Nagy
 */
public class Hex16 {
/**
 * Convert a string to base-16, hexadecimal, format.
 * 
 * @param line Unencoded string.
 * @return 
 */
    public static String Encode(String line) {
        StringBuffer results = new StringBuffer();
        for (char ch : line.toCharArray()) {
            int val = (int) ch;
            String chars = String.format("%02x", val);
            results.append(chars);
        }
        return results.toString();
    }

    /**
     * Convert a string from base-16, hexadecimal, format.
     * @param line Encoded string.
     * @return 
     */
    public static String Decode(String line) {
        StringBuilder results = new StringBuilder();
        for (int ss = 0; ss < line.length() - 1; ss += 2) {
            String chars = line.substring(ss, (ss + 2));
            int val = Integer.parseInt(chars, 16);
            results.append((char) val);
        }
        return results.toString();
    }

}
