package pt.ips.tapoo.factories.abstractfactory;

import pt.ips.tapoo.factories.model.ReportData;

/** Abstract Product A. This contract replaces the one used in stages 1 and 2. */
public interface ReportDocument {
    /** Combines the report data with an already rendered table of the same family. */
    String render(ReportData data, String tableContent);
}
