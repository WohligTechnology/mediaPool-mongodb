package com.mediapool

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class BoardController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Board.list(params), model:[boardCount: Board.count()]
    }

    def show(Board board) {
        respond board
    }

    def create() {
        respond new Board(params)
    }

    @Transactional
    def save(Board board) {
        if (board == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (board.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond board.errors, view:'create'
            return
        }

        board.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'board.label', default: 'Board'), board.id])
                redirect board
            }
            '*' { respond board, [status: CREATED] }
        }
    }

    def edit(Board board) {
        respond board
    }

    @Transactional
    def update(Board board) {
        if (board == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (board.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond board.errors, view:'edit'
            return
        }

        board.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'board.label', default: 'Board'), board.id])
                redirect board
            }
            '*'{ respond board, [status: OK] }
        }
    }

    @Transactional
    def delete(Board board) {

        if (board == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        board.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'board.label', default: 'Board'), board.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'board.label', default: 'Board'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
