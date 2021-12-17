package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.R
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.fragments.LoginFragment

class FragmentManagerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_manager)


        //Probar como funciona
        supportFragmentManager.addOnBackStackChangedListener {
            if(supportFragmentManager.backStackEntryCount > 0){
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
            }else{
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
                supportActionBar?.hide()
            }
        }

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.contenedor_fragments,LoginFragment())
        fragmentTransaction.commit()

    }

    //Mirar para que sirve
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home){
            supportFragmentManager.popBackStack()
        }
        return super.onOptionsItemSelected(item)
    }

}