import org.junit.jupiter.api.Test;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.*;

public class TestWyszukiwaniaKsiazek {

    // Test wyszukiwania książek po słowie kluczowym
    @Test
    public void testWyszukiwanieKsiążek() throws IOException {
        String url = "https://biblioteka.wsiz.rzeszow.pl/integro/catalog?query=Java"; // Przykład wyszukiwania książek po słowie "Java"

        // Utworzenie połączenia HTTP
        HttpURLConnection polaczenie = (HttpURLConnection) new URL(url).openConnection();
        polaczenie.setRequestMethod("GET");
        polaczenie.connect();

        // Pobranie statusu HTTP
        int statusKod = polaczenie.getResponseCode();
        assertEquals(200, statusKod, "Wyszukiwanie książek nie powiodło się lub zwróciło błąd");

        // Odczytanie treści odpowiedzi
        Scanner scanner = new Scanner(polaczenie.getInputStream());
        StringBuilder content = new StringBuilder();
        while (scanner.hasNext()) {
            content.append(scanner.nextLine());
        }

        // Sprawdzenie, czy odpowiedź zawiera tytuł książki (np. książki o "Java")
        assertTrue(content.toString().contains("Java"), "Odpowiedź nie zawiera książek o tematyce Java.");


    }

    // Test wyszukiwania książek, gdy nie ma wyników
    @Test
    public void testBrakWynikowWyszukiwania() throws IOException {
        String url = "https://biblioteka.wsiz.rzeszow.pl/integro/catalog?query=NieistniejącaKsiążka"; // Przykład wyszukiwania książek, których nie ma

        // Utworzenie połączenia HTTP
        HttpURLConnection polaczenie = (HttpURLConnection) new URL(url).openConnection();
        polaczenie.setRequestMethod("GET");
        polaczenie.connect();

        // Pobranie statusu HTTP
        int statusKod = polaczenie.getResponseCode();
        assertEquals(200, statusKod, "Wyszukiwanie książek zwróciło błąd lub nie zwróciło odpowiednich wyników");

        // Odczytanie treści odpowiedzi
        Scanner scanner = new Scanner(polaczenie.getInputStream());
        StringBuilder content = new StringBuilder();
        while (scanner.hasNext()) {
            content.append(scanner.nextLine());
        }

        // Opcjonalnie: Można sprawdzić, czy odpowiedź zawiera informację o braku wyników
        assertTrue(content.toString().contains("Brak wyników"), "Odpowiedź nie zawiera komunikatu o braku wyników.");

        // Dodatkowe sprawdzenie: czy nie ma linków do książek (jeśli brak wyników)
        assertFalse(content.toString().contains("<a href="), "Odpowiedź zawiera linki, mimo braku wyników.");
    }

    // Test wyszukiwania książek bez podania zapytania (puste zapytanie)
    @Test
    public void testPusteWyszukiwanie() throws IOException {
        String url = "https://biblioteka.wsiz.rzeszow.pl/integro/search/description?q=&index=1&scope=full"; // Wyszukiwanie bez słowa kluczowego

        // Utworzenie połączenia HTTP
        HttpURLConnection polaczenie = (HttpURLConnection) new URL(url).openConnection();
        polaczenie.setRequestMethod("GET");
        polaczenie.connect();

        // Pobranie statusu HTTP
        int statusKod = polaczenie.getResponseCode();
        assertEquals(200, statusKod, "Wyszukiwanie bez zapytania zwróciło błąd");

        // Odczytanie treści odpowiedzi
        Scanner scanner = new Scanner(polaczenie.getInputStream());
        StringBuilder content = new StringBuilder();
        while (scanner.hasNext()) {
            content.append(scanner.nextLine());
        }

        // Sprawdzanie, czy odpowiedź zawiera komunikat o braku wyników
        assertTrue(content.toString().contains("Brak wyników"), "Odpowiedź nie zawiera komunikatu o braku wyników.");

        // Dodatkowe sprawdzenie: Czy odpowiedź zawiera jakiekolwiek książki lub propozycje
        assertFalse(content.toString().contains("<a href="), "Odpowiedź zawiera linki do książek mimo braku wyników.");
    }

    // Test wyszukiwania książek z odpowiedzią, która zawiera linki do książek
    @Test
    public void testObecnosciLinkowDoKsiazek() throws IOException {
        String url = "https://biblioteka.wsiz.rzeszow.pl/integro/catalog?query=Java"; // Przykład wyszukiwania książek po słowie "Java"

        // Utworzenie połączenia HTTP
        HttpURLConnection polaczenie = (HttpURLConnection) new URL(url).openConnection();
        polaczenie.setRequestMethod("GET");
        polaczenie.connect();

        // Pobranie statusu HTTP
        int statusKod = polaczenie.getResponseCode();
        assertEquals(200, statusKod, "Wyszukiwanie książek nie powiodło się lub zwróciło błąd");

        // Odczytanie treści odpowiedzi
        Scanner scanner = new Scanner(polaczenie.getInputStream());
        StringBuilder content = new StringBuilder();
        while (scanner.hasNext()) {
            content.append(scanner.nextLine());
        }

        // Sprawdzanie, czy odpowiedź zawiera linki do książek
        assertTrue(content.toString().contains("<a href="), "Odpowiedź nie zawiera linków do książek.");
    }

    // Test wyszukiwania książek, w których w wynikach znajdują się tytuły książek
    @Test
    public void testObecnoscTytulowKsiazek() throws IOException {
        String url = "https://biblioteka.wsiz.rzeszow.pl/integro/catalog?query=Java"; // Przykład wyszukiwania książek po słowie "Java"

        // Utworzenie połączenia HTTP
        HttpURLConnection polaczenie = (HttpURLConnection) new URL(url).openConnection();
        polaczenie.setRequestMethod("GET");
        polaczenie.connect();

        // Pobranie statusu HTTP
        int statusKod = polaczenie.getResponseCode();
        assertEquals(200, statusKod, "Wyszukiwanie książek nie powiodło się lub zwróciło błąd");

        // Odczytanie treści odpowiedzi
        Scanner scanner = new Scanner(polaczenie.getInputStream());
        StringBuilder content = new StringBuilder();
        while (scanner.hasNext()) {
            content.append(scanner.nextLine());
        }

        // Sprawdzanie, czy odpowiedź zawiera tytuły książek
        assertTrue(content.toString().contains("java"), "Odpowiedź nie zawiera tytułów książek.");
    }
}
