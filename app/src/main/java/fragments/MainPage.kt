package fragments

import adapters.CharacterAdapter
import adapters.MainPageTalentsAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.genshinhelper.Calculator
import com.example.genshinhelper.Details
import com.example.genshinhelper.R
import com.example.genshinhelper.TimerResign
import models.Characters
import models.manyToManyTables.DaysMaterials
import models.referenceTables.Days
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import server.ApiInterface
import server.RequestBuilder
import java.util.*
import kotlin.collections.ArrayList

class MainPage : Fragment() {

    private lateinit var adapterTalents : MainPageTalentsAdapter
    private lateinit var adapterWeapons : MainPageTalentsAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val day : Int = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
        val view : View =inflater.inflate(R.layout.fragment_main_page, container,false)
        val recyclerTalents : RecyclerView  = view.findViewById(R.id.talentsDay)
        val recyclerWeapons : RecyclerView  = view.findViewById(R.id.weaponsDay)
        val button : Button = view.findViewById(R.id.gotoresign)
        val button2 : Button = view.findViewById(R.id.goToCalculator)

        val api : ApiInterface = RequestBuilder.buildRequest().create(ApiInterface::class.java)
        recyclerTalents.layoutManager = LinearLayoutManager(view.context,LinearLayoutManager.HORIZONTAL, false)
        recyclerWeapons.layoutManager = LinearLayoutManager(view.context,LinearLayoutManager.HORIZONTAL, false)
        val getMaterialsByDay : Call<ArrayList<DaysMaterials>> = api.getMaterialsByDay()

        button.setOnClickListener {
            val intent = Intent(view.context, TimerResign::class.java)
            startActivity(intent)
        }
        button2.setOnClickListener {
            val intent = Intent(view.context, Calculator::class.java)
            startActivity(intent)
        }
        getMaterialsByDay.enqueue(object : Callback<ArrayList<DaysMaterials>>{
            override fun onResponse(
                call: Call<ArrayList<DaysMaterials>>,
                response: Response<ArrayList<DaysMaterials>>
            ) {
                if (response.isSuccessful) {
                    val materials: ArrayList<DaysMaterials>? = response.body()
                    val sortedListTalents : ArrayList<DaysMaterials> = arrayListOf()
                    val sortedListWeapons : ArrayList<DaysMaterials> = arrayListOf()
                    materials?.forEach {
                        if (it.daysId == day - 1 && it.material.typeMaterialId == 2) {
                            sortedListTalents.add(it)
                        }
                        else if (it.daysId == day - 1 && it.material.typeMaterialId == 7){
                            sortedListWeapons.add(it)
                        }
                    }
                    adapterTalents = MainPageTalentsAdapter(sortedListTalents)
                    adapterWeapons = MainPageTalentsAdapter(sortedListWeapons)
                    recyclerWeapons.adapter = adapterWeapons
                    recyclerTalents.adapter = adapterTalents
                            Log.e("material", sortedListTalents.toString())

                    adapterWeapons.setOnItemClickListener(object : MainPageTalentsAdapter.OnItemClickListener{
                        override fun onItemClick(position: Int) {
                            val intent = Intent(view.context, Details::class.java)
                            intent.putExtra("id", sortedListWeapons[position].material.idMaterial)
                            startActivity(intent)
                        }
                    })
                    adapterTalents.setOnItemClickListener(object : MainPageTalentsAdapter.OnItemClickListener{
                                override fun onItemClick(position: Int) {
                                    val intent = Intent(view.context, Details::class.java)
                                    intent.putExtra("id", sortedListTalents[position].material.idMaterial)
                                    startActivity(intent)
                                }
                            })


                }
            }

            override fun onFailure(call: Call<ArrayList<DaysMaterials>>, t: Throwable) {
                Toast.makeText(view.context, "Ошибка со стороны клиента", Toast.LENGTH_SHORT).show()
            }
        })
        return view
    }
}