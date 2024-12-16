import org.junit.jupiter.api.Test;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.*;

public class TestLogowania {

    // Test logowania z poprawnymi danymi
    @Test
    public void testLogowanieZPoprawnymiDanymi() throws IOException {
        String url = "https://biblioteka.wsiz.rzeszow.pl/integro/authorization/login";

        // Parametry do wysłania w formularzu logowania
        String daneUzytkownika = "username=correctUser&password=correctPassword"; // Poprawne dane logowania

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

        // Jeśli kod odpowiedzi to 200 lub 3xx (przekierowanie), logowanie jest poprawne
        boolean logowanieUdane = statusKod == 200 || statusKod >= 300;

        // Sprawdzenie, że logowanie powinno zwrócić true dla poprawnych danych
        assertTrue(logowanieUdane, "Logowanie z poprawnymi danymi nie powiodło się. Kod odpowiedzi: " + statusKod);
    }

    // Test logowania z błędnymi danymi
    @Test
    public void testLogowanieZBlednymiDanymi() throws IOException {
        String url = "https://biblioteka.wsiz.rzeszow.pl/integro/authorization/login";

        // Parametry do wysłania w formularzu logowania
        String daneUzytkownika = "username=wrongUser&password=wrongPassword"; // Błędne dane logowania

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

        // Sprawdzenie, czy odpowiedź to błąd 401 (Unauthorized) dla błędnych danych
        assertEquals(401, statusKod, "Logowanie powinno się nie powieść z błędnymi danymi, kod odpowiedzi: " + statusKod);
    }

    // Test logowania z pustymi danymi
    @Test
    public void testLogowanieZPustymiDanymi() throws IOException {
        String url = "https://biblioteka.wsiz.rzeszow.pl/integro/authorization/login";

        // Parametry do wysłania w formularzu logowania (puste dane)
        String daneUzytkownika = "username=&password=";

        // Utworzenie połączenia HTTP typu POST
        HttpURLConnection polaczenie = (HttpURLConnection) new URL(url).openConnection();
        polaczenie.setRequestMethod("POST");
        polaczenie.setDoOutput(true);
        polaczenie.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        // Wysłanie pustych danych formularza
        try (OutputStream os = polaczenie.getOutputStream()) {
            byte[] input = daneUzytkownika.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        // Pobranie odpowiedzi HTTP
        int statusKod = polaczenie.getResponseCode();

        // Jeśli kod odpowiedzi to 400, 422, 301 lub 302 (błąd lub przekierowanie), logowanie powinno zwrócić false
        boolean logowanieUdane = statusKod == 400 || statusKod == 422 || statusKod == 301 || statusKod == 302;

        // Sprawdzenie, że logowanie powinno zwrócić false dla pustych danych
        assertFalse(logowanieUdane, "Logowanie z pustymi danymi powinno zwrócić false. Kod odpowiedzi: " + statusKod);
    }
}
