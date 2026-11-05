package icop.parcialpromociongabrielukf.service;

import icop.parcialpromociongabrielukf.dto.RequestSocioDto;
import icop.parcialpromociongabrielukf.dto.ResponseSocioDto;
import icop.parcialpromociongabrielukf.entity.Socio;
import icop.parcialpromociongabrielukf.exception.ReglaNegocioException;
import icop.parcialpromociongabrielukf.exception.RecursoNoEncontradoException;
import icop.parcialpromociongabrielukf.repository.SocioRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SocioService {

    private final SocioRepository socioRepository;

    public SocioService(SocioRepository socioRepository) {
        this.socioRepository = socioRepository;
    }

    @Transactional(readOnly = true)
    public List<ResponseSocioDto> listar() {
        return socioRepository.findAll(Sort.by("id")).stream().map(this::aDto).toList();
    }

    @Transactional(readOnly = true)
    public ResponseSocioDto buscarPorId(Long id) {
        return aDto(obtenerSocio(id));
    }

    @Transactional
    public ResponseSocioDto crear(RequestSocioDto request) {
        if (request == null || vacio(request.nombre()) || vacio(request.apellido()) || vacio(request.dni())) {
            throw new ReglaNegocioException("nombre, apellido y dni son obligatorios");
        }
        if (socioRepository.existsByDni(request.dni())) {
            throw new ReglaNegocioException("Ya existe un socio con el DNI: " + request.dni());
        }
        Socio socio = new Socio();
        socio.setNombre(request.nombre());
        socio.setApellido(request.apellido());
        socio.setDni(request.dni());
        return aDto(socioRepository.save(socio));
    }

    @Transactional
    public void eliminar(Long id) {
        Socio socio = obtenerSocio(id);
        if (!socio.getPrestamos().isEmpty()) {
            throw new ReglaNegocioException(
                    "No se puede eliminar el socio con id: " + id + " porque tiene préstamos asociados");
        }
        socioRepository.delete(socio);
    }

    private Socio obtenerSocio(Long id) {
        return socioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Socio no encontrado con id: " + id));
    }

    private boolean vacio(String s) {
        return s == null || s.isBlank();
    }

    private ResponseSocioDto aDto(Socio socio) {
        return new ResponseSocioDto(socio.getNombreCompleto(), socio.getDni());
    }
}
