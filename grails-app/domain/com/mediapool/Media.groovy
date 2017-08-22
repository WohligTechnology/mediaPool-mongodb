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
    static hasMany = [user:User,board:Board,confirmedBasket:ConfirmedBasket]

    static constraints = {
        fields size: 5..15
    }
}
