package com.mediapool

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class ConfigController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Config.list(params), model:[configCount: Config.count()]
    }

    def show(Config config) {
        respond config
    }

    def create() {
        respond new Config(params)
    }

    @Transactional
    def save(Config config) {
        if (config == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (config.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond config.errors, view:'create'
            return
        }

        config.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'config.label', default: 'Config'), config.id])
                redirect config
            }
            '*' { respond config, [status: CREATED] }
        }
    }

    def edit(Config config) {
        respond config
    }

    @Transactional
    def update(Config config) {
        if (config == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (config.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond config.errors, view:'edit'
            return
        }

        config.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'config.label', default: 'Config'), config.id])
                redirect config
            }
            '*'{ respond config, [status: OK] }
        }
    }

    @Transactional
    def delete(Config config) {

        if (config == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        config.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'config.label', default: 'Config'), config.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'config.label', default: 'Config'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
