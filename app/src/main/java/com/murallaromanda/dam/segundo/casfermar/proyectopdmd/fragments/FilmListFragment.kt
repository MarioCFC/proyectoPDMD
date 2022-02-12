package com.murallaromanda.dam.segundo.casfermar.proyectopdmd.fragments

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.R
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.activities.LoginAndRegisterActivity
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.adapters.ListaPeliculasAdapter
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.databinding.FragmentFilmListBinding
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.ErrorResponse
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.models.entities.Movie
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.utilidades.GestorSharedPreferences
import com.murallaromanda.dam.segundo.casfermar.proyectopdmd.utilidades.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FilmListFragment : Fragment() {
    private lateinit var binding: FragmentFilmListBinding
    private lateinit var activity: AppCompatActivity
    private lateinit var menuItem: Menu

    //TODO: Mirar de integrar la busqueda aquí ya que creo que no tenemos una lista tipo "mis peliculas" por lo que esta lista seria SearchList
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity = getActivity() as AppCompatActivity
        //Cambios en la ToolBar
        setHasOptionsMenu(true)
        activity.supportActionBar?.setTitle("Peliculas")

        //Enlazamos y ajustamos la disposicion y el numero de columnas del recycler
        binding = FragmentFilmListBinding.inflate(inflater, container, false)

        val layoutManager = GridLayoutManager(activity, 2)
        binding.rvFilmList.layoutManager = layoutManager

        /*Cargamos las peliculas mas populares en el RecyclerView*/
        rellenarRecyclerView(binding.rvFilmList)


        //TODO:Poner un unico boton de crear
        binding.fabUno.setOnClickListener() {
            val ft = activity?.supportFragmentManager?.beginTransaction()
            ft?.replace(R.id.contenedor_fragments, FilmCreateFragment())
            ft?.addToBackStack(null)
            ft?.commit()
        }

        return binding.root
    }


    override fun onResume() {
        super.onResume()
        rellenarRecyclerView(binding.rvFilmList)
    }

    fun rellenarRecyclerView(recyclerView: RecyclerView) {
        //TODO:Error al añadir al token Bearer, se produce una NumberFormatException
        var token: String = GestorSharedPreferences(requireContext()).getPersonalToken()!!
        var call = RetrofitService().getMovieService().getAllMovies(token)
        call.enqueue(object : Callback<List<Movie>> {
            override fun onResponse(call: Call<List<Movie>>, response: Response<List<Movie>>) {
                if (response.isSuccessful) {
                    val adapter =
                        ListaPeliculasAdapter(response.body()!!, activity)
                    recyclerView.adapter = adapter

                } else {
                    var error: ErrorResponse =
                        Gson().fromJson(response.errorBody()!!.string(), ErrorResponse::class.java)
                    Log.d("Main", error.message!!)
                }
            }

            override fun onFailure(call: Call<List<Movie>>, t: Throwable) {
                Log.d("Main", t.message!!)
            }
        })
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        activity.menuInflater.inflate(R.menu.menu_add_base, menu)
        menuItem = menu!!
        //Boton borrar
        menuItem.add(300, 0, 0, "Cerrar sesion")
            .setIcon(activity.getDrawable(R.drawable.ic_baseline_delete))
        menuItem.getItem(0).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var id: Int = item.itemId
        //Dandole a eliminar muestra un aviso, este metodo nos permite pasarle otro metodo para que ejecute en caso afirmativo
        when (id) {

            menuItem.getItem(0).itemId -> {
                avisoCerrarSesion(activity)
            }


        }
        return super.onOptionsItemSelected(item)
    }

    fun avisoCerrarSesion(activity: AppCompatActivity) {
        val dialogBuilder = AlertDialog.Builder(activity)
        dialogBuilder.setTitle("Cerrar sesion")
        dialogBuilder.setMessage("Quieres cerrar sesión?")
            .setCancelable(false)
            .setPositiveButton(
                activity.getString(R.string.AlertDialogPositiveButton),
                DialogInterface.OnClickListener { dialog, id ->
                    LoginAndRegisterActivity.logOut(activity)
                })
            .setNegativeButton(
                activity.getString(R.string.AlertDialogNegativeButton),
                DialogInterface.OnClickListener { dialog, id ->
                    dialog.cancel()
                })

        val alert = dialogBuilder.create()
        alert.setTitle("Cerrar sesion")
        alert.show()
    }
}