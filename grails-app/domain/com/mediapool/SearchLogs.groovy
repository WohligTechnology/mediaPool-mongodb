package com.mediapool

class SearchLogs {

    static belongsTo = [user:User]
    String search
    String filters
    Date timestamp

    static constraints = {
    }
}
