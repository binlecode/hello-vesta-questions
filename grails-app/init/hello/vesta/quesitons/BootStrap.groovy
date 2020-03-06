package hello.vesta.quesitons

import groovy.util.logging.Log4j
import vesta.Program
import vesta.Question
import vesta.User
import vesta.UserProgram

class BootStrap {

    def init = { servletContext ->
        User.withNewTransaction {
            User u1 = new User(name: 'skywalker').save(flush: true, failOnError: true)
            log.info("user created: ${u1}")

//            User u2 = new User(name: 'jajabinks').save()
//            User u3 = new User(name: 'batman').save()
//            User u4 = new User(name: 'ironman').save()

            Program p1 = new Program(name: 'Jedi School').save(flush: true, failOnError: true)
            Program p2 = new Program(name: 'Avenger League').save(flush: true, failOnError: true)
            Program p3 = new Program(name: 'Hero League').save(flush: true, failOnError: true)
//
            new UserProgram(user: u1, program: p1).save(flush: true, failOnError: true)
            new UserProgram(user: u1, program: p3).save(flush: true, failOnError: true)
//            new UserProgram(user: u2, program: p1).save()
//            new UserProgram(user: u3, program: p2).save()
//            new UserProgram(user: u4, program: p2).save()
            new Question(program: p1, title: "Who is Luke's father?").save(flush: true, failOnError: true)
            new Question(program: p1, title: "Who is Luke's mother?").save(flush: true, failOnError: true)
            new Question(program: p1, title: "Who is Luke's sister?").save(flush: true, failOnError: true)
            new Question(program: p1, title: "What species is Jabba?").save(flush: true, failOnError: true)
            new Question(program: p1, title: "What vehicle does Luke fly with?").save(flush: true, failOnError: true)
            new Question(program: p1, title: "What's the name of rebel base planet in episode 3?").save(flush: true, failOnError: true)
            new Question(program: p1, title: "Who is the captain of Millennium Falcon?").save(flush: true, failOnError: true)
            new Question(program: p1, title: "What is jedi's main weapon?").save(flush: true, failOnError: true)
            new Question(program: p1, title: "Who played Princess Leia?").save(flush: true, failOnError: true)

            new Question(program: p2, title: "What is the name of black widow?").save(flush: true, failOnError: true)
            new Question(program: p2, title: "What is the company behind ironman?").save(flush: true, failOnError: true)

        }

    }
    def destroy = {
    }
}
