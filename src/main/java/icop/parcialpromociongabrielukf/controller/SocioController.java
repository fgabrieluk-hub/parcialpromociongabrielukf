package icop.parcialpromociongabrielukf.controller;

import icop.parcialpromociongabrielukf.dto.RequestSocioDto;
import icop.parcialpromociongabrielukf.dto.ResponseSocioDto;
import icop.parcialpromociongabrielukf.service.SocioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/socios")
public class SocioController {

    private final SocioService socioService;

    public SocioController(SocioService socioService) {
        this.socioService = socioService;
    }

    @GetMapping
    public ResponseEntity<List<ResponseSocioDto>> listar() {
        return ResponseEntity.ok(socioService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseSocioDto> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(socioService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<ResponseSocioDto> crear(@RequestBody RequestSocioDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(socioService.crear(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        socioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
