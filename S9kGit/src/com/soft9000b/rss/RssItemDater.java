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

import com.soft900b.eTroolean;

/**
 * I've a need to update my feed whenever I update my daily recipe card.
 *
 * @author Randall Nagy
 */
public class RssItemDater {

    /**
     * The unique identifier for any item is the link.
     *
     * @param chan An loaded channel.
     * @param link The URL, exactly as provided, in the .item.link.
     * @return eTroolean.TRUE if the URL is present.
     */
    public static eTroolean HasLink(final RssChannel chan, String link) {
        if (chan == null || chan.isSane() == false) {
            return eTroolean.ERROR;
        }
        for (RssItem item : chan.items) {
            if (item.Link.equals(link)) {
                return eTroolean.TRUE;
            }
        }
        return eTroolean.FALSE;
    }

    /**
     * Add today's timestamp to any unique item's link identifier.
     *
     * @param chan An loaded channel.
     * @param link The URL, exactly as provided, in the .item.link.
     * @param timeZone "GMT", "EST", etc.
     * @return
     */
    public static boolean TouchLink(final RssChannel chan, String link, String timeZone) {
        if (HasLink(chan, link) != eTroolean.TRUE) {
            return false;
        }
        for (RssItem item : chan.items) {
            if (item.Link.equals(link)) {
                item.setDate(timeZone);
                return true;
            }
        }
        return false;
    }

    /**
     * Get the date for any UTL. String will be empty when none found.
     *
     * @param chan RssChannel
     * @param link The unique URL. Date will be empty if more than one!
     * @return The RFC 822 date, else an empty, string.
     */
    public static String GetDate(final RssChannel chan, String link) {
        if (chan == null || chan.isSane() == false) {
            return "";
        }
        for (RssItem item : chan.items) {
            if (item.Link.equals(link)) {
                return item.Date;
            }
        }
        return "";
    }
}
