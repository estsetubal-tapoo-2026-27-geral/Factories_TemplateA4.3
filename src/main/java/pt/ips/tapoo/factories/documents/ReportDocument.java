package pt.ips.tapoo.factories.documents;

import pt.ips.tapoo.factories.model.ReportData;

/** Product contract for the baseline, Simple Factory and Factory Method variants. */
public interface ReportDocument {
    /** Renders a complete report. The argument must not be null. */
    String render(ReportData data);
}
