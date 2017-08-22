package com.mediapool

class MediaLogs {

    static belongsTo = [user:User,media:Media]
    Date timestamp
    String event

    static constraints = {
    }
}
