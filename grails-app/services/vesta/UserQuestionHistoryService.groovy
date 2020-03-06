package vesta

import grails.gorm.services.Service

@Service(UserQuestionHistory)
interface UserQuestionHistoryService {

    UserQuestionHistory get(Serializable id)

    List<UserQuestionHistory> list(Map args)

    Long count()

    void delete(Serializable id)

    UserQuestionHistory save(UserQuestionHistory userQuestionHistory)

}