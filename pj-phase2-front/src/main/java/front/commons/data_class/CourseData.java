package front.commons.data_class;

import front.commons.enums.CourseLevel;

import java.util.ArrayList;
import java.util.UUID;

public class CourseData {
    public UUID id;
    public String facultyName;
    public UUID facultyId;
    public String professorName;
    public UUID professorId;
    public CourseLevel level;
    public String name;
    public int size;
    public String time;
    public Boolean temporaryScoresSet;
    public Boolean archived;
    public ArrayList<UUID> examsId;
    public ArrayList<UUID> scoresId;
    public ArrayList<String> weekDays;
    public ArrayList<UUID> TAsId;
    public ArrayList<UUID> educationalContentsId;

    public CourseData(UUID id, String facultyName, UUID facultyId, String professorName, UUID professorId,
                      CourseLevel level, String name, int size, String time, Boolean temporaryScoresSet,
                      Boolean archived, ArrayList<UUID> examsId, ArrayList<UUID> scoresId, ArrayList<String> weekDays,
                      ArrayList<UUID> TAsId, ArrayList<UUID> educationalContentsId) {
        this.id = id;
        this.facultyName = facultyName;
        this.facultyId = facultyId;
        this.professorName = professorName;
        this.professorId = professorId;
        this.level = level;
        this.name = name;
        this.size = size;
        this.time = time;
        this.temporaryScoresSet = temporaryScoresSet;
        this.archived = archived;
        this.examsId = examsId;
        this.scoresId = scoresId;
        this.weekDays = weekDays;
        this.TAsId = TAsId;
        this.educationalContentsId = educationalContentsId;
    }

    /**
     * used for creation from user input
     */
    public CourseData(UUID facultyId, UUID professorId, CourseLevel level, String name, int size) {
        this.facultyId = facultyId;
        this.professorId = professorId;
        this.level = level;
        this.name = name;
        this.size = size;
    }
}