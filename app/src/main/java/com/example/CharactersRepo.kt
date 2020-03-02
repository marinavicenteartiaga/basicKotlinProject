package com.example

import android.content.Context
import android.view.textclassifier.TextLinks
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.CharactersRepo.characters
import org.json.JSONArray
import org.json.JSONObject
import kotlin.random.Random

const val URL_CHARACTERS = "https://5e56ca8f4c695f001432f75d.mockapi.io/characters"

object CharactersRepo {
    var characters: MutableList<Character> = mutableListOf()
    //custom get
    /*get() {
        if (field.isEmpty())
            field.addAll(dummyCharacters())
        return field
    }*/

    fun requestCharacters(
        context: Context,
        success: ((MutableList<Character>) -> Unit),
        error: (() -> Unit)
    ) { //llamada síncrona = no retorna nada
        val queue = Volley.newRequestQueue(context)
        if (characters.isEmpty()) {
            val request = JsonArrayRequest(Request.Method.GET, URL_CHARACTERS, null,
                Response.Listener <JSONArray>{ response ->
                    characters = parseCharacters(response)
                    success.invoke(characters)
                }, Response.ErrorListener{ volleyError ->
                    error.invoke()
                })
            queue.add(request)
        } else {
            success.invoke(characters)
        }
    }

    private fun parseCharacters(jsonArray: JSONArray): MutableList<Character> {
        val characters = mutableListOf<Character>()
        for (index in 0..(jsonArray.length() - 1)) {
            val character = parseCharacter(jsonArray.getJSONObject(index))
            characters.add(character)
        }
        return characters
    }

    private fun parseCharacter(jsonCharacter: JSONObject): Character {
        return Character(
            jsonCharacter.getString("id"),
            jsonCharacter.getString("name"),
            jsonCharacter.getString("born"),
            jsonCharacter.getString("title"),
            jsonCharacter.getString("actor"),
            jsonCharacter.getString("quote"),
            jsonCharacter.getString("father"),
            jsonCharacter.getString("mother"),
            jsonCharacter.getString("spouse"),
            jsonCharacter.getString("img"),
            parseHouse(jsonCharacter.getJSONObject("house"))
        )
    }

    private fun parseHouse(jsonHouse: JSONObject): House {
        return House(
            jsonHouse.getString("name"),
            jsonHouse.getString("region"),
            jsonHouse.getString("img"),
            jsonHouse.getString("words")
        )
    }

    //Datos dummy porque aún no hay servidor
    /*private fun dummyCharacters(): MutableList<Character> {
        return (1..10).map {
            intToCharacter(it)
        }.toMutableList()
    }*/

    fun findCharacterById(id: String): Character? {
        return characters.find { character ->
            character.id == id
        }
    }

    /*private fun intToCharacter(int: Int): Character {
        return Character(
            name = "Personaje ${int}",
            title = "Título ${int}",
            born = "Nació en ${int}",
            actor = "Actor ${int}",
            quote = "Frase ${int}",
            father = "Padre ${int}",
            mother = "Mother ${int}",
            spouse = "Esposo/a ${int}",
            house = dummyHouse()
        )
    }

    private fun dummyHouse(): House {
        val ids = arrayOf("stark", "lannister", "tyrell", "arryn", "baratheon", "targarian")
        val randomIdPosition = Random.nextInt(ids.size)

        return House(
            name = ids[randomIdPosition],
            region = "Región",
            words = "Lema"
        )
    }*/


}