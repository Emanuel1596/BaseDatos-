package com.example.basedatos

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.basedatos.databinding.ActivityMainBinding
import com.example.basedatos.model.Usuario
import com.example.basedatos.repositories.UsuarioRepository
import com.example.basedatos.viewmodel.UsuarioViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: UsuarioViewModel
    private lateinit var repository: UsuarioRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = MyApplication.getDatabase(this)
        repository = UsuarioRepository(db.usuarioDao())
        viewModel = UsuarioViewModel(repository)

        binding.btnRegistrar.setOnClickListener {
            val nombre = binding.edtNombre.text.toString()
            val edad = binding.edtEdad.text.toString().toIntOrNull()

            if (nombre.isEmpty() || edad == null) {
                Toast.makeText(this, "Escribe nombre y edad válidos", Toast.LENGTH_SHORT).show()
            } else {
                lifecycleScope.launch {
                    repository.addUsuario(Usuario(nombre = nombre, edad = edad))
                    viewModel.cargarUsuarios()
                }
            }
        }

        binding.btnMostrar.setOnClickListener {
            viewModel.cargarUsuarios()
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.usuarios.collect { listaDeUsuarios ->
                    if (listaDeUsuarios.isEmpty()) {
                        binding.tvwList.text = "La base de datos está vacía."
                    } else {
                        val stringBuilder = StringBuilder()

                        listaDeUsuarios.forEach { usuario ->
                            stringBuilder.append("ID: ${usuario.id} | Nombre: ${usuario.nombre} | Edad: ${usuario.edad}\n")
                        }

                        binding.tvwList.text = stringBuilder.toString()
                    }
                }
            }
        }
    }
}