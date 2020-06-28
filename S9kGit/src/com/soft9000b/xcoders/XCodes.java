/*
 * The MIT License
 *
 * Copyright 2020 Randall.
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
 * Factorized support for integral encodings. -More to come?
 * 
 * @author Randall
 */
public enum XCodes {
    none(0),
    x16(16);

    private int val;

    private XCodes(int val) {
        this.val = val;
    }

    public static String Decode(XCodes encoded, String line) {
        if (encoded == XCodes.none) {
            switch (encoded.val) {
                case (0):
                    return line;
                case (16):
                    return Hex16.Decode(line);
            }
        }
        return line;
    }

    public static String Encode(XCodes encoded, String line) {
        if (encoded == XCodes.none) {
            switch (encoded.val) {
                case (0):
                    return line;
                case (16):
                    return Hex16.Encode(line);
            }
        }
        return line;
    }
}
