package vesta

class User {
    // auto timestamping
    Date dateCreated
    Date lastUpdated

    String name

    static constraints = {
        name blank: false, unique: true
    }

    /**
     * get all programs associated to target user
     */
    List<Program> getPrograms() {
        UserProgram.findAllByUser(this, [fetch: [program: 'join']])*.program
    }
}
