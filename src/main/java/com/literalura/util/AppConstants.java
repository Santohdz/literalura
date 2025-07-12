package com.literalura.util;

/**
 * Clase que contiene constantes utilizadas en toda la aplicación.
 */
public final class AppConstants {
    // Mensajes de la aplicación
    public static final String APP_TITLE = "LITERALURA";
    public static final String EXIT_MESSAGE = "¡Gracias por usar Literalura! ¡Hasta pronto! 📖";
    public static final String INVALID_OPTION = "❌ Opción no válida. Por favor intente de nuevo.";
    public static final String INVALID_NUMBER = "❌ Error: Por favor ingrese un número válido.";
    public static final String NO_BOOKS_FOUND = "No hay libros registrados en la base de datos.";
    public static final String NO_AUTHORS_FOUND = "No hay autores registrados en la base de datos.";
    public static final String NO_BOOKS_IN_LANGUAGE = "No se encontraron libros en %s.";
    public static final String NO_AUTHORS_IN_YEAR = "No se encontraron autores vivos en el año %d.";

    // Formatos
    public static final String DATE_FORMAT = "yyyy";
    public static final String MENU_ITEM_FORMAT = "*%s%2d. %s%s%" + (50 - 7) + "s*";

    // Longitudes máximas
    public static final int MAX_TITLE_LENGTH = 255;
    public static final int MAX_NAME_LENGTH = 100;
    public static final int MAX_LANGUAGE_LENGTH = 10;

    // Validación
    public static final String TITLE_REQUIRED = "El título del libro es obligatorio";
    public static final String AUTHOR_NAME_REQUIRED = "El nombre del autor es obligatorio";
    public static final String LANGUAGE_REQUIRED = "El idioma es obligatorio";

    // Expresiones regulares
    public static final String LANGUAGE_CODE_PATTERN = "[a-z]{2}";

    private AppConstants() {
        // Constructor privado para evitar instanciación
    }
}
