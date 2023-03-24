package models

import models.referenceTables.TypeDomains

data class Domains(
    var idDomain : Int,
    var nameDomain : String,
    var descriptionDomain : String,
    var typeDomainId : Int,
    var typeDomain : TypeDomains,
)
