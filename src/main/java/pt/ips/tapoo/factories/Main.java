package pt.ips.tapoo.factories;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import pt.ips.tapoo.factories.abstractfactory.HtmlReportFactory;
import pt.ips.tapoo.factories.abstractfactory.MarkdownReportFactory;
import pt.ips.tapoo.factories.abstractfactory.ReportFactory;
import pt.ips.tapoo.factories.abstractfactory.ReportGenerator;
import pt.ips.tapoo.factories.baseline.ReportApplication;
import pt.ips.tapoo.factories.factorymethod.HtmlReportExporter;
import pt.ips.tapoo.factories.factorymethod.MarkdownReportExporter;
import pt.ips.tapoo.factories.factorymethod.ReportExporter;
import pt.ips.tapoo.factories.model.ReportData;
import pt.ips.tapoo.factories.model.ReportFormat;
import pt.ips.tapoo.factories.model.SampleData;

/** Runs the starting version or one of the three activity stages. */
public final class Main {
    private Main() { }

    public static void main(String[] args) throws IOException {
        // Prepare the data and the default execution settings.
        ReportData data = SampleData.classroom();
        String mode = "baseline";
        Path output = Path.of("target/reports");


        if (args.length > 2) {
            throw new IllegalArgumentException(
                    "Usage: [baseline|simple|method|abstract|all] [output-directory]");
        }
        if (args.length > 0) {
            mode = args[0].toLowerCase(Locale.ROOT);
        }
        if (args.length > 1) {
            output = Path.of(args[1]);
        }
        Files.createDirectories(output);

        // Each method below shows one version of the same application.
        try {
            switch (mode) {
                case "baseline":
                    runBaseline(data, output);
                    break;
                case "simple":
                    runSimpleFactory(data, output);
                    break;
                case "method":
                    runFactoryMethod(data, output);
                    break;
                case "abstract":
                    runAbstractFactory(data, output);
                    break;
                case "all":
                    runBaseline(data, output);
                    runSimpleFactory(data, output);
                    runFactoryMethod(data, output);
                    runAbstractFactory(data, output);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown mode: " + mode);
            }
        } catch (UnsupportedOperationException e) {
            // The methods still to be completed throw this exception with a "TODO ..." message.
            String message = e.getMessage();
            if (message == null || !message.startsWith("TODO")) {
                throw e;
            }
            System.err.println("Activity incomplete: " + message);
            return;
        }

        System.out.println("Reports saved in: " + output.toAbsolutePath());
    }

    /** Starting version: use the application that directly creates documents. */
    private static void runBaseline(ReportData data, Path output) throws IOException {
        ReportApplication application = new ReportApplication();
        application.export(data, ReportFormat.HTML, output.resolve("baseline-report.html"));
        application.export(data, ReportFormat.MARKDOWN, output.resolve("baseline-report.md"));
    }

    /** Requirement 1: use the client that obtains documents from a simple factory. */
    private static void runSimpleFactory(ReportData data, Path output) throws IOException {
        // This ReportApplication belongs to a different package from the baseline client.
        pt.ips.tapoo.factories.simplefactory.ReportApplication application =
                new pt.ips.tapoo.factories.simplefactory.ReportApplication();
        application.export(data, ReportFormat.HTML, output.resolve("simple-report.html"));
        application.export(data, ReportFormat.MARKDOWN, output.resolve("simple-report.md"));
    }

    /** Requirement 2: choose a concrete creator for each output format. */
    private static void runFactoryMethod(ReportData data, Path output) throws IOException {
        ReportExporter htmlExporter = new HtmlReportExporter();
        htmlExporter.export(data, output.resolve("method-report.html"));

        ReportExporter markdownExporter = new MarkdownReportExporter();
        markdownExporter.export(data, output.resolve("method-report.md"));
    }

    /** Requirement 3: give the generator a factory for the selected product family. */
    private static void runAbstractFactory(ReportData data, Path output) throws IOException {
        ReportFactory htmlFactory = new HtmlReportFactory();
        ReportGenerator htmlGenerator = new ReportGenerator(htmlFactory);
        String htmlContent = htmlGenerator.generate(data);
        Files.writeString(output.resolve("abstract-report.html"), htmlContent);

        ReportFactory markdownFactory = new MarkdownReportFactory();
        ReportGenerator markdownGenerator = new ReportGenerator(markdownFactory);
        String markdownContent = markdownGenerator.generate(data);
        Files.writeString(output.resolve("abstract-report.md"), markdownContent);
    }
}