/*
 * .@NagyLicense@.
 *  - r.a.nagy@gmail.com
 */
package com.soft9000b.tv;

import java.util.ArrayList;

/**
 *
 * @author R.A. Nagy
 */
public class Tag implements java.io.Serializable, java.lang.Comparable {

    /**
     * The data. Can be anything. Use convenience functions for better
     * management.
     */
    private String sTag = "";    // (LVALUE)
    /**
     * The payload. Can be anything. Use convenience functions for better
     * management.
     */
    private String sValue = "";  // (RVALUE)

    /**
     * Null constructor.
     */
    public Tag() {
    }

    /**
     * Copy constructor.
     *
     * @param ref Another Tag()
     */
    public Tag(final Tag ref) {
        if (ref != null) {
            sTag = ref.sTag;
            sValue = ref.sValue;
        }
    }

    /**
     * Populated construction. Will use empty-string on any null parameters.
     *
     * @param sTag The tag
     * @param sValue The value.
     */
    public Tag(String sTag, String sValue) {
        if (sTag != null) {
            this.sTag = sTag;
        }
        if (sValue != null) {
            this.sValue = sValue;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (super.equals(obj)) {
            return true;
        }
        if (obj instanceof Tag) {
            Tag ref = (Tag) obj;
            return (this.sTag.equals(ref.sTag) && this.sValue.equals(ref.sValue));
        }
        return false;
    }

    @Override
    public int hashCode() {
        return (sTag.hashCode() * 1000) + sValue.hashCode();
    }

    /**
     * Convenience function: Assign the empty string on null. Will also always
     * trim the assignment.
     *
     * @param sTag The string to assign.
     */
    public void setTag(String sTag) {
        if (sTag == null) {
            sTag = "";
        }
        this.sTag = new String(sTag);
    }

    /**
     * Convenience function: Will not return a null.
     *
     * @return Representational tag.
     */
    public String getTag() {
        if (sTag == null) {
            return "";
        }
        return sTag;
    }

    /**
     * Convenience function: Assign the empty string on null. <b>NO TRIM</b>
     *
     * @param sValue The value to assign.
     */
    public void setValue(String sValue) {
        if (sValue == null) {
            sValue = "";
        }
        this.sValue = new String(sValue);
    }

    /**
     * Convenience function: Will not return a null.
     *
     * @return Representational value
     */
    public String getValue() {
        if (sValue == null) {
            return "";
        }
        return sValue;
    }

    /**
     * 2020 DELTA: Either the tag, or the value, can be null - but not both.
     *
     * @return False if the object is usable (!null)
     */
    public boolean isNull() {
        if (sTag == null || sTag.length() == 0) {
            if (sValue == null || sValue.length() == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Comparison of Tag()
     *
     * @param obj A Tag.
     * @return As implied by Comparable.
     */
    public int compareTo(Object obj) {
        if (obj == null) {
            if (isNull()) {
                return 1;
            }
            return 0;
        }
        if (this == obj) {
            return 0;
        }
        if (isNull()) {
            return -1;
        }
        Tag ref = (Tag) obj;
        if (ref != null) {
            int iRes = this.sTag.compareTo(ref.sTag);
            if (iRes == 0) {
                return this.sValue.compareTo(ref.sValue);
            }
            return iRes;
        }
        return -1;
    }

    /**
     * Handy little function to search an array of Tag(), looking for all data
     * tagged with a given token.
     *
     * @param sTag The name of the tag to look for.
     * @param tags The array to search.
     * @param bIgnoreCase True if you want case-insensitivity, else false.
     * @return The array of data found that matches the tag. The array length is
     * zero on error / not found.
     */
    public static Tag[] FindTagged(String sTag, final Tag[] tags, boolean bIgnoreCase) {
        Tag[] result = new Tag[0];
        if (sTag == null || tags == null) {
            return result;
        }
        if (bIgnoreCase) {
            sTag = sTag.toLowerCase();
        }
        ArrayList<Tag> list = new ArrayList<Tag>();
        for (int ss = 0; ss < tags.length; ss++) {
            Tag ref = tags[ss];
            if (ref != null) {
                String strB = ref.sTag;
                if (bIgnoreCase) {
                    strB = ref.sTag.toLowerCase();
                }
                if (strB.equals(sTag)) {
                    list.add(ref);
                }
            }
        }
        result = new Tag[list.size()];
        list.toArray(result);
        return result;
    }

    /**
     * Handy little function to search an ArrayList of Tag(), looking for all
     * data tagged with a given token.
     *
     * @param sTag The name of the tag to look for.
     * @param list The ArrayList to search.
     * @param bIgnoreCase True if you want case-insensitivity, else false.
     * @return The array of data found that matches the tag. The array length is
     * zero on error / not found.
     */
    public static Tag[] FindTagged(String sTag, ArrayList<Tag> list, boolean bIgnoreCase) {
        Tag[] thunk = new Tag[list.size()];
        list.toArray(thunk);
        return FindTagged(sTag, thunk, bIgnoreCase);
    }
}
