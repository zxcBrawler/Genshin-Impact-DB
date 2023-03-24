package adapters

import adapters.CharSkillsAdapter.MyHolder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.genshinhelper.R
import com.squareup.picasso.Picasso
import models.characterInfo.CharSkills
import models.characterInfo.CharWeapons

class CharSkillsAdapter (private val skillsList: ArrayList<CharSkills>) :
    RecyclerView.Adapter<MyHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.skills_card, parent, false)
        return MyHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.nameSkill.text = skillsList[position].skill.nameSkill
        holder.descriptionSkill.text = skillsList[position].skill.descriptionSkill
        holder.typeSkill.text = skillsList[position].skill.typeSkill.nameTypeSkill
        Picasso.get().load(skillsList[position].skill.imageSkill).fit().centerCrop().into(holder.imageSkill)
    }

    override fun getItemCount(): Int {
        return skillsList.size
    }

    class MyHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val nameSkill : TextView = itemView.findViewById(R.id.name_skill)
        val descriptionSkill : TextView = itemView.findViewById(R.id.description_skill)
        val typeSkill : TextView = itemView.findViewById(R.id.type_skill)
        val imageSkill : ImageView = itemView.findViewById(R.id.image_skill)


    }
}