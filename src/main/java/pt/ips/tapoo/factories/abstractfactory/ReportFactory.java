package pt.ips.tapoo.factories.abstractfactory;

/** Abstract Factory: supplies the related products of one output family. */
public interface ReportFactory {
    ReportDocument createDocument();
    ReportTable createTable();
}
