package models.characterInfo

import models.Characters
import models.referenceTables.ArtefactSets
import models.referenceTables.Foods

data class CharFoods(
    var idCharFood : Int,
    var characterId : Int,
    var foodId : Int,
    var food : Foods,
    var character : Characters,
    )
