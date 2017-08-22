package com.mediapool

class ConfirmedBasket {

    static belongsTo = [user:User,media:Media]
    String zip
    Date expiryTime
    Date zipCompletionTime


    static constraints = {
    }
}
