package icop.parcialpromociongabrielukf.controller;

import icop.parcialpromociongabrielukf.dto.RequestPrestamoDto;
import icop.parcialpromociongabrielukf.dto.ResponsePrestamoDto;
import icop.parcialpromociongabrielukf.service.PrestamoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/prestamos")
public class PrestamoController {

    private final PrestamoService prestamoService;

    public PrestamoController(PrestamoService prestamoService) {
        this.prestamoService = prestamoService;
    }

    @PostMapping
    public ResponseEntity<ResponsePrestamoDto> crear(@RequestBody RequestPrestamoDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(prestamoService.crear(request));
    }

    @GetMapping("/socio/{id}")
    public ResponseEntity<List<ResponsePrestamoDto>> listarPorSocio(@PathVariable Long id) {
        return ResponseEntity.ok(prestamoService.listarPorSocio(id));
    }
}
