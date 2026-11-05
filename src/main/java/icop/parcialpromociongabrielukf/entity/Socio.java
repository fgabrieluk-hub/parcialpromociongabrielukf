package icop.parcialpromociongabrielukf.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "socio")
@Getter
@Setter
@NoArgsConstructor
public class Socio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "apellido", nullable = false, length = 100)
    private String apellido;

    @Column(name = "dni", nullable = false, unique = true, length = 20)
    private String dni;

    // Lado inverso de la relación: la FK vive en la tabla prestamo (Prestamo es el lado dueño)
    @OneToMany(mappedBy = "socio", cascade = CascadeType.ALL)
    @OrderBy("id ASC")
    private List<Prestamo> prestamos = new ArrayList<>();

    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }
}
