package com.example.model

object GestionAlumnos {

    private val alumnos = mutableListOf(
        Alumno(1, "Pedro", "05/06/2000", Curso.DAM1,
            "pedro@educa.jcyl.es", listOf(Asignatura.AAD, Asignatura.PMDM, Asignatura.EIE)
        ),
        Alumno(2, "Pepe", "12/02/2001", Curso.DAM2,
            "pepe@educa.jcyl.es", listOf(Asignatura.AAD, Asignatura.PSP)
        ),
        Alumno(3, "Alicia", "05/06/2000", Curso.DAW1,
            "alicia@educa.jcyl.es", listOf(Asignatura.DDI, Asignatura.AAD, Asignatura.PSP)
        )
    )

    fun getAlumnos() = alumnos

    fun getAlumnoCurso(curso:Curso) = alumnos.filter {
        it.curso == curso
    }

    fun getAlumnoNombre(nombre: String) = alumnos.find {
        it.nombre.equals(nombre, ignoreCase = true)
    }

    fun getAlumnoId(id: Int) = alumnos.find {
        it.id == id
    }

    fun nuevoAlumno(alumno: Alumno): Alumno {
        var alumnoFinal = alumno
        // Se comprueba si el id viene nulo o si nos lo pasan
        // Si nos lo pasan se comprueba que no exista
        // Si llega nulo se calcula con MAX + 1
        if (alumno.id != null && alumno.id > 0) {
            val alumnoTmp = getAlumnoId(alumno.id)
            if (alumnoTmp != null) {
                // Alumno existente, valor repetido
                throw IllegalStateException("¡is Alumno ya existe!")
            } else {
                // Se inserta el nuevo alumno
                alumnos.add(alumno)
            }
        } else {
            // Se obtiene el valor máximo de ños idAlumno y se
            // suma 1 para insertarlo como nuwevo id
            val maxId = alumnos.maxWith(Comparator.comparingInt {
                it.id
            }).id
            alumno.id = maxId + 1
            alumnos.add(alumno)
        }
        return alumnoFinal
    }

}
