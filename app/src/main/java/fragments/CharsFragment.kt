package fragments

import adapters.CharacterAdapter
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.genshinhelper.Details
import com.example.genshinhelper.R
import models.Characters
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import server.ApiInterface
import server.RequestBuilder
import java.util.*
import kotlin.collections.ArrayList


class CharsFragment : Fragment() {

    private lateinit var chars : ArrayList<Characters>
    private lateinit var charsText : ArrayList<String>
    private lateinit var adapter : CharacterAdapter
    private lateinit var recycler : RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View =inflater.inflate(R.layout.fragment_chars, container,false)
        recycler = view.findViewById(R.id.recyclerView)
        recycler.layoutManager = GridLayoutManager(view.context,2)
        val searchBar : SearchView = view.findViewById(R.id.search)

        val api : ApiInterface = RequestBuilder.buildRequest().create(ApiInterface::class.java)
        val getCharacters : Call<ArrayList<Characters>> = api.getCharacters()
        getCharacters.enqueue(object : Callback<ArrayList<Characters>> {
            override fun onResponse(call: Call<ArrayList<Characters>>, response: Response<ArrayList<Characters>>) {
                if (response.isSuccessful) run {
                    recycler.setHasFixedSize(true)
                    chars = response.body()!!
                    adapter = CharacterAdapter(chars!!)
                    recycler.adapter = adapter
                    adapter.setOnItemClickListener(object : CharacterAdapter.OnItemClickListener{
                        override fun onItemClick(position: Int) {
                            val intent = Intent(view.context, Details::class.java)
                            intent.putExtra("id", chars[position].idCharacter)
                            startActivity(intent)
                        }
                    })
                }
            }

            override fun onFailure(call: Call<ArrayList<Characters>>, t: Throwable) {
                Toast.makeText(view.context, "Ошибка со стороны клиента ", Toast.LENGTH_LONG)
                    .show()
            }
        })

        searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
               return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { filter(it) }
                return false
            }
        })
        return view
    }
    fun filter (text : String){
        val filteredList : ArrayList<Characters> = ArrayList()
        for (item in chars){
            if(item.nameCharacter.lowercase(Locale.ROOT).contains(text.lowercase(Locale.ROOT))){
                filteredList.add(item)
            }
        }

        if (filteredList.isEmpty()){
            adapter = CharacterAdapter(filteredList)
            recycler.adapter = adapter
        }else {
            adapter = CharacterAdapter(filteredList)
            recycler.adapter = adapter
            adapter.setOnItemClickListener(object : CharacterAdapter.OnItemClickListener{
                override fun onItemClick(position: Int) {
                    val intent = Intent(requireView().context, Details::class.java)
                    intent.putExtra("id", filteredList[position].idCharacter)
                    startActivity(intent)
                }
            })
        }
    }
}