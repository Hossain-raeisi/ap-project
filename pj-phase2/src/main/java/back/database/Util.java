package back.database;

import commons.enums.CourseLevel;
import commons.enums.ProfessorRank;
import commons.enums.RequestType;

public class Util {
    public static int getProfessorRankInt(ProfessorRank professorRank) {
        switch (professorRank) {
            case assistantProfessor -> {
                return 0;
            }
            case associateProfessor -> {
                return 1;
            }
            case fullProfessor -> {
                return 2;
            }
        }
        return -1;
    }

    public static int getCourseLevelInt(CourseLevel courseLevel) {
        switch (courseLevel) {
            case bachelor -> {
                return 0;
            }
            case master -> {
                return 1;
            }
            case doctorate -> {
                return 2;
            }
        }
        return -1;
    }

    public static int getRequestTypeInt(RequestType requestType) {
        switch (requestType) {
            case recommendation -> {
                return 0;
            }
            case withdrawal -> {
                return 1;
            }
            case dorm -> {
                return 2;
            }
            case minor -> {
                return 3;
            }
            case objectionRequest -> {
                return 4;
            }
        }
        return -1;
    }
}
