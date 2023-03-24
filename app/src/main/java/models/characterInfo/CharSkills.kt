package models.characterInfo

import models.Characters
import models.Skills

data class CharSkills(
    var idCharSkill : Int,
    var characterId : Int,
    var skillId : Int,
    var character : Characters,
    var skill : Skills,

)
