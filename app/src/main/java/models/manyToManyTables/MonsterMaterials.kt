package models.manyToManyTables

import models.Domains
import models.Materials
import models.Monsters

data class MonsterMaterials(
    var idMonsterMaterial : Int,
    var materialId : Int,
    var monsterId : Int,
    var monster : Monsters,
    var material : Materials,
)
