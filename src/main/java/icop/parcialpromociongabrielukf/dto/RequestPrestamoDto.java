package icop.parcialpromociongabrielukf.dto;

import java.time.LocalDate;

public record RequestPrestamoDto(Long socioId, String tituloLibro, LocalDate fechaPrestamo) {
}
