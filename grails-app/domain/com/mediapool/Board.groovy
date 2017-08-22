package com.mediapool

class Board {

    String name
    static belongsTo = [user:User,media:[Media]]
    String comment
    String urlSlug

    static constraints = {
    }
}
