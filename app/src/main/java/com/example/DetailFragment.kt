package com.example

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.data_character.*
import kotlinx.android.synthetic.main.header_character.*
import kotlinx.android.synthetic.main.item_character.view.*

class DetailFragment : Fragment() {

    companion object {
        fun newInstance(id: String): DetailFragment {
            val instance = DetailFragment()

            val args = Bundle()
            args.putString("key_id", id)

            instance.arguments = args

            return instance
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = arguments!!.getString("key_id")
        val character = CharactersRepo.findCharacterById(id)

        character?.let {
            with(character) {
                labelName.text = name
                labelTitle.text = title
                labelActor.text = actor
                labelBorn.text = born
                labelParents.text = "${father} & ${mother}"
                labelQuote.text = quote
                labelSpouse.text = spouse
                //button.text = house.name
                val overlayColor = House.getOverlayColor(character.house.name)
                imgOverlay.background =
                    context?.let { it1 -> ContextCompat.getDrawable(it1, overlayColor) }
                val baseColor = House.getBaseColor(character.house.name)
                btnHouse.backgroundTintList =
                    context?.let { it1 -> ContextCompat.getColorStateList(it1, baseColor) }
                val idDrawable = House.getIcon(character.house.name)
                val drawable = context?.let { it1 -> ContextCompat.getDrawable(it1, idDrawable) }
                btnHouse.setImageDrawable(drawable)

                Picasso.get()
                    .load(character.img)
                    .placeholder(R.drawable.test)
                    .into(imgCharacter)
            }
        }

        btnHouse.setOnClickListener {
            if(character != null){
                showDialog(character.house)
            }
        }
    }

    private fun showDialog(house: House){
        val dialog = HouseDialog.newInstance(house)
        dialog.show(childFragmentManager, "house_dialog")
    }
}
