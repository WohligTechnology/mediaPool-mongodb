package com.mediapool

class Media {

    String name
    String classifications
    String fields
    String url
    String mimeType
    String mediaType
    String keyword
    Object files
    static hasMany = [user:User,board:Board,confirmedBasket:ConfirmedBasket,mediaLogs:MediaLogs]

    static constraints = {
        fields blank: false
//        url blank: false, url: true

    }
}
