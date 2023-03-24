package adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.genshinhelper.R
import com.squareup.picasso.Picasso
import models.Characters
import models.characterInfo.CharMaterials
import models.manyToManyTables.SkillMaterials

class CalculatorMaterialsSkillsAdapter (private var skillMaterials: ArrayList<SkillMaterials>) :
    RecyclerView.Adapter<CalculatorMaterialsSkillsAdapter.MyHolder>() {
    class MyHolder (itemView : View) : RecyclerView.ViewHolder(itemView){
        val image: ImageView = itemView.findViewById(R.id.image)
        val name: TextView = itemView.findViewById(R.id.name)
        val quantity: TextView = itemView.findViewById(R.id.quantity)
    }

    override fun getItemCount(): Int {
        return skillMaterials.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.materials_char_card, parent, false)
        return MyHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.name.text = skillMaterials[position].material.nameMaterial
        holder.quantity.text = skillMaterials[position].quantity.toString()
        Picasso.get().load(skillMaterials[position].material.imageMaterial).fit().into(holder.image)
    }
}