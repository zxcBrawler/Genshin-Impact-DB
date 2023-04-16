package fragments

import adapters.ArtefactCharAdapter
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.genshinhelper.Details
import com.example.genshinhelper.DetailsArtefact
import com.example.genshinhelper.R
import models.characterInfo.CharArtefacts
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import server.ApiInterface
import server.RequestBuilder


class CharArtefactsFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View = inflater.inflate(R.layout.fragment_char_artefacts, container, false)
        val activity : Details = activity as Details
        val charId : ArrayList<CharArtefacts> = ArrayList()
        val id = activity.getIdChar()
        val recycler : RecyclerView = view.findViewById(R.id.recyclerArtefactsChar)
        recycler.layoutManager = LinearLayoutManager(view.context)
        val api : ApiInterface = RequestBuilder.buildRequest().create(ApiInterface::class.java)
        val getCharArtefacts : Call<ArrayList<CharArtefacts>> = api.getCharArtefacts()
        getCharArtefacts.enqueue(object :Callback<ArrayList<CharArtefacts>>{
            override fun onResponse(
                call: Call<ArrayList<CharArtefacts>>,
                response: Response<ArrayList<CharArtefacts>>
            ) {
                if (response.isSuccessful) run {
                    recycler.layoutManager = LinearLayoutManager(view.context)
                    recycler.setHasFixedSize(true)
                    val artefacts: ArrayList<CharArtefacts>? = response.body()
                    artefacts!!.forEach {
                        if (it.characterId == id) {
                            charId.add(it)
                        }
                    }

                    val adapter = ArtefactCharAdapter(charId!!)
                    recycler.adapter = adapter
                    adapter.setOnItemClickListener(object :
                        ArtefactCharAdapter.OnItemClickListener {
                        override fun onItemClick(position: Int) {
                            val intent = Intent(view.context, DetailsArtefact::class.java)
                            intent.putExtra("id", artefacts[position].artefactSetsId)
                            startActivity(intent)
                        }
                    })
                }
            }

                    override fun onFailure(call: Call<ArrayList<CharArtefacts>>, t: Throwable) {
                        TODO("Not yet implemented")
                    }
        })
        return view
    }
}