package fragments

import adapters.CharSkillsAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.genshinhelper.Details
import com.example.genshinhelper.R
import models.characterInfo.CharSkills
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import server.ApiInterface
import server.RequestBuilder


class CharSkillFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View = inflater.inflate(R.layout.fragment_char_skill, container, false)
        val activity : Details = activity as Details
        val recyclerSkills : RecyclerView = view.findViewById(R.id.recyclerSkills)
        val recyclerPassive : RecyclerView = view.findViewById(R.id.recyclerPassives)
        recyclerSkills.layoutManager = LinearLayoutManager(view.context)
        recyclerPassive.layoutManager = LinearLayoutManager(view.context)
        val api : ApiInterface = RequestBuilder.buildRequest().create(ApiInterface::class.java)
        val getSkillsChars : Call<ArrayList<CharSkills>> = api.getCharSkills()
        val skills : ArrayList<CharSkills> = ArrayList()
        val passives : ArrayList<CharSkills> = ArrayList()
        val id = activity.getIdChar()

        getSkillsChars.enqueue(object : Callback<ArrayList<CharSkills>>{
            override fun onResponse(
                call: Call<ArrayList<CharSkills>>,
                response: Response<ArrayList<CharSkills>>
            ) {
                if (response.isSuccessful) run {
                    recyclerSkills.layoutManager = LinearLayoutManager(view.context)
                    recyclerPassive.layoutManager = LinearLayoutManager(view.context)
                    recyclerPassive.setHasFixedSize(true)
                    recyclerSkills.setHasFixedSize(true)
                    val allSkills : ArrayList<CharSkills>? = response.body()
                    allSkills!!.forEach {
                        if(it.skill.typeSkillId == 4 && it.characterId == id){
                            passives.add(it)
                        }else if (it.characterId == id) {
                            skills.add(it)
                        }
                    }
                    val adapterFirst = CharSkillsAdapter(passives!!)
                    recyclerPassive.adapter = adapterFirst
                    val adapterSecond = CharSkillsAdapter(skills!!)
                    recyclerSkills.adapter = adapterSecond

                }
            }

            override fun onFailure(call: Call<ArrayList<CharSkills>>, t: Throwable) {
                Toast.makeText(view.context,"Ошибка со стороны клиента", Toast.LENGTH_LONG).show()
            }
        })
        return view
    }
}