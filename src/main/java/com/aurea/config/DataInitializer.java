package com.aurea.config;

import com.aurea.entity.*;
import com.aurea.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

// =============================================
// DATA INITIALIZER
// Popola il database con dati di esempio all'avvio
// Utile per sviluppo e demo, commentare @Component per disabilitare
// =============================================
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UtenteRepository utenteRepo;
    private final IscrittoRepository iscrittoRepo;
    private final AbbonamentoRepository abbonamentoRepo;
    private final CorsoRepository corsoRepo;
    private final PrenotazioneRepository prenotazioneRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        // Crea utenti del gestionale solo se non esistono
        if (!utenteRepo.existsByUsername("admin")) {
            Utente admin = new Utente(null, "admin",
                    passwordEncoder.encode("admin123"), // password: admin123
                    "ADMIN", "Mario", "Rossi");
            utenteRepo.save(admin);

            Utente receptionist = new Utente(null, "receptionist",
                    passwordEncoder.encode("recep123"), // password: recep123
                    "RECEPTIONIST", "Anna", "Verdi");
            utenteRepo.save(receptionist);
        }

        // Crea iscritti di esempio
        if (iscrittoRepo.count() == 0) {
            Iscritto i1 = new Iscritto(null, "Luca", "Ferrari", "luca.ferrari@email.it",
                    "3331234567", LocalDate.of(1990, 5, 15),
                    LocalDate.of(2024, 1, 10), "ATTIVO", null, null);

            Iscritto i2 = new Iscritto(null, "Sara", "Bianchi", "sara.bianchi@email.it",
                    "3347654321", LocalDate.of(1995, 8, 22),
                    LocalDate.of(2024, 3, 1), "ATTIVO", null, null);

            Iscritto i3 = new Iscritto(null, "Marco", "Esposito", "marco.esposito@email.it",
                    "3389876543", LocalDate.of(1988, 11, 3),
                    LocalDate.of(2024, 2, 15), "ATTIVO", null, null);

            Iscritto i4 = new Iscritto(null, "Giulia", "Romano", "giulia.romano@email.it",
                    "3361112233", LocalDate.of(2000, 4, 10),
                    LocalDate.of(2024, 4, 20), "SOSPESO", null, null);

            i1 = iscrittoRepo.save(i1);
            i2 = iscrittoRepo.save(i2);
            i3 = iscrittoRepo.save(i3);
            i4 = iscrittoRepo.save(i4);

            // Crea abbonamenti
            LocalDate oggi = LocalDate.now();

            abbonamentoRepo.save(new Abbonamento(null, "PRO",
                    oggi.minusMonths(2), oggi.plusMonths(1),
                    new BigDecimal("69.00"), "ATTIVO", i1));

            abbonamentoRepo.save(new Abbonamento(null, "ELITE",
                    oggi.minusMonths(1), oggi.plusDays(5), // scade tra 5 giorni!
                    new BigDecimal("129.00"), "ATTIVO", i2));

            abbonamentoRepo.save(new Abbonamento(null, "BASE",
                    oggi.minusMonths(3), oggi.plusMonths(2),
                    new BigDecimal("39.00"), "ATTIVO", i3));

            abbonamentoRepo.save(new Abbonamento(null, "PRO",
                    oggi.minusMonths(4), oggi.minusMonths(1),
                    new BigDecimal("69.00"), "SCADUTO", i4));

            // Crea corsi
            Corso crossfit = new Corso(null, "CrossFit", "Allenamento ad alta intensità",
                    "Sara Ruggiero", "LUNEDI", LocalTime.of(18, 0), 45, 15, "ATTIVO", null);

            Corso yoga = new Corso(null, "Yoga", "Flessibilità e benessere",
                    "Maria Conti", "MARTEDI", LocalTime.of(9, 0), 60, 12, "ATTIVO", null);

            Corso spinning = new Corso(null, "Spinning", "Cardio su bici stazionaria",
                    "Marco Esposito", "MERCOLEDI", LocalTime.of(19, 30), 50, 20, "ATTIVO", null);

            Corso boxe = new Corso(null, "Boxe", "Tecniche base e avanzate",
                    "Antonio De Rosa", "GIOVEDI", LocalTime.of(20, 0), 60, 10, "ATTIVO", null);

            crossfit = corsoRepo.save(crossfit);
            yoga = corsoRepo.save(yoga);
            spinning = corsoRepo.save(spinning);
            boxe = corsoRepo.save(boxe);

            // Crea prenotazioni
            prenotazioneRepo.save(new Prenotazione(null, oggi,
                    LocalDateTime.now().minusHours(2), "CONFERMATA", i1, crossfit));

            prenotazioneRepo.save(new Prenotazione(null, oggi,
                    LocalDateTime.now().minusHours(1), "CONFERMATA", i2, crossfit));

            prenotazioneRepo.save(new Prenotazione(null, oggi.plusDays(1),
                    LocalDateTime.now(), "CONFERMATA", i3, yoga));

            System.out.println("✅ Dati di esempio caricati con successo!");
            System.out.println("👤 Admin: username=admin, password=admin123");
            System.out.println("👤 Receptionist: username=receptionist, password=recep123");
        }
    }
}
