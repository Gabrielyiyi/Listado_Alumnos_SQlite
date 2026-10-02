# 🎓 Listado de Alumnos — Android App (SQLite)

Aplicación nativa de Android en Kotlin para la gestión de alumnos (CRUD). Utiliza SQLite para la persistencia local de datos, RecyclerView con ViewBinding para el listado interactivo y Glide para la carga de fotos de perfil. Diseñada con Material 3, incluye diálogos para agregar, editar y eliminar alumnos, y soporte responsivo Edge-to-Edge.

## 🚀 Características Principales

* **📋 Listado Dinámico:** Visualización fluida de alumnos con `RecyclerView` y `MaterialCardView`.
* **💾 Base de Datos Local (SQLite):** Implementación de `SQLiteOpenHelper` (`DBHelperAlumno`) con las operaciones CRUD completas:
  * **Crear:** Registro de nuevos alumnos.
  * **Leer:** Carga automática y conteo de alumnos.
  * **Actualizar:** Edición de información existente prellenada en el diálogo.
  * **Eliminar:** Borrado de registros con diálogo de confirmación.
* **🖼️ Carga de Imágenes:** Integración con **Glide** para renderizar fotos de perfil remotas desde URLs con imagen de reserva (*placeholder*).
* **🎨 Diseño Material 3:** Paleta de colores moderna, tarjetas elevadas, botones de acción rápida con efecto *ripple* y pantalla de estado vacío (*empty state*).
* **📱 Responsivo & Edge-to-Edge:** Manejo dinámico de `WindowInsets` para garantizar compatibilidad con pantallas notch/cámara y la barra de navegación de Android.

## 🛠️ Tecnologías Utilizadas

* **Lenguaje:** Kotlin
* **Arquitectura / UI:** ViewBinding, RecyclerView, Material Design 3
* **Persistencia:** SQLite (`SQLiteOpenHelper`)
* **Librerías:** Glide (`v4.16.0`)
* **Min SDK:** API 27 (Android 8.1 Oreo)
* **Target SDK:** API 37 / Android 15

## 📂 Estructura del Proyecto

```text
app/src/main/
├── java/com/example/listado_alumnos/
│   ├── Alumno.kt            # Model (Data class)
│   ├── AlumnoAdapter.kt     # RecyclerView Adapter (ViewBinding + Glide)
│   ├── DBHelperAlumno.kt    # SQLite OpenHelper (CRUD SQL)
│   └── MainActivity.kt      # Main Activity
└── res/
    ├── layout/
    │   ├── activity_main.xml # Layout principal (AppBarLayout + RecyclerView + FAB)
    │   ├── item_persona.xml  # Tarjeta de alumno
    │   └── dialog_alumno.xml # Diálogo modal para Agregar / Editar
    └── values/
        ├── colors.xml        # Paleta Material 3
        └── strings.xml       # Textos del sistema
```
