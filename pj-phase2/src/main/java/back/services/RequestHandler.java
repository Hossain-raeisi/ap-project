package back.services;

import back.database.DataBase;
import back.database.Strings;
import back.models.course.Score;
import back.models.faculty.Faculty;
import back.models.request.Request;
import back.models.users.Professor;
import back.models.users.Student;
import back.models.users.User;
import commons.enums.RequestStatus;
import commons.enums.RequestType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;

public class RequestHandler {

    public static void minorRequest(Student student, Faculty faculty, String minorName) {
        try {
            DataBase.entityManager.getTransaction().begin();

            Request newMinorRequest = new Request(student,
                    new ArrayList<>() {
                        {
                            add(faculty.getDeputyEducationProfessor());
                            add(student.getFaculty().getDeputyEducationProfessor());
                        }
                    },
                    student.getFullName() + " minor request",
                    student.getFullName() + " from " + student.getFaculty().getName() + " major: "
                            + student.getMajor() + " for " + minorName,
                    RequestType.minor);

            DataBase.entityManager.persist(newMinorRequest);

            student.addAssignerRequest(newMinorRequest);
            if (student.getTotalGradePointAverage() < 16) {
                newMinorRequest.changeStatus(RequestStatus.disapproved);
                DataBase.entityManager.getTransaction().commit();
                return;
            }

            faculty.getDeputyEducationProfessor().addAssigneeRequest(newMinorRequest);
            student.getFaculty().getDeputyEducationProfessor().addAssigneeRequest(newMinorRequest);

            DataBase.entityManager.getTransaction().commit();

            Logger.Info("New minor request with id: " + newMinorRequest.getId());
        } catch (Exception e) {
            DataBase.entityManager.getTransaction().rollback();
            e.printStackTrace();
            Logger.Error("Couldn't create minor request");
        }

    }

    public static void withdrawalRequest(Student student) {
        try {
            DataBase.entityManager.getTransaction().begin();

            Request newWithdrawalRequest = new Request(student,
                    new ArrayList<>() {
                        {
                            add(student.getFaculty().getDeputyEducationProfessor());
                        }
                    },
                    student.getFullName() + " withdrawalRequest",
                    student.getFullName() + " from " + student.getFaculty().getName() +
                            " major: " + student.getMajor() + " withdrawal request",
                    RequestType.withdrawal);

            DataBase.entityManager.persist(newWithdrawalRequest);

            student.addAssignerRequest(newWithdrawalRequest);
            student.getFaculty().getDeputyEducationProfessor().addAssigneeRequest(newWithdrawalRequest);

            DataBase.entityManager.getTransaction().commit();

            Logger.Info("New withdrawal request with id: " + newWithdrawalRequest.getId());

        } catch (Exception e) {
            DataBase.entityManager.getTransaction().rollback();
            e.printStackTrace();
            Logger.Error("Couldn't create withdrawal request");
        }
    }

    public static void dormRequest(Student student) {
        try {
            DataBase.entityManager.getTransaction().begin();

            Request newDormRequest = new Request(student,
                    new ArrayList<>(),
                    student.getFullName() + "dorm request",
                    student.getFullName() + "dorm request",
                    RequestType.dorm);

            DataBase.entityManager.persist(newDormRequest);

            student.addAssignerRequest(newDormRequest);

            Logger.Info("New dorm request with id: " + newDormRequest.getId());

            switch ((new Random()).nextInt(2)) {
                case 0 -> approveRequest(null, newDormRequest, "");
                case 1 -> disApproveRequest(newDormRequest, "");
            }

            DataBase.entityManager.getTransaction().commit();
        } catch (Exception e) {
            DataBase.entityManager.getTransaction().rollback();
            e.printStackTrace();
            Logger.Error("Couldn't create dorm request");
        }
    }

    public static void recommendationRequest(Student student, Professor professor) {
        try {
            DataBase.entityManager.getTransaction().begin();

            Request newRecommendationRequest = new Request(student,
                    new ArrayList<>() {
                        {
                            add(professor);
                        }
                    },
                    student.getFullName() + " recommendation request",
                    student.getFullName() + " from " + student.getFaculty().getName() + " major: "
                            + student.getMajor() + " recommendation request from: " + professor.getFullName(),
                    RequestType.recommendation);

            DataBase.entityManager.persist(newRecommendationRequest);

            student.addAssignerRequest(newRecommendationRequest);
            professor.addAssigneeRequest(newRecommendationRequest);

            DataBase.entityManager.getTransaction().commit();
            Logger.Info("New recommendation request with id: " + newRecommendationRequest.getId());

            handleRecommendationRequest(DataBase.entityManager.find(Request.class, newRecommendationRequest.getId()));
        } catch (Exception e) {
            DataBase.entityManager.getTransaction().rollback();
            e.printStackTrace();
            Logger.Error("Couldn't create recommendation request");
        }
    }

    public static void objectionRequest(Score score, String objectionText) {
        try {
            DataBase.entityManager.getTransaction().begin();

            Request newObjectionRequest = new Request(score.getStudent(),
                    new ArrayList<>() {
                        {
                            add(score.getCourse().getProfessor());
                        }
                    },
                    score.getStudent().getFullName() +
                            " objection on his score in course: " + score.getCourse().getName(),
                    objectionText,
                    RequestType.objectionRequest);

            DataBase.entityManager.persist(newObjectionRequest);

            score.getStudent().addAssignerRequest(newObjectionRequest);
            score.getCourse().getProfessor().addAssigneeRequest(newObjectionRequest);

            DataBase.entityManager.getTransaction().commit();

            Logger.Info("New objection request with id: " + newObjectionRequest.getId());
        } catch (Exception e) {
            DataBase.entityManager.getTransaction().rollback();
            e.printStackTrace();
            Logger.Error("Couldn't create objection request");
        }
    }

    public static void chatRequest(User sender, User receiver) {
        // TODO
    }

    public static void approveRequest(User approver, Request request, String responseText) {
        try {
            DataBase.entityManager.getTransaction().begin();

            assert request != null;
            request.changeStatus(RequestStatus.approved);

            for (User user : request.getAssignees()) {
                if (user.equals(approver)) {
                    user.removeAssigneeRequest(request);
                }
            }

            if (request.getType() == RequestType.withdrawal) {
                DataBase.entityManager.remove(request.getAssigner());
            }

            if (request.getType() == RequestType.chatRequest) {
                // TODO
            }

            request.setResponse(responseText);

            DataBase.entityManager.getTransaction().commit();

            Logger.Info("Request: " + request.getId() + " has been approved");
        } catch (Exception e) {
            DataBase.entityManager.getTransaction().rollback();
            e.printStackTrace();
            Logger.Error("Couldn't create recommendation request");
        }
    }

    public static void disApproveRequest(Request request, String responseText) {
        try {
            DataBase.entityManager.getTransaction().begin();

            assert request != null;
            request.changeStatus(RequestStatus.disapproved);

            for (User user : request.getAssignees()) {
                user.removeAssigneeRequest(request);
            }

            request.setResponse(responseText);

            DataBase.entityManager.getTransaction().commit();

            Logger.Info("Request: " + request.getId() + " has been disapproved");
        } catch (Exception e) {
            DataBase.entityManager.getTransaction().rollback();
            e.printStackTrace();
            Logger.Error("Couldn't create recommendation request");
        }
    }

    public static void handleRecommendationRequest(Request recommendationRequest) {
        try {
            DataBase.entityManager.getTransaction().begin();

            recommendationRequest.changeStatus(RequestStatus.approved);

            StringBuilder responseText = new StringBuilder();

            responseText.append("injaneb ").append(recommendationRequest.getAssignees().get(0).
                    getFullName()).append(" govahi midaham k ");
            responseText.append("agha/khnum ").append(recommendationRequest.getAssigner().
                            getFullName()).append(" b shomare daneshjuyi ").
                    append(((Student) recommendationRequest.getAssigner()).getStudentNumber());
            responseText.append("dorus: \n");

            for (Score score : ((Student) recommendationRequest.getAssigner()).getPassedScores()) {
                if (score.getCourse().getProfessor().equals(recommendationRequest.getAssignees().get(0))) {
                    responseText.append(score.getCourse().getName()).append(" ba nomre: ").
                            append(score.getFinalScore()).append("\n");
                }
            }

            responseText.append("gozarande va dar dorus ... b onvan TA faaliat dashte");

            for (User user : recommendationRequest.getAssignees()) {
                user.removeAssigneeRequest(recommendationRequest);
            }

            recommendationRequest.setResponse(responseText.toString());

            DataBase.entityManager.getTransaction().commit();


            Logger.Info("Request: " + recommendationRequest.getId() + " has been handled");
        } catch (Exception e) {
            DataBase.entityManager.getTransaction().rollback();
            e.printStackTrace();
            Logger.Error("Couldn't handle recommendation request");
        }
    }

    public static String requestServiceExemption(Student student) {
        String[] sampleRequest = Strings.serviceExemptionResponse.split("#");
        String result = "";
        result += sampleRequest[0] + student.getFirstName() + " " + student.getLastName();
        result += sampleRequest[1] + student.getStudentNumber();
        result += sampleRequest[2] + student.getMajor();
        result += sampleRequest[3] + LocalDateTime.now();

        Logger.Info("Request service exemption for student: " + student.getNationalId());

        return result;
    }
}
