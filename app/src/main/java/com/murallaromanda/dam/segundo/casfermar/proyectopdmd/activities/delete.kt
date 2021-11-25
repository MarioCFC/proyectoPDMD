package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.activities

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.databinding.ActivityDeleteBinding
import kr.co.prnd.readmore.ReadMoreTextView
import java.text.AttributedString
import android.util.Xml


import android.content.res.XmlResourceParser
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.R


class delete : AppCompatActivity() {
    private lateinit var binding: ActivityDeleteBinding
    private var loremIpsum:String ="sdfjksd"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete)
        binding = ActivityDeleteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        readMoreSpawn()
    }



    private fun addEditText() {
        // Create EditText

        val editText = EditText(this)
        editText.setText("Hola")
        editText.setBackgroundResource(android.R.color.transparent);

        editText.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT)
        editText.id = View.generateViewId()
        var constraintLayout = binding.constraintFilmDetailLayout
        constraintLayout.addView(editText)
        var constraintSet = ConstraintSet()
        constraintSet.clone(constraintLayout)





        constraintSet.connect(editText.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END);
        constraintSet.connect(editText.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START);
        constraintSet.connect(editText.getId(), ConstraintSet.TOP, binding.FilmDetaiIvCaratula.id, ConstraintSet.BOTTOM);
        constraintSet.applyTo(constraintLayout)

    }

    fun readMoreSpawn(){
        val readMore = ReadMoreTextView(this)
        readMore.setText(loremIpsum)
        readMore.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT)
        readMore.id = View.generateViewId()
        readMore.inputType = EditText.AUTOFILL_TYPE_NONE

        var constraintLayout = binding.constraintFilmDetailLayout
        constraintLayout.addView(readMore)
        var constraintSet = ConstraintSet()
        constraintSet.clone(constraintLayout)


        constraintSet.connect(readMore.getId(), ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END);
        constraintSet.connect(readMore.getId(), ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START);
        constraintSet.connect(readMore.getId(), ConstraintSet.TOP, binding.FilmDetaiIvCaratula.id, ConstraintSet.BOTTOM);
        constraintSet.applyTo(constraintLayout)






    }



}