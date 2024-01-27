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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

/**
 * This OBJECT will write lines - and maintain the file handle - for a linear
 * TEXT file.
 *
 * @author Professor Nagy
 */
public class TextLineWriter {

    private File pwFile = null;
    private BufferedWriter out = null;
    private final XCodes encoded;

    /**
     * Create a 'slow writer' for a newline-encrusted file. Will not be deleted
     * or created until it is opened.
     *
     * @param file A newline-delimited file that you want to <b>over-write,
     * delete, or create</b>.
     * @param encoded Encode lines so embedded newlines, may be therein.
     *
     */
    public TextLineWriter(File file, XCodes encoded) {
        pwFile = file;
        this.encoded = encoded;
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
     * Very useful for writing models form a common controller w/o munging
     * states. Exceptions become a bonus.
     *
     * @return The BufferedWriter is and when open. Else null.
     */
    public BufferedWriter getStream() {
        return out;
    }

    /**
     * Open a file for writing. Will not be deleted until it is opened.
     *
     * @return False on error / file n/f.
     */
    public boolean open() {
        return _open(false);
    }

    /**
     * Open a file for appending to existing file. Existing file (if any) Will
     * not be deleted.
     *
     * @return False on error / file n/f.
     */
    public boolean openAppend() {
        return _open(true);
    }

    /**
     * Writes a line to the file. Returns null on error or end of file.
     *
     * @param str Clear-text string to put into the file.
     * @return True if all went well.
     */
    public boolean writeLine(String str) {
        if (out == null) {
            return false;
        }
        try {
            out.write(XCodes.Encode(encoded,str));
            out.newLine();
            out.flush();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * From time to time - like when processing xml - we need to simply add a
     * block/line of text, assuming that it already HAS the required newlines.
     * Normalization will NOT take place!
     *
     * @param str The NORMALIZED, NEWLINE DELIMITED line / bock of text.
     * @return True if all went well
     */
    public boolean write(String str) {
        if (out == null) {
            return false;
        }
        try {
            out.write(str);
            out.flush();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * Close the stream, if it is open. Can *always* be called.
     */
    public boolean close() {
        if (out == null) {
            return true;
        }
        try {
            out.flush();
            out.close();
            return true;
        } catch (Exception ex) {
            return false;
        } finally {
            out = null;
        }
    }

    private boolean _open(boolean bAppend) {
        if (out != null) {
            close();
        }

        if (bAppend == false && pwFile.exists()) {
            pwFile.delete();
        }

        try {
            FileWriter reader = new FileWriter(pwFile, bAppend);
            out = new BufferedWriter(reader);
        } catch (Exception e) {
            out = null;
            return false;
        }
        return true;
    }
}
