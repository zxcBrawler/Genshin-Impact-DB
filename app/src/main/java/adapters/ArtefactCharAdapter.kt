package adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.genshinhelper.R
import com.squareup.picasso.Picasso
import models.Artefacts
import models.characterInfo.CharArtefacts
import models.characterInfo.CharWeapons


class ArtefactCharAdapter(private val charArtefacts: ArrayList<CharArtefacts>) :
    RecyclerView.Adapter<ArtefactCharAdapter.MyHolder>(){

    private lateinit var mListener : OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener){
        mListener = listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.artefact_card, parent, false)
        return MyHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.nameSet.text =  charArtefacts[position].artefactSets.nameArtefactSets
        when (charArtefacts[position].artefactSets.maxRarity) {
            5 -> {
                holder.stars.setImageResource(R.drawable.star5)
            }
            4 -> {
                holder.stars.setImageResource(R.drawable.star4)
            }
            else -> {
                holder.stars.setImageResource(R.drawable.star3)
            }
        }

    }

    override fun getItemCount(): Int {
        return charArtefacts.size
    }
    class MyHolder(itemView : View, listener: OnItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val nameSet: TextView = itemView.findViewById(R.id.name_set)
        val stars: ImageView = itemView.findViewById(R.id.star)

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

}