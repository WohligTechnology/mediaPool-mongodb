package com.mediapool

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class SearchLogsController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond SearchLogs.list(params), model:[searchLogsCount: SearchLogs.count()]
    }

    def show(SearchLogs searchLogs) {
        respond searchLogs
    }

    def create() {
        respond new SearchLogs(params)
    }

    @Transactional
    def save(SearchLogs searchLogs) {
        if (searchLogs == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (searchLogs.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond searchLogs.errors, view:'create'
            return
        }

        searchLogs.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'searchLogs.label', default: 'SearchLogs'), searchLogs.id])
                redirect searchLogs
            }
            '*' { respond searchLogs, [status: CREATED] }
        }
    }

    def edit(SearchLogs searchLogs) {
        respond searchLogs
    }

    @Transactional
    def update(SearchLogs searchLogs) {
        if (searchLogs == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (searchLogs.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond searchLogs.errors, view:'edit'
            return
        }

        searchLogs.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'searchLogs.label', default: 'SearchLogs'), searchLogs.id])
                redirect searchLogs
            }
            '*'{ respond searchLogs, [status: OK] }
        }
    }

    @Transactional
    def delete(SearchLogs searchLogs) {

        if (searchLogs == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        searchLogs.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'searchLogs.label', default: 'SearchLogs'), searchLogs.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'searchLogs.label', default: 'SearchLogs'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
