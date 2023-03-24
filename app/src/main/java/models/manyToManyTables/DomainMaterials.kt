package models.manyToManyTables

import models.Domains
import models.Materials
import models.referenceTables.Days

data class DomainMaterials(
    var idDomainMaterial : Int,
    var materialId : Int,
    var domainId : Int,
    var domain : Domains,
    var material : Materials,

    )
