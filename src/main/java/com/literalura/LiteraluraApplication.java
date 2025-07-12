package com.literalura;

import com.literalura.model.Author;
import com.literalura.service.AuthorService;
import com.literalura.service.BookService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

    private final BookService bookService;
    private final AuthorService authorService;
    private final Scanner scanner = new Scanner(System.in);
    
    private static final String RESET = "\u001B[0m";
    private static final String CYAN = "\u001B[36m";
    private static final String YELLOW = "\u001B[33m";
    private static final String GREEN = "\u001B[32m";
    private static final String RED = "\u001B[31m";
    private static final String BOLD = "\u001B[1m";

    public LiteraluraApplication(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    public static void main(String[] args) {
        SpringApplication.run(LiteraluraApplication.class, args);
    }

    @Override
    public void run(String... args) {
        mostrarMenuPrincipal();
    }

    private void mostrarMenuPrincipal() {
        int opcion;
        do {
            System.out.println(CYAN + "\n" + "*".repeat(50));
            System.out.println(BOLD + " LITERALURA " + RESET);
            System.out.println(CYAN + "*" + " ".repeat(48) + "*");
            System.out.println("*" + YELLOW + "  1. " + RESET + "Buscar libro por título" + " ".repeat(23) + CYAN + "*");
            System.out.println("*" + YELLOW + "  2. " + RESET + "Listar libros registrados" + " ".repeat(17) + CYAN + "*");
            System.out.println("*" + YELLOW + "  3. " + RESET + "Listar autores registrados" + " ".repeat(16) + CYAN + "*");
            System.out.println("*" + YELLOW + "  4. " + RESET + "Buscar autores vivos en un año" + " ".repeat(11) + CYAN + "*");
            System.out.println("*" + YELLOW + "  5. " + RESET + "Listar libros por idioma" + " ".repeat(17) + CYAN + "*");
            System.out.println("*" + YELLOW + "  0. " + RESET + "Salir" + " ".repeat(39) + CYAN + "*");
            System.out.println("*".repeat(50) + RESET);
            System.out.print("\n" + BOLD + "Seleccione una opción: " + RESET);

            try {
                opcion = Integer.parseInt(scanner.nextLine());
                procesarOpcion(opcion);
            } catch (NumberFormatException e) {
                System.out.println(RED + "\n❌ Error: Por favor ingrese un número válido." + RESET);
                opcion = -1;
            }
        } while (opcion != 0);
    }

    private void procesarOpcion(int opcion) {
        switch (opcion) {
            case 1 -> buscarLibroPorTitulo();
            case 2 -> listarLibrosRegistrados();
            case 3 -> listarAutoresRegistrados();
            case 4 -> buscarAutoresVivosEnAnio();
            case 5 -> listarLibrosPorIdioma();
            case 0 -> salir();
            default -> System.out.println(RED + "\n❌ Opción no válida. Por favor intente de nuevo." + RESET);
        }
    }

    private void buscarLibroPorTitulo() {
        System.out.println(CYAN + "\n" + "-".repeat(50));
        System.out.println(BOLD + "  BUSCAR LIBRO POR TÍTULO" + RESET);
        System.out.println(CYAN + "-".repeat(50) + RESET);
        System.out.print("\nIngrese el título del libro a buscar: ");
        String titulo = scanner.nextLine();
        
        try {
            String resultado = bookService.buscarYGuardarLibroPorTitulo(titulo);
            System.out.println("\n" + GREEN + "✓ Resultados de la búsqueda:" + RESET);
            System.out.println(resultado);
        } catch (Exception e) {
            System.out.println(RED + "\n❌ Error al buscar el libro: " + e.getMessage() + RESET);
        }
    }

    private void listarLibrosRegistrados() {
        System.out.println(CYAN + "\n" + "-".repeat(50));
        System.out.println(BOLD + "  LIBROS REGISTRADOS" + RESET);
        System.out.println(CYAN + "-".repeat(50) + RESET);
        
        var libros = bookService.listarLibros();
        if (libros.isEmpty()) {
            System.out.println(YELLOW + "\nNo hay libros registrados en la base de datos." + RESET);
        } else {
            libros.forEach(libro -> System.out.println("\n" + libro));
        }
    }

    private void listarAutoresRegistrados() {
        System.out.println(CYAN + "\n" + "-".repeat(50));
        System.out.println(BOLD + "  AUTORES REGISTRADOS" + RESET);
        System.out.println(CYAN + "-".repeat(50) + RESET);
        
        var autores = authorService.listarAutores();
        if (autores.isEmpty()) {
            System.out.println(YELLOW + "\nNo hay autores registrados en la base de datos." + RESET);
        } else {
            autores.forEach(autor -> System.out.println("\n" + autor));
        }
    }

    private void buscarAutoresVivosEnAnio() {
        System.out.println(CYAN + "\n" + "-".repeat(50));
        System.out.println(BOLD + "  BUSCAR AUTORES VIVOS POR AÑO" + RESET);
        System.out.println(CYAN + "-".repeat(50) + RESET);
        
        System.out.print("\nIngrese el año a consultar: ");
        try {
            int anio = Integer.parseInt(scanner.nextLine());
            List<Author> autores = authorService.listarAutoresVivosEnAnio(anio);
            
            System.out.println("\n" + GREEN + "✓ Autores vivos en el año " + anio + ":" + RESET);
            if (autores.isEmpty()) {
                System.out.println(YELLOW + "No se encontraron autores vivos en el año " + anio + "." + RESET);
            } else {
                autores.forEach(autor -> System.out.println("\n" + autor));
            }
        } catch (NumberFormatException e) {
            System.out.println(RED + "\n❌ Error: Por favor ingrese un año válido." + RESET);
        } catch (Exception e) {
            System.out.println(RED + "\n❌ Error al buscar autores: " + e.getMessage() + RESET);
        }
    }

    private void listarLibrosPorIdioma() {
        System.out.println(CYAN + "\n" + "-".repeat(50));
        System.out.println(BOLD + "  BUSCAR LIBROS POR IDIOMA" + RESET);
        System.out.println(CYAN + "-".repeat(50) + RESET);
        System.out.println("\nCódigos de idioma soportados: es (español), en (inglés), fr (francés), pt (portugués)");
        System.out.print("\nIngrese el código de idioma: ");
        
        String idioma = scanner.nextLine().toLowerCase().trim();
        try {
            var libros = bookService.listarLibrosPorIdioma(idioma);
            System.out.println("\n" + GREEN + "✓ Libros en " + obtenerNombreIdioma(idioma) + ":" + RESET);
            if (libros.isEmpty()) {
                System.out.println(YELLOW + "No se encontraron libros en " + obtenerNombreIdioma(idioma) + "." + RESET);
            } else {
                libros.forEach(libro -> System.out.println("\n" + libro));
            }
        } catch (Exception e) {
            System.out.println(RED + "\n❌ Error al buscar libros: " + e.getMessage() + RESET);
        }
    }

    private String obtenerNombreIdioma(String codigo) {
        return switch (codigo) {
            case "es" -> "español";
            case "en" -> "inglés";
            case "fr" -> "francés";
            case "pt" -> "portugués";
            default -> codigo;
        };
    }

    private void salir() {
        System.out.println("\n" + GREEN + "¡Gracias por usar Literalura! ¡Hasta pronto!" + RESET);
        System.exit(0);
    }
}