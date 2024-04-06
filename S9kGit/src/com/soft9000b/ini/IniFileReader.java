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

import com.soft9000b.file.TextLineReader;
import com.soft9000b.tv.TagPair;
import com.soft9000b.xcoders.XCodes;
import java.io.File;

/**
 *
 * @author Randall Nagy
 */
public class IniFileReader {

    private TextLineReader reader = null;

    public static IniFileReader Open(File file) {
        if (!file.exists()) {
            return null;
        }
        TextLineReader codex = new TextLineReader(file, XCodes.none);
        return new IniFileReader(codex);
    }

    private IniFileReader(TextLineReader codex) {
        this.reader = codex;
    }

    public IniFile readAll() {
        if (!reader.open()) {
            return null;
        }
        IniFile results = new IniFile(reader.getFile());
        IniSection pwRow = new IniSection();
        while (true) {
            String sline = reader.readLine();
            if (sline == null) {
                break;
            }
            sline = sline.trim();
            if (sline.startsWith(";")) {
                pwRow.addComment(sline);
                continue;
            }
            // The .INI File Format's group identifier contains no data:
            if (sline.startsWith("[") && sline.endsWith("]")) {
                if (pwRow.isNull()) {
                    pwRow.setSectionName(sline);
                } else {
                    results.add(pwRow); // SECTION CAN BE EMPTY
                    pwRow = new IniSection();
                    pwRow.setSectionName(sline);
                }
            }
            if (sline.contains("=")) {
                TagPair pair = IniSection.Dequote(sline);
                pwRow.add(pair);
            }
        }
        if (!pwRow.isNull()) { // SECTION CAN BE EMPTY
            results.add(pwRow);
        }
        reader.close();
        return results;
    }

}
