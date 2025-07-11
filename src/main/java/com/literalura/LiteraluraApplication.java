import com.literalura.service.GutendexClient;
import com.literalura.dto.BookDTO;

import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

	private final GutendexClient client = new GutendexClient();

	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Scanner scanner = new Scanner(System.in);

		int opcion = -1;

		while (opcion != 0) {
			System.out.println("\n*** MENU ***");
			System.out.println("1 - Mostrar libros desde Gutendex");
			System.out.println("2 - Buscar libros en español");
			System.out.println("0 - Salir");
			System.out.print("Elige una opción: ");

			// validar si realmente es un número
			if (!scanner.hasNextInt()) {
				System.out.println("Por favor ingresa un número válido.");
				scanner.next(); // limpiar la entrada errónea
				continue;
			}

			opcion = scanner.nextInt();
			scanner.nextLine(); // limpiar salto de línea

			switch (opcion) {
				case 1:
					mostrarLibros();
					break;
				case 2:
					buscarLibrosPorIdioma(scanner, "es");
					break;
				case 0:
					System.out.println("¡Hasta luego!");
					break;
				default:
					System.out.println("Opción no válida, intenta nuevamente.");
			}
		}
	}

	private void mostrarLibros() {
		List<BookDTO> books = client.getBooks();
		books.forEach(System.out::println);
	}

	private void buscarLibrosPorIdioma(Scanner scanner, String idioma) {
		List<BookDTO> books = client.getBooks();
		System.out.println("\nLibros en idioma '" + idioma + "':");
		books.stream()
				.filter(book -> book.getLanguages() != null && book.getLanguages().contains(idioma))
				.forEach(System.out::println);
	}
}
