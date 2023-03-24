package adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.genshinhelper.R
import com.squareup.picasso.Picasso
import models.characterInfo.CharWeapons


class WeaponCharAdapter (private val charWeaponsList: ArrayList<CharWeapons>) :
    RecyclerView.Adapter<WeaponCharAdapter.MyHolder>(){

    private lateinit var mListener : OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener){
        mListener = listener
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.weapon_char_card, parent, false)
        return MyHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.nameWeapon.text =  charWeaponsList[position].weapon.nameWeapon
        holder.atkWeapon.text = "Базовая атака: ${charWeaponsList[position].weapon.baseAtkWeapon}"
        holder.substatWeapon.text = "Саб-стат: ${charWeaponsList[position].weapon.substatWeapon}"
        Picasso.get().load(charWeaponsList[position].weapon.imageWeapon).fit().centerCrop().into(holder.imageWeapon)
        when (charWeaponsList[position].weapon.rarityWeapon) {
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
        return charWeaponsList.size
    }
    class MyHolder(itemView : View, listener: OnItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val nameWeapon: TextView = itemView.findViewById(R.id.name_weapon)
        val imageWeapon: ImageView = itemView.findViewById(R.id.image_weapon)
        val atkWeapon: TextView = itemView.findViewById(R.id.atk_weapon)
        val substatWeapon: TextView = itemView.findViewById(R.id.substat_weapon)
        val stars: ImageView = itemView.findViewById(R.id.star)

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

}