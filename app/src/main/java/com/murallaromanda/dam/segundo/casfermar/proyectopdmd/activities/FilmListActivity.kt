package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.activities

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.recyclerview.widget.GridLayoutManager
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.R
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.adapters.ListaPeliculasAdapter
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.databinding.ActivityFilmListBinding
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.data.PeliculaDataMock
import android.view.WindowInsets
import android.app.Activity
import android.graphics.Insets


class FilmListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFilmListBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_film_list)

        binding = ActivityFilmListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val peliculaData = PeliculaDataMock()
        val lista = peliculaData.getLista()

        val layoutManager = GridLayoutManager(this,3)
        val adapter = ListaPeliculasAdapter(lista,this)

        binding.rvListaPeliculas.layoutManager = layoutManager
        binding.rvListaPeliculas.adapter = adapter

    }



    //Necesario hacerlo mas preciso, tener en cuenta la dp
    fun calcularColumnasLista() : Int{
        val resultadoDivision = (getScreenWidth(this) /185)
        if (resultadoDivision % 1 > 0.7)
            return resultadoDivision.toInt() - 1
        else if(resultadoDivision % 1 < 0.5){
            return  resultadoDivision.toInt() + 1
        }else
            return resultadoDivision.toInt()
    }
    // 2.403 y 3.428
    fun getScreenWidth(activity: Activity): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val windowMetrics = activity.windowManager.currentWindowMetrics
            val insets: Insets = windowMetrics.windowInsets
                .getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
            windowMetrics.bounds.width() - insets.left - insets.right
        } else {
            val displayMetrics = DisplayMetrics()
            activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
            displayMetrics.widthPixels
        }
    }





}