-- =========================================================
-- EventPass - Esquema MySQL
-- Compatible con entidades JPA actuales del proyecto.
-- =========================================================

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS registros_acceso;
DROP TABLE IF EXISTS entradas;
DROP TABLE IF EXISTS asistentes;
DROP TABLE IF EXISTS eventos;
DROP TABLE IF EXISTS usuarios;

SET FOREIGN_KEY_CHECKS = 1;

-- ---------------------------------------------------------
-- Tabla: usuarios
-- ---------------------------------------------------------
CREATE TABLE usuarios (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(120) NOT NULL,
    email VARCHAR(190) NOT NULL,
    contrasena_cifrada VARCHAR(255) NOT NULL,
    rol ENUM('ADMIN', 'USUARIO', 'VALIDADOR') NOT NULL,
    creado_en DATETIME(6) NOT NULL,

    PRIMARY KEY (id),
    UNIQUE KEY uk_usuarios_email (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ---------------------------------------------------------
-- Tabla: eventos
-- ---------------------------------------------------------
CREATE TABLE eventos (
    id BIGINT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(180) NOT NULL,
    descripcion TEXT NOT NULL,
    ubicacion VARCHAR(180) NOT NULL,
    fecha_inicio DATETIME(6) NOT NULL,
    fecha_fin DATETIME(6) NOT NULL,
    capacidad INT NOT NULL,
    estado ENUM('PLANIFICADO', 'PUBLICADO', 'CANCELADO', 'FINALIZADO') NOT NULL,
    creado_en DATETIME(6) NOT NULL,
    id_organizador BIGINT NOT NULL,

    PRIMARY KEY (id),
    KEY idx_eventos_id_organizador (id_organizador),
    KEY idx_eventos_estado (estado),
    KEY idx_eventos_fecha_inicio (fecha_inicio),

    CONSTRAINT fk_eventos_usuario_organizador
        FOREIGN KEY (id_organizador)
        REFERENCES usuarios(id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ---------------------------------------------------------
-- Tabla: asistentes
-- Nota: Tabla de asociación entre usuarios y eventos
-- ---------------------------------------------------------
CREATE TABLE asistentes (
    id BIGINT NOT NULL AUTO_INCREMENT,
    registrado_en DATETIME(6) NOT NULL,
    id_usuario BIGINT NOT NULL,
    id_evento BIGINT NOT NULL,

    PRIMARY KEY (id),
    KEY idx_asistentes_id_usuario (id_usuario),
    KEY idx_asistentes_id_evento (id_evento),

    CONSTRAINT fk_asistentes_usuario
        FOREIGN KEY (id_usuario)
        REFERENCES usuarios(id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,

    CONSTRAINT fk_asistentes_evento
        FOREIGN KEY (id_evento)
        REFERENCES eventos(id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ---------------------------------------------------------
-- Tabla: entradas
-- ---------------------------------------------------------
CREATE TABLE entradas (
    id BIGINT NOT NULL AUTO_INCREMENT,
    qr_token VARCHAR(255) NOT NULL,
    estado ENUM('ACTIVA', 'USADA', 'CANCELADA') NOT NULL,
    estado_pago ENUM('PENDIENTE', 'PAGADO', 'CANCELADO') NOT NULL,
    comprada_en DATETIME(6) NOT NULL,
    usada_en DATETIME(6) NULL,
    id_asistente BIGINT NOT NULL,

    PRIMARY KEY (id),
    UNIQUE KEY uk_entradas_qr_token (qr_token),
    KEY idx_entradas_id_asistente (id_asistente),
    KEY idx_entradas_estado (estado),

    CONSTRAINT fk_entradas_asistente
        FOREIGN KEY (id_asistente)
        REFERENCES asistentes(id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ---------------------------------------------------------
-- Tabla: registros_acceso
-- Nota: Registro de acceso a eventos mediante entradas
-- ---------------------------------------------------------
CREATE TABLE registros_acceso (
    id BIGINT NOT NULL AUTO_INCREMENT,
    fecha_acceso DATETIME(6) NOT NULL,
    observaciones TEXT,
    id_entrada BIGINT NOT NULL,

    PRIMARY KEY (id),
    KEY idx_registros_acceso_id_entrada (id_entrada),
    KEY idx_registros_acceso_fecha_acceso (fecha_acceso),

    CONSTRAINT fk_registros_acceso_entrada
        FOREIGN KEY (id_entrada)
        REFERENCES entradas(id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
