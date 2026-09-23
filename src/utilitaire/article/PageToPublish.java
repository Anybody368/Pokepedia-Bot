/*
 * Copyright (c) 2026 - Poképedia's contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the “Software”), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package utilitaire.article;

public class PageToPublish {
    private final Page page;
    private final String newContent;
    private final String summary;
    private final boolean isMinor;

    public PageToPublish(Page page, String newContent, String summary, boolean isMinor) {
        this.page = page;
        this.newContent = newContent;
        this.summary = summary;
        this.isMinor = isMinor;
    }

    public PageToPublish(Page page, String newContent, String summary) {
        this(page, newContent, summary, false);
    }

    public Page getPage() {
        return page;
    }

    public String getNewContent() {
        return newContent;
    }

    public String getSummary() {
        return summary;
    }

    public boolean isMinor() {
        return isMinor;
    }
}
