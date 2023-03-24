package models.manyToManyTables

import models.Materials
import models.referenceTables.Days

data class DaysMaterials(
    var idDaysMaterial : Int,
    var materialId : Int,
    var daysId : Int,
    var days : Days,
    var material : Materials,
)
