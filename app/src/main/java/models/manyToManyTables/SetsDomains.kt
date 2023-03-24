package models.manyToManyTables

import models.Domains
import models.Materials
import models.referenceTables.ArtefactSets

data class SetsDomains(
    var idSetsDomain : Int,
    var artefactSetsId : Int,
    var domainId : Int,
    var domain : Domains,
    var artefactSets : ArtefactSets,
)
