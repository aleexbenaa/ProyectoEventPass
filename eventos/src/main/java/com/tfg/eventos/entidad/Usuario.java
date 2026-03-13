package com.tfg.eventos.entidad;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import com.tfg.eventos.entidad.enums.RolUsuario;
// Clase entidad de la tabla usuarios, contiene sus atributos, constructores,
// getters y setters. Esta clase no tiene relaciones. 

@Entity
@Table(name = "usuarios")
public class Usuario{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String contrasena_cifrada;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RolUsuario rol;
    @Column(nullable = false)
    private LocalDateTime creado_en;

    public Usuario(){}

    public Usuario(Long id, String nombre, String email, String contrasena_cifrada, RolUsuario rol, LocalDateTime creado_en){
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.contrasena_cifrada = contrasena_cifrada;
        this.rol = rol;
        this.creado_en = creado_en;
    }

    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getContrasenaCifrada() {
        return contrasena_cifrada;
    }
    
    public void setContrasenaCifrada(String contrasena_cifrada) {
        this.contrasena_cifrada = contrasena_cifrada;
    }
    
    public RolUsuario getRol() {
        return rol;
    }
    
    public void setRol(RolUsuario rol) {
        this.rol = rol;
    }
    
    public LocalDateTime getCreadoEn() {
        return creado_en;
    }
    
    public void setCreadoEn(LocalDateTime creado_en) {
        this.creado_en = creado_en;
    }
    
}