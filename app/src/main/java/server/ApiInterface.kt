package server

import models.Artefacts
import models.Characters
import models.Materials
import models.Weapons
import models.characterInfo.CharArtefacts
import models.characterInfo.CharMaterials
import models.characterInfo.CharSkills
import models.characterInfo.CharWeapons
import models.manyToManyTables.DaysMaterials
import models.manyToManyTables.SkillMaterials
import models.referenceTables.ArtefactSets
import models.referenceTables.Foods
import models.userInfo.User
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*
import java.sql.RowId

interface ApiInterface {

    @GET("Users/")
    fun getUsers() : Call<List<User>>

    @POST("Users/")
    fun register(@Body info : User) : Call<User>

    @GET("Users/{id}")
    fun getUserById(@Path("id") userId : Int) : Call<User>

    @PUT("Users/{id}")
    fun changeProfileInfo(@Path("id") userId : Int, @Body info : User) : Call<User>

    @GET("Characters/")
    fun getCharacters() : Call<ArrayList<Characters>>

    @GET("Characters/{id}")
    fun getCharacterById(@Path("id") charID : Int) : Call<Characters>

    @GET("CharMaterials/")
    fun getCharMaterial() : Call<ArrayList<CharMaterials>>

    @GET("CharWeapons/")
    fun getCharWeapons() : Call<ArrayList<CharWeapons>>

    @GET("CharSkills/")
    fun getCharSkills() : Call<ArrayList<CharSkills>>

    @GET("ArtefactSets/")
    fun getArtefactSets() : Call<ArrayList<ArtefactSets>>
    @GET("ArtefactSets/{id}")
    fun getArtefactSetsId(@Path("id") id : Int) : Call<ArtefactSets>

    @GET("DaysMaterials/")
    fun getMaterialsByDay() : Call<ArrayList<DaysMaterials>>

    @GET("SkillMaterials/")
    fun getSkillMaterials() : Call<ArrayList<SkillMaterials>>

    @GET("Artefacts/")
    fun getArtefact() : Call<ArrayList<Artefacts>>

    @GET("CharArtefacts/")
    fun getCharArtefacts() : Call<ArrayList<CharArtefacts>>

    @GET("Weapons/")
    fun getWeapons() : Call<ArrayList<Weapons>>

    @GET("Weapons/{id}")
    fun getWeaponsById(@Path("id") id : Int) : Call<Weapons>

    @GET("Food/")
    fun getFood() : Call<ArrayList<Foods>>
}