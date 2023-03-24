package models

import models.referenceTables.TypeWeapons

data class Weapons(
    var idWeapon : Int,
    var nameWeapon : String,
    var descriptionWeapon : String,
    var rarityWeapon : Int,
    var baseAtkWeapon : Int,
    var imageWeapon : String,
    var substatWeapon : String?,
    var substatValue : String?,
    var namePassiveWeapon : String?,
    var descriptionPassiveWeapon : String?,
    var typeWeaponId : Int,
    var typeWeapon : TypeWeapons,
)
