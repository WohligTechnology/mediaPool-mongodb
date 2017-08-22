package com.mediapool

//import grails.rest.*
//@Resource(uri = "/people", formats = ['json','xml'])

class User {

    String accessToken
    String name
    String profilePic
    String emailId
    String accessLevel
    String onesignal
    Date lastLogin
    Integer boardCount
    static belongsTo = [basket:Media]
    static hasMany = [board:Board,confirmedBasket:ConfirmedBasket,searchLogs:SearchLogs]


    static constraints = {
    }


}
