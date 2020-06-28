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

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Randall
 */
public class HexTest {

    String test_line = " The eniter \n \tpoint\r\n is to be 123=! resilient ... ?";

    public HexTest() {
    }

    /**
     * Test of Encode method, of class Hex16.
     */
    @Test
    public void testEncode() {
        System.out.println("Encode");
        {
            String eresult = Hex16.Encode(test_line);
            String dresult = Hex16.Decode(eresult);
            assertEquals(dresult, test_line);
        }

    }

    /**
     * Test of decode method, of class Hex16.
     */
    @Test
    public void testDecode() {
        System.out.println("Decode");
        // testEncode(); // Lo mismo ...
    }

}
