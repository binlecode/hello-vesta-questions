package vesta

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

class UserQuestionController {

    DailyQuestionService dailyQuestionService
    UserQuestionHistoryService userQuestionHistoryService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {

        //todo: preload user skywalker
        params.userId = 1

        params.max = Math.min(max ?: 10, 100)
        User user = User.get(params.userId)
        respond UserQuestionHistory.findAllByUser(user, [fetch: [user: 'join', question: 'join']]),
                model: [userQuestionHistoryCount: UserQuestionHistory.countByUser(user)]
    }

    def requestDailyQuestions() {
        params.userId = 1
        User user = User.get(params.userId)
        List<Question> userDailyQuestionList = dailyQuestionService.getDailyQuestions(user)

        // add fetched questions to history track
        //todo: add this to service layer
        UserQuestionHistory.withNewTransaction {
            userDailyQuestionList.each { Question q ->
                new UserQuestionHistory(user: user, question: q).save(failOnError: true)
            }
        }

        List<UserQuestionHistory> userQuestionHistoryList =
                UserQuestionHistory.findAllByUser(user, [fetch: [user: 'join', question: 'join']])

        render view: 'index',
                model: [userDailyQuestionList: userDailyQuestionList,
                        userQuestionHistoryList: userQuestionHistoryList,
                        userQuestionHistoryCount: UserQuestionHistory.countByUser(user)]
    }

    def clearQuestionHistory() {
        params.userId = 1
        //todo: move this to service layer
        UserQuestionHistory.withNewTransaction {
            UserQuestionHistory.where {
                user == User.get(params.userId)
            }.deleteAll()
        }
        redirect action: 'requestDailyQuestions', params: [userId: params.userId]
    }

    def show(Long id) {
        respond userQuestionHistoryService.get(id)
    }

    def create() {
        respond new UserQuestionHistory(params)
    }

    def save(UserQuestionHistory userQuestionHistory) {
        if (userQuestionHistory == null) {
            notFound()
            return
        }

        try {
            userQuestionHistoryService.save(userQuestionHistory)
        } catch (ValidationException e) {
            respond userQuestionHistory.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'userQuestionHistory.label', default: 'UserQuestionHistory'), userQuestionHistory.id])
                redirect userQuestionHistory
            }
            '*' { respond userQuestionHistory, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond userQuestionHistoryService.get(id)
    }

    def update(UserQuestionHistory userQuestionHistory) {
        if (userQuestionHistory == null) {
            notFound()
            return
        }

        try {
            userQuestionHistoryService.save(userQuestionHistory)
        } catch (ValidationException e) {
            respond userQuestionHistory.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'userQuestionHistory.label', default: 'UserQuestionHistory'), userQuestionHistory.id])
                redirect userQuestionHistory
            }
            '*'{ respond userQuestionHistory, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        userQuestionHistoryService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'userQuestionHistory.label', default: 'UserQuestionHistory'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'userQuestionHistory.label', default: 'UserQuestionHistory'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
