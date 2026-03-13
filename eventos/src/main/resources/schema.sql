-- =========================================================
-- EventPass - Esquema MySQL
-- Compatible con entidades JPA actuales del proyecto.
-- =========================================================

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS validaciones;
DROP TABLE IF EXISTS entradas;
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
    titulo VARCHAR(180) NOT NULL,
    descripcion TEXT NOT NULL,
    ubicacion VARCHAR(180) NOT NULL,
    fecha_inicio DATETIME(6) NOT NULL,
    fecha_fin DATETIME(6) NOT NULL,
    aforo INT NOT NULL,
    precio DOUBLE NOT NULL,
    estado ENUM('BORRADOR', 'PUBLICADO', 'CANCELADO', 'FINALIZADO') NOT NULL,
    creado_en DATETIME(6) NOT NULL,
    id_creador BIGINT NOT NULL,

    PRIMARY KEY (id),
    KEY idx_eventos_id_creador (id_creador),
    KEY idx_eventos_estado (estado),
    KEY idx_eventos_fecha_inicio (fecha_inicio),

    CONSTRAINT fk_eventos_usuario_creador
        FOREIGN KEY (id_creador)
        REFERENCES usuarios(id)
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
    id_evento BIGINT NOT NULL,
    id_comprador BIGINT NOT NULL,

    PRIMARY KEY (id),
    UNIQUE KEY uk_entradas_qr_token (qr_token),
    KEY idx_entradas_id_evento (id_evento),
    KEY idx_entradas_id_comprador (id_comprador),
    KEY idx_entradas_estado (estado),

    CONSTRAINT fk_entradas_evento
        FOREIGN KEY (id_evento)
        REFERENCES eventos(id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,

    CONSTRAINT fk_entradas_comprador
        FOREIGN KEY (id_comprador)
        REFERENCES usuarios(id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ---------------------------------------------------------
-- Tabla: validaciones
-- Nota: 1 entrada = 1 validación (UNIQUE en entrada_id)
-- ---------------------------------------------------------
CREATE TABLE validaciones (
    id BIGINT NOT NULL AUTO_INCREMENT,
    entrada_id BIGINT NOT NULL,
    evento_id BIGINT NOT NULL,
    id_validador BIGINT NOT NULL,
    fecha_validacion DATETIME(6) NOT NULL,
    resultado ENUM('VALIDA', 'YA_USADA', 'EVENTO_INCORRECTO', 'QR_INVALIDO', 'CANCELADA') NOT NULL,

    PRIMARY KEY (id),
    UNIQUE KEY uk_validaciones_entrada_id (entrada_id),
    KEY idx_validaciones_evento_id (evento_id),
    KEY idx_validaciones_id_validador (id_validador),
    KEY idx_validaciones_resultado (resultado),

    CONSTRAINT fk_validaciones_entrada
        FOREIGN KEY (entrada_id)
        REFERENCES entradas(id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,

    CONSTRAINT fk_validaciones_evento
        FOREIGN KEY (evento_id)
        REFERENCES eventos(id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,

    CONSTRAINT fk_validaciones_validador
        FOREIGN KEY (id_validador)
        REFERENCES usuarios(id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
