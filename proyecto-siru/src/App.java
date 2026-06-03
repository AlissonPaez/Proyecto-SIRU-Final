import excepciones.SiruException;
import excepciones.autenticacion.*;
import excepciones.documentos.*;
import excepciones.matricula.*;
import excepciones.pago.*;
import excepciones.validacion.*;

import sede_principal.datos.*;
import sede_principal.negocio.*;
import sede_principal.presentacion.*;
import sede_rural.*;

/**
 * Clase principal del sistema SIRU (Sistema de Información de Registro Universitario).
 *
 * Actúa como driver de demostración que ejecuta 7 escenarios representativos
 * del flujo completo del sistema, ejercitando todas las capas:
 *   - Datos (repositorios y servicios simulados)
 *   - Negocio (gestores, módulos y motor de reglas)
 *   - Presentación (portal de verificación)
 *   - Sede Rural (BD local y sincronización)
 *
 * Cada escenario incluye tanto flujos exitosos como manejo de excepciones
 * para demostrar la jerarquía de errores de negocio del sistema.
 */
public class App {

    // ── Capa de datos ──────────────────────────────────────────────
    private static BDAcademica bdAcademica;
    private static RepositorioUsuarios repositorioUsuarios;
    private static PasarelaExterna pasarelaExterna;
    private static ServicioFirmaDigital servicioFirmaDigital;
    private static RepositorioDocsFirmados repositorioDocsFirmados;
    private static LogAuditoria logAuditoria;

    // ── Capa de negocio ────────────────────────────────────────────
    private static GestorAutenticacion gestorAutenticacion;
    private static MotorReglas motorReglas;
    private static ModuloPasarelaPagos moduloPasarelaPagos;
    private static GestorMatriculas gestorMatriculas;
    private static ModuloCalificacionesInasistencias moduloCalificaciones;
    private static GeneradorDocumentosLegales generadorDocumentos;
    private static ModuloReportes moduloReportes;

    // ── Capa de presentación ───────────────────────────────────────
    private static PortalVerificacionDocumentos portalVerificacion;

    // ── Sede rural ─────────────────────────────────────────────────
    private static BDLocal bdLocal;
    private static AgenteSincronizacion agenteSincronizacion;

    // ═══════════════════════════════════════════════════════════════
    //  PUNTO DE ENTRADA
    // ═══════════════════════════════════════════════════════════════

    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║           SISTEMA SIRU — Demostración Integral              ║");
        System.out.println("║      Sistema de Información de Registro Universitario       ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        System.out.println();

        inicializarSistema();

        demostrarAutenticacion();
        demostrarMatricula();
        demostrarCalificaciones();
        demostrarDocumentosLegales();
        demostrarReportes();
        demostrarVerificacionDocumentos();
        demostrarSedeRural();

        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║          Demostración finalizada exitosamente                ║");
        System.out.println("║      Todos los módulos del sistema fueron ejercitados        ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
    }

    // ═══════════════════════════════════════════════════════════════
    //  INICIALIZACIÓN — Inyección de dependencias manual
    // ═══════════════════════════════════════════════════════════════

    /**
     * Instancia todos los componentes del sistema respetando el orden de
     * dependencias: primero la capa de datos, luego negocio, presentación
     * y finalmente la sede rural.
     */
    private static void inicializarSistema() {
        imprimirEncabezado("INICIALIZACIÓN DEL SISTEMA");

        // Capa de datos
        bdAcademica = new BDAcademica();
        repositorioUsuarios = new RepositorioUsuarios();
        pasarelaExterna = new PasarelaExterna();
        servicioFirmaDigital = new ServicioFirmaDigital();
        repositorioDocsFirmados = new RepositorioDocsFirmados();
        logAuditoria = new LogAuditoria();
        imprimirExito("Capa de datos inicializada (BDAcademica, RepositorioUsuarios, PasarelaExterna, ServicioFirmaDigital, RepositorioDocsFirmados, LogAuditoria)");

        // Capa de negocio
        gestorAutenticacion = new GestorAutenticacion(repositorioUsuarios);
        motorReglas = new MotorReglas(bdAcademica);
        moduloPasarelaPagos = new ModuloPasarelaPagos(pasarelaExterna, bdAcademica);
        gestorMatriculas = new GestorMatriculas(moduloPasarelaPagos, bdAcademica, motorReglas);
        generadorDocumentos = new GeneradorDocumentosLegales(bdAcademica, servicioFirmaDigital, repositorioDocsFirmados);
        moduloCalificaciones = new ModuloCalificacionesInasistencias(bdAcademica, generadorDocumentos);
        moduloReportes = new ModuloReportes(bdAcademica, motorReglas);
        imprimirExito("Capa de negocio inicializada (GestorAutenticacion, MotorReglas, ModuloPasarelaPagos, GestorMatriculas, GeneradorDocumentosLegales, ModuloCalificaciones, ModuloReportes)");

        // Capa de presentación
        portalVerificacion = new PortalVerificacionDocumentos(repositorioDocsFirmados, logAuditoria);
        imprimirExito("Capa de presentación inicializada (PortalVerificacionDocumentos)");

        // Sede rural
        bdLocal = new BDLocal();
        agenteSincronizacion = new AgenteSincronizacion(bdLocal, bdAcademica);
        imprimirExito("Sede rural inicializada (BDLocal, AgenteSincronizacion)");

        imprimirSeparador();
    }

    // ═══════════════════════════════════════════════════════════════
    //  ESCENARIO 1: AUTENTICACIÓN
    // ═══════════════════════════════════════════════════════════════

    /**
     * Demuestra el flujo completo de autenticación:
     *   - Registro de usuario
     *   - Inicio de sesión exitoso con generación de token
     *   - Verificación de token activo
     *   - Recuperación de contraseña
     *   - Cierre de sesión
     *   - Intento de login con credenciales inválidas (manejo de excepción)
     *   - Intento de login con usuario inexistente (manejo de excepción)
     */
    private static void demostrarAutenticacion() {
        imprimirEncabezado("ESCENARIO 1: AUTENTICACIÓN");

        // 1.1 — Registro de usuario
        String email = "maria.garcia@universidad.edu.co";
        String contraseña = "Clave$egura123";
        repositorioUsuarios.registrarUsuario(email, contraseña);
        imprimirExito("Usuario registrado: " + email);

        // 1.2 — Inicio de sesión exitoso
        try {
            String token = gestorAutenticacion.iniciarSesion(email, contraseña);
            imprimirExito("Sesión iniciada — Token generado: " + token);

            // 1.3 — Verificación de token
            String emailVerificado = gestorAutenticacion.verificarToken(token);
            imprimirExito("Token verificado — Usuario: " + emailVerificado);

            // 1.4 — Recuperación de contraseña
            String respuestaRecuperacion = gestorAutenticacion.recuperarContraseña(email);
            imprimirExito(respuestaRecuperacion);

            // 1.5 — Cierre de sesión
            String respuestaCierre = gestorAutenticacion.cerrarSesion(token);
            imprimirExito(respuestaCierre);

        } catch (SiruException e) {
            imprimirError("Error inesperado en flujo de autenticación: " + e.getMessage());
        }

        // 1.6 — Intento con credenciales inválidas (excepción esperada)
        try {
            gestorAutenticacion.iniciarSesion(email, "claveIncorrecta");
            imprimirError("No se lanzó la excepción esperada");
        } catch (CredencialesInvalidasException e) {
            imprimirExito("[Excepción esperada] CredencialesInvalidasException: " + e.getMessage());
        }

        // 1.7 — Intento con usuario inexistente (excepción esperada)
        try {
            gestorAutenticacion.iniciarSesion("noexiste@test.com", "1234");
            imprimirError("No se lanzó la excepción esperada");
        } catch (UsuarioNoEncontradoException e) {
            imprimirExito("[Excepción esperada] UsuarioNoEncontradoException: " + e.getMessage());
        }

        imprimirSeparador();
    }

    // ═══════════════════════════════════════════════════════════════
    //  ESCENARIO 2: MATRÍCULA
    // ═══════════════════════════════════════════════════════════════

    /**
     * Demuestra el flujo completo de matrícula:
     *   - Apertura de período académico
     *   - Matrícula de estudiante con pago automático
     *   - Consulta de estado de matrícula
     *   - Cierre de período
     *   - Intento de matrícula sin período activo (manejo de excepción)
     */
    private static void demostrarMatricula() {
        imprimirEncabezado("ESCENARIO 2: MATRÍCULA");

        // 2.1 — Apertura de período
        try {
            String resultadoApertura = gestorMatriculas.abrirPeriodo("2026-01-15", "2026-02-15");
            imprimirExito(resultadoApertura);
        } catch (SiruException e) {
            imprimirError("Error al abrir período: " + e.getMessage());
        }

        // 2.2 — Pre-registro del estudiante en la BD para que exista
        String idEstudiante = "EST-2024001";
        String idGrupo = "GRP-ING-SW2-A";
        bdAcademica.registrarInscripcion(idEstudiante, "pre-registro");
        imprimirExito("Estudiante pre-registrado en el sistema: " + idEstudiante);

        // 2.3 — Matrícula de estudiante
        try {
            String resultadoMatricula = gestorMatriculas.matricularEstudiante(idEstudiante, idGrupo);
            imprimirExito(resultadoMatricula);
        } catch (SiruException e) {
            imprimirError("Error en matrícula: " + e.getMessage());
        }

        // 2.4 — Consulta de estado de pago
        try {
            String estadoPago = moduloPasarelaPagos.consultarEstado(idEstudiante, "2026-1");
            imprimirExito("Estado de pago de " + idEstudiante + ": " + estadoPago);
        } catch (Exception e) {
            imprimirError("Error al consultar estado de pago: " + e.getMessage());
        }

        // 2.5 — Consulta de estado de matrícula
        try {
            String estadoMatricula = gestorMatriculas.estadoMatricula(idEstudiante);
            imprimirExito("Estado de matrícula de " + idEstudiante + ": " + estadoMatricula);
        } catch (Exception e) {
            imprimirError("Error al consultar estado: " + e.getMessage());
        }

        // 2.6 — Cierre de período
        try {
            String resultadoCierre = gestorMatriculas.cerrarPeriodo();
            imprimirExito(resultadoCierre);
        } catch (SiruException e) {
            // El período simulado no cambia de estado, así que esto es esperado
            imprimirExito("[Excepción esperada] " + e.getClass().getSimpleName() + ": " + e.getMessage());
        }

        // 2.7 — Intento de matrícula sin período activo (excepción esperada)
        try {
            gestorMatriculas.matricularEstudiante("EST-2024002", "GRP-MAT-101");
            imprimirError("No se lanzó la excepción esperada");
        } catch (PeriodoNoActivoException e) {
            imprimirExito("[Excepción esperada] PeriodoNoActivoException: " + e.getMessage());
        } catch (SiruException e) {
            imprimirExito("[Excepción esperada] " + e.getClass().getSimpleName() + ": " + e.getMessage());
        }

        imprimirSeparador();
    }

    // ═══════════════════════════════════════════════════════════════
    //  ESCENARIO 3: CALIFICACIONES E INASISTENCIAS
    // ═══════════════════════════════════════════════════════════════

    /**
     * Demuestra el flujo de gestión académica:
     *   - Registro de calificación por el docente
     *   - Registro de inasistencia
     *   - Consulta de calificaciones del estudiante
     *   - Consulta de listado de grupo
     */
    private static void demostrarCalificaciones() {
        imprimirEncabezado("ESCENARIO 3: CALIFICACIONES E INASISTENCIAS");

        String idDocente = "DOC-001";
        String idEstudiante = "EST-2024001";
        String idGrupo = "GRP-ING-SW2-A";

        // 3.1 — Registro de calificación
        try {
            String resultadoCal = moduloCalificaciones.registrarCalificacion(idDocente, idEstudiante, idGrupo);
            imprimirExito(resultadoCal);
        } catch (SiruException e) {
            imprimirError("Error al registrar calificación: " + e.getMessage());
        }

        // 3.2 — Registro de inasistencia
        try {
            String resultadoIna = moduloCalificaciones.registrarInasistencias(idDocente, idEstudiante, idGrupo, "2026-03-15");
            imprimirExito(resultadoIna);
        } catch (SiruException e) {
            imprimirError("Error al registrar inasistencia: " + e.getMessage());
        }

        // 3.3 — Consulta de calificaciones
        try {
            String calificaciones = moduloCalificaciones.consultarCalificaciones(idEstudiante, "ING-SW2");
            imprimirExito("Calificaciones de " + idEstudiante + " en ING-SW2: " + calificaciones);
        } catch (Exception e) {
            imprimirError("Error al consultar calificaciones: " + e.getMessage());
        }

        // 3.4 — Listado de grupo
        try {
            String listado = moduloCalificaciones.consultarListadoGrupo(idDocente, idGrupo);
            imprimirExito("Listado del grupo " + idGrupo + ": " + listado);
        } catch (SiruException e) {
            imprimirError("Error al consultar listado: " + e.getMessage());
        }

        imprimirSeparador();
    }

    // ═══════════════════════════════════════════════════════════════
    //  ESCENARIO 4: DOCUMENTOS LEGALES
    // ═══════════════════════════════════════════════════════════════

    /**
     * Demuestra la generación y validación de documentos legales:
     *   - Generación de acta de notas con firma digital
     *   - Generación de diploma universitario
     *   - Validación de diploma emitido
     *   - Intento de validación de diploma inexistente (excepción esperada)
     */
    private static void demostrarDocumentosLegales() {
        imprimirEncabezado("ESCENARIO 4: DOCUMENTOS LEGALES");

        String idDocente = "DOC-001";
        String idGrupo = "GRP-ING-SW2-A";
        String idEstudiante = "EST-2024001";

        // 4.1 — Generación de acta de notas
        try {
            String idActa = generadorDocumentos.generarActaNotas(idDocente, idGrupo);
            imprimirExito("Acta de notas generada y firmada — ID: " + idActa);
        } catch (CalificacionesPendientesException e) {
            imprimirExito("[Excepción controlada] CalificacionesPendientesException: " + e.getMessage());
        } catch (SiruException e) {
            imprimirError("Error al generar acta: " + e.getMessage());
        }

        // 4.2 — Generación de diploma
        String idDiploma = null;
        try {
            idDiploma = generadorDocumentos.generarDiploma(idEstudiante, "Ingeniería de Software", "2026-06-15");
            imprimirExito("Diploma generado y firmado — ID: " + idDiploma);
        } catch (SiruException e) {
            imprimirError("Error al generar diploma: " + e.getMessage());
        }

        // 4.3 — Validación de diploma existente
        if (idDiploma != null) {
            try {
                String contenidoDiploma = generadorDocumentos.validarDiploma(idDiploma);
                imprimirExito("Diploma validado — Contenido: " + contenidoDiploma);
            } catch (DocumentoLegalException e) {
                imprimirError("Error al validar diploma: " + e.getMessage());
            }
        }

        // 4.4 — Validación de diploma inexistente (excepción esperada)
        try {
            generadorDocumentos.validarDiploma("DIPLOMA-FALSO-999");
            imprimirError("No se lanzó la excepción esperada");
        } catch (DocumentoLegalException e) {
            imprimirExito("[Excepción esperada] DocumentoLegalException: " + e.getMessage());
        }

        imprimirSeparador();
    }

    // ═══════════════════════════════════════════════════════════════
    //  ESCENARIO 5: REPORTES
    // ═══════════════════════════════════════════════════════════════

    /**
     * Demuestra el módulo de reportes de la Vicerrectoría Académica:
     *   - Reporte de rendimiento académico por sede y período
     *   - Identificación de estudiantes en riesgo académico
     *   - Intento de acceso sin rol autorizado (excepción esperada)
     */
    private static void demostrarReportes() {
        imprimirEncabezado("ESCENARIO 5: REPORTES — VICERRECTORÍA ACADÉMICA");

        String idVicerrector = "USR-VICE-001";
        String periodo = "2026-1";
        String sede = "Sede Principal";

        // 5.1 — Reporte de rendimiento
        try {
            String reporte = moduloReportes.generarReporteRendimiento(idVicerrector, periodo, sede);
            imprimirExito("Reporte de rendimiento generado para " + sede + " período " + periodo + ": " + reporte);
        } catch (SiruException e) {
            imprimirError("Error al generar reporte: " + e.getMessage());
        }

        // 5.2 — Estudiantes en riesgo
        try {
            String estudiantesRiesgo = moduloReportes.identificacionEstudiantesEnRiesgo(idVicerrector, periodo);
            imprimirExito("Estudiantes en riesgo académico período " + periodo + ": " + estudiantesRiesgo);
        } catch (SiruException e) {
            imprimirError("Error al identificar estudiantes en riesgo: " + e.getMessage());
        }

        imprimirSeparador();
    }

    // ═══════════════════════════════════════════════════════════════
    //  ESCENARIO 6: VERIFICACIÓN PÚBLICA DE DOCUMENTOS
    // ═══════════════════════════════════════════════════════════════

    /**
     * Demuestra el portal público de verificación de documentos:
     *   - Almacenamiento de un documento firmado para verificación
     *   - Verificación exitosa por código QR
     *   - Verificación fallida de código QR inválido
     *   - Búsqueda de documento por datos del graduado
     *   - Consulta del log de auditoría
     */
    private static void demostrarVerificacionDocumentos() {
        imprimirEncabezado("ESCENARIO 6: VERIFICACIÓN PÚBLICA DE DOCUMENTOS");

        // 6.1 — Preparar un documento firmado para la demo
        String codigoQR = "QR-SIRU-2026-00001";
        repositorioDocsFirmados.guardarDocumento(codigoQR,
            "Diploma | Estudiante: María García | Programa: Ingeniería de Software | Firmado por: Vicerrectoría");
        imprimirExito("Documento firmado almacenado con QR: " + codigoQR);

        // 6.2 — Verificación exitosa por QR
        try {
            String resultado = portalVerificacion.verificarDocumento(codigoQR);
            imprimirExito("Verificación por QR exitosa — Contenido: " + resultado);
        } catch (Exception e) {
            imprimirError("Error en verificación: " + e.getMessage());
        }

        // 6.3 — Verificación con QR inválido
        try {
            String resultadoInvalido = portalVerificacion.verificarDocumento("QR-INVALIDO-999");
            imprimirExito("Resultado de QR inválido: " + resultadoInvalido);
        } catch (Exception e) {
            imprimirError("Error en verificación: " + e.getMessage());
        }

        // 6.4 — Búsqueda por datos del graduado
        try {
            String resultadoBusqueda = portalVerificacion.buscarDocumento(
                "María García", "Ingeniería de Software", "2026-06-15");
            imprimirExito("Búsqueda por datos del graduado: " + resultadoBusqueda);
        } catch (Exception e) {
            imprimirError("Error en búsqueda: " + e.getMessage());
        }

        // 6.5 — Consulta del log de auditoría
        System.out.println("   📋 Log de auditoría:");
        for (String registro : logAuditoria.consultarLog()) {
            System.out.println("      • " + registro);
        }

        imprimirSeparador();
    }

    // ═══════════════════════════════════════════════════════════════
    //  ESCENARIO 7: SEDE RURAL
    // ═══════════════════════════════════════════════════════════════

    /**
     * Demuestra el flujo de la sede rural:
     *   - Registro de inscripciones locales en modo offline
     *   - Consulta de inscripciones pendientes de sincronización
     *   - Sincronización con la sede principal
     *   - Verificación de que los datos llegaron a la BD central
     */
    private static void demostrarSedeRural() {
        imprimirEncabezado("ESCENARIO 7: SEDE RURAL — MODO OFFLINE Y SINCRONIZACIÓN");

        // 7.1 — Inscripciones locales en modo offline
        bdLocal.registrarInscripcionLocal("EST-RURAL-001", "GRP-AGR-101");
        imprimirExito("Inscripción local (offline): EST-RURAL-001 → GRP-AGR-101");

        bdLocal.registrarInscripcionLocal("EST-RURAL-002", "GRP-AGR-102");
        imprimirExito("Inscripción local (offline): EST-RURAL-002 → GRP-AGR-102");

        bdLocal.registrarInscripcionLocal("EST-RURAL-003", "GRP-VET-201");
        imprimirExito("Inscripción local (offline): EST-RURAL-003 → GRP-VET-201");

        // 7.2 — Consulta de pendientes antes de sincronizar
        System.out.println("   📋 Inscripciones pendientes de sincronización: "
                + bdLocal.consultarPendientes().size());
        bdLocal.consultarPendientes().forEach((est, grp) ->
            System.out.println("      • " + est + " → " + grp));

        // 7.3 — Sincronización
        agenteSincronizacion.sincronizar();
        imprimirExito("Sincronización completada con la sede principal");

        // 7.4 — Verificación post-sincronización
        System.out.println("   📋 Inscripciones pendientes después de sincronizar: "
                + bdLocal.consultarPendientes().size());
        imprimirExito("Los datos de la sede rural fueron sincronizados exitosamente con la BD central");

        // 7.5 — Verificar que los datos llegaron a la BD central
        try {
            String datosEstudiante = bdAcademica.consultarEstudiante("EST-RURAL-001");
            imprimirExito("Verificación en BD central — EST-RURAL-001 inscrito en: " + datosEstudiante);
        } catch (SiruException e) {
            imprimirError("Error al verificar en BD central: " + e.getMessage());
        }

        imprimirSeparador();
    }

    // ═══════════════════════════════════════════════════════════════
    //  UTILIDADES DE FORMATO
    // ═══════════════════════════════════════════════════════════════

    /**
     * Imprime un encabezado de sección con formato destacado.
     *
     * @param titulo Título de la sección a mostrar.
     */
    private static void imprimirEncabezado(String titulo) {
        System.out.println();
        System.out.println("┌──────────────────────────────────────────────────────────────┐");
        System.out.printf("│  %-60s│%n", titulo);
        System.out.println("└──────────────────────────────────────────────────────────────┘");
    }

    /**
     * Imprime un mensaje de operación exitosa con indicador visual.
     *
     * @param mensaje Mensaje descriptivo de la operación exitosa.
     */
    private static void imprimirExito(String mensaje) {
        System.out.println("   ✅ " + mensaje);
    }

    /**
     * Imprime un mensaje de error con indicador visual.
     *
     * @param mensaje Mensaje descriptivo del error ocurrido.
     */
    private static void imprimirError(String mensaje) {
        System.out.println("   ❌ " + mensaje);
    }

    /**
     * Imprime una línea separadora entre secciones.
     */
    private static void imprimirSeparador() {
        System.out.println();
        System.out.println("────────────────────────────────────────────────────────────────");
    }
}
