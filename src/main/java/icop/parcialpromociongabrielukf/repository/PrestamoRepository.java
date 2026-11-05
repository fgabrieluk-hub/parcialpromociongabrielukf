package icop.parcialpromociongabrielukf.repository;

import icop.parcialpromociongabrielukf.entity.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {

    // Libro actualmente prestado: mismo título con devuelto = false
    boolean existsByTituloLibroAndDevueltoFalse(String tituloLibro);
}
