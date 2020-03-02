package com.example

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_characters.*

class CharactersActivity : AppCompatActivity(), CharactersFragment.OnItemClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_characters)

       if(savedInstanceState == null) {
           val fragment = CharactersFragment()
           this.supportFragmentManager
               .beginTransaction()
               .add(R.id.listContainer, fragment)
               .commit()
       }
    }

    override fun onItemClicked(character: Character){
        if (isDetailViewAvailable())
            showFragmentDetails(character.id)
        else
            launchDetailActivity(character.id)
    }

    private fun isDetailViewAvailable() = detailContainer != null

    private fun showFragmentDetails(characterId: String){
        val detailFragment = DetailFragment.newInstance(characterId)


        supportFragmentManager
            .beginTransaction()
            .replace(R.id.detailContainer, detailFragment)
            .commit()
    }


    private fun launchDetailActivity(characterId: String){
        val intent: Intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("key_id", characterId)
        startActivity(intent)
    }
}