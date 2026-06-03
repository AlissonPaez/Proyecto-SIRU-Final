# Proyecto SIRU (Sistema de Información de Registro Universitario)

SIRU es un sistema académico desarrollado en Java puro (sin frameworks adicionales), diseñado utilizando una **arquitectura de 3 capas** clásica, junto con un módulo especializado para la gestión de sedes rurales (operación offline y sincronización).

## Arquitectura del Sistema

El proyecto está organizado en las siguientes capas y módulos principales:

### 1. Capa de Presentación (`sede_principal.presentacion`)
Contiene los puntos de entrada e interfaces hacia el exterior del sistema.
- **`PortalVerificacionDocumentos`**: Portal público que permite a terceros (como empleadores) validar la autenticidad de los documentos académicos mediante códigos QR o datos del graduado.

### 2. Capa de Negocio (`sede_principal.negocio`)
Contiene toda la lógica central, reglas de negocio y orquestación del sistema.
- **`GestorAutenticacion`**: Manejo de inicio de sesión, generación de tokens de sesión y recuperación de contraseñas.
- **`GestorMatriculas`**: Administración de la apertura/cierre de periodos académicos y matriculación de estudiantes.
- **`ModuloPasarelaPagos`**: Lógica de procesamiento de pagos (con soporte para reintentos).
- **`ModuloCalificacionesInasistencias`**: Registro de notas e inasistencias por parte de los docentes.
- **`GeneradorDocumentosLegales`**: Generación de diplomas y actas de notas con firma digital.
- **`ModuloReportes`**: Generación de reportes de rendimiento e identificación de estudiantes en riesgo para la Vicerrectoría.
- **`MotorReglas`**: Evaluación de prerrequisitos y conflictos de horario.

### 3. Capa de Datos (`sede_principal.datos`)
Responsable del almacenamiento, persistencia y acceso a servicios externos (simulados en este caso mediante estructuras en memoria).
- **`BDAcademica`**: Base de datos central en memoria.
- **`RepositorioUsuarios`**: Almacenamiento de credenciales y datos de usuario.
- **`PasarelaExterna`**: Simulación de una pasarela de pagos bancaria/externa.
- **`ServicioFirmaDigital`**: Servicio para aplicar firmas digitales a documentos.
- **`RepositorioDocsFirmados`**: Almacenamiento de documentos legales ya generados y firmados.
- **`LogAuditoria`**: Trazabilidad y registro de eventos críticos del sistema.

### 4. Módulo Sede Rural (`sede_rural`)
Módulo diseñado para funcionar en entornos con conectividad intermitente.
- **`BDLocal`**: Almacenamiento temporal de inscripciones cuando no hay conexión.
- **`AgenteSincronizacion`**: Proceso encargado de enviar los datos locales hacia la base de datos central cuando la conexión se restablece.

### 5. Manejo de Excepciones (`excepciones`)
Una jerarquía completa de excepciones personalizadas heredadas de `SiruException` para manejar errores específicos de negocio (e.g. `PeriodoNoActivoException`, `CredencialesInvalidasException`, `DocumentoLegalException`, etc.).

---

## Cómo Ejecutar el Proyecto

El proyecto incluye una clase principal `App.java` (Driver) que ejecuta una **demostración integral** del sistema. Esta demostración instancia todas las capas y simula 7 escenarios completos ejercitando todas las funcionalidades mencionadas.

### Opción 1: Ejecución desde Terminal (PowerShell / Bash)

1. Abre una terminal y navega a la carpeta del código fuente:
   ```bash
   cd proyecto-siru
   ```

2. Compila el código fuente (asegurando el uso de codificación UTF-8 para evitar problemas con tildes):
   ```bash
   javac -encoding UTF-8 -d bin src/excepciones/*.java src/excepciones/autenticacion/*.java src/excepciones/documentos/*.java src/excepciones/matricula/*.java src/excepciones/pago/*.java src/excepciones/validacion/*.java src/sede_principal/datos/*.java src/sede_principal/negocio/*.java src/sede_principal/presentacion/*.java src/sede_rural/*.java src/App.java
   ```

3. Ejecuta la clase principal de la demostración:
   ```bash
   java -cp bin App
   ```

### Opción 2: Ejecución desde Visual Studio Code

1. Abre la carpeta `proyecto-siru` en Visual Studio Code.
2. Abre el archivo `src/App.java`.
3. Haz clic en el botón **Run** (o la opción *Run Java*) que aparece encima del método `public static void main(String[] args)`. Visual Studio Code compilará y ejecutará el proyecto automáticamente de acuerdo a la configuración en `.vscode/settings.json`.