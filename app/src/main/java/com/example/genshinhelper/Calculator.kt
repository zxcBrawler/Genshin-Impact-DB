package com.example.genshinhelper

import adapters.CalculatorMaterialsAdapter
import adapters.CalculatorMaterialsSkillsAdapter
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.slider.Slider
import com.squareup.picasso.Picasso

import models.Characters
import models.characterInfo.CharMaterials
import models.characterInfo.CharSkills
import models.manyToManyTables.SkillMaterials
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import server.ApiInterface
import server.RequestBuilder


class Calculator : AppCompatActivity(),  AdapterView.OnItemSelectedListener {

    val api : ApiInterface = RequestBuilder.buildRequest().create(ApiInterface::class.java)

    private lateinit var adapter : CalculatorMaterialsAdapter
    private lateinit var adapter2 : CalculatorMaterialsSkillsAdapter
    private lateinit var recycler : RecyclerView
    private lateinit var normalRecycler : RecyclerView
    private lateinit var skillRecycler : RecyclerView
    private lateinit var burstRecycler : RecyclerView
    private lateinit var imageChar : ImageView
    private lateinit var slider : Slider
    private lateinit var spinner : Spinner
    private lateinit var label : TextView
    private lateinit var normalAttackImage : ImageView
    private lateinit var elementalSkillImage : ImageView
    private lateinit var elementalBurstImage : ImageView
    private lateinit var normalLevels : Spinner
    private lateinit var skillLevels : Spinner
    private lateinit var burstLevels : Spinner
    private lateinit var tooltips : Tooltips

    private var levels = arrayOf(1,2,3,4,5,6,7,8,9,10,11,12,13)
    private var names : ArrayList<String> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator)
        tooltips = Tooltips(this)
        label = findViewById(R.id.levelLabel)
        spinner = findViewById(R.id.charSpinner)
        normalLevels = findViewById(R.id.normalAttackSpinner)
        skillLevels  = findViewById(R.id.elementalSkillSpinner)
        burstLevels  = findViewById(R.id.elementalBurstSpinner)
        slider = findViewById(R.id.slider)
        recycler = findViewById(R.id.materialsRecycler)
        normalRecycler = findViewById(R.id.recyclerNormalAtk)
        burstRecycler = findViewById(R.id.recyclerBurst)
        skillRecycler = findViewById(R.id.recyclerSkillAtk)
        imageChar = findViewById(R.id.charImage)
        normalAttackImage = findViewById(R.id.normalAttackImg)
        elementalSkillImage = findViewById(R.id.elementalSkillImg)
        elementalBurstImage = findViewById(R.id.elementalBurstImg)
        recycler.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false)
        normalRecycler.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false)
        skillRecycler.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false)
        burstRecycler.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL, false)

        label.setOnClickListener {
            tooltips.levelTooltip.showAlignBottom(slider)
        }
        val getCharNames : Call<ArrayList<Characters>> = api.getCharacters()

        supportActionBar?.hide()

        var aa = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, levels)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        with(normalLevels)
        {
            adapter = aa
            normalLevels.setSelection(0, false)
            onItemSelectedListener = this@Calculator
        }
        with(skillLevels)
        {
            adapter = aa
            skillLevels.setSelection(0, false)
            onItemSelectedListener = this@Calculator
        }

        with(burstLevels)
        {
            adapter = aa
            burstLevels.setSelection(0, false)
            onItemSelectedListener = this@Calculator
        }
        getCharNames.enqueue(object : Callback<ArrayList<Characters>>{
            override fun onResponse(
                call: Call<ArrayList<Characters>>,
                response: Response<ArrayList<Characters>>
            ) {
                if (response.isSuccessful){
                    response.body()?.forEach{
                        names.add(it.nameCharacter)
                    }
                    val arrayAdapter = ArrayAdapter(this@Calculator,android.R.layout.simple_spinner_dropdown_item,names)
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    with(spinner)
                    {
                        adapter = arrayAdapter
                        onItemSelectedListener = this@Calculator
                        prompt = "Выберите персонажа"
                        setPopupBackgroundResource(R.color.black)
                        gravity = Gravity.CENTER

                    }
                }
            }

            override fun onFailure(call: Call<ArrayList<Characters>>, t: Throwable) {
                Toast.makeText(applicationContext,"Ошибка со стороны клиента", Toast.LENGTH_LONG).show()
            }

        })
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val getSkillMaterials : Call<ArrayList<SkillMaterials>> = api.getSkillMaterials()

        when (parent?.id) {
            R.id.normalAttackSpinner -> {
                var materialNormals : ArrayList<SkillMaterials> = arrayListOf()
                materialNormals.clear()
                getSkillMaterials.enqueue(object: Callback<ArrayList<SkillMaterials>>{
                    override fun onResponse(
                        call: Call<ArrayList<SkillMaterials>>,
                        response: Response<ArrayList<SkillMaterials>>
                    ) {
                        response.body()?.forEach{
                            if(it.level == position + 1){
                                materialNormals.add(it)
                            }
                        }
                        adapter2 = CalculatorMaterialsSkillsAdapter(materialNormals!!)
                        normalRecycler.adapter = adapter2
                    }

                    override fun onFailure(call: Call<ArrayList<SkillMaterials>>, t: Throwable) {
                        TODO("Not yet implemented")
                    }
                })
            }
            R.id.elementalSkillSpinner -> {
                getSkillMaterials.enqueue(object: Callback<ArrayList<SkillMaterials>>{
                    override fun onResponse(
                        call: Call<ArrayList<SkillMaterials>>,
                        response: Response<ArrayList<SkillMaterials>>
                    ) {
                        var materialSkill : ArrayList<SkillMaterials> = arrayListOf()
                        response.body()?.forEach{
                            if(it.level == position + 1){
                                materialSkill.add(it)
                            }
                        }
                        adapter2 = CalculatorMaterialsSkillsAdapter(materialSkill!!)
                        skillRecycler.adapter = adapter2
                    }

                    override fun onFailure(call: Call<ArrayList<SkillMaterials>>, t: Throwable) {
                        Toast.makeText(applicationContext,"Ошибка со стороны клиента", Toast.LENGTH_LONG).show()
                    }

                })

            }
            R.id.elementalBurstSpinner -> {
                getSkillMaterials.enqueue(object: Callback<ArrayList<SkillMaterials>>{
                    override fun onResponse(
                        call: Call<ArrayList<SkillMaterials>>,
                        response: Response<ArrayList<SkillMaterials>>
                    ) {
                        var materialBurst : ArrayList<SkillMaterials> = arrayListOf()
                        response.body()?.forEach{
                            if(it.level == position + 1){
                                materialBurst.add(it)
                            }
                        }
                        adapter2 = CalculatorMaterialsSkillsAdapter(materialBurst!!)
                        burstRecycler.adapter = adapter2
                    }
                    override fun onFailure(call: Call<ArrayList<SkillMaterials>>, t: Throwable) {
                        Toast.makeText(applicationContext,"Ошибка со стороны клиента", Toast.LENGTH_LONG).show()
                    }
                })
            }
            R.id.charSpinner -> {
                val material : ArrayList<CharMaterials> = arrayListOf()
                material.clear()
                val getCharById : Call<Characters> = api.getCharacterById(names.indexOf(names[position]) + 1)
                val getCharSkills : Call<ArrayList<CharSkills>> = api.getCharSkills()
                getCharSkills.enqueue(object : Callback<ArrayList<CharSkills>>{
                    override fun onResponse(
                        call: Call<ArrayList<CharSkills>>,
                        response: Response<ArrayList<CharSkills>>
                    ) {
                        if(response.isSuccessful){
                            response.body()?.forEach{
                                if(it.characterId == spinner.selectedItemPosition + 1){
                                    material.clear()
                                    when (it.skill.typeSkillId) {
                                        1 -> {
                                            Picasso.get().load(it.skill.imageSkill).fit().into(normalAttackImage)
                                        }
                                        2 -> {
                                            Picasso.get().load(it.skill.imageSkill).fit().into(elementalSkillImage)
                                        }
                                        3 -> {
                                            Picasso.get().load(it.skill.imageSkill).fit().into(elementalBurstImage)
                                        }
                                    }
                                }
                            }
                        }
                    }

                    override fun onFailure(call: Call<ArrayList<CharSkills>>, t: Throwable) {
                        Toast.makeText(applicationContext,"Ошибка со стороны клиента", Toast.LENGTH_LONG).show()
                    }

                })
                slider.addOnChangeListener(Slider.OnChangeListener { slider, value, fromUser ->
                    run {
                        material.clear()
                        val getCharMaterial : Call<ArrayList<CharMaterials>> = api.getCharMaterial()
                        getCharMaterial.enqueue(object : Callback<ArrayList<CharMaterials>>{
                            override fun onResponse(
                                call: Call<ArrayList<CharMaterials>>,
                                response: Response<ArrayList<CharMaterials>>
                            ) {
                                if(response.isSuccessful){
                                    response.body()?.forEach{
                                        if(it.level == value.toInt() && it.characterId == spinner.selectedItemPosition + 1){
                                            material.add(it)
                                        }
                                    }
                                    adapter = CalculatorMaterialsAdapter(material!!)
                                    recycler.adapter = adapter
                                }
                            }

                            override fun onFailure(call: Call<ArrayList<CharMaterials>>, t: Throwable) {
                                Toast.makeText(applicationContext,"Ошибка со стороны клиента", Toast.LENGTH_LONG).show()
                            }
                        })
                        label.text = "Уровень ${value.toInt()}"

                    }

                })
                getCharById.enqueue(object : Callback<Characters>{
                    override fun onResponse(call: Call<Characters>, response: Response<Characters>) {
                        if (response.isSuccessful){
                            val character = response.body()
                            Picasso.get().load(character?.imageFullCharacter).fit().centerCrop().into(imageChar)
                        }
                    }

                    override fun onFailure(call: Call<Characters>, t: Throwable) {
                        Toast.makeText(applicationContext,"Ошибка со стороны клиента", Toast.LENGTH_LONG).show()
                    }
                })
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        Picasso.get().load(R.drawable.alhaitam).fit().centerCrop().into(imageChar)
    }
}