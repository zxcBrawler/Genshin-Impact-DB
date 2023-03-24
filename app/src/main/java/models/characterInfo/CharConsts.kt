package models.characterInfo

import models.Characters
import models.referenceTables.Constallations

data class CharConsts(
    var idCharConst : Int,
    var characterId : Int,
    var constallationId : Int,
    var constallation : Constallations,
    var character : Characters,

    )
