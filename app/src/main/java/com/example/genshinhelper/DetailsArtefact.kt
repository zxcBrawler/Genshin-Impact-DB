package com.example.genshinhelper

import ArtefactDetailsAdapter
import adapters.ArtefactCharAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import models.Artefacts
import models.Weapons
import models.referenceTables.ArtefactSets
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import server.ApiInterface
import server.RequestBuilder
import java.sql.Array

class DetailsArtefact : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_artefact)
        val id = intent.getIntExtra("id", -1)
        val api : ApiInterface = RequestBuilder.buildRequest().create(ApiInterface::class.java)
        val getArtefactSetById : Call<ArtefactSets> = api.getArtefactSetsId(id)
        val getArtefacts: Call<ArrayList<Artefacts>> = api.getArtefact()
        val artefacts : ArrayList<Artefacts> = arrayListOf()
        val nameSet : TextView = findViewById(R.id.name_set)
        supportActionBar?.hide()
        val twoPieceBonus : TextView = findViewById(R.id.twoPieceBonus)
        val fourPieceBonus : TextView = findViewById(R.id.fourPieceBonus)
        val recycler : RecyclerView = findViewById(R.id.recyclerArtefacts)
        recycler.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.HORIZONTAL, false)
        getArtefactSetById.enqueue(object: Callback<ArtefactSets>{
            override fun onResponse(call: Call<ArtefactSets>, response: Response<ArtefactSets>) {
                val artefact = response.body()
                nameSet.text = artefact?.nameArtefactSets
                twoPieceBonus.text = artefact?._2PieceBonus
                fourPieceBonus.text = artefact?._4PieceBonus
            }

            override fun onFailure(call: Call<ArtefactSets>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
        getArtefacts.enqueue(object: Callback<ArrayList<Artefacts>>{
            override fun onResponse(
                call: Call<ArrayList<Artefacts>>,
                response: Response<ArrayList<Artefacts>>
            ) {
                if (response.isSuccessful){
                    response.body()?.forEach {
                            if (it.artefactSets.nameArtefactSets == nameSet.text){
                                artefacts.add(it)
                            }
                    }
                    val adapter = ArtefactDetailsAdapter(artefacts!!)
                    recycler.adapter = adapter
                }
            }

            override fun onFailure(call: Call<ArrayList<Artefacts>>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}