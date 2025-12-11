package com.example.phonecinemaapp.ui.home

import com.example.phonecinemaapp.data.repository.PeliculasRepositoryRemote
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: HomeViewModel
    private lateinit var repository: PeliculasRepositoryRemote

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk()
        viewModel = HomeViewModel(repository)
    }

    @Test
    fun homeViewModel_estado_inicial() = runTest {
        val uiState = viewModel.uiState.first()

        assertEquals(emptyList<Categoria>(), uiState.categorias)
        assertEquals("Invitado", uiState.nombreUsuario)
        assertFalse(uiState.loading)
        assertEquals(null, uiState.error)
    }

    @Test
    fun cargarPeliculas_error() = runTest {
        coEvery {
            repository.getAll()
        } throws Exception("Error de conexión")

        // Crear viewModel (ejecutará init y fallará)
        val viewModelConError = HomeViewModel(repository)

        val uiState = viewModelConError.uiState.first()

        assertFalse(uiState.loading)
        assertEquals("Error de conexión", uiState.error)
        assertEquals(emptyList<Categoria>(), uiState.categorias)
    }

    @Test
    fun cargarPeliculas_error_mensaje_generico() = runTest {
        coEvery {
            repository.getAll()
        } throws Exception()

        // Crear viewModel
        val viewModelConErrorGenerico = HomeViewModel(repository)

        val uiState = viewModelConErrorGenerico.uiState.first()

        assertEquals("Error al cargar películas", uiState.error)
    }

    @Test
    fun loadUser_actualiza_nombre_usuario() = runTest {
        viewModel.loadUser("Juan Pérez")

        val uiState = viewModel.uiState.first()
        assertEquals("Juan Pérez", uiState.nombreUsuario)
    }

    @Test
    fun loadUser_varias_veces() = runTest {
        viewModel.loadUser("Nombre 1")
        var uiState = viewModel.uiState.first()
        assertEquals("Nombre 1", uiState.nombreUsuario)

        viewModel.loadUser("Nombre 2")
        uiState = viewModel.uiState.first()
        assertEquals("Nombre 2", uiState.nombreUsuario)

        viewModel.loadUser("Nombre 3")
        uiState = viewModel.uiState.first()
        assertEquals("Nombre 3", uiState.nombreUsuario)
    }

    @Test
    fun cargarPeliculas_sin_peliculas() = runTest {
        coEvery {
            repository.getAll()
        } returns emptyList()

        val viewModelSinPeliculas = HomeViewModel(repository)

        val uiState = viewModelSinPeliculas.uiState.first()

        assertEquals(emptyList<Categoria>(), uiState.categorias)
        assertEquals(null, uiState.error)
        assertFalse(uiState.loading)
    }

}