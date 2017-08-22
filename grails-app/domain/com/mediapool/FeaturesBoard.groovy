package com.mediapool

class FeaturesBoard {

    static belongsTo = [board:Board]
    Double sequence

    static constraints = {
        board nullable: false
        sequence blank: false
    }
}
