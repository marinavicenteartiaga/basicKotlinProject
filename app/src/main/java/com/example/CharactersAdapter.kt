package com.example

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import kotlinx.android.synthetic.main.item_character.view.*
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.CharactersRepo.characters
import com.squareup.picasso.Picasso

class CharactersAdapter: RecyclerView.Adapter<CharactersAdapter.CharacterViewHolder> {

    constructor(): super(){
        itemClickListener = null //si es null, añadir ?
    }

    constructor(itemClickListener: ((Character, Int) -> Unit)): super(){
        this.itemClickListener = itemClickListener
    }

    private val items = mutableListOf<Character>()

    private val itemClickListener: ((Character, Int) -> Unit)?

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_character, parent, false)
        return CharacterViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val item = items[position]
        holder.character = item
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setCharacters(character: MutableList<Character>){
        items.clear()
        items.addAll(characters)
        notifyDataSetChanged()
    }

    inner class CharacterViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var character: Character? = null
            set(value) {
                value?.let {
                    itemView.labelName.text = value.name
                    itemView.labelTitle.text = value.title

                    val overlayColor = House.getOverlayColor(value.house.name)
                    itemView.imgOverlay.background = ContextCompat.getDrawable(itemView.context, overlayColor)

                    Picasso.get()
                        .load(value.img)
                        .placeholder(R.drawable.test)
                        .into(itemView.imgCharacter)

                }

                field = value
            }
        init{ //aseguramos con un cast que el personaje no es nulo
           itemView.setOnClickListener {
                character?.let{
                    itemClickListener?.invoke(character as Character, adapterPosition)
                }
            }
        }
    }
}