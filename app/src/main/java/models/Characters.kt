package models

import models.referenceTables.Element
import models.referenceTables.Region
import models.referenceTables.Role
import models.referenceTables.TypeWeapons

data class Characters(
    val idCharacter : Int,
    val nameCharacter : String,
    val descriptionCharacter : String,
    val birthdayCharacter : String,
    val rarityCharacter : Int,
    val constallationCharacter : String,
    val imageIconCharacter : String,
    val imageFullCharacter	: String,
    val elementId : Int,
    val roleId : Int,
    val typeWeaponId : Int,
    val regionId : Int,
    val element: Element,
    val region: Region,
    val role: Role,
    val typeWeapon: TypeWeapons,
)
