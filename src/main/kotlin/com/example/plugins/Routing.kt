package com.example.plugins

import com.example.model.Alumno
import com.example.model.Curso
import com.example.model.GestionAlumnos
import io.ktor.serialization.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        // Static plugin. Try to access `/static/index.html`
        staticResources("/static", "static")

        get("/") {
            call.respondText("¡HOLA CLASE!")
        }
        // Ruta común
        route("/alumnos") {
            get {
                call.respond(GestionAlumnos.getAlumnos())
            }
            get("/curso/{curso}") {
                val cursoTxt = call.parameters["curso"]
                val curso = Curso.valueOf(cursoTxt!!)
                val alumnos = GestionAlumnos.getAlumnoCurso(curso)
                if(alumnos.isEmpty()) {
                    call.respondText("No se han encontrado resultados")
                } else {
                    call.respond(alumnos)
                }
            }

            get("/nombre/{paramNombre}") {
                val nombre = call.parameters["paramNombre"]
                if (nombre == null) {
                    call.respondText("El parámetro de búsqueda es obligatorio")
                } else {
                    val alumno = GestionAlumnos.getAlumnoNombre(nombre)
                    if(alumno == null) {
                        call.respondText("No se ha encontrado ningún resultado.")
                    } else {
                        call.respond(alumno)
                    }
                }
            }

            // Se recoge la llamada post para insertar un nuevo alumno:
            post {
                try {
                    val alumno = call.receive<Alumno>()
                    val alumnoInsertado = GestionAlumnos.nuevoAlumno(alumno)
                    call.respond(alumnoInsertado)
                } catch (jsone: JsonConvertException) {
                    call.respondText("¡Datos inválidos!")
                } catch (ise: IllegalStateException) {
                    call.respondText(ise.message.toString())
                }
            }

            get("/eliminar/{idAlumno}") {
                val idAlumno = call.parameters["idAlumno"]
                if (idAlumno != null) {
                    if (GestionAlumnos.borrarAlumno(idAlumno.toInt())) {
                        call.respondText("Alumno con id $idAlumno eliminado correctamente.")
                    } else {
                        call.respondText("No se ha encontrado ningún alumno con el id = $idAlumno")
                    }
                } else {
                    call.respondText("idAlumno campo obligatorio")
                }
            }

            delete("/eliminar/{idAlumno}") {
                val idAlumno = call.parameters["idAlumno"]
                if (idAlumno != null) {
                    if (GestionAlumnos.borrarAlumno(idAlumno.toInt())) {
                        call.respondText("Alumno con id $idAlumno eliminado correctamente.")
                    } else {
                        call.respondText("No se ha encontrado ningún alumno con el id = $idAlumno")
                    }
                } else {
                    call.respondText("idAlumno campo obligatorio")
                }
            }

            delete("/eliminar") {
                val idAlumno = call.receive<Int>()
                if (idAlumno != null) {
                    if (GestionAlumnos.borrarAlumno(idAlumno.toInt())) {
                        call.respondText("Alumno con id $idAlumno eliminado correctamente.")
                    } else {
                        call.respondText("No se ha encontrado ningún alumno con el id = $idAlumno")
                    }
                } else {
                    call.respondText("idAlumno campo obligatorio")
                }
            }

        }

    }
}
