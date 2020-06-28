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
package com.soft9000b.collections;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Basic testing. A place for growth?
 * 
 * @author Randall
 */
public class ObjectEnumeratorTest {

    String[] vals1 = {
        "One", "Two", "Three"
    };

    public ObjectEnumeratorTest() {
    }

    /**
     * Test of hasMoreElements method, of class ObjectEnumerator.
     */
    @Test
    public void testHasMoreElements() {
        System.out.println("hasMoreElements");
        ObjectEnumerator iOne = new ObjectEnumerator(this.vals1);
        int total = 0;
        while(iOne.hasMoreElements()) {
            Object zNext = iOne.nextElement();
            assert(vals1[total].equals(zNext));
            total += 1;
        }
        assertEquals(total, vals1.length);

    }

    /**
     * Test of nextElement method, of class ObjectEnumerator.
     */
    @Test
    public void testNextElement() {
        this.testHasMoreElements();
    }

}
