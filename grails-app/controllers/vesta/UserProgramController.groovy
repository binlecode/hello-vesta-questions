package vesta

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

class UserProgramController {

    UserProgramService userProgramService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond userProgramService.list(params), model:[userProgramCount: userProgramService.count()]
    }

    def show(Long id) {
        respond userProgramService.get(id)
    }

    def create() {
        respond new UserProgram(params)
    }

    def save(UserProgram userProgram) {
        if (userProgram == null) {
            notFound()
            return
        }

        try {
            userProgramService.save(userProgram)
        } catch (ValidationException e) {
            respond userProgram.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'userProgram.label', default: 'UserProgram'), userProgram.id])
                redirect userProgram
            }
            '*' { respond userProgram, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond userProgramService.get(id)
    }

    def update(UserProgram userProgram) {
        if (userProgram == null) {
            notFound()
            return
        }

        try {
            userProgramService.save(userProgram)
        } catch (ValidationException e) {
            respond userProgram.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'userProgram.label', default: 'UserProgram'), userProgram.id])
                redirect userProgram
            }
            '*'{ respond userProgram, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        userProgramService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'userProgram.label', default: 'UserProgram'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'userProgram.label', default: 'UserProgram'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
