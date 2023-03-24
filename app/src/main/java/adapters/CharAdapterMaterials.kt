package adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.genshinhelper.R
import com.squareup.picasso.Picasso
import models.characterInfo.CharMaterials
import models.characterInfo.CharSkills

class CharAdapterMaterials (private val materialsList: ArrayList<CharMaterials>) :
    RecyclerView.Adapter<CharAdapterMaterials.MyHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.materials_char_card, parent, false)
        return MyHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.nameMaterial.text = materialsList[position].material.nameMaterial
        holder.quantity.text = materialsList[position].quantity.toString()

        Picasso.get().load(materialsList[position].material.imageMaterial).fit().centerCrop().into(holder.imageMaterial)
    }

    override fun getItemCount(): Int {
        return materialsList.size
    }


    class MyHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        val nameMaterial : TextView = itemView.findViewById(R.id.name)
        val quantity : TextView = itemView.findViewById(R.id.quantity)
        val imageMaterial : ImageView = itemView.findViewById(R.id.image)


    }
}