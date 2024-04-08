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
    public String Description = "";

    /**
     * Create a basic RSS 2.0 file.
     *
     * @return Your site's unary channel XML as a string - ready to write.
     */
    public String getFeed() {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n"
                + "<rss version=\"2.0\">\n"
                + "<channel>\n"
                + "<docs>https://www.rssboard.org/rss-specification</docs>\n"
                + "<generator>https://github.com/soft9000/com.soft9000b</generator>\n"
                + "<title>" + this.Title + "</title>\n"
                + "<link>" + this.Link + "</link>\n"
                + "<description>" + this.Title + "</description>\n"
        );

        for (RssItem item : this.items) {
            sb.append("  <item>\n"
                    + "  <title>" + item.Title + "</title>\n"
                    + "  <link>" + item.Link + "</link>\n"
                    + "  <description>" + item.Description + "</description>\n");
            if (!item.Date.isEmpty()) {
                sb.append("  <pubDate>" + item.Date + "</pubDate>\n");
            }
            if (!item.AuthorEmailAddress.isEmpty()) {
                sb.append("  <author>" + item.AuthorEmailAddress + "</author>\n");
            }
            if (!item.Comments.isEmpty()) {
                sb.append("  <comments>" + item.Comments + "</comments>\n");
            }
            if (!item.UniqueItemID.isEmpty()) {
                sb.append("  <guid>" + item.UniqueItemID + "</guid>\n");
            }
            sb.append("   </item>\n");

        }
        sb.append("</channel>\n"
                + "</rss>");
        return sb.toString();
    }

    /**
     * See if we've anything.
     *
     * @return True when we're empty.
     */
    public boolean isNull() {
        if (this.Description.isEmpty() && this.Link.isEmpty() && this.Title.isEmpty()) {
            return this.items.isEmpty();
        }
        return false;
    }

    /**
     * There must be only a single URL in any of our feeds.
     *
     * @param link The precise .item.link.
     * @return The number of time an .item has a .link
     */
    public int itemLinks(String link) {
        int count = 0;
        if (this.isNull()) {
            return count;
        }
        for (RssItem item : this.items) {
            if (item.Link.equals(link)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Whilst we may have data, we still might not be "all there yet." We also
     * might have more than a single link, which we should not.
     *
     * @return True if all is ready for saving. Caveat dated items.
     */
    public boolean isSane() {
        if (isNull()) {
            return false;
        }
        if (this.Description.isEmpty() || this.Link.isEmpty() || this.Title.isEmpty()) {
            return false;
        }
        for (RssItem item : this.items) {
            if (!item.isSane()) {
                return false;
            }
            if (!item.Link.isEmpty()) {
                if (itemLinks(item.Link) != 1) {
                    return false;
                }
            }
        }
        return true;
    }
}
