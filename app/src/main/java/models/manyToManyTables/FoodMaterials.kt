package models.manyToManyTables

import models.Domains
import models.Materials
import models.referenceTables.Foods

data class FoodMaterials(
    var idFoodMaterial : Int,
    var quantity : Int,
    var materialId : Int,
    var foodId : Int,
    var food : Foods,
    var material : Materials,
)
