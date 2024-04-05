/*
 * The MIT License
 *
 * Copyright 2024 Randall Nagy.
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

import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author Randall Nagy
 */
public class IniFile implements Comparable {

    /**
     * Another common use case - convenience:
     *
     * @param tfile
     * @return Warning: null if the file is not found.
     */
    public static IniFile Read(File tfile) {
        if (tfile.exists() == false) {
            return null;
        }
        IniFile ini = new IniFile(tfile);
        return ini.read();
    }

    public ArrayList<IniSection> groups = new ArrayList<>();
    protected File zfile = null;

    /**
     * A file for either reading, or writing.
     *
     * @param file File may, or may'nt, exist.
     */
    public IniFile(File file) {
        this.zfile = file;
    }

    /**
     * If a file has not been assigned and there is no content, then the
     * instance is null.
     *
     * @return
     */
    public boolean isNull() {
        if (zfile == null) {
            return isEmpty();
        }
        return false;
    }

    /**
     * If there are no sections, then the file is empty.
     *
     * @return
     */
    public boolean isEmpty() {
        return groups.isEmpty();
    }

    /**
     * Add an IniSection to an INI File. Only new IniSection will be added to
     * the file.
     *
     * @param agroup
     * @return True only when a unique section name was added.
     */
    public boolean add(IniSection agroup) {
        if (agroup == null || agroup.isNull() || agroup.isEmpty()) {
            return false;
        }

        for (IniSection group : groups) {
            if (group.getSectionName().equals(agroup.getSectionName())) {
                return false;
            }
        }
        return groups.add(agroup);
    }

    /**
     * Create a file-worthy rendition.
     *
     * @return Representational / parsable file content.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (IniSection sec : this.groups) {
            sb.append(sec.toString());
        }
        return sb.toString();
    }

    /**
     * Create, name, and add a section to this file.
     *
     * @param myTest
     * @return A section definition.
     */
    public IniSection addSection(String myTest) {
        IniSection result = new IniSection();
        result.setSectionName(myTest);
        groups.add(result);
        return result;
    }

    /**
     * Overwrite or create the ini file,
     *
     * @return False if unable to write the ini file.
     */
    public boolean save() {
        return IniFileWriter.Overwrite(this);
    }

    /**
     * Load & return another instance.
     *
     * @return null on error.
     */
    public IniFile read() {
        IniFileReader reader = IniFileReader.Open(zfile);
        if (reader == null) {
            return null;
        }
        IniFile result = reader.readAll();
        if (result == null) {
            return null;
        }
        return result;
    }

    @Override
    public int compareTo(Object obj) {
        if (obj == null) {
            return -1;
        }
        int count = 0;
        if (obj instanceof IniFile) {
            IniFile ref = (IniFile) obj;
            for (IniSection sec : this.groups) {
                if (sec.compareTo(sec) == 0) {
                    count++;
                }
            }
        }
        return this.groups.size() - count;
    }

    @Override
    public boolean equals(Object obj) {
        return this.compareTo(obj) == 0;
    }

    /**
     * Get a list of section names.
     *
     * @return
     */
    public String[] getSectionNames() {
        ArrayList<String> names = new ArrayList<>();
        String[] results = new String[0];
        for (int ss = 0; ss < groups.size(); ss++) {
            String sname = groups.get(ss).getSectionName();
            if (sname != null) {
                names.add(sname);
            }
        }
        return names.toArray(results);
    }

    /**
     * Locate a section, by name.
     *
     * @param section_name
     * @return null if the named section is not found.
     */
    public IniSection getSectionNamed(String section_name) {
        if (section_name != null && !section_name.isEmpty()) {
            section_name = IniSection.MkSectionName(section_name);
            for (IniSection group : groups) {
                if (group.getSectionName().equals(section_name)) {
                    return group;
                }
            }
        }
        return null;
    }
}
