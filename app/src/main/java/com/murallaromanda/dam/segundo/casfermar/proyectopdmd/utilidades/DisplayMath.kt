package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.utilidades

import android.content.res.Resources

class DisplayMath {
    companion object {

        fun toPd(numero: Int): Int {
            return (numero / Resources.getSystem().displayMetrics.density).toInt()
        }
    }
}