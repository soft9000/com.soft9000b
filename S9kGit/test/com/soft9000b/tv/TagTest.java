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
package com.soft9000b.tv;

import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Randall Nagy
 */
public class TagTest {

    public TagTest() {
    }
    
    @Test
    public void testToFromString() {
        System.out.println("stringifications");
        final Tag iA = new Tag("123", "346");
        String str = iA.toString();
        TagPair iB = TagPair.FromString(str);
        assert (iA.getTag().equals(iB.getTag()));
        assert (iA.getValue().equals(iB.getValue()));
        TagPair iC = iB.fromString("789=012");
        assert(iC.getTag().equals("789"));
        assert(iC.getValue().equals("012"));
        assert(iC.assign(str) == true);
        assert (iA.getTag().equals(iC.getTag()));
        assert (iA.getValue().equals(iC.getValue()));
    }

    /**
     * Test of equals method, of class Tag.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Tag iA = new Tag();
        Tag iB = new Tag();
        assert (iA.equals(iB));
        assert (iA.getTag().equals(iB.getTag()));
        assert (iA.getValue().equals(iB.getValue()));

        Tag iC = new Tag("abe", "baker");
        assert (!iA.equals(iC));
        Tag iD = new Tag("abe", "baker");
        assert (iC.equals(iD));
        assert (iC.getTag().equals(iD.getTag()));
        assert (iC.getValue().equals(iD.getValue()));

        assert (!iA.getTag().equals(iD.getTag()));
        assert (!iA.getValue().equals(iD.getValue()));
    }

    /**
     * Test of hashCode method, of class Tag.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        Tag iA = new Tag();
        String nope = "";
        assert (iA.getTag().hashCode() == nope.hashCode());
        assert (iA.getValue().hashCode() == nope.hashCode());
        String aKey = "Rum";
        String sValue = "Bum";
        Tag iB = new Tag(aKey, sValue);
        assert (aKey.hashCode() == iB.getTag().hashCode());
        assert (iB.getValue().hashCode() == sValue.hashCode());
    }

    /**
     * Test of setTag method, of class Tag.
     */
    @Test
    public void testSetTag() {
        System.out.println("setTag");
        Tag instance = new Tag();

        {
            String sTag = "Zoom";
            instance.setTag(sTag);
            assert (instance.getTag().equals(sTag));
        }
        {
            String sTag2 = " ";
            instance.setTag(sTag2);
            assert (instance.getTag().equals(sTag2));
        }
        String sTag3 = " why the heck, not the best? ";
        instance.setTag(sTag3);
        assert (instance.getTag().equals(sTag3));
    }

    /**
     * Test of getTag method, of class Tag.
     */
    @Test
    public void testGetTag() {
        System.out.println("getTag");
        testSetTag();
    }

    /**
     * Test of setValue method, of class Tag.
     */
    @Test
    public void testSetValue() {
        System.out.println("setValue");
        Tag instance = new Tag();
        {
            String sValue = "The Value";
            instance.setValue(sValue);
            assert (instance.getValue().equals(sValue));
        }
        {
            String sValue2 = " The Value ";
            instance.setValue(sValue2);
            assert (instance.getValue().equals(sValue2));
        }
        {
            String sValue3 = " The\nValue\t ";
            instance.setValue(sValue3);
            assert (instance.getValue().equals(sValue3));
        }
    }

    /**
     * Test of getValue method, of class Tag.
     */
    @Test
    public void testGetValue() {
        System.out.println("getValue");
        testSetValue();
    }

    /**
     * Test of isNull method, of class Tag.
     */
    @Test
    public void testIsNull() {
        System.out.println("isNull");
        {
            Tag instance = new Tag();
            assert (instance.isNull());
        }
        {
            Tag instance = new Tag("foo", "");
            assert (!instance.isNull());
        }
        {
            Tag instance = new Tag("", "bar");
            assert (!instance.isNull());
        }
    }

    /**
     * Test of compareTo method, of class Tag.
     */
    @Test
    public void testCompareTo() {
        System.out.println("compareTo");
        {
            Tag iA = new Tag();
            Tag iB = new Tag();
            assertEquals(iA, iB);
        }
        {
            Tag iA = new Tag("one", "tweo");
            Tag iB = new Tag("one", "tweo");
            assertEquals(iA, iB);
        }
        {
            Tag iA = new Tag("one", "tweo");
            Tag iB = new Tag("tweo", "tweo");
            assertNotEquals(iA, iB);
        }
        {
            Tag iA = new Tag("one", "tweo");
            Tag iB = new Tag("tweo", "one");
            assertNotEquals(iA, iB);
        }
    }

    /**
     * Test of FindTagged method, of class Tag.
     */
    @Test
    public void testFindTagged_3args_1() {
        System.out.println("FindTagged - Heap");
        Tag[] tags = {
            new Tag("One", "1"),
            new Tag("one", "1"),
            new Tag("oNe", "1"),};
        {
            Tag[] result = Tag.FindTagged("one", tags, true);
            assert (result.length == 3);
        }
        {
            Tag[] result = Tag.FindTagged("one", tags, false);
            assert (result.length == 1);
        }
    }

    /**
     * Test of FindTagged method, of class Tag.
     */
    @Test
    public void testFindTagged_3args_2() {
        System.out.println("FindTagged - ArrayList");
        ArrayList<Tag> tags = new ArrayList();
        assert(tags.add(new Tag("One", "1")));
        assert(tags.add(new Tag("one", "1")));
        assert(tags.add(new Tag("oNe", "1")));
        {
            Tag[] result = Tag.FindTagged("one", tags, true);
            assert (result.length == 3);
        }
        {
            Tag[] result = Tag.FindTagged("one", tags, false);
            assert (result.length == 1);
        }
    }

}
