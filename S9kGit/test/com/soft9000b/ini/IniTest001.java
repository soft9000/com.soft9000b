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
import java.io.File;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Basic testing of the INI read, write, & represent ability.
 *
 * @author Randall Nagy
 */
public class IniTest001 {

    static File[] zFiles = {
        new File("TEST001.INI"),
        new File("TEST002.INI"),
        new File("TEST003.INI"),};

    @BeforeClass
    public static void setUpClass() {
        cleanup();
    }

    @AfterClass
    public static void tearDownClass() {
        // WIP: Artifacts are good for review, at the moment.
        // cleanup();
    }

    private static void cleanup() {
        for (File file : zFiles) {
            if (file.exists()) {
                assert (file.delete() == true);
            }
        }
    }

    public IniTest001() {
    }

    @Test
    public void Test00000() {
        IniFile ini = new IniFile(zFiles[0]);
        IniSection sec = ini.addSection("zMyTest");
        assert (sec.add("LeftValue", "Assigned") == true);
        assert (sec.getValue("LeftValue").equals("Assigned"));
        assert(ini.getSectionNamed("zMyTest") != null);
        assert(ini.getSectionNamed("zMy?Test") == null);
        assert(ini.getSectionNamed("[zMyTest]") != null);
    }

    @Test
    public void Test00100() {
        IniFile ini = new IniFile(zFiles[0]);
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
        IniFile ini = new IniFile(zFiles[1]);
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
        // Modernly, lvalues may'nt be encoded:
        File tfile = zFiles[2];
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
        String[] names = ini.getSectionNames();
        assert (names.length == 2);

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
        assert (ini.compareTo(ini2) == 0);

        // Enquote
        assert (ini2.save() == true);

        // Dequote
        IniFile ini3 = ini2.read();
        assert (ini2.compareTo(ini3) == 0);

        sec = ini3.getSectionNamed("[BlockB]");
        assert (sec != null);
        sec = ini3.getSectionNamed("BlockB");
        assert (sec != null);

    }
}
