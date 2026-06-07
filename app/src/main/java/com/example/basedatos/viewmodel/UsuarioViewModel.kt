package com.example.basedatos.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.basedatos.model.Usuario
import com.example.basedatos.repositories.UsuarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class UsuarioViewModel(private val repository: UsuarioRepository) : ViewModel() {
    private val _usuarios = MutableStateFlow<List<Usuario>>(emptyList())
    val usuarios = _usuarios

    fun cargarUsuarios() {
        viewModelScope.launch {
            _usuarios.value = repository.getAllUsuarios()
        }
    }
}