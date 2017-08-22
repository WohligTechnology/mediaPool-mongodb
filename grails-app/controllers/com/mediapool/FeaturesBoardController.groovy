package com.mediapool

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class FeaturesBoardController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond FeaturesBoard.list(params), model:[featuresBoardCount: FeaturesBoard.count()]
    }

    def show(FeaturesBoard featuresBoard) {
        respond featuresBoard
    }

    def create() {
        respond new FeaturesBoard(params)
    }

    @Transactional
    def save(FeaturesBoard featuresBoard) {
        if (featuresBoard == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (featuresBoard.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond featuresBoard.errors, view:'create'
            return
        }

        featuresBoard.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'featuresBoard.label', default: 'FeaturesBoard'), featuresBoard.id])
                redirect featuresBoard
            }
            '*' { respond featuresBoard, [status: CREATED] }
        }
    }

    def edit(FeaturesBoard featuresBoard) {
        respond featuresBoard
    }

    @Transactional
    def update(FeaturesBoard featuresBoard) {
        if (featuresBoard == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (featuresBoard.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond featuresBoard.errors, view:'edit'
            return
        }

        featuresBoard.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'featuresBoard.label', default: 'FeaturesBoard'), featuresBoard.id])
                redirect featuresBoard
            }
            '*'{ respond featuresBoard, [status: OK] }
        }
    }

    @Transactional
    def delete(FeaturesBoard featuresBoard) {

        if (featuresBoard == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        featuresBoard.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'featuresBoard.label', default: 'FeaturesBoard'), featuresBoard.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'featuresBoard.label', default: 'FeaturesBoard'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
