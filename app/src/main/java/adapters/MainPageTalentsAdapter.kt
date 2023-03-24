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
import java.util.Calendar;
import java.util.Date;
import models.manyToManyTables.DaysMaterials

class MainPageTalentsAdapter (private var daysMaterialsList: ArrayList<DaysMaterials>) :
    RecyclerView.Adapter<MainPageTalentsAdapter.MyHolder>() {


    private lateinit var mListener : OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener){
        mListener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.talents_by_days_main, parent, false)
        return MyHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.nameTalent.text = daysMaterialsList[position].material.nameMaterial
        Picasso.get().load(daysMaterialsList[position].material.imageMaterial).fit().into(holder.imageTalent)
    }

    override fun getItemCount(): Int {
        return daysMaterialsList.size
    }

    class MyHolder(itemView : View, listener: OnItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val nameTalent: TextView = itemView.findViewById(R.id.name_talent_text)
        val imageTalent: ImageView = itemView.findViewById(R.id.image_talent)

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }





}