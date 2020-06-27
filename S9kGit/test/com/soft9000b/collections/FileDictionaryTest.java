/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soft9000b.collections;

import com.soft9000b.tv.TagPair;
import java.util.Enumeration;
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

    String sFileName = "~test.txt";

    final FileDictionary zFile;

    public FileDictionaryTest() {
        zFile = new FileDictionary(sFileName);
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
