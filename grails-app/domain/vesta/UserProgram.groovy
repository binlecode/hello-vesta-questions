package vesta

/**
 * many-to-many mapping table between user and program domains
 */
class UserProgram {

    User user
    Program program

    static belongsTo = [user: User, program: Program]

    static constraints = {
    }
}
