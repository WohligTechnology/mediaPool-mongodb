package com.mediapool

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class MediaLogsController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond MediaLogs.list(params), model:[mediaLogsCount: MediaLogs.count()]
    }

    def show(MediaLogs mediaLogs) {
        respond mediaLogs
    }

    def create() {
        respond new MediaLogs(params)
    }

    @Transactional
    def save(MediaLogs mediaLogs) {
        if (mediaLogs == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (mediaLogs.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond mediaLogs.errors, view:'create'
            return
        }

        mediaLogs.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'mediaLogs.label', default: 'MediaLogs'), mediaLogs.id])
                redirect mediaLogs
            }
            '*' { respond mediaLogs, [status: CREATED] }
        }
    }

    def edit(MediaLogs mediaLogs) {
        respond mediaLogs
    }

    @Transactional
    def update(MediaLogs mediaLogs) {
        if (mediaLogs == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (mediaLogs.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond mediaLogs.errors, view:'edit'
            return
        }

        mediaLogs.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'mediaLogs.label', default: 'MediaLogs'), mediaLogs.id])
                redirect mediaLogs
            }
            '*'{ respond mediaLogs, [status: OK] }
        }
    }

    @Transactional
    def delete(MediaLogs mediaLogs) {

        if (mediaLogs == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        mediaLogs.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'mediaLogs.label', default: 'MediaLogs'), mediaLogs.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'mediaLogs.label', default: 'MediaLogs'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
