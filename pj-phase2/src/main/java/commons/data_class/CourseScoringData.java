package commons.data_class;

public class CourseScoringData {
    Double totalGradePointAverage;
    int passedNumber;
    int failedNumber;
    Double totalGradePointAverageWithoutFails;

    public CourseScoringData(Double totalGradePointAverage, int passedNumber, int failedNumber,
                             Double totalGradePointAverageWithoutFails) {
        this.totalGradePointAverage = totalGradePointAverage;
        this.passedNumber = passedNumber;
        this.failedNumber = failedNumber;
        this.totalGradePointAverageWithoutFails = totalGradePointAverageWithoutFails;
    }
}
