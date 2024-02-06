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
package com.soft9000b.ini;

import com.soft9000b.tv.TagPair;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * A collection of TagValue pairs associated with an INI File's "["Group
 * Name"]".
 *
 * @author Randall
 */
public class IniSection implements Comparable {

    private static final String DOS_NEWLINE = "\r\n";
    private static final String COMMENT = "][[!]][";
    private static final String SECTIONID = "[]]![[]";

    /**
     * Pull the KEY / Tag values.
     *
     * @param section A list of TagPair.
     * @return An ArrayList. Always.
     */
    public static ArrayList<String> GetKeys(final ArrayList<TagPair> section) {
        ArrayList<String> results = new ArrayList<>();
        if (section != null) {
            for (TagPair tag : section) {
                results.add(tag.getTag());
            }
        }
        return results;
    }

    private static TagPair MkComment(String sline) {
        return new TagPair(IniSection.COMMENT, sline);
    }

    /**
     * Create a "paralegal" section name for a string.
     *
     * @param groupName
     * @return Always returns a string.
     */
    public static String MkSectionName(String groupName) {
        groupName = groupName.trim();
        if (!groupName.startsWith("[")) {
            groupName = "[" + groupName;
        }
        if (!groupName.endsWith("]")) {
            groupName = groupName + "]";
        }
        return groupName;
    }

    final ArrayList<TagPair> section;

    /**
     * The default IniRow has a null section name.
     */
    public IniSection() {
        section = new ArrayList<>();
    }

    /**
     * Cobble together an INI Section. Newline is at the end. Empty sections are
     * okay.
     *
     * @return The representation string, suitable for use in an INI File.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (this.isEmpty() || this.isNull()) {
            return sb.toString();
        }

        for (TagPair tv : this.section) {
            if (tv.getTag().equals(IniSection.COMMENT)) {
                sb.append(tv.getValue());
                sb.append(DOS_NEWLINE); // DOS convention.
                continue;
            }
            if (tv.getTag().equals(IniSection.SECTIONID)) {
                sb.append(tv.getValue());
                sb.append(DOS_NEWLINE); // DOS convention.
                continue;
            }
            sb.append(Enquote(tv));
            sb.append(DOS_NEWLINE); // DOS convention.
        }
        sb.append(DOS_NEWLINE); // DOS convention.
        return sb.toString();
    }

    /**
     * Return the assigned section name.
     *
     * @return The default section name can be null!
     */
    public String getSectionName() {
        TagPair ref = this.getTagRef(SECTIONID);
        if (ref == null) {
            return null;
        }
        return ref.getValue();
    }

    /**
     * See if the section name is null.
     *
     * @return true if section name is dangerous / null.
     */
    public boolean isNull() {
        return this.getTagRef(SECTIONID) == null;
    }

    /**
     * See if the section has any TagValue items.
     *
     * @return False if nothing is in the section.
     */
    public boolean isEmpty() {
        return section.isEmpty();
    }

    /**
     * Assign a section / INI Section Name to any TagValue pairs. Spaces will be
     * trimmed, and "[]" will be added to the section name as required.
     *
     * @param groupName A null section name translates to "[default]".
     */
    public void setSectionName(String groupName) {
        if (groupName == null) {
            groupName = "[default]";
        } else {
            groupName = IniSection.MkSectionName(groupName);
        }
        TagPair ref = this.getTagRef(SECTIONID);
        if (ref != null) {
            ref.setValue(groupName);
        } else {
            this.section.add(new TagPair(SECTIONID, groupName));
        }
    }

    /**
     * Add a TagValue item to the section / section name.
     *
     * @param pair
     * @return False when the provided TagValue item is null.
     */
    public boolean add(TagPair pair) {
        if (pair != null && !pair.isNull()) {
            return section.add(pair);
        }
        return false;
    }

    /**
     * A chance to combine, as well as to merge, TagValues regardless of their
     * section names.
     *
     * @param source
     * @return False if tag / KEY names.
     */
    public boolean merge(final IniSection source) {
        ArrayList<TagPair> cooper = (ArrayList<TagPair>) source.section.clone();
        ArrayList<String> keys = source.getKeys();
        for (String key : keys) {
            TagPair self = this.getTagRef(key);
            if (self != null) {
                TagPair other = source.getTagRef(key);
                if (other == null) {
                    return false; // yikes.
                }
                self.setValue(other.getValue());
                source.remove(key);
            }
        }

        return this.section.addAll(source.section);
    }

    /**
     * Get a REFERENCE to any TagPair. Can be used to update the section.
     *
     * @param tag Section KEY / Tag
     * @return Caveat: null when none found.
     */
    public TagPair getTagRef(String tag) {
        for (TagPair sec : section) {
            if (sec.getTag().equals(tag)) {
                return sec;
            }
        }
        return null;
    }

    /**
     * Find the place to begin an insertion (etc.)
     *
     * @param tag KEY
     * @return
     */
    private int getTagPos(String tag) {
        for (int ss = 0; ss < section.size(); ss++) {
            TagPair sec = section.get(ss);
            if (sec.getTag().equals(tag)) {
                return ss;
            }
        }
        return -1;
    }

    /**
     * Get a COPY of any TagPair.
     *
     * @param tag Section KEY / Tag
     * @return Caveat: null when none found.
     */
    public TagPair getTagCopy(String tag) {
        for (TagPair sec : section) {
            if (sec.getTag().equals(tag)) {
                return new TagPair(sec);
            }
        }
        return null;
    }

    /**
     * See if a KEY / Tag has already been defined.
     *
     * @param tag
     * @return False when no KEY / tag found.
     */
    public boolean hasTag(String tag) {
        for (TagPair sec : section) {
            if (sec.getTag().equals(tag)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get the keys. Only.
     *
     * @return Always an ArrayList<String>. Simply check if it is empty.
     */
    public ArrayList<String> getKeys() {
        return IniSection.GetKeys(section);
    }

    /**
     * Remove a KEY / Tag from a section. Key need not exist.
     *
     * @param key
     */
    public void remove(String key) {
        if (key == null) {
            return;
        }
        TagPair found = null;
        for (TagPair ref : this.section) {
            if (ref.getTag().equals(key)) {
                found = ref;
                break;
            }
        }
        if (found != null) {
            this.section.remove(found);
        }
    }

    /**
     * Add a comment to the section.
     *
     * @param sline
     */
    public void addComment(String str) {
        if (str == null) {
            return;
        }
        if (!str.startsWith(";")) {
            str = "; " + str;
        }
        this.section.add(IniSection.MkComment(str));
    }

    public boolean add(String key, String value) {
        if (key == null | key.isEmpty()) {
            return false;
        }
        if (value == null | value.isEmpty()) {
            return false;
        }
        return this.add(new TagPair(key, value));
    }

    @Override
    public int compareTo(Object obj) {
        if (obj == null) {
            return -1;
        }
        int count = 0;
        if (obj instanceof IniSection) {
            IniSection ref = (IniSection) obj;
            for (TagPair tv : this.section) {
                if (tv.equals(tv)) {
                    count++;
                }
            }
        }
        return this.section.size() - count;
    }

    /**
     * Super-comments preceed SECTION names.
     *
     * @param str
     * @return
     */
    public boolean addSuperComment(String str) {
        if (str == null) {
            // Not going to create a default section - nope.
            return false;
        }
        if (!str.startsWith(";")) {
            str = "; " + str;
        }
        int pos = getTagPos(SECTIONID);
        if (pos == -1) {
            this.addComment(str);
        } else {
            this.section.add(pos, IniSection.MkComment(str));
        }
        return true;
    }

    /**
     * Classic INI l-values are delimited by double quotes. Use Dequote(below)
     * to revert.
     *
     * @param tv
     * @return INI encoded string.
     */
    static String Enquote(final TagPair tv) {
        StringBuilder sb = new StringBuilder();
        sb.append(tv.getTag());
        sb.append(TagPair.DEFAULT_SEP);
        String value = tv.getValue();
        if (!value.startsWith("\"")) {
            sb.append('"');
        }
        sb.append(value);
        if (!value.endsWith("\"")) {
            sb.append('"');
        }
        return sb.toString();
    }

    /**
     * Parse + remove quotations from an INI value line.
     *
     * @param str Enquote(above) result.
     * @return
     */
    static TagPair Dequote(String str) {
        TagPair result = TagPair.FromString(str);
        String value = result.getValue().trim();
        if (value.startsWith("\"")) {
            value = value.substring(1);
        }
        if (value.endsWith("\"")) {
            value = value.substring(0, value.length() - 1);
        }
        result.setValue(value);
        return result;
    }

    /**
     * Return the value (rvalue) for a KEY / Tag in this SECTION. Note the keys
     * are case insensitive.
     *
     * @param key The lvalue. Because an empty payload is possible, this
     * function also returns null if the KEY / Tag is not found.
     * @return Null if not found.
     */
    public String getValue(String _key) {
        if (_key == null || _key.isEmpty()) {
            return null;
        }
        String key = _key.toLowerCase();
        for (TagPair tv : this.section) {
            if (tv.getTag().toLowerCase().equals(key)) {
                return tv.getValue();
            }
        }
        return null;
    }

    /**
     * Locate 0:* keyed pair(s). Note the keys are case insensitive.
     *
     * @param keyMulti The key to match.
     * @return Null if none found.
     */
    public TagPair[] getAll(String keyMulti) {
        if (keyMulti == null || keyMulti.isEmpty()) {
            return null;
        }
        String key = keyMulti.toLowerCase();
        ArrayList<TagPair> set = new ArrayList<>();
        for (TagPair tv : this.section) {
            if (tv.getTag().toLowerCase().equals(key)) {
                set.add(tv);
            }
        }
        TagPair[] results = new TagPair[0];
        return set.toArray(results);
    }

}
