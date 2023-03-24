package fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.genshinhelper.Details
import com.example.genshinhelper.R
import com.squareup.picasso.Picasso
import models.Characters
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import server.ApiInterface
import server.RequestBuilder


class DetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view : View = inflater.inflate(R.layout.fragment_details, container, false)

        val activity : Details = activity as Details

        val id = activity.getIdChar()
        val element : ImageView = view.findViewById(R.id.element)
        val name : TextView = view.findViewById(R.id.full_name)
        val desc : TextView = view.findViewById(R.id.descChar)
        val birthday : TextView = view.findViewById(R.id.birthday)
        val weaponType : TextView = view.findViewById(R.id.weaponType)
        val roleType : TextView = view.findViewById(R.id.roleType)

        val star3 : ImageView = view.findViewById(R.id.star3)

        val fullImage : ImageView = view.findViewById(R.id.fullImageChar)
        val region : TextView = view.findViewById(R.id.region)
        val api : ApiInterface = RequestBuilder.buildRequest().create(ApiInterface::class.java)
        val getCharacterById : Call<Characters> = api.getCharacterById(id)
        getCharacterById.enqueue(object : Callback<Characters> {
            override fun onResponse(call: Call<Characters>, response: Response<Characters>) {
                if (response.isSuccessful) run {
                    val chars: Characters? = response.body()
                    Picasso.get().load(chars!!.element.imageElement).fit().into(element)
                    Picasso.get().load(chars!!.imageFullCharacter).fit().centerCrop().into(fullImage)
                    name.text = chars.nameCharacter
                    desc.text = chars.descriptionCharacter
                    birthday.text = "День рождения: ${chars.birthdayCharacter}"
                    region.text = "Регион: ${chars.region.nameRegion}"
                    weaponType.text = "Тип оружия: ${chars.typeWeapon.nameTypeWeapon}"
                    roleType.text = "Роль: ${chars.role.nameRole}"
                    if (chars.rarityCharacter == 5) {
                        star3.setImageResource(R.drawable.star5)
                    }
                    else {
                        star3.setImageResource(R.drawable.star4)
                    }
                }
            }

            override fun onFailure(call: Call<Characters>, t: Throwable) {
                Toast.makeText(activity, "Ошибка со стороны клиента ", Toast.LENGTH_LONG)
                    .show()
            }
        })
        return view
    }
}