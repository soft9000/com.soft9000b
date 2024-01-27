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
package com.soft9000b.file;

import com.soft9000b.xcoders.Hex16;
import com.soft9000b.xcoders.XCodes;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * This OBJECT will read lines - and maintain the file handle - for a linear
 * TEXT file.
 *
 * An opportunity to create a simple, line-oriented, string reader. This must be
 * a 'weaponized' class whose interface must be relatively robust (i.e. it will
 * be used a lot!)
 *
 * @author ProfNagy
 */
public class TextLineReader {

    private File pwFile = null;
    private BufferedReader in = null;
    private final XCodes encoded;

    /**
     * Create a 'slow reader' for a newline-encrusted file.
     *
     * @param file A newline-delimited file.
     * @param encoded Decoded lines as embedded newlines, may be therein.
     */
    public TextLineReader(File file, XCodes encoded) {
        pwFile = file;
        this.encoded = encoded;
    }

    /**
     * See if the the this instance is ready to be used.
     *
     * @return TRUE if the buffers are open & ready to use. False if all is
     * closed.
     */
    public boolean isOpen() {
        return (in != null);
    }

    /**
     * Tell us what we passed to the constructor -
     *
     * @return What was passed to the constructor -
     */
    public File getFile() {
        return pwFile;
    }

    /**
     * Open a file for reading.
     *
     * @return False on error / file n/f.
     */
    public boolean open() {
        if (in != null) {
            close();
        }

        if (pwFile.exists() == false) {
            return false;
        }

        try {
            FileReader reader = new FileReader(pwFile);
            in = new BufferedReader(reader);
        } catch (IOException e) {
            in = null;
            return false;
        }
        return true;

    }

    /**
     * Reads a line from the file. Returns null on error or end of file.
     *
     * @return The line, or a null.
     */
    public String readLine() {
        if (in == null) {
            return null;
        }
        try {
            String line = in.readLine();
            if (line == null)
                return line;
            return XCodes.Decode(encoded, line);

        } catch (IOException ex) {
            return null;
        }
    }

    /**
     * Close the stream, if it is open. Can *always* be called.
     */
    public boolean close() {
        if (in == null) {
            return true;
        }
        try {
            in.close();
            return true;
        } catch (IOException ex) {
            return false;
        } finally {
            in = null;
        }
    }
}
