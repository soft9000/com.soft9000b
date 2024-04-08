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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Simple classes for a simple undertaking!
 *
 * @author Randall Nagy
 */
public class RssItem {

    public String Title = "";
    public String Link = "";
    public String Description = "";
    public String Date = "";
    public String Comments = "";
    public String AuthorEmailAddress = "";// aka "<author/>
    public String UniqueItemID = ""; // aka "<guid/>"

    /**
     * Set the Date field as required by RFC 822.
     *
     * @param date
     * @param timeZone "EST", "GMT", etc.
     */
    public void setDate(Date date, String timeZone) {
        // RFC 822 Date Format:
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz");
        dateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
        this.Date = dateFormat.format(date);
    }

    /**
     * Use the default Date(NOW).
     *
     * @param timeZone "EST", "GMT", etc.
     */
    public void setDate(String timeZone) {
        setDate(new Date(), timeZone);
    }

    /**
     * Use a epoch time (etc.)
     *
     * @param timAsMs Epoch time.
     * @param timeZone "EST", "GMT", etc.
     */
    public void setDate(long timAsMs, String timeZone) {
        setDate(new Date(timAsMs), timeZone);
    }

    /**
     * See if we've anything.
     *
     * @return True when we're empty.
     */
    public boolean isNull() {
        return this.Description.isEmpty() && this.Link.isEmpty() && this.Title.isEmpty();
    }
    
    /**
     * Whilst we may have data, we still might not be "all there yet." State is particularly
     * valid when we've had a problem during re-loading.
     * 
     * @return True if all is ready for saving. Caveat dated items.
     */
    boolean isSane() {
        if (isNull()) {
            return false;
        }
        if (this.Description.isEmpty() || this.Link.isEmpty() || this.Title.isEmpty()) {
            return false;
        }
        return true;
    }
}
