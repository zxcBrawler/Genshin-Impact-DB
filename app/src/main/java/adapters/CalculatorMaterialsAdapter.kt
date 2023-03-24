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

class CalculatorMaterialsAdapter (private var materialsCalculator: ArrayList<CharMaterials>) :
    RecyclerView.Adapter<CalculatorMaterialsAdapter.MyHolder>() {
    class MyHolder (itemView : View) : RecyclerView.ViewHolder(itemView){
        val image: ImageView = itemView.findViewById(R.id.image)
        val name: TextView = itemView.findViewById(R.id.name)
        val quantity: TextView = itemView.findViewById(R.id.quantity)
    }

    override fun getItemCount(): Int {
       return materialsCalculator.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.materials_char_card, parent, false)
        return MyHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.name.text = materialsCalculator[position].material.nameMaterial
        holder.quantity.text = materialsCalculator[position].quantity.toString()
        Picasso.get().load(materialsCalculator[position].material.imageMaterial).fit().into(holder.image)
    }
}