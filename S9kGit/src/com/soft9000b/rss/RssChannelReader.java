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
package com.soft9000b.rss;

import com.soft9000b.file.TextLineReader;
import com.soft9000b.xcoders.XCodes;
import java.io.File;
import java.io.FileNotFoundException;

/**
 *
 * @author Randall Nagy
 */
public class RssChannelReader {

    private static String _untag(String line, String root) {
        line = line.replace("<" + root + ">", "");
        return line.replace("</" + root + ">", "").trim();
    }

    public static RssChannel Read(File rssFile) throws FileNotFoundException, RssException {
        if (rssFile == null || rssFile.exists() == false) {
            throw new FileNotFoundException("Unable to locate RssChannel.");
        }
        TextLineReader reader = new TextLineReader(rssFile, XCodes.x16);
        reader.open();
        String line = reader.readAll(5000);
        reader.close();
        return RssChannelReader.ReadFeed(line);
    }

    /**
     * Simply restore something WE created. Use case is to maintain our own
     * meta, for our own feed, as I need do for the daily recipe updates on my
     * own "speak easy" web sites.
     *
     * @param repr A getFeed() result.
     * @return Returned instance will be .isNull() on error.
     */
    public static RssChannel ReadFeed(String repr) throws RssException {
        int level = 0;
        int where = 0;
        RssChannel result = new RssChannel();
        String[] lines = repr.split("\n");
        for (String line : lines) {
            where++;
            line = line.trim();
            if (line.isEmpty()) {
                continue;
            }
            if (line.startsWith("<channel>")) {
                if (level != 0) {
                    // one channel, only.
                    throw new RssException("Malformed RSS: Multi-Channel @" + where + ".", where);
                }
                level = 1;
                continue;
            }
            if (line.startsWith("<item>")) {
                if (level == 0) {
                    throw new RssException("Malformed RSS: Channel Not Found @" + where + ".", where);
                }
                result.items.add(new RssItem());
                level = 2;
                continue;
            }
            if (line.startsWith("<title>")) {
                if (level == 0) {
                    throw new RssException("Malformed RSS: Unexpected <title> definition @" + where + ".", where);
                }
                line = _untag(line, "title");
                if (level == 1) {
                    if (!result.Title.isEmpty()) {
                        throw new RssException("Malformed RSS: duplicate channel <title> @" + where + ".", where);
                    }
                    result.Title = line;
                    continue;
                }
                if (level == 2) {
                    if (result.items.isEmpty()) {
                        throw new RssException("Malformed RSS: Orphaned <title> @" + where + ".", where);
                    }
                    RssItem pwItem = result.items.get(result.items.size() - 1);
                    if (!pwItem.Title.isEmpty()) {
                        throw new RssException("Malformed RSS: duplicate item <title> @" + where + ".", where);
                    }
                    pwItem.Title = line;
                    continue;
                }
            }
            if (line.startsWith("<link>")) {
                if (level == 0) {
                    throw new RssException("Malformed RSS: Unexpected <link> definition @" + where + ".", where);
                }
                line = _untag(line, "link");
                if (level == 1) {
                    if (!result.Link.isEmpty()) {
                        throw new RssException("Malformed RSS: duplicate channel <link> @" + where + ".", where);
                    }
                    result.Link = line;
                    continue;
                }
                if (level == 2) {
                    if (result.items.isEmpty()) {
                        throw new RssException("Malformed RSS: Orphaned <link> @" + where + ".", where);
                    }
                    RssItem pwItem = result.items.get(result.items.size() - 1);
                    if (!pwItem.Link.isEmpty()) {
                        throw new RssException("Malformed RSS: duplicate item <link> @" + where + ".", where);
                    }
                    pwItem.Link = line;
                    continue;
                }
            }
            if (line.startsWith("<description>")) {
                if (level == 0) {
                    throw new RssException("Malformed RSS: unexpected <description> @" + where + ".", where);
                }
                line = _untag(line, "description");
                if (level == 1) {
                    if (!result.Description.isEmpty()) {
                        throw new RssException("Malformed RSS: duplicate channel <description> @" + where + ".", where);
                    }
                    result.Description = line;
                    continue;
                }
                if (level == 2) {
                    if (result.items.isEmpty()) {
                        throw new RssException("Malformed RSS: Orphaned <description> @" + where + ".", where);
                    }
                    RssItem pwItem = result.items.get(result.items.size() - 1);
                    if (!pwItem.Description.isEmpty()) {
                        throw new RssException("Malformed RSS: duplicate item <description> @" + where + ".", where);
                    }
                    pwItem.Description = line;
                    continue;
                }
            }
            if (line.startsWith("<pubDate>")) {
                if (level != 2) {
                    throw new RssException("Malformed RSS: Unexpected <pubDate> @" + where + ".", where);
                }
                if (result.items.isEmpty()) {
                    throw new RssException("Malformed RSS: Orphaned <pubDate> @" + where + ".", where);
                }
                line = _untag(line, "pubDate");
                RssItem pwItem = result.items.get(result.items.size() - 1);
                if (!pwItem.Date.isEmpty()) {
                    throw new RssException("Malformed RSS: Duplicate <pubDate> @" + where + ".", where);
                }
                pwItem.Date = line;
            }

        }
        return result;
    }

}
