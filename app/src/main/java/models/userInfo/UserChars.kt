package models.userInfo

import models.Characters

data class UserChars(
    var idUserChars : Int,
    var characterId : Int,
    var userId : Int,
    var character : Characters,
    var user : User,
)
