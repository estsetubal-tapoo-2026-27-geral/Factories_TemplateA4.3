package pt.ips.tapoo.factories.rendering;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import pt.ips.tapoo.factories.model.ReportData;
import pt.ips.tapoo.factories.model.StudentResult;

/** Supplied formatting details. The activity focuses on object creation. */
public final class ReportRendering {
    private ReportRendering() { }

    public static String htmlTable(List<StudentResult> rows) {
        Objects.requireNonNull(rows, "rows");
        StringBuilder text = new StringBuilder();
        text.append("<table>\n<thead><tr><th scope=\"col\">Student</th>")
                .append("<th scope=\"col\">Grade</th></tr></thead>\n<tbody>\n");
        for (StudentResult row : rows) {
            text.append("<tr><td>").append(htmlText(row.studentName()))
                    .append("</td><td>").append(number(row.grade()))
                    .append("</td></tr>\n");
        }
        return text.append("</tbody>\n</table>\n").toString();
    }

    public static String markdownTable(List<StudentResult> rows) {
        Objects.requireNonNull(rows, "rows");
        StringBuilder text = new StringBuilder("| Student | Grade |\n|---|---:|\n");
        for (StudentResult row : rows) {
            text.append("| ").append(markdownText(row.studentName()))
                    .append(" | ").append(number(row.grade())).append(" |\n");
        }
        return text.toString();
    }

    public static String htmlDocument(ReportData data, String tableContent) {
        Objects.requireNonNull(data, "data");
        Objects.requireNonNull(tableContent, "tableContent");
        String title = htmlText(data.courseName());
        return "<!doctype html>\n<html lang=\"en\">\n<head>\n"
                + "<meta charset=\"UTF-8\">\n<title>" + title + "</title>\n"
                + "<style>body{font-family:Arial,sans-serif;max-width:850px;margin:40px auto;}"
                + "table{border-collapse:collapse;width:100%;}th,td{border:1px solid #ccd;"
                + "padding:10px;text-align:left;}th{background:#eaf2f7;}h1{color:#164e73;}"
                + "</style>\n</head>\n<body>\n<h1>" + title + "</h1>\n"
                + tableContent + "<p>Students: " + data.studentCount() + "</p>\n"
                + "<p>Average: " + averageText(data) + "</p>\n</body>\n</html>\n";
    }

    public static String markdownDocument(ReportData data, String tableContent) {
        Objects.requireNonNull(data, "data");
        Objects.requireNonNull(tableContent, "tableContent");
        return "# " + markdownText(data.courseName()) + "\n\n" + tableContent
                + "\nStudents: " + data.studentCount() + "\n\nAverage: "
                + averageText(data) + "\n";
    }

    private static String averageText(ReportData data) {
        return data.studentCount() == 0 ? "n/a" : number(data.average());
    }

    private static String number(double value) {
        return String.format(Locale.ROOT, "%.2f", value);
    }

    private static String htmlText(String text) {
        return text.replace("&", "&amp;").replace("<", "&lt;")
                .replace(">", "&gt;").replace("\"", "&quot;").replace("'", "&#39;");
    }

    private static String markdownText(String text) {
        return htmlText(text).replace("\\", "\\\\").replace("|", "\\|")
                .replace("`", "\\`").replace("*", "\\*").replace("_", "\\_")
                .replace("[", "\\[").replace("]", "\\]")
                .replace("#", "\\#").replace("\r", " ").replace("\n", " ");
    }
}
