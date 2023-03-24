package fragments

import adapters.CharacterAdapter
import adapters.WeaponAdapter
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.genshinhelper.Details
import com.example.genshinhelper.DetailsWeapon
import com.example.genshinhelper.R

import models.Characters
import models.Weapons
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import server.ApiInterface
import server.RequestBuilder


class GameItems : Fragment() {

    private lateinit var weapons : ArrayList<Weapons>
    private lateinit var adapter : WeaponAdapter
    private lateinit var recycler : RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       val view : View = inflater.inflate(R.layout.fragment_game_items, container, false)
        recycler = view.findViewById(R.id.weaponsRecycler)
        recycler.layoutManager = GridLayoutManager(view.context,2)
        val api : ApiInterface = RequestBuilder.buildRequest().create(ApiInterface::class.java)
        val getWeapons : Call<ArrayList<Weapons>> = api.getWeapons()

        getWeapons.enqueue(object: Callback<ArrayList<Weapons>>{
            override fun onResponse(
                call: Call<ArrayList<Weapons>>,
                response: Response<ArrayList<Weapons>>
            ) {
                if (response.isSuccessful){
                    recycler.layoutManager = GridLayoutManager(view.context,2)
                    recycler.setHasFixedSize(true)
                    weapons = response.body()!!
                    adapter = WeaponAdapter(weapons!!)
                    recycler.adapter = adapter

                    adapter.setOnItemClickListener(object : WeaponAdapter.OnItemClickListener {
                        override fun onItemClick(position: Int) {
                            val intent = Intent(view.context, DetailsWeapon::class.java)
                            intent.putExtra("id", weapons[position].idWeapon)
                            startActivity(intent)
                        }
                    })
                }
            }

            override fun onFailure(call: Call<ArrayList<Weapons>>, t: Throwable) {
                Toast.makeText(view.context, "Ошибка со стороны клиента ", Toast.LENGTH_LONG)
                    .show()
            }
        })
        return view
    }
}