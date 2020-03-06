package vesta

class Question {
    // auto timestamping
    Date dateCreated
    Date lastUpdated

    String title
    Program program

    static belongsTo = [program: Program]

    static constraints = {
        title blank: false, maxSize: 1028
    }
}
