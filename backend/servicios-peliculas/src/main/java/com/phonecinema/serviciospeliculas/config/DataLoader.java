package com.phonecinema.serviciospeliculas.config;

import com.phonecinema.serviciospeliculas.model.Pelicula;
import com.phonecinema.serviciospeliculas.repository.PeliculaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataLoader implements CommandLineRunner {

    private final PeliculaRepository repo;

    public DataLoader(PeliculaRepository repo) {
        this.repo = repo;
    }

    @Override
    public void run(String... args) {

        if (repo.count() > 0) return; // evita duplicados

        // =============================
        // ACCIÓN
        // =============================

        repo.save(new Pelicula(null, "Joker",
                "Un comediante fallido se vuelve loco y se transforma en un criminal psicópata.",
                "Acción", "2h 2m", 2019,
                "https://image.tmdb.org/t/p/w500/udDclJoHjfjb8Ekgsd4FDteOkCU.jpg"));

        repo.save(new Pelicula(null, "Kill Bill",
                "Una asesina se despierta de un coma y busca venganza contra su antiguo jefe.",
                "Acción", "1h 51m", 2003,
                "https://image.tmdb.org/t/p/w500/v7TaX8kXMXs5yFFGR41guUDNcnB.jpg"));

        repo.save(new Pelicula(null, "Matrix",
                "Un experto en computadoras descubre que su mundo es una simulación total.",
                "Acción", "2h 16m", 1999,
                "https://image.tmdb.org/t/p/w500/aOIuZAjPaRIE6CMzbazvcHuHXDc.jpg"));

        repo.save(new Pelicula(null, "John Wick",
                "Un ex asesino a sueldo regresa al inframundo criminal.",
                "Acción", "1h 41m", 2014,
                "https://image.tmdb.org/t/p/w500/fZPSd91yGE9fCcCe6OoQr6E3Bev.jpg"));


        // =============================
        // COMEDIA
        // =============================

        repo.save(new Pelicula(null, "Loco por Mary",
            "Un hombre emplea a un detective privado para espiar a la mujer de la que está enamorado.",
            "Comedia", "1h 42m", 2003,
            "https://image.tmdb.org/t/p/w500/dSOfZ9Ni0Nd7dAXR2CT2JI4ljsl.jpg"));

        repo.save(new Pelicula(null, "Irene, yo y mi otro yo",
            "Charlie es un policía con doble personalidad que se enamora de la misma mujer.",
            "Comedia", "1h 56m", 2000,
            "https://image.tmdb.org/t/p/w500/9Jx2FzdKUYqF3BojG65xjqIG9aj.jpg"));

        repo.save(new Pelicula(null, "Scary Movie",
            "Parodia de los filmes de asesinatos donde un homicida acecha adolescentes.",
            "Comedia", "1h 28m", 2000,
            "https://image.tmdb.org/t/p/w500/fHWR3YplPQWciYzxEity2kyDoDn.jpg"));

        repo.save(new Pelicula(null, "Supercool",
            "Dos estudiantes buscan disfrutar su fiesta de graduación.",
            "Comedia", "1h 59m", 2007,
            "https://image.tmdb.org/t/p/w500/ftUDFRYywWzajElI2RT9NIno2PS.jpg"));


        // =============================
        // ROMANCE
        // =============================

        repo.save(new Pelicula(null, "Lost in Translation",
            "Una amistad inusual se forma entre dos americanos en Tokio.",
            "Romance", "1h 42m", 2003,
            "https://image.tmdb.org/t/p/w500/ybXq8xTs6VMx3hM4RDYMXaDZwcz.jpg"));

        repo.save(new Pelicula(null, "Cuestión de Tiempo",
            "Tim Lake puede viajar en el tiempo para conquistar a Mary.",
            "Romance", "2h 3m", 2013,
            "https://image.tmdb.org/t/p/w500/6b8zipp2Z4Io5RVuaATBNKtlbE6.jpg"));

        repo.save(new Pelicula(null, "500 días con ella",
            "Tom recuerda los 500 días con Summer para entender su relación.",
            "Romance", "1h 42m", 2009,
            "https://image.tmdb.org/t/p/w500/xziurNdvxWZkgonZ5ZaGRB9YdLt.jpg"));

        repo.save(new Pelicula(null, "Orgullo y Prejuicio",
            "Elizabeth Bennet conoce al apuesto Sr. Darcy.",
            "Romance", "2h 8m", 2005,
            "https://image.tmdb.org/t/p/w500/oixzLjpyaLagLa8UREts1NiHr6F.jpg"));



        // =============================
        // DRAMA
        // =============================

        repo.save(new Pelicula(null, "Eterno Resplandor de una mente sin recuerdos",
                "Una pareja borra sus recuerdos tras una ruptura dolorosa.",
                "Drama", "1h 48m", 2004,
                "https://image.tmdb.org/t/p/w500/5MwkWH9tYHv3mV9OdYTMR5qreIz.jpg"));

        repo.save(new Pelicula(null, "Réquiem por un sueño",
                "Una viuda se vuelve adicta mientras su hijo enfrenta su propia adicción.",
                "Drama", "1h 42m", 2000,
                "https://image.tmdb.org/t/p/w500/nOd6vjEmzCT0k4VYqsA2hwyi87C.jpg"));

        repo.save(new Pelicula(null, "American History X",
                "Exneonazi intenta evitar que su hermano repita sus errores.",
                "Drama", "1h 59m", 1998,
                "https://image.tmdb.org/t/p/w500/h2cDqHvnZkycBJKoF7WhcQ2MX1V.jpg"));

        repo.save(new Pelicula(null, "Sueños de Fuga",
                "Un hombre inocente es enviado a una prisión corrupta y sentenciado por doble asesinato.",
                "Drama", "2h 2m", 1994,
                "https://image.tmdb.org/t/p/w500/lyQBXzOQSuE59IsHyhrp0qIiPAz.jpg"));
    }
}
