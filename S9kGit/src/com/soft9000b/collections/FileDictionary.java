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

import com.soft9000b.file.TextLineReader;
import com.soft9000b.file.TextLineWriter;
import com.soft9000b.tv.TagPair;
import com.soft9000b.xcoders.XCodes;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;

/**
 * An opportunity to create a production-quality way to C.R.U.D a serializable
 * collection of tag-value pairs.
 *
 * @author Randall
 */
public class FileDictionary extends Dictionary implements Serializable {

    private File zFile;

    private ArrayList<com.soft9000b.tv.TagPair> dict = new ArrayList<>();

    public static final String DEFAULT_FILE = ".options.dat";

    public FileDictionary(String aFile) {
        this.zFile = new File(aFile);
    }

    public FileDictionary() {
        this(DEFAULT_FILE);
    }

    public boolean load() {
        return this.load(zFile);
    }

    public boolean load(File file) {
        if (file == null) {
            return false;
        }
        return this.load(file.getAbsolutePath());
    }

    public boolean load(String aFile) {
        if (aFile == null || aFile.length() == 0) {
            return false;
        }
        this.zFile = new File(aFile);
        com.soft9000b.file.TextLineReader foo = new TextLineReader(zFile, XCodes.x16);
        if(!foo.open())
            return false;
        String sLine;
        do {
            sLine = foo.readLine();
            if (sLine != null) {
                TagPair zPair = TagPair.FromString(sLine);
                if (zPair == null) {
                    return false;
                }
                if (this.dict.add(zPair) == false) {
                    return false;
                }
            }
        } while (sLine != null);
        return foo.close();
    }

    public boolean save() {
        com.soft9000b.file.TextLineWriter foo = new TextLineWriter(zFile, XCodes.x16);
        if(!foo.open())
            return false;
        for (TagPair zTag : this.dict) {
            if (foo.writeLine(zTag.toString()) == false) {
                return false;
            }
        }
        return foo.close();
    }
    
    public boolean save(String aFile) {
        zFile = new File(aFile);
        com.soft9000b.file.TextLineWriter foo = new TextLineWriter(zFile, XCodes.x16);
        if(!foo.open())
            return false;
        for (TagPair zTag : this.dict) {
            if (foo.writeLine(zTag.toString()) == false) {
                return false;
            }
        }
        return foo.close();
    }

    public static FileDictionary Load() {
        FileDictionary results = new FileDictionary();
        results.load(DEFAULT_FILE);
        return results;
    }

    @Override
    public int size() {
        return this.dict.size();
    }

    @Override
    public boolean isEmpty() {
        return this.dict.isEmpty();
    }

    @Override
    public Enumeration keys() {
        ArrayList<String> results = new ArrayList<>();
        for (TagPair val : dict) {
            results.add(val.getTag());
        }
        return new ObjectEnumerator<>(results.toArray());
    }

    @Override
    public Enumeration elements() {
        ArrayList<TagPair> results = new ArrayList<>();
        for (TagPair val : dict) {
            results.add(val);
        }
        return new ObjectEnumerator<>(results.toArray());
    }
    
    public TagPair get(int ss) {
        if(ss >= this.size()) {
            return null;
        }
        return this.dict.get(ss);
    }
    
    public TagPair remove(int ss) {
        if(ss < this.size()) {
            return this.dict.remove(ss);
        } else {
            return null;
        }

    }

    @Override
    public Object get(Object key) {
        for (TagPair item : dict) {
            if (item.getTag().equals(key)) {
                return item;
            }
        }
        return null;
    }

    @Override
    public Object put(Object key, Object value) {
        TagPair zVal = (TagPair) this.get(key);
        if (zVal == null) {
            zVal = new TagPair(key.toString(), value.toString());
            dict.add(zVal);
        } else {
            zVal.setValue(((TagPair) value).getValue());
        }
        return zVal;
    }

    @Override
    public Object remove(Object key) {
        if(key instanceof TagPair) {
            return remove(((TagPair)key).getTag());
        }
        TagPair zVal = (TagPair) this.get(key);
        if (zVal != null) {
            zVal = new TagPair(zVal);
            dict.remove(zVal);
        }
        return zVal;
    }

    public void clear() {
        this.dict.clear();
    }

}
