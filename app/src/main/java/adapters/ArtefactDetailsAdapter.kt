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

class ArtefactDetailsAdapter(private val artefacts: ArrayList<Artefacts>) :
    RecyclerView.Adapter<ArtefactDetailsAdapter.MyHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.artefact_details, parent, false)
        return MyHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        Picasso.get().load(artefacts[position].imageArtefact).fit().into(holder.imageDetails)
    }

    override fun getItemCount(): Int {
        return artefacts.size
    }
    class MyHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val imageDetails: ImageView = itemView.findViewById(R.id.image_details)

    }

}