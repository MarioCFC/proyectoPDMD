package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.FragmentActivity
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.R
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.fragments.FilmListFragment
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.fragments.LoginFragment
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.utilidades.GestorSharedPreferences

class FilmListFragmentManagerActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_manager)
        var mTopToolbar =  findViewById(R.id.my_toolbar) as Toolbar;
        setSupportActionBar(mTopToolbar);

        supportFragmentManager.addOnBackStackChangedListener {
            if(supportFragmentManager.backStackEntryCount > 0){
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
            }else{
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
            }
        }
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.contenedor_fragments,FilmListFragment())
        fragmentTransaction.commit()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home){
            supportFragmentManager.popBackStack()
        }
        return super.onOptionsItemSelected(item)
    }

}