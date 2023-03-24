package models

import models.referenceTables.TypeMaterials

data class Materials(
    var idMaterial : Int,
    var nameMaterial : String,
    var descriptionMaterial : String,
    var imageMaterial : String,
    var rarityMaterial : Int,
    var typeMaterialId : Int,
    var typeMaterial : TypeMaterials,
)
