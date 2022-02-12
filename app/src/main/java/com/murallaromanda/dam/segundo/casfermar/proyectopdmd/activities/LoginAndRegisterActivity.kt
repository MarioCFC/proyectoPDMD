package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.R
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.fragments.LoginFragment
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.utilidades.GestorSharedPreferences

class LoginAndRegisterActivity : AppCompatActivity(){

    companion object{
        fun logOut(activity: AppCompatActivity){
            //TODO:Crear metodo para borrar el token y iniciar de nuevo el login
            activity.startActivity(
                Intent(activity,this::class.java).addFlags(
                    Intent.FLAG_ACTIVITY_CLEAR_TOP ).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP))
            GestorSharedPreferences(activity).resetPersonalToken()
            activity.finish()
        }
    }

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