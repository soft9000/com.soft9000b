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
import com.soft9000b.xcoders.XCodes;

/**
 *
 * @author Randall Nagy
 */
public class IniFileWriter {

    /**
     * Replace the content of an existing IniFile.
     *
     * @param file
     * @return File will not exist on error / false.
     */
    public static boolean Overwrite(IniFile file) {
        // INI Files must be human editable:
        boolean br = _Overwrite(file, XCodes.none);
        if (br == false) {
            file.zfile.delete();
        }
        return br;
    }

    /**
     * Attempt to write the ini file.
     *
     * @param file
     * @return False means that the file "may or may not" have been created.
     */
    private static boolean _Overwrite(IniFile file, XCodes code) {
        if (file == null) {
            return false;
        }
        if (file.zfile.exists()) {
            if (file.zfile.delete() == false) {
                return false;
            }
        }
        TextLineWriter writer = new TextLineWriter(file.zfile, code);
        if (writer.open() == false) {
            return false;
        }
        for (IniSection section : file.groups) {
            if (writer.writeLine(section.toString()) == false) {
                writer.close();
                return false;
            }
        }
        writer.close(); // sync
        return file.zfile.exists();
    }

}
