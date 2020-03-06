package vesta

class Program {
    // auto timestamping
    Date dateCreated
    Date lastUpdated

    String name

    static hasMany = [questions: Question]

    static constraints = {
        name blank: false, maxSize: 256
    }

    /**
     * get all users associated to target program
     */
    List<User> getUsers() {
        UserProgram.findAllByProgram(this, [fetch: [user: 'join']])*.user
    }
}
