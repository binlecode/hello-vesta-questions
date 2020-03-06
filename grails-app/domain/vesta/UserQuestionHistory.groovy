package vesta

class UserQuestionHistory {

    Date dateCreated
//    Date lastUpdated

    User user
    Question question

    static belongsTo = [user: User, question: Question]

    static constraints = {
    }
}
