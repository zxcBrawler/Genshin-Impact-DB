package adapters

import adapters.CharacterAdapter.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.genshinhelper.R
import com.squareup.picasso.Picasso
import models.Characters

class CharacterAdapter (private var charList: ArrayList<Characters>) :
    RecyclerView.Adapter<MyHolder>() {

    private lateinit var mListener : OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener){
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.chars_card, parent, false)
        return MyHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
       holder.nameChar.text = charList[position].nameCharacter
       Picasso.get().load(charList[position].imageIconCharacter).fit().into(holder.imageChar)
       Picasso.get().load(charList[position].element.imageElement).fit().into(holder.imageElement)
    }

    override fun getItemCount(): Int {
        return charList.size
    }
    class MyHolder(itemView : View, listener: OnItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val nameChar: TextView = itemView.findViewById(R.id.name_chars_text)
        val imageChar: ImageView = itemView.findViewById(R.id.image_char)
        val imageElement: ImageView = itemView.findViewById(R.id.image_element)

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }
}