package models

data class WeaponRefinements(
    var idWeaponRefinements : Int,
    var rankRefinement : Int,
    var descriptionRefinement : String,
    var weaponId : Int,
    var weapon : Weapons,
)
