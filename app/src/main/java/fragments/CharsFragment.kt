package fragments

import adapters.CharacterAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.genshinhelper.Details
import com.example.genshinhelper.R
import com.example.genshinhelper.Tooltips
import com.skydoves.balloon.ArrowPositionRules
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.BalloonAnimation
import com.skydoves.balloon.BalloonSizeSpec
import models.Characters
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import server.ApiInterface
import server.RequestBuilder
import java.util.*


class CharsFragment : Fragment() {

    private  var chars : ArrayList<Characters> = arrayListOf()
    val filteredList = arrayListOf<Characters>()
    private lateinit var tooltips : Tooltips
    private lateinit var adapter : CharacterAdapter
    private lateinit var recycler : RecyclerView
    private lateinit var cryoCheckBox: CheckBox
    private lateinit var electroCheckBox: CheckBox
    private lateinit var geoCheckBox: CheckBox
    private lateinit var anemoCheckBox: CheckBox
    private lateinit var hydroCheckBox: CheckBox
    private lateinit var dendroCheckBox: CheckBox
    private lateinit var pyroCheckBox: CheckBox
    private lateinit var star5: CheckBox
    private lateinit var star4: CheckBox

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View =inflater.inflate(R.layout.fragment_chars, container,false)
        recycler = view.findViewById(R.id.recyclerView)
        tooltips = Tooltips(view.context)
        recycler.layoutManager = GridLayoutManager(view.context,2)
        val searchBar : SearchView = view.findViewById(R.id.search)
        cryoCheckBox = view.findViewById(R.id.cryo)
        electroCheckBox = view.findViewById(R.id.electro)
        geoCheckBox = view.findViewById(R.id.geo)
        anemoCheckBox = view.findViewById(R.id.anemo)
        hydroCheckBox = view.findViewById(R.id.hydro)
        dendroCheckBox = view.findViewById(R.id.dendro)
        pyroCheckBox = view.findViewById(R.id.pyro)
        star5 = view.findViewById(R.id.star5)
        star4 = view.findViewById(R.id.star4)

        cryoCheckBox.isChecked = false
        electroCheckBox.isChecked = false
        geoCheckBox.isChecked = false
        anemoCheckBox.isChecked = false
        hydroCheckBox.isChecked = false
        dendroCheckBox.isChecked = false
        pyroCheckBox.isChecked = false
        star5.isChecked = false
        star4.isChecked = false



        val api : ApiInterface = RequestBuilder.buildRequest().create(ApiInterface::class.java)
        val getCharacters : Call<ArrayList<Characters>> = api.getCharacters()
        getCharacters.enqueue(object : Callback<ArrayList<Characters>> {
            override fun onResponse(call: Call<ArrayList<Characters>>, response: Response<ArrayList<Characters>>) {
                if (response.isSuccessful) run {
                    recycler.setHasFixedSize(true)
                    chars = response.body()!!
                    adapter = CharacterAdapter(chars)
                    recycler.adapter = adapter
                    adapter.setOnItemClickListener(object : CharacterAdapter.OnItemClickListener {
                        override fun onItemClick(position: Int) {
                            val intent = Intent(requireView().context, Details::class.java)
                            intent.putExtra("id", chars[position].idCharacter)
                            startActivity(intent)
                        }
                    })
                    checkBoxStars(star5)
                    checkBoxStars(star4)
                    elementCheckBox(cryoCheckBox)
                    elementCheckBox(electroCheckBox)
                    elementCheckBox(dendroCheckBox)
                    elementCheckBox(pyroCheckBox)
                    elementCheckBox(hydroCheckBox)
                    elementCheckBox(geoCheckBox)
                    elementCheckBox(anemoCheckBox)
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
                    tooltips.searchTooltip.showAlignBottom(searchBar)
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

    fun checkBoxStars(checkBox: CheckBox){
        checkBox.setOnCheckedChangeListener{ _, isChecked ->
            for (item in chars){
                if (isChecked &&
                    item.rarityCharacter == checkBox.text.toString().toInt()){
                    filteredList.add(item)
                    adapter = CharacterAdapter(filteredList)
                    cryoCheckBox.isEnabled = false
                    electroCheckBox.isEnabled = false
                    geoCheckBox.isEnabled = false
                    anemoCheckBox.isEnabled = false
                    hydroCheckBox.isEnabled = false
                    dendroCheckBox.isEnabled = false
                    pyroCheckBox.isEnabled = false
                }
                else if (!isChecked && item.rarityCharacter == checkBox.text.toString().toInt()){
                    filteredList.removeIf {item.rarityCharacter == checkBox.text.toString().toInt()}
                    adapter = CharacterAdapter(filteredList)
                    cryoCheckBox.isEnabled = true
                    electroCheckBox.isEnabled = true
                    geoCheckBox.isEnabled = true
                    anemoCheckBox.isEnabled = true
                    hydroCheckBox.isEnabled = true
                    dendroCheckBox.isEnabled = true
                    pyroCheckBox.isEnabled = true
                }


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
    fun elementCheckBox(checkBox: CheckBox){
        checkBox.setOnCheckedChangeListener { _, isChecked ->
            for (item in chars){
                if (isChecked && item.element.nameElement == checkBox.text){
                    filteredList.add(item)
                    adapter = CharacterAdapter(filteredList)
                    star5.isEnabled = false
                    star4.isEnabled = false
                }
                else if (!isChecked && item.element.nameElement == checkBox.text){
                    filteredList.removeIf {it.element.nameElement == checkBox.text}
                    adapter = CharacterAdapter(filteredList)
                    star5.isEnabled = true
                    star4.isEnabled = true
                }

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