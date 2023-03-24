package fragments

import adapters.WeaponCharAdapter
import android.content.Intent
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
import models.characterInfo.CharWeapons
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import server.ApiInterface
import server.RequestBuilder


class CharWeaponsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View = inflater.inflate(R.layout.fragment_char_weapons, container, false)
        val activity : Details = activity as Details
        val charId : ArrayList<CharWeapons> = ArrayList()
        val id = activity.getIdChar()
        val recycler : RecyclerView = view.findViewById(R.id.recyclerWeaponChar)
        recycler.layoutManager = LinearLayoutManager(view.context)
        val api : ApiInterface = RequestBuilder.buildRequest().create(ApiInterface::class.java)
        val getWeaponChars : Call<ArrayList<CharWeapons>> = api.getCharWeapons()
        getWeaponChars.enqueue(object : Callback<ArrayList<CharWeapons>>{
            override fun onResponse(
                call: Call<ArrayList<CharWeapons>>,
                response: Response<ArrayList<CharWeapons>>
            ) {
                if (response.isSuccessful) run {
                    recycler.layoutManager = LinearLayoutManager(view.context)
                    recycler.setHasFixedSize(true)
                    val chars: ArrayList<CharWeapons>? = response.body()
                    chars!!.forEach {
                        if (it.characterId == id) {
                            charId.add(it)
                        }
                    }

                    val adapter = WeaponCharAdapter(charId!!)
                    recycler.adapter = adapter
                    adapter.setOnItemClickListener(object : WeaponCharAdapter.OnItemClickListener {
                        override fun onItemClick(position: Int) {
                            val intent = Intent(view.context, Details::class.java)
                            intent.putExtra("id", chars[position].weaponId)
                            startActivity(intent)
                        }
                    })


                }
            }

            override fun onFailure(call: Call<ArrayList<CharWeapons>>, t: Throwable) {
                Toast.makeText(activity, "Ошибка со стороны клиента ", Toast.LENGTH_LONG)
                    .show()
            }
        })
        return view
    }
}