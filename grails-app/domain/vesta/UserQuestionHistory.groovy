package vesta

class UserQuestionHistory {

    /** when the question is provided to user */
    Date dateCreated
    /** when the question is answered by user */
    Date lastUpdated

    User user
    Question question

    static belongsTo = [user: User, question: Question]

    static constraints = {
    }
}
