package pt.ips.tapoo.factories.model;

/** Output variants supported by the starter project. */
public enum ReportFormat {
    HTML("html"), MARKDOWN("md");

    private final String extension;
    ReportFormat(String extension) { this.extension = extension; }
    public String extension() { return extension; }
}
