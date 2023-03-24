package models.characterInfo

import models.Characters
import models.Weapons

data class CharWeapons(
    var idCharWeapon  : Int,
    var characterId  : Int,
    var weaponId  : Int,
    var character  : Characters,
    var weapon  : Weapons,
)
