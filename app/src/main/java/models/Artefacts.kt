package models

import models.referenceTables.ArtefactSets
import models.referenceTables.TypeArtefacts

data class Artefacts(
    var idArtefact : Int,
    var nameArtefact  : String,
    var descriptionArtefact : String,
    var imageArtefact : String,
    var artefactSetsId : Int,
    var typeArtefactId : Int,
    var artefactSets : ArtefactSets,
    var typeArtefact : TypeArtefacts,
)
