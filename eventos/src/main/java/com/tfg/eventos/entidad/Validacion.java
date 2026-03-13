package com.tfg.eventos.entidad;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import com.tfg.eventos.entidad.enums.ResultadoValidacion;
// Clase entidad de la tabla validaciones, contiene sus atributos, constructores,
// getters y setters. También sus relaciones. 

@Entity
@Table(name = "validaciones")
public class Validacion{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(unique=true, nullable=false)
    private Entrada entrada;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Evento evento;
    @ManyToOne
    @JoinColumn(name = "id_validador", nullable = false)
    private Usuario validador;
    @Column(nullable = false)
    private LocalDateTime fecha_validacion;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ResultadoValidacion resultado;

    public Validacion(){}

    public Validacion(Long id, Entrada entrada, Evento evento, Usuario validador,
        LocalDateTime fecha_validacion, ResultadoValidacion resultado) {
        this.id = id;
        this.entrada = entrada;
        this.evento = evento;
        this.validador = validador;
        this.fecha_validacion = fecha_validacion;
        this.resultado = resultado;
    }

    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Entrada getEntrada() {
        return entrada;
    }
    
    public void setEntrada(Entrada entrada) {
        this.entrada = entrada;
    }
    
    public Evento getEvento() {
        return evento;
    }
    
    public void setEvento(Evento evento) {
        this.evento = evento;
    }
    
    public Usuario getValidador() {
        return validador;
    }
    
    public void setValidador(Usuario validador) {
        this.validador = validador;
    }
    
    public LocalDateTime getFechaValidacion() {
        return fecha_validacion;
    }
    
    public void setFechaValidacion(LocalDateTime fecha_validacion) {
        this.fecha_validacion = fecha_validacion;
    }
    
    public ResultadoValidacion getResultado() {
        return resultado;
    }
    
    public void setResultado(ResultadoValidacion resultado) {
        this.resultado = resultado;
    }
    
}