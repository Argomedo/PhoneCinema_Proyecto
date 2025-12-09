package com.phonecinema.serviciosusuario;

import com.phonecinema.serviciosusuario.dto.LoginDTO;
import com.phonecinema.serviciosusuario.dto.UsuarioRegistroDTO;
import com.phonecinema.serviciosusuario.dto.AuthResponseDTO;
import com.phonecinema.serviciosusuario.model.Rol;
import com.phonecinema.serviciosusuario.model.Usuario;
import com.phonecinema.serviciosusuario.repository.UsuarioRepository;
import com.phonecinema.serviciosusuario.service.UsuarioServiceImpl;
import com.phonecinema.serviciosusuario.repository.RolRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioServiceImplTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private RolRepository rolRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // ------------------------------------------------------------
    // registrarUsuario()
    // ------------------------------------------------------------

    @Test
    void registrarUsuario_correcto() {

        UsuarioRegistroDTO dto = new UsuarioRegistroDTO();
        dto.setNombre("Diego");
        dto.setEmail("d@mail.com");
        dto.setPassword("Abc123*#");
        dto.setRol("USUARIO");
        dto.setFotoPerfilUrl("");

        Rol rol = new Rol(1, "USUARIO");

        when(rolRepository.findByNombre("USUARIO")).thenReturn(Optional.of(rol));
        when(passwordEncoder.encode("Abc123*#")).thenReturn("hashed");
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(inv -> {
            Usuario u = inv.getArgument(0);
            u.setIdUsuario(10);
            return u;
        });

        Usuario u = service.registrarUsuario(dto);

        assertNotNull(u.getIdUsuario());
        assertEquals("Diego", u.getNombre());
        assertEquals("hashed", u.getPassword());
        assertEquals("USUARIO", u.getRol().getNombre());
    }

    @Test
    void registrarUsuario_contraseñaInvalida_debeFallar() {

        UsuarioRegistroDTO dto = new UsuarioRegistroDTO();
        dto.setNombre("Diego");
        dto.setEmail("d@mail.com");
        dto.setPassword("abc");  // inválida
        dto.setRol("USUARIO");

        assertThrows(IllegalArgumentException.class,
                () -> service.registrarUsuario(dto));
    }

    @Test
    void registrarUsuario_rolNoEncontrado() {

        UsuarioRegistroDTO dto = new UsuarioRegistroDTO();
        dto.setNombre("Diego");
        dto.setEmail("d@mail.com");
        dto.setPassword("Abc123*#");
        dto.setRol("NO_EXISTE");

        when(rolRepository.findByNombre("NO_EXISTE")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> service.registrarUsuario(dto));
    }

    // ------------------------------------------------------------
    // loginUsuario()
    // ------------------------------------------------------------

    @Test
    void loginUsuario_correcto() {

        LoginDTO dto = new LoginDTO();
        dto.setEmail("a@mail.com");
        dto.setPassword("Abc123*#");

        Rol rol = new Rol(1, "ADMIN");

        Usuario usuario = new Usuario();
        usuario.setIdUsuario(1);
        usuario.setNombre("Admin");
        usuario.setEmail("a@mail.com");
        usuario.setPassword("hashedpass");
        usuario.setRol(rol);

        when(usuarioRepository.findByEmail("a@mail.com")).thenReturn(usuario);
        when(passwordEncoder.matches("Abc123*#", "hashedpass")).thenReturn(true);

        AuthResponseDTO response = service.loginUsuario(dto);

        assertEquals("Admin", response.getNombre());
        assertEquals("ADMIN", response.getRol());
    }

    @Test
    void loginUsuario_fallaCredenciales() {

        LoginDTO dto = new LoginDTO();
        dto.setEmail("a@mail.com");
        dto.setPassword("wrong");

        when(usuarioRepository.findByEmail("a@mail.com")).thenReturn(null);

        assertThrows(RuntimeException.class,
                () -> service.loginUsuario(dto));
    }

    // ------------------------------------------------------------
    // obtenerPorId()
    // ------------------------------------------------------------

    @Test
    void obtenerPorId_correcto() {

        Usuario u = new Usuario();
        u.setIdUsuario(5);
        when(usuarioRepository.findById(5)).thenReturn(Optional.of(u));

        Usuario result = service.obtenerPorId(5);

        assertEquals(5, result.getIdUsuario());
    }

    @Test
    void obtenerPorId_noExiste() {
        when(usuarioRepository.findById(99)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.obtenerPorId(99));
    }

    // ------------------------------------------------------------
    // obtenerTodos()
    // ------------------------------------------------------------

    @Test
    void obtenerTodos_retornaLista() {
        Usuario u = new Usuario();
        u.setIdUsuario(1);
        when(usuarioRepository.findAll()).thenReturn(List.of(u));

        List<Usuario> lista = service.obtenerTodos();

        assertEquals(1, lista.size());
    }

    // ------------------------------------------------------------
    // actualizarRol()
    // ------------------------------------------------------------

    @Test
    void actualizarRol_correcto() {

        Usuario u = new Usuario();
        u.setIdUsuario(1);

        Rol rol = new Rol(2, "ADMIN");

        when(usuarioRepository.findById(1)).thenReturn(Optional.of(u));
        when(rolRepository.findByNombre("ADMIN")).thenReturn(Optional.of(rol));
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(inv -> inv.getArgument(0));

        Usuario actualizado = service.actualizarRol(1, "ADMIN");

        assertEquals("ADMIN", actualizado.getRol().getNombre());
    }

    @Test
    void actualizarRol_usuarioNoExiste() {

        when(usuarioRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> service.actualizarRol(1, "ADMIN"));
    }

    // ------------------------------------------------------------
    // cambiarPassword()
    // ------------------------------------------------------------

    @Test
    void cambiarPassword_correcto() {

        Usuario u = new Usuario();
        u.setIdUsuario(1);
        u.setPassword("hashOld");

        when(usuarioRepository.findById(1)).thenReturn(Optional.of(u));
        when(passwordEncoder.matches("oldPass", "hashOld")).thenReturn(true);
        when(passwordEncoder.encode("Abc123*#")).thenReturn("newHash");

        service.cambiarPassword(1, "oldPass", "Abc123*#");

        verify(usuarioRepository).save(u);
        assertEquals("newHash", u.getPassword());
    }

    @Test
    void cambiarPassword_contraseñaActualIncorrecta() {

        Usuario u = new Usuario();
        u.setPassword("hashOld");

        when(usuarioRepository.findById(1)).thenReturn(Optional.of(u));
        when(passwordEncoder.matches("wrong", "hashOld")).thenReturn(false);

        assertThrows(RuntimeException.class,
                () -> service.cambiarPassword(1, "wrong", "Abc123*#"));
    }

    @Test
    void cambiarPassword_contraseñaNuevaInvalida() {

        Usuario u = new Usuario();
        u.setPassword("hashOld");

        when(usuarioRepository.findById(1)).thenReturn(Optional.of(u));
        when(passwordEncoder.matches("oldPass", "hashOld")).thenReturn(true);

        assertThrows(IllegalArgumentException.class,
                () -> service.cambiarPassword(1, "oldPass", "abc"));
    }

}
