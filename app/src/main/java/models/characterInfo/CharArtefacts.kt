package models.characterInfo

import models.Characters
import models.referenceTables.ArtefactSets

data class CharArtefacts (
        var idCharArtefact : Int,
        var characterId : Int,
        var artefactSetsId : Int,
        var artefactSets : ArtefactSets,
        var character : Characters,
        )
