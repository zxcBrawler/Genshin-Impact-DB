package models

import models.referenceTables.TypeSkills

data class Skills(
    var idSkill : Int,
    var nameSkill : String,
    var descriptionSkill : String,
    var imageSkill : String ,
    var typeSkillId : Int,
    var typeSkill : TypeSkills,

)
