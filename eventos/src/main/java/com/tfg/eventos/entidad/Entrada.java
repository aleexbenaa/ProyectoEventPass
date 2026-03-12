package com.tfg.eventos.entidad;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import com.tfg.eventos.entidad.enums.EstadoEntrada;
import com.tfg.eventos.entidad.enums.EstadoPago;

@Entity
@Table(name = "entradas")
public class Entrada{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String qr_token;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoEntrada estado;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoPago estado_pago;

    @Column(nullable = false)
    private LocalDateTime comprada_en;

    @Column
    private LocalDateTime usada_en;

    @ManyToOne
    @JoinColumn(name = "id_evento", nullable = false)
    private Evento evento;

    @ManyToOne
    @JoinColumn(name = "id_comprador", nullable = false)
    private Usuario comprador;

    public Entrada() {
    }

    public Entrada(Long id, String qr_token, EstadoEntrada estado, EstadoPago estado_pago,
                   LocalDateTime comprada_en, LocalDateTime usada_en,
                   Evento evento, Usuario comprador) {
        this.id = id;
        this.qr_token = qr_token;
        this.estado = estado;
        this.estado_pago = estado_pago;
        this.comprada_en = comprada_en;
        this.usada_en = usada_en;
        this.evento = evento;
        this.comprador = comprador;
    }

    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getQrToken() {
        return qr_token;
    }
    
    public void setQrToken(String qr_token) {
        this.qr_token = qr_token;
    }
    
    public EstadoEntrada getEstado() {
        return estado;
    }
    
    public void setEstado(EstadoEntrada estado) {
        this.estado = estado;
    }
    
    public EstadoPago getEstadoPago() {
        return estado_pago;
    }
    
    public void setEstadoPago(EstadoPago estado_pago) {
        this.estado_pago = estado_pago;
    }
    
    public LocalDateTime getCompradaEn() {
        return comprada_en;
    }
    
    public void setCompradaEn(LocalDateTime comprada_en) {
        this.comprada_en = comprada_en;
    }
    
    public LocalDateTime getUsadaEn() {
        return usada_en;
    }
    
    public void setUsadaEn(LocalDateTime usada_en) {
        this.usada_en = usada_en;
    }
    
    public Evento getEvento() {
        return evento;
    }
    
    public void setEvento(Evento evento) {
        this.evento = evento;
    }
    
    public Usuario getComprador() {
        return comprador;
    }
    
    public void setComprador(Usuario comprador) {
        this.comprador = comprador;
    }
    
}