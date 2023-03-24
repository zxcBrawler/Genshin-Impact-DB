package models.characterInfo

import models.Characters
import models.Materials

data class CharMaterials(

    var idCharMaterial : Int,
    var quantity : Int,
    var materialId : Int,
    var characterId : Int,
    var character : Characters,
    var material : Materials,
    var level : Int,

)
