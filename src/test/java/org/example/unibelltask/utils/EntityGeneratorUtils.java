package org.example.unibelltask.utils;

import org.example.unibelltask.entity.Client;
import org.example.unibelltask.entity.Email;
import org.example.unibelltask.entity.Phone;
import org.example.unibelltask.repository.ClientRepository;
import org.example.unibelltask.repository.EmailRepository;
import org.example.unibelltask.repository.PhoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Утилитный класс для генерации случайных тестовых данных клиентов, телефонов и email.
 *
 * @author Kirill Shinkarev.
 */
@Component
public final class EntityGeneratorUtils {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PhoneRepository phoneRepository;

    @Autowired
    private EmailRepository emailRepository;

    private static final String[] FIRST_NAMES = {
        "Иван", "Мария", "Петр", "Елена", "Дмитрий",
        "Анна", "Алексей", "Ольга", "Сергей", "Наталья"
    };

    private static final String[] LAST_NAMES = {
        "Иванов", "Петрова", "Сидоров", "Кузнецова", "Смирнов",
        "Попова", "Васильев", "Соколова", "Михайлов", "Федорова"
    };

    private static final String[] EMAIL_DOMAINS = {
        "example.com", "mail.com", "test.org", "domain.net"
    };

    /**
     * Генерирует случайные тестовые данные для указанного количества клиентов.
     * Для каждого клиента также генерируется случайное количество телефонов и email.
     *
     * @param clientCount количество клиентов для генерации
     */
    public void generateTestData(int clientCount) {
        for (int i = 0; i < clientCount; i++) {
            Client client = createRandomClient();
            clientRepository.save(client);

            int phoneCount = ThreadLocalRandom.current().nextInt(1, 4); // От 1 до 3 телефонов
            int emailCount = ThreadLocalRandom.current().nextInt(1, 3); // От 1 до 2 email

            createRandomPhones(client, phoneCount);
            createRandomEmails(client, emailCount);
        }
    }

    /**
     * Создает случайного клиента с случайными именем и фамилией.
     *
     * @return объект {@link Client} с заполненными данными
     */
    private Client createRandomClient() {
        String firstName = FIRST_NAMES[ThreadLocalRandom.current().nextInt(FIRST_NAMES.length)];
        String lastName = LAST_NAMES[ThreadLocalRandom.current().nextInt(LAST_NAMES.length)];

        Client client = new Client();
        client.setFirstName(firstName);
        client.setLastName(lastName);

        return client;
    }

    /**
     * Создает и сохраняет заданное количество случайных номеров телефонов для указанного клиента.
     *
     * @param client клиент, которому будут привязаны телефоны
     * @param count  количество телефонов для генерации
     */
    private void createRandomPhones(Client client, int count) {
        Set<String> usedNumbers = new HashSet<>();

        for (int i = 0; i < count; i++) {
            String phoneNumber;
            do {
                phoneNumber = generateRandomPhoneNumber();
            } while (usedNumbers.contains(phoneNumber)); // Избегаем дубликатов для клиента

            usedNumbers.add(phoneNumber);

            Phone phone = new Phone();
            phone.setPhoneNumber(phoneNumber);
            phone.setClient(client);

            phoneRepository.save(phone);
        }
    }

    /**
     * Создает и сохраняет заданное количество случайных email для указанного клиента.
     *
     * @param client клиент, которому будут привязаны email
     * @param count  количество email для генерации
     */
    private void createRandomEmails(Client client, int count) {
        Set<String> usedEmails = new HashSet<>();

        for (int i = 0; i < count; i++) {
            String emailAddress;
            do {
                emailAddress = generateRandomEmail(client.getFirstName(), client.getLastName());
            } while (usedEmails.contains(emailAddress)); // Избегаем дубликатов для клиента

            usedEmails.add(emailAddress);

            Email email = new Email();
            email.setEmailAddress(emailAddress);
            email.setClient(client);

            emailRepository.save(email);
        }
    }

    /**
     * Генерирует случайный номер телефона в формате +7XXXXXXXXXX.
     *
     * @return строка с номером телефона
     */
    private String generateRandomPhoneNumber() {
        long number = ThreadLocalRandom.current().nextLong(1000000000L, 9999999999L);
        return "+7" + number;
    }

    /**
     * Генерирует случайный email на основе имени, фамилии и случайного домена.
     *
     * @param firstName имя клиента
     * @param lastName  фамилия клиента
     * @return строка с email адресом
     */
    private String generateRandomEmail(String firstName, String lastName) {
        String domain = EMAIL_DOMAINS[ThreadLocalRandom.current().nextInt(EMAIL_DOMAINS.length)];
        String email = firstName.toLowerCase() + "." + lastName.toLowerCase()
            + ThreadLocalRandom.current().nextInt(1000) + "@" + domain;
        return email;
    }
}
