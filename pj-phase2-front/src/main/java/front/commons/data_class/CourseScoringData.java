package front.commons.data_class;

public class CourseScoringData {
    public Float totalGradePointAverage;
    public int passedNumber;
    public int failedNumber;
    public Float totalGradePointAverageWithoutFails;

    public CourseScoringData(Float totalGradePointAverage, int passedNumber, int failedNumber,
                             Float totalGradePointAverageWithoutFails) {
        this.totalGradePointAverage = totalGradePointAverage;
        this.passedNumber = passedNumber;
        this.failedNumber = failedNumber;
        this.totalGradePointAverageWithoutFails = totalGradePointAverageWithoutFails;
    }
}