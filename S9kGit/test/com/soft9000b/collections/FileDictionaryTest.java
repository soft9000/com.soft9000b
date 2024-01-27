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
package com.soft9000b.collections;

import com.soft9000b.tv.TagPair;
import java.io.File;
import java.util.Enumeration;
import org.junit.After;
import org.junit.AfterClass;
// import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Randall
 */
public class FileDictionaryTest {

    final String[] test_keys = {
        "One", "Two", "Three"
    };

    final String[] test_values = {
        "Four", "Five", "Six"
    };

    static String TEST_FILE = "~test.txt";

    final FileDictionary zFile;

    public FileDictionaryTest() {
        zFile = new FileDictionary(TEST_FILE);
    }

    @BeforeClass
    public static void setUpClass() {
        {
            File file = new File(FileDictionary.DEFAULT_FILE);
            if (file.exists()) {
                file.delete();
            }
        }
        {
            File file = new File(TEST_FILE);
            if (file.exists()) {
                file.delete();
            }

        }
    }

    @AfterClass
    public static void tearDownClass() {
        {
            File file = new File(FileDictionary.DEFAULT_FILE);
            if (file.exists()) {
                file.delete();
            }
        }
        {
            File file = new File(TEST_FILE);
            if (file.exists()) {
                file.delete();
            }

        }
    }

/*
    @Before
    public void setUp() {

    }
*/
    @After
    public void tearDown() {
        dePopulate();
    }

    /**
     * Test of load method, of class FileDictionary.
     */
    @Test
    public void testLoad_String() {
        System.out.println("load");
        testSave();
        dePopulate();
        assert (this.zFile.load());
        int tally = this.zFile.size();
        rePopulate();
        assert (tally == this.zFile.size());
    }

    /**
     * Test of save method, of class FileDictionary.
     */
    @Test
    public void testSave() {
        System.out.println("save");
        rePopulate();
        assert (this.zFile.save());
    }

    /**
     * Test of Load method, of class FileDictionary.
     */
    @Test
    public void testLoad_0args() {
        dePopulate();
        rePopulate();
        assert (this.zFile.save(FileDictionary.DEFAULT_FILE));
        FileDictionary zTest = FileDictionary.Load();
        assert (zTest.isEmpty() == false);
        dePopulate();
    }

    /**
     * Test of size method, of class FileDictionary.
     */
    @Test
    public void testSize() {
        System.out.println("size");
        dePopulate();
        assert (zFile.size() == 0);
        rePopulate();
        assert (zFile.size() != 0);
        dePopulate();
        assert (zFile.size() == 0);
    }

    /**
     * Test of isEmpty method, of class FileDictionary.
     */
    @Test
    public void testIsEmpty() {
        System.out.println("IsEmpty");
        dePopulate();
        assert (this.zFile.isEmpty());
        rePopulate();
        assert (!this.zFile.isEmpty());
        this.zFile.clear();
        assert (this.zFile.isEmpty());
    }

    /**
     * Test of keys method, of class FileDictionary.
     */
    @Test
    public void testKeys() {
        System.out.println("keys");
        dePopulate();
        rePopulate();
        Enumeration vals = this.zFile.keys();
        int count = 0;
        while (vals.hasMoreElements()) {
            vals.nextElement();
            count++;
        }
        assert (count != 0); // tested elsewhere, as well.
        dePopulate();
    }

    /**
     * Test of elements method, of class FileDictionary.
     */
    @Test
    public void testElements() {
        System.out.println("elements");
        dePopulate();
        rePopulate();
        Enumeration vals = this.zFile.elements();
        int count = 0;
        while (vals.hasMoreElements()) {
            vals.nextElement();
            count++;
        }
        assert (count == this.zFile.size()); // tested elsewhere, as well.
        dePopulate();
    }

    /**
     * Test of get method, of class FileDictionary.
     */
    @Test
    public void testGet() {
        System.out.println("get");
        dePopulate();
        rePopulate();
        int count = 0;
        for (int ss = 0; ss < this.zFile.size(); ss++) {
            String zKey = this.test_keys[ss];
            TagPair val = (TagPair) this.zFile.get(zKey);
            assert (val != null);
            assert (val.getTag().equals(zKey));
            String zValue = this.test_values[ss];
            assert (val.getValue().equals(zValue));
            count += 1;
        }
        assert (count == this.zFile.size()); // tested elsewhere, as well.
        dePopulate();

    }

    /**
     * Test of put method, of class FileDictionary.
     */
    @Test
    public void testPut() {
        System.out.println("put");
        dePopulate();
        rePopulate();
        FileDictionary zTest = new FileDictionary();
        Enumeration vals = this.zFile.elements();
        while (vals.hasMoreElements()) {
            TagPair zNext = (TagPair) vals.nextElement();
            assert (zNext != null);
            TagPair zObj = (TagPair) zTest.put(zNext.getTag(), zNext.getValue());
            assert (zObj != null);
            assert (zNext.getTag().equals(zObj.getTag()));
            assert (zNext.getValue().equals(zObj.getValue()));
        }
        assert (zTest.size() == this.zFile.size());

    }

    /**
     * Test of remove method, of class FileDictionary.
     */
    @Test
    public void testRemove() {
        System.out.println("remove");
        dePopulate();
        rePopulate();
        Enumeration vals = this.zFile.elements();
        while (vals.hasMoreElements()) {
            TagPair zNext = (TagPair) vals.nextElement();
            assert (zNext != null);
            TagPair zObj = (TagPair) this.zFile.remove(zNext);
            assert (zObj != null);
            assert (zNext.getTag().equals(zObj.getTag()));
            assert (zNext.getValue().equals(zObj.getValue()));
        }
    }

    /**
     * Test of remove method, of class FileDictionary.
     */
    @Test
    public void testRemove_int() {
        System.out.println("remove");
        dePopulate();
        rePopulate();
        for (int ss = 0; ss < this.zFile.size(); ss++) {
            TagPair zObj1 = this.zFile.get(ss);
            TagPair zObj2 = this.zFile.remove(ss);
        }

    }

    public void rePopulate() {
        dePopulate();
        this.zFile.clear();
        for (int ss = 0; ss < this.test_keys.length; ss++) {
            Object obj = this.zFile.put(this.test_keys[ss], this.test_values[ss]);
            assert (obj != null);
            assert (((TagPair) obj) != null);
        }

    }

    private void dePopulate() {
        this.zFile.clear();
    }

}
