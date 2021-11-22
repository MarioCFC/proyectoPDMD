package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.utilidades

import android.os.AsyncTask
import android.os.Parcel
import android.os.Parcelable
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import android.util.JsonReader
import android.util.Log
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.Pelicula
import java.io.Console


class JSONObject() : AsyncTask<Void, Void, Void>() {

    fun hacerConsulta() {
        var consulta: String =
            "https://api.themoviedb.org/3/movie/808?api_key=1e07834b3cf658fea5163c09e574b152"
        var current = ""

        var url: URL
        var conexion: HttpURLConnection? = null


        url = URL(consulta)
        conexion = url.openConnection() as HttpURLConnection

        var input = conexion?.inputStream

        readJsonStream(input)
    }

    @Throws(IOException::class)
    fun readJsonStream(inp: InputStream?) {
        val reader = JsonReader(InputStreamReader(inp, "UTF-8"))
        readPelicula(reader)
    }

    fun readPelicula(reader: JsonReader) {

        var titulo: String? = null
        var genero: String? = null
        var director: String? = "director"
        var valoracion: String? = null
        var sinopsis: String? = null
        var urlImagen: String? = null
        var urlBanner: String? = null

        reader.beginObject()

        while (reader.hasNext()) {
            try {
                val name = reader.nextName()
                if (name == "original_title") {
                    titulo = reader.nextString()
                } else if (name == "genres") {
                    //reader.nextString()
                } else if (name == "vote_average") {
                    valoracion = reader.nextDouble().toString()
                } else if (name == "overview") {
                    sinopsis = reader.nextString()
                } else if (name == "poster_path") {
                    urlImagen = reader.nextString()
                } else if (name == "backdrop_path") {
                    urlBanner = reader.nextString()
                } else
                    reader.skipValue()
            }catch (e:Exception){
                Log.d("MainActivity",e.message.toString())
            }
        }
            reader.endObject()

            var pel = titulo?.let {
                genero?.let { it1 ->
                    director?.let { it2 ->
                        valoracion?.let { it3 ->
                            sinopsis?.let { it4 ->
                                urlImagen?.let { it5 ->
                                    urlBanner?.let { it6 ->
                                        Pelicula(
                                            it,
                                            it1, it2, it3, it4, it5, it6
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
    }

    override fun doInBackground(vararg p0: Void?): Void? {
        hacerConsulta()
        return null
    }
}