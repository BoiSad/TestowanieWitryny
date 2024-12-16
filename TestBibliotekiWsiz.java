import org.junit.jupiter.api.Test;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

public class TestBibliotekiWsiz {

    // Test, który sprawdza, czy główna strona katalogu jest dostępna i zwraca poprawny status HTTP
    @Test
    public void testDostepnoscStronyGlownej() throws IOException {
        String url = "https://biblioteka.wsiz.rzeszow.pl/integro/catalog";

        // Utworzenie połączenia HTTP
        HttpURLConnection polaczenie = (HttpURLConnection) new URL(url).openConnection();
        polaczenie.setRequestMethod("GET");
        polaczenie.connect();

        // Pobranie statusu HTTP
        int statusKod = polaczenie.getResponseCode();

        // Sprawdzenie, czy strona odpowiada kodem 200 (OK)
        assertEquals(200, statusKod, "Strona główna katalogu jest niedostępna lub zwróciła błąd");
    }

    // Test, który sprawdza, czy strona logowania jest dostępna
    @Test
    public void testDostepnoscStronyLogowania() throws IOException {
        String url = "https://biblioteka.wsiz.rzeszow.pl/integro/authorization/login"; // URL formularza logowania

        // Utworzenie połączenia HTTP
        HttpURLConnection polaczenie = (HttpURLConnection) new URL(url).openConnection();
        polaczenie.setRequestMethod("GET");
        polaczenie.connect();

        // Pobranie statusu HTTP
        int statusKod = polaczenie.getResponseCode();

        // Sprawdzenie, czy strona logowania jest dostępna
        assertEquals(200, statusKod, "Strona logowania jest niedostępna lub zwróciła błąd");
    }

    // Test, który sprawdza, czy strona rejestracji jest dostępna
    @Test
    public void testDostepnoscStronyRejestracji() throws IOException {
        String url = "https://biblioteka.wsiz.rzeszow.pl/integro/registration"; // URL formularza rejestracji

        // Utworzenie połączenia HTTP
        HttpURLConnection polaczenie = (HttpURLConnection) new URL(url).openConnection();
        polaczenie.setRequestMethod("GET");
        polaczenie.connect();

        // Pobranie statusu HTTP
        int statusKod = polaczenie.getResponseCode();

        // Sprawdzenie, czy strona rejestracji jest dostępna
        assertEquals(200, statusKod, "Strona rejestracji jest niedostępna lub zwróciła błąd");
    }

    // Test, który sprawdza, czy funkcjonalność wyszukiwania działa poprawnie
    @Test
    public void testFunkcjonalnoscWyszukiwania() throws IOException {
        String url = "https://biblioteka.wsiz.rzeszow.pl/integro/catalog?query=Java"; // Zmienna URL dla wyszukiwania książek

        // Utworzenie połączenia HTTP
        HttpURLConnection polaczenie = (HttpURLConnection) new URL(url).openConnection();
        polaczenie.setRequestMethod("GET");
        polaczenie.connect();

        // Pobranie statusu HTTP
        int statusKod = polaczenie.getResponseCode();

        // Sprawdzenie, czy strona wyszukiwania książek zwróciła odpowiedni kod
        assertEquals(200, statusKod, "Strona wyszukiwania książek jest niedostępna lub zwróciła błąd");
    }

    // Test logowania z poprawnymi danymi
    @Test
    public void testLogowanieZPoprawnymiDanymi() throws IOException {
        // URL formularza logowania
        String url = "https://biblioteka.wsiz.rzeszow.pl/integro/authorization/login";

        // Parametry do wysłania w formularzu logowania
        String daneUzytkownika = "username=testuser&password=testpassword"; // Zastąp odpowiednimi danymi

        // Utworzenie połączenia HTTP typu POST
        HttpURLConnection polaczenie = (HttpURLConnection) new URL(url).openConnection();
        polaczenie.setRequestMethod("POST");
        polaczenie.setDoOutput(true);
        polaczenie.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        // Wysłanie danych formularza (login + hasło)
        try (OutputStream os = polaczenie.getOutputStream()) {
            byte[] input = daneUzytkownika.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        // Pobranie odpowiedzi HTTP
        int statusKod = polaczenie.getResponseCode();

        // Sprawdzenie, czy odpowiedź jest poprawna (np. kod 200 lub 3xx dla przekierowania)
        assertTrue(statusKod == 200 || statusKod >= 300, "Logowanie nie powiodło się lub kod odpowiedzi jest nieprawidłowy: " + statusKod);
    }

    // Test logowania z błędnymi danymi
    @Test
    public void testLogowanieZBlednymiDanymi() throws IOException {
        // URL formularza logowania
        String url = "https://biblioteka.wsiz.rzeszow.pl/integro/authorization/login";

        // Parametry do wysłania w formularzu logowania
        String daneUzytkownika = "username=wronguser&password=wrongpassword"; // Zastąp błędnymi danymi

        // Utworzenie połączenia HTTP typu POST
        HttpURLConnection polaczenie = (HttpURLConnection) new URL(url).openConnection();
        polaczenie.setRequestMethod("POST");
        polaczenie.setDoOutput(true);
        polaczenie.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        // Wysłanie danych formularza (login + hasło)
        try (OutputStream os = polaczenie.getOutputStream()) {
            byte[] input = daneUzytkownika.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        // Pobranie odpowiedzi HTTP
        int statusKod = polaczenie.getResponseCode();

        // Sprawdzenie, czy odpowiedź jest błędna (np. 401 Unauthorized dla niepoprawnych danych)
        assertEquals(401, statusKod, "Logowanie powinno nie powieść się z błędnymi danymi, kod odpowiedzi: " + statusKod);
    }
}
