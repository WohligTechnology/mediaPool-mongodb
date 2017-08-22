package com.mediapool

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class ConfirmedBasketController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond ConfirmedBasket.list(params), model:[confirmedBasketCount: ConfirmedBasket.count()]
    }

    def show(ConfirmedBasket confirmedBasket) {
        respond confirmedBasket
    }

    def create() {
        respond new ConfirmedBasket(params)
    }

    @Transactional
    def save(ConfirmedBasket confirmedBasket) {
        if (confirmedBasket == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (confirmedBasket.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond confirmedBasket.errors, view:'create'
            return
        }

        confirmedBasket.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'confirmedBasket.label', default: 'ConfirmedBasket'), confirmedBasket.id])
                redirect confirmedBasket
            }
            '*' { respond confirmedBasket, [status: CREATED] }
        }
    }

    def edit(ConfirmedBasket confirmedBasket) {
        respond confirmedBasket
    }

    @Transactional
    def update(ConfirmedBasket confirmedBasket) {
        if (confirmedBasket == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (confirmedBasket.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond confirmedBasket.errors, view:'edit'
            return
        }

        confirmedBasket.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'confirmedBasket.label', default: 'ConfirmedBasket'), confirmedBasket.id])
                redirect confirmedBasket
            }
            '*'{ respond confirmedBasket, [status: OK] }
        }
    }

    @Transactional
    def delete(ConfirmedBasket confirmedBasket) {

        if (confirmedBasket == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        confirmedBasket.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'confirmedBasket.label', default: 'ConfirmedBasket'), confirmedBasket.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'confirmedBasket.label', default: 'ConfirmedBasket'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
