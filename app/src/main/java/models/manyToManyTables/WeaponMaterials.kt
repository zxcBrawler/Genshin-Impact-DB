package models.manyToManyTables

import models.Materials

data class WeaponMaterials(
    var idWeaponMaterial : Int,
    var quantity : Int,
    var materialId : Int,
    var weaponId : Int,
    var material : Materials,
)
