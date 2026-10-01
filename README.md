# Ejercicio 10 — Padrón de matrimonios

Aplicación JavaFX para gestionar registros de uniones civiles.

## Campos
Número de acta, contrayente 1, contrayente 2, fecha de celebración y lugar.

## Reglas
- El número de acta debe ser único (al modificar, se permite conservar el propio número).
- Los contrayentes deben ser distintos y la fecha no puede ser futura.
- Búsqueda por nombre de cualquiera de los contrayentes (o por número de acta).

## Ejecutar
JDK 21. Abre esta carpeta en IntelliJ IDEA como proyecto Maven y ejecuta SysVentas.java, o:

    ./mvnw clean javafx:run

Los datos se guardan en memoria.
