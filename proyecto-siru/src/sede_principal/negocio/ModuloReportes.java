package sede_principal.negocio;

import sede_principal.datos.BDAcademica;

public class ModuloReportes {

    private BDAcademica bdAcademica;
    private MotorReglas motorReglas;

    public ModuloReportes(BDAcademica bdAcademica, MotorReglas motorReglas) {
        this.bdAcademica = bdAcademica;
        this.motorReglas = motorReglas;
    }

    // Genera un reporte consolidado del rendimiento académico por sede y período.
    // Solo es accesible por la Vicerrectoría Académica.
    public String generarReporteRendimiento(String periodo, String sede) {
        throw new UnsupportedOperationException("Por implementar");
    }


    // Identifica y retorna la lista de estudiantes en riesgo académico en el período indicado.
    // Un estudiante se considera en riesgo si tiene inasistencias altas o calificaciones bajas.
    // Es accesible por la Vicerrectoría y los docentes tutores.
    public String identificacionEstudiantesEnRiesgo(String periodo) {
        throw new UnsupportedOperationException("Por implementar");
    }
}

