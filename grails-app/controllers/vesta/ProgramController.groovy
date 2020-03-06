package vesta

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

class ProgramController {

    ProgramService programService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond programService.list(params), model:[programCount: programService.count()]
    }

    def show(Long id) {
        respond programService.get(id)
    }

    def create() {
        respond new Program(params)
    }

    def save(Program program) {
        if (program == null) {
            notFound()
            return
        }

        try {
            programService.save(program)
        } catch (ValidationException e) {
            respond program.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'program.label', default: 'Program'), program.id])
                redirect program
            }
            '*' { respond program, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond programService.get(id)
    }

    def update(Program program) {
        if (program == null) {
            notFound()
            return
        }

        try {
            programService.save(program)
        } catch (ValidationException e) {
            respond program.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'program.label', default: 'Program'), program.id])
                redirect program
            }
            '*'{ respond program, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        programService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'program.label', default: 'Program'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'program.label', default: 'Program'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
