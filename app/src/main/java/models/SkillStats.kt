package models

data class SkillStats(
    var idSkillStats : Int,
    var nameSkillStats : String,
    var valueSkillStats : String,
    var levelSkillStats : Int,
    var skillId : Int,
    var skill : Skills,
)
