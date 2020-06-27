/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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

    @BeforeClass
    public static void setUpClass() {

    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {

    }

    @After
    public void tearDown() {
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
