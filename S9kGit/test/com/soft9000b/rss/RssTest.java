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
import com.soft9000b.file.TextLineWriter;
import com.soft9000b.xcoders.XCodes;
import java.io.File;

/**
 *
 * @author Randall Nagy
 */
public class RssTest {
    
    public static void main(String... args) throws RssException {
        RssChannel chan = new RssChannel();

        // SINGLE CHANNEL
        chan.Title = "Nagy's Nexus";
        chan.Link = "https://soft9000.com";
        chan.Descryption = "Content to share and enjoy.";
        
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
        
        item = new RssItem();
        item.Title = "Nagy's Training";
        item.Link = "https://soft9000.com/index.html";
        item.Description = "Educational Opportunities by Randall Nagy";
        chan.items.add(item);
        
        File file = new File("./nexus_test.rss");
        
        TextLineWriter writer = new TextLineWriter(file);
        writer.open();
        writer.writeLine(chan.getFeed());
        writer.close();
        System.out.println(file.getPath());
        
        TextLineReader reader = new TextLineReader(file, XCodes.x16);
        reader.open();
        StringBuilder sb = new StringBuilder();
        String line = "";
        while (line != null) {
            line = reader.readLine();
            if (line != null && !line.isEmpty()) {
                sb.append(line);
                sb.append("\n");
            }
        }

        RssChannel chanB = RssChannel.ReadFeed(sb.toString());
        if (chan.getFeed().equals(chanB.getFeed())) {
            System.out.println("Testing Success.");
        }
    }
}
