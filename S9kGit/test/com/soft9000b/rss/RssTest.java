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

import java.io.File;
import java.io.FileNotFoundException;
import static java.lang.Math.min;

/**
 * Testing the critical path, only.
 *
 * @author Randall Nagy
 */
public class RssTest {

    public static void main(String... args) throws RssException, FileNotFoundException {
        RssChannel chan = new RssChannel();

        // SINGLE CHANNEL
        chan.Title = "Nagy's Nexus";
        chan.Link = "https://soft9000.com";
        chan.Description = "Content to share and enjoy.";

        RssItem item = new RssItem();
        item.Title = "Turtlebot Programming";
        item.Link = "https://soft9000.com/PY3KTG/index.html";
        item.Description = "Tutorial";
        chan.items.add(item);

        item = new RssItem();
        item.Title = "Nagy's News";
        item.Link = "https://soft9000.com/eIdRecipePage.html";
        item.Description = "Quotations and Collectable Recipe Cards";
        item.setDate("GMT");
        chan.items.add(item);
        RssItem testItem = item;

        item = new RssItem();
        item.Title = "Nagy's Training";
        item.Link = "https://soft9000.com/index.html";
        item.Description = "Educational Opportunities by Randall Nagy";
        item.UniqueItemID = "" + item.Link.hashCode();
        item.Comments = "Where I like to feature the key content for this site - no newlines, please.";
        item.AuthorEmailAddress = "foo@bar.net (Foo Happens)";
        chan.items.add(item);

        File file = new File("./nexus_test.rss");

        RssChannelWriter.Write(chan, file);
        System.out.println(file.getPath());

        RssChannel chanB = RssChannelReader.Read(file);
        String sA = chan.getFeed();
        String sB = chanB.getFeed();
        if (!sA.equals(sB)) {
            int line = 1;
            int where = 1;
            int min = min(sA.length(), sB.length());
            for (int ss = 0; ss < min; ss++) {
                if (sA.charAt(ss) != sB.charAt(ss)) {
                    throw new RssException("Regression: CharComp at " + line + "@" + where, line);
                }
                if (sA.charAt(ss) == '\n') {
                    line++;
                    where = 1;
                }
                where++;
            }

        }

        // Date munging is on our critical path:
        testItem.Date = "Sat, 06 Apr 2024 10:14:31 GMT";
        String oldDate = RssItemDater.GetDate(chan,
                "https://soft9000.com/eIdRecipePage.html");
        if (oldDate.isEmpty()) {
            throw new RssException("Regression: Date 1000", -1);
        }

        if (RssItemDater.TouchLink(chan,
                "https://soft9000.com/eIdRecipePage.html", "GMT") == true) {
            String newDate = RssItemDater.GetDate(chan,
                    "https://soft9000.com/eIdRecipePage.html");
            if (oldDate.equals(newDate)) {
                throw new RssException("Regression: Date 2000", -1);
            }
        }
        System.out.println("Testing Success.");
    }
}
