package com.tfg.eventos.entidad;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import com.tfg.eventos.entidad.enums.EstadoEvento;
// Clase entidad de la tabla eventos, contiene sus atributos, constructores,
// getters y setters. También sus relaciones.

@Entity
@Table(name = "eventos")
public class Evento{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String titulo;
    @Column(nullable = false)
    private String descripcion;
    @Column(nullable = false)
    private String ubicacion;
    @Column(nullable = false)
    private LocalDateTime fecha_inicio;
    @Column(nullable = false)
    private LocalDateTime fecha_fin;
    @Column(nullable = false)
    private int aforo;
    @Column(nullable = false)
    private double precio;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoEvento estado;
    @Column(nullable = false)
    private LocalDateTime creado_en;
    @ManyToOne
    @JoinColumn(name = "id_creador", nullable = false)
    private Usuario creado_por;

    public Evento(){}

    public Evento(Long id, String titulo, String descripcion, String ubicacion, LocalDateTime fecha_inicio, LocalDateTime fecha_fin, int aforo, double precio, EstadoEvento estado, LocalDateTime creado_en, Usuario creado_por){
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.ubicacion = ubicacion;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
        this.aforo = aforo;
        this.precio = precio;
        this.estado = estado;
        this.creado_en = creado_en;
        this.creado_por = creado_por;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public LocalDateTime getFechaInicio() {
        return fecha_inicio;
    }

    public void setFechaInicio(LocalDateTime fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public LocalDateTime getFechaFin() {
        return fecha_fin;
    }

    public void setFechaFin(LocalDateTime fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    public int getAforo() {
        return aforo;
    }

    public void setAforo(int aforo) {
        this.aforo = aforo;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public EstadoEvento getEstado() {
        return estado;
    }

    public void setEstado(EstadoEvento estado) {
        this.estado = estado;
    }

    public LocalDateTime getCreadoEn() {
        return creado_en;
    }

    public void setCreadoEn(LocalDateTime creado_en) {
        this.creado_en = creado_en;
    }

    public Usuario getCreadoPor() {
        return creado_por;
    }

    public void setCreadoPor(Usuario creado_por) {
        this.creado_por = creado_por;
    }
    
}