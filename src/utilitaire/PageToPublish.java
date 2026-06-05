package utilitaire;

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
