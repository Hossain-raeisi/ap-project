package commons.data_class;

import commons.enums.CourseLevel;

public class CourseFilter {
    public String name;
    public Integer size;
    public String facultyId;
    public String id;
    public CourseLevel level;

    public CourseFilter(String name, Integer size, String facultyId, String id, CourseLevel level) {
        this.name = name;
        this.size = size;
        this.facultyId = facultyId;
        this.id = id;
        this.level = level;
    }
}
