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

import java.util.ArrayList;

/**
 * Simple classes for a simple undertaking!
 * 
 * @author Randall Nagy
 */
public class RssChannel {

    public ArrayList<RssItem> items = new ArrayList<>();
    public String Title = "";
    public String Link = "";
    public String Descryption = "";

    /**
     * Create a basic RSS 2.0 file.
     * @return Your site's unary channel XML as a string - ready to write.
     */
    public String getFeed() {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n"
                + "<rss version=\"2.0\">\n"
                + "<channel>\n"
                + "<title>" + this.Title + "</title>\n"
                + "<link>" + this.Link + "</link>\n"
                + "<description>" + this.Title + "</description>\n"
        );

        for (RssItem item : this.items) {
            sb.append("  <item>\n"
                    + "  <title>" + item.Title + "</title>\n"
                    + "  <link>" + item.Link + "</link>\n"
                    + "  <description>" + item.Title + "</description>\n");
            if (!item.Date.isEmpty()) {
                sb.append("  <pubDate>" + item.Date + "</pubDate>\n");
            }
            sb.append("   </item>\n");

        }
        sb.append("</channel>\n"
                + "</rss>");
        return sb.toString();
    }
}
