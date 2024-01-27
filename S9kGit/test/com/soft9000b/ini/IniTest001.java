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

import com.soft9000b.file.TextLineWriter;
import com.soft9000b.tv.TagPair;
import java.io.File;
import org.junit.Test;

/**
 *
 * @author ranag
 */
public class IniTest001 {

    public IniTest001() {
    }

    @Test
    public void Test00100() {
        IniFile ini = new IniFile(new File("TEST001.INI"));
        IniSection sec = ini.addSection("MyTest");
        assert (sec.add("LeftValue", "Assigned") == true);
        sec = ini.addSection("MyTest2");
        assert (sec.add("TheOther", "Value") == true);
        //System.out.println(ini.toString());
        if (ini.save() == false) {
            System.err.println("TEST 00100 Failure.");
        }
        IniFile ini2 = ini.read();
        if (ini2 == null) {
            System.err.println("TEST 00110 Failure.");
        }
        if (!ini.equals(ini2)) {
            System.err.println("TEST 00120 Failure.");
        }
    }

    @Test
    public void Test00200() {
        IniFile ini = new IniFile(new File("TEST002.INI"));
        IniSection sec = ini.addSection("AnotherSection");
        sec.addComment("We've commented.");
        assert (sec.add("Complex", "Also assigned, as-is.") == true);
        sec = ini.addSection("Another Section");
        sec.addComment("We've commented, again.");
        assert (sec.add("TheOther", "Value") == true);
        //System.out.println(ini.toString());
        if (ini.save() == false) {
            System.err.println("TEST 00100 Failure.");
        }
        IniFile ini2 = ini.read();
        if (ini2 == null) {
            System.err.println("TEST 00110 Failure.");
        }
        if (!ini.equals(ini2)) {
            System.err.println("TEST 00120 Failure.");
        }
    }

    @Test
    public void Test00300() {
        File tfile = new File("TEST003.INI");
        TextLineWriter writer = new TextLineWriter(tfile);
        assert (writer.open() == true);
        writer.writeLine("; Super-Block Comment");
        writer.writeLine("[BlockA]");
        writer.writeLine("zTag=value");
        writer.writeLine("");
        writer.writeLine(";Another super-block comment");
        writer.writeLine("[BlockB]");
        writer.writeLine("; Subblock comment A");
        writer.writeLine("zTagA=value");
        writer.writeLine(";Subblock comment B");
        writer.writeLine("zTagB=value");
        assert (writer.close() == true);

        IniFile ini = IniFile.Read(tfile);
        IniFile ini2 = new IniFile(tfile);
        IniSection sec = ini2.addSection("BlockA");
        sec.addSuperComment("Super-Block Comment");
        assert (sec.add("zTag", "value") == true);
        sec = ini2.addSection("[BlockB]");
        sec.addSuperComment(";Another super-block comment");
        assert (sec.add("zTag", "value") == true);
        sec.addComment("Subblock comment A");
        assert (sec.add("zTagA", "value") == true);
        sec.addComment(";Subblock comment B");
        assert (sec.add("zTagB", "value") == true);
        assert(ini.compareTo(ini2) == 0);
        //System.out.print("[" + ini.toString() + "]");
        //System.out.print("[" + ini2.toString() + "]");

    }
}
