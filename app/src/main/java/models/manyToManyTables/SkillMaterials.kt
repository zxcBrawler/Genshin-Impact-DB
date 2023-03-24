package models.manyToManyTables

import models.Domains
import models.Materials
import models.Skills

data class SkillMaterials(
    var idSkillMaterial : Int,
    var quantity : Int,
    var materialId : Int,
    var skillId : Int,
    var skill : Skills,
    var level : Int,
    var material : Materials,
)
