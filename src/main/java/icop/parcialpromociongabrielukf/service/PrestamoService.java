package icop.parcialpromociongabrielukf.service;

import icop.parcialpromociongabrielukf.dto.RequestPrestamoDto;
import icop.parcialpromociongabrielukf.dto.ResponsePrestamoDto;
import icop.parcialpromociongabrielukf.entity.Prestamo;
import icop.parcialpromociongabrielukf.entity.Socio;
import icop.parcialpromociongabrielukf.exception.ReglaNegocioException;
import icop.parcialpromociongabrielukf.exception.RecursoNoEncontradoException;
import icop.parcialpromociongabrielukf.repository.PrestamoRepository;
import icop.parcialpromociongabrielukf.repository.SocioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PrestamoService {

    private final PrestamoRepository prestamoRepository;
    private final SocioRepository socioRepository;

    public PrestamoService(PrestamoRepository prestamoRepository, SocioRepository socioRepository) {
        this.prestamoRepository = prestamoRepository;
        this.socioRepository = socioRepository;
    }

    @Transactional
    public ResponsePrestamoDto crear(RequestPrestamoDto request) {
        if (request == null || request.socioId() == null) {
            throw new ReglaNegocioException("socioId es obligatorio");
        }
        // 1) El socio debe existir (404)
        Socio socio = obtenerSocio(request.socioId());

        if (request.tituloLibro() == null || request.tituloLibro().isBlank() || request.fechaPrestamo() == null) {
            throw new ReglaNegocioException("tituloLibro y fechaPrestamo son obligatorios");
        }
        // 2) El libro no puede estar actualmente prestado (400)
        if (prestamoRepository.existsByTituloLibroAndDevueltoFalse(request.tituloLibro())) {
            throw new ReglaNegocioException("El libro " + request.tituloLibro() + " ya se encuentra prestado");
        }

        Prestamo prestamo = new Prestamo();
        prestamo.setTituloLibro(request.tituloLibro());
        prestamo.setFechaPrestamo(request.fechaPrestamo());
        prestamo.setDevuelto(false); // todo préstamo se crea con devuelto = false
        prestamo.setSocio(socio);
        socio.getPrestamos().add(prestamo); // mantiene sincronizado el lado inverso

        return aDto(prestamoRepository.save(prestamo));
    }

    @Transactional(readOnly = true)
    public List<ResponsePrestamoDto> listarPorSocio(Long socioId) {
        Socio socio = obtenerSocio(socioId);
        // La lista se obtiene navegando la relación del socio
        return socio.getPrestamos().stream().map(this::aDto).toList();
    }

    private Socio obtenerSocio(Long id) {
        return socioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Socio no encontrado con id: " + id));
    }

    private ResponsePrestamoDto aDto(Prestamo p) {
        return new ResponsePrestamoDto(
                p.getId(),
                p.getTituloLibro(),
                p.getFechaPrestamo(),
                p.isDevuelto(),
                p.getSocio().getNombreCompleto());
    }
}
