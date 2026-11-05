package icop.parcialpromociongabrielukf.dto;

import java.time.LocalDate;

public record ResponsePrestamoDto(Long id, String tituloLibro, LocalDate fechaPrestamo,
                                  boolean devuelto, String nombreSocio) {
}
