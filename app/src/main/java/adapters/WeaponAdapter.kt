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
import models.Weapons

class WeaponAdapter (private var weaponList: ArrayList<Weapons>) :
    RecyclerView.Adapter<WeaponAdapter.MyHolder>() {


    private lateinit var mListener : OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener){
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.weapon_card, parent, false)
        return MyHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.nameWeapon.text = weaponList[position].nameWeapon
        Picasso.get().load(weaponList[position].imageWeapon).fit().into(holder.imageWeapon)
        when (weaponList[position].rarityWeapon) {
            5 -> {
                holder.stars.setImageResource(R.drawable.star5)
            }
            4 -> {
                holder.stars.setImageResource(R.drawable.star4)
            }
            3 -> {
                holder.stars.setImageResource(R.drawable.star3)
            }
        }
    }

    override fun getItemCount(): Int {
        return weaponList.size
    }
    class MyHolder(itemView : View, listener: WeaponAdapter.OnItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val nameWeapon: TextView = itemView.findViewById(R.id.name_weapon)
        val imageWeapon: ImageView = itemView.findViewById(R.id.image_weapon)
        val stars : ImageView = itemView.findViewById(R.id.star)


        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }
}