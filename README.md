# ChistesJPA (ChistesRetrofitJPC)

Aplicación Android en **Kotlin + Jetpack Compose** que consume una API de chistes y permite **visualizar chistes por categorías** y **valorar** cada chiste (0 a 5 estrellas), persistiendo la valoración en base de datos. Incluye pantallas de **Login/Registro** (indicadas como no esenciales para la funcionalidad principal).

---

## Índice

- [Descripción general](#descripción-general)
- [Características principales](#características-principales)
- [Tecnologías utilizadas](#tecnologías-utilizadas)
- [Estructura del proyecto](#estructura-del-proyecto)
- [Requisitos previos](#requisitos-previos)
- [Instalación y ejecución](#instalación-y-ejecución)
- [Configuración](#configuración)
- [Buenas prácticas](#buenas-prácticas)
- [Troubleshooting](#troubleshooting)
- [Contribución](#contribución)
- [Licencia](#licencia)
- [Autor / contacto](#autor--contacto)

---

## Descripción general

ChistesJPA es una app Android que:
- se conecta a una API de chistes,
- organiza el contenido por categorías,
- permite asignar una puntuación (0–5),
- almacena/gestiona datos localmente (según lo indicado en la descripción actual del proyecto).

---

## Características principales

- Listado de chistes por categoría.
- Valoración por estrellas (0 a 5).
- Persistencia de datos (puntuaciones) en base de datos.
- Pantallas de autenticación (Login/Registro) disponibles, aunque no críticas para el core.

---

## Tecnologías utilizadas

A partir de la configuración del proyecto:
- Kotlin
- Android Gradle (Kotlin DSL)
- Jetpack Compose
- Navigation Compose
- Retrofit 2 (converters Moshi/Gson, coroutines adapter)
- Kotlin Coroutines
- Coil (imágenes)
- Lifecycle (extensions + kapt compiler)

---

## Estructura del proyecto

```text
.
├─ app/
│  ├─ src/main/
│  │  ├─ AndroidManifest.xml
│  │  ├─ java/                  # código Kotlin (paquetes)
│  │  └─ res/
│  └─ build.gradle.kts
├─ build.gradle.kts
├─ settings.gradle.kts
├─ gradle/
├─ gradlew
└─ gradlew.bat
```

---

## Requisitos previos

- Android Studio (recomendado)
- JDK 11 (configurado en el proyecto)
- Android SDK (compile/target sdk configurados en Gradle)

---

## Instalación y ejecución

1. Abre el proyecto en Android Studio.
2. Sincroniza Gradle.
3. Ejecuta el módulo `app` en un emulador o dispositivo.

Opcional por CLI:

```bash
./gradlew assembleDebug
```

---

## Configuración

- Revisa en el código dónde se define la URL base de la API (suele estar en un servicio/cliente Retrofit).
- Si hay credenciales o endpoints por entorno, se recomienda centralizarlos en un único archivo de configuración.

---

## Buenas prácticas

- Mantener contratos de red (DTOs) separados de modelos de UI.
- Manejar errores de red y estados de carga de forma explícita en Compose.
- Añadir tests (unitarios para parsing y repositorios; instrumentados para flows críticos).

---

## Troubleshooting

- **Fallo al compilar**: confirmar JDK 11 y SDK instalados.
- **Errores de red**: comprobar endpoint/API y permisos de Internet en Manifest (si aplica).

---

## Contribución

1. Fork
2. Rama: `feat/mi-cambio`
3. PR con descripción y pasos de prueba

---

## Autor / contacto

- Ignacio Sanchez Yuste (GitHub: `@IgnacioSanchezYuste`)
