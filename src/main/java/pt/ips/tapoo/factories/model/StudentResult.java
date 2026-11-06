package pt.ips.tapoo.factories.model;

import java.util.Objects;

/** Immutable result on a scale from 0 to 20. */
public final class StudentResult {
    private final String studentName;
    private final double grade;

    public StudentResult(String studentName, double grade) {
        this.studentName = Objects.requireNonNull(studentName, "studentName").strip();
        if (this.studentName.isEmpty()) {
            throw new IllegalArgumentException("Student name must not be blank");
        }
        if (!Double.isFinite(grade) || grade < 0 || grade > 20) {
            throw new IllegalArgumentException("Grade must be finite and between 0 and 20");
        }
        this.grade = grade;
    }

    public String studentName() { return studentName; }
    public double grade() { return grade; }
}
