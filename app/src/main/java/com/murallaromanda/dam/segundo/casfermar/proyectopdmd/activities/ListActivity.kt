package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.activities

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.Display
import android.view.WindowManager
import android.widget.GridLayout
import android.widget.LinearLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.R
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.adapters.ListaPeliculasAdapter
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.databinding.ActivityListBinding
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.data.PeliculaDataMock
import okhttp3.internal.Util
import android.view.Gravity
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.utilidades.DisplayMath
import android.view.WindowInsets

import android.view.WindowMetrics

import android.app.Activity
import android.graphics.Insets

import androidx.annotation.NonNull





class ListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val peliculaData = PeliculaDataMock()
        val lista = peliculaData.getLista()

        val layoutManager = GridLayoutManager(this,calcularColumnasLista())
        val adapter = ListaPeliculasAdapter(lista,this)

        binding.rvListaPeliculas.layoutManager = layoutManager
        binding.rvListaPeliculas.adapter = adapter

    }



    //Necesario hacerlo mas preciso, tener en cuenta la dp
    fun calcularColumnasLista() : Int{
        val resultadoDivision = (getScreenWidth(this) / (resources.getDimension(R.dimen.ancho_item_pelicula) + resources.getDimension(R.dimen.margen_horizontal_item_pelicula)))

        val densidad = DisplayMetrics().densityDpi

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