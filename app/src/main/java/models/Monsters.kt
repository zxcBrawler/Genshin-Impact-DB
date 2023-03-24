package models

import models.referenceTables.TypeMonsters

data class Monsters(
    var idMonster  : Int,
    var nameMonster  : String,
    var descriptionMonster  : String,
    var imageIconMonster  : String,
    var imageFullMonster  : String,
    var typeMonsterId  : Int,
    var typeMonster  : TypeMonsters,
)
