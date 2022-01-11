package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.R
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.fragments.LoginFragment

class LoginAndRegisterActivity : AppCompatActivity(){

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
        fragmentTransaction.add(R.id.contenedor_fragments, LoginFragment())
        fragmentTransaction.commit()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home){
            supportFragmentManager.popBackStack()
        }
        return super.onOptionsItemSelected(item)
    }

}