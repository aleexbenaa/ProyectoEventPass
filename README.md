# EventPass 🎫

Una plataforma integral para la gestión y validación de entradas a eventos. Incluye un backend robusto con interfaz web y una aplicación móvil Android para escaneo y validación de QR en tiempo real.

## 📋 Descripción

EventPass es una solución completa de gestión de eventos que permite:

- **Gestión de Eventos**: Crear, editar y gestionar eventos
- **Sistema de Entradas**: Generación y distribución de entradas con códigos QR
- **Validación en Tiempo Real**: Escaneo de QR mediante aplicación móvil
- **Autenticación Segura**: Sistema de login y registro de usuarios
- **Panel Administrativo**: Dashboard para administradores con estadísticas
- **Búsqueda de Eventos**: Interfaz intuitiva para clientes finales

## ✨ Características Principales

- ✅ Autenticación y autorización con Spring Security
- ✅ Base de datos relacional con JPA/Hibernate
- ✅ Interfaz web responsiva con Thymeleaf
- ✅ Generación de códigos QR para entradas
- ✅ Aplicación móvil nativa Android
- ✅ Escaneo de QR en tiempo real
- ✅ API REST integrada
- ✅ Gestión de usuarios y roles

## 🛠️ Tecnologías Utilizadas

### Backend
- **Framework**: Spring Boot 3.5.7
- **Lenguaje**: Java 17
- **ORM**: JPA/Hibernate
- **Base de Datos**: H2 (desarrollo), configurable para producción
- **Seguridad**: Spring Security
- **Template Engine**: Thymeleaf
- **Build Tool**: Maven

### Frontend
- **HTML5/CSS3**: Interfaz responsiva
- **Thymeleaf**: Templates dinámicos
- **Bootstrap**: Componentes UI

### Mobile
- **Plataforma**: Android
- **Lenguaje**: Java/Kotlin
- **SDK**: Android API 21+
- **Funcionalidades**: 
  - Escaneo de códigos QR
  - Autenticación de usuario
  - Validación de entradas

## 📁 Estructura del Proyecto

```
ProyectoEventPass/
├── eventos/                          # Backend Spring Boot
│   ├── pom.xml                      # Dependencias Maven
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/tfg/eventos/
│   │   │   │   ├── controlador/     # Controladores REST/Web
│   │   │   │   ├── entidad/         # Modelos JPA
│   │   │   │   ├── repositorio/     # Repositories
│   │   │   │   ├── servicio/        # Lógica de negocio
│   │   │   │   └── config/          # Configuración
│   │   │   └── resources/
│   │   │       ├── application.properties
│   │   │       ├── schema.sql
│   │   │       ├── static/css/
│   │   │       └── templates/       # Vistas Thymeleaf
│   │   └── test/                    # Tests unitarios
│   ├── mvnw                         # Maven wrapper (Linux/Mac)
│   └── mvnw.cmd                     # Maven wrapper (Windows)
│
└── eventosmobile/                   # Aplicación Android
    ├── app/
    │   ├── build.gradle.kts         # Configuración Gradle
    │   ├── src/
    │   │   ├── main/
    │   │   │   ├── java/com/example/
    │   │   │   ├── res/
    │   │   │   │   ├── drawable/
    │   │   │   │   ├── layout/
    │   │   │   │   ├── values/
    │   │   │   │   └── xml/
    │   │   │   └── AndroidManifest.xml
    │   │   ├── androidTest/
    │   │   └── test/
    │   └── proguard-rules.pro
    ├── gradle/
    ├── build.gradle.kts
    ├── gradlew
    └── gradlew.bat
```

## 🚀 Instalación y Configuración

### Requisitos Previos

#### Backend
- **Java 17** o superior
- **Maven 3.6+** (incluido en el proyecto con mvnw)
- **Base de datos** (H2 por defecto, configurable)

#### Mobile
- **Android Studio** Flamingo o superior
- **Android SDK 21+**
- **JDK 17**

### Backend - Spring Boot

1. **Clona el repositorio**
   ```bash
   git clone https://github.com/tu-usuario/EventPass.git
   cd EventPass/eventos
   ```

2. **Compila el proyecto**
   ```bash
   # En Windows
   mvnw.cmd clean package
   
   # En Linux/Mac
   ./mvnw clean package
   ```

3. **Ejecuta la aplicación**
   ```bash
   # En Windows
   mvnw.cmd spring-boot:run
   
   # En Linux/Mac
   ./mvnw spring-boot:run
   ```

4. **Accede a la aplicación**
   ```
   URL: http://localhost:8080
   ```

#### Configuración de Base de Datos

Edita `src/main/resources/application.properties`:

```properties
# Base de datos H2 (desarrollo)
spring.datasource.url=jdbc:h2:mem:eventodb
spring.datasource.driverClassName=org.h2.Driver
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect

# Para MySQL/PostgreSQL, modifica según tu BD
# spring.datasource.url=jdbc:mysql://localhost:3306/eventodb
# spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
# spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
```

### Mobile - Android

1. **Abre el proyecto en Android Studio**
   ```bash
   cd EventPass/eventosmobile
   ```

2. **Sincroniza Gradle**
   - Android Studio detectará automáticamente el proyecto Gradle
   - Déjalo sincronizar todas las dependencias

3. **Conecta un dispositivo o emulador Android**
   - Dispositivo físico: Habilita USB debugging
   - Emulador: Crea uno desde AVD Manager

4. **Ejecuta la aplicación**
   ```bash
   # Desde Android Studio: Click en "Run" o presiona Shift+F10
   ```

## 💻 Uso

### Backend

**Endpoints principales:**

- `GET /` - Página de inicio
- `GET /eventos` - Listado de eventos
- `GET /eventos/{id}` - Detalle de evento
- `POST /login` - Autenticación
- `POST /register` - Registro de usuario
- `GET /admin/dashboard` - Panel administrativo
- `GET /admin/eventos` - Gestión de eventos (admin)

### Mobile

1. **Login**: Ingresa credenciales de usuario registrado
2. **Escaneo**: Abre la cámara y apunta a un código QR
3. **Validación**: La entrada se marca como validada automáticamente
4. **Historial**: Consulta el registro de entradas escaneadas

## 🔧 Desarrollo

### Build del Backend

```bash
# Limpiar y compilar
./mvnw clean package

# Ejecutar tests
./mvnw test

# Crear WAR para producción
./mvnw clean package -DskipTests
```

### Build de Mobile

```bash
cd eventosmobile

# Compilar la aplicación
./gradlew build

# Generar APK
./gradlew assembleRelease

# Instalar en dispositivo conectado
./gradlew installRelease
```

## 📱 API REST

La aplicación expone endpoints REST para integración con aplicaciones terceras:

```bash
# Validar entrada
POST /api/validar-entrada
Content-Type: application/json

{
  "codigoQR": "xxxx-xxxx-xxxx",
  "idEvento": 1
}

# Obtener eventos
GET /api/eventos

# Obtener detalles de evento
GET /api/eventos/{id}
```

## 🔐 Seguridad

- Autenticación con Spring Security
- Validación de entrada en formularios
- Hash seguro de contraseñas (BCrypt)
- CSRF protection en formularios web
- Roles y permisos granulares (Usuario, Admin)

## 📝 Base de Datos

El esquema se inicializa automáticamente desde `schema.sql`:

- **Usuarios**: Gestión de autenticación
- **Eventos**: Información de eventos
- **Entradas**: Tickets y códigos QR
- **Validaciones**: Registro de escaneos

## 🤝 Contribuciones

Las contribuciones son bienvenidas. Para cambios significativos:

1. Fork el repositorio
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## 📄 Licencia

Este proyecto está licenciado bajo la Licencia MIT - ver el archivo LICENSE para más detalles.

## 📞 Contacto y Soporte

Para reportar problemas o sugerencias:
- 📧 Email: soporte@eventpass.com
- 🐛 Issues: [GitHub Issues](https://github.com/tu-usuario/EventPass/issues)

## 📚 Recursos Adicionales

- [Documentación Spring Boot](https://spring.io/projects/spring-boot)
- [Documentación Android](https://developer.android.com/docs)
- [Spring Security](https://spring.io/projects/spring-security)

---

**EventPass** - Gestión de eventos simplificada ✨

Última actualización: Mayo 2026
