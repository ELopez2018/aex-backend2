package com.aex.platform.service;

import com.aex.platform.entities.*;
import com.aex.platform.entities.models.TransantionOld;
import com.aex.platform.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.swing.text.html.Option;
import java.security.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ApiService {

    @Autowired
    UserRepository dataRepository;
    private final PasswordEncoder passwordEncoder;

    private final RestTemplate restTemplate;

    @Autowired
    BankDataRepository bankDataRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BankRepository bankRepository;
    @Autowired
    TransactionsRepository transactionsRepository;

    @Autowired
    StatusRepository statusRepository;

    @Autowired
    public ApiService(PasswordEncoder passwordEncoder, RestTemplate restTemplate) {
        this.passwordEncoder = passwordEncoder;
        this.restTemplate = restTemplate;
    }

    private Long usersTotal = 0L;

    public String fetchDataFromApi(String apiUrl) {
        UserOld[] response = restTemplate.getForObject(apiUrl, UserOld[].class);
            List<User> userList = new ArrayList<>();
        Set<String> processedDocumentNumbers = new HashSet<>();
        this.usersTotal = 0l;
        for (UserOld user : response) {
            this.usersTotal++;
            String documentNumber = keepOnlyNumbers(user.getIdentification());
            if (documentNumber != null && !documentNumber.isEmpty()) {
                if (!processedDocumentNumbers.contains(documentNumber)) {
                    Optional<User> existingUser = dataRepository.findByDocumentNumber(Long.parseLong(documentNumber));
                    if (existingUser.isEmpty()) {
                        userList.add(convertUser(user));
                    }
                    processedDocumentNumbers.add(documentNumber);
                }
            }
        }
        List<User> savedUsers = dataRepository.saveAll(userList);
        return "Migracion de Usuarios Existoso. " + savedUsers.size() + " usuarios de " + this.usersTotal + " registros";
    }

    public User convertUser(UserOld user) {
        if (user == null) {
            return null;
        }
        String[] fullName = user.getName().split(" ");
        String firstName = "";
        String secondName = "";
        String lastName = "";
        String surname = "";
        switch (fullName.length) {
            case 1:
                firstName = fullName[0].toUpperCase();
                break;
            case 2:
                firstName = fullName[0].toUpperCase();
                lastName = fullName[1].toUpperCase();
                break;
            case 3:
                firstName = fullName[0].toUpperCase();
                secondName = fullName[1].toUpperCase();
                lastName = fullName[2].toUpperCase();
                break;
            case 4:
                firstName = fullName[0].toUpperCase();
                secondName = fullName[1].toUpperCase();
                lastName = fullName[2].toUpperCase();
                surname = fullName[3].toUpperCase();

        }
        //System.out.println("Usuario nuevo " + user.getIdentification());
        User newUser = new User();
        newUser.setFirstName(firstName.toUpperCase());
        newUser.setSecondName(secondName.toUpperCase());
        newUser.setLastName(lastName.toUpperCase());
        newUser.setSurname(surname.toUpperCase());
        newUser.setFullName(user.getName().toUpperCase());
        newUser.setBirthdate(user.getBirthday());
        newUser.setCellPhone(user.getPhone());
        newUser.setEmail(user.getEmail());
        newUser.setCurrentCountry(2L);
        String dni = keepOnlyNumbers(user.getIdentification());
        if (dni.isEmpty()) {
            Instant timestamp = Instant.now();
            Long milliseconds = timestamp.toEpochMilli();
            newUser.setDocumentNumber(milliseconds);
        } else {
            newUser.setDocumentNumber(Long.parseLong(dni));
        }
        //newUser.setDocumentNumber(Long.parseLong(keepOnlyNumbers(user.getIdentification())));
        if (user.getTypeIdentification() == null || user.getTypeIdentification().isEmpty()) {
            newUser.setDocumentType("CI");
        } else {
            newUser.setDocumentType(user.getTypeIdentification());
        }
        newUser.setBalance(0.00);
        newUser.setPostpaid(false);
        newUser.setRole(Role.USER);
        newUser.setCreatedAt(LocalDateTime.now(ZoneId.of("America/Bogota")).toString());
        newUser.setUpdatedAt(LocalDateTime.now(ZoneId.of("America/Bogota")).toString());
        newUser.setPassword(passwordEncoder.encode(keepOnlyNumbers(user.getIdentification())));
        return newUser;
    }

    public String keepOnlyNumbers(String input) {
        if (input == "") {
            return null;
        }
        ;
        Pattern pattern = Pattern.compile("\\D+");
        Matcher matcher = pattern.matcher(input);
        return matcher.replaceAll("");
    }

    public String migrationTransaction(String apiUrl) {
        this.usersTotal = 0l;
        TransantionOld lastGiro = new TransantionOld();
        TransantionOld[] response = restTemplate.getForObject(apiUrl, TransantionOld[].class);
        for (TransantionOld giro : response) {
            lastGiro = giro;
            //obtener los usuarios
            User userSent = getUserSend(giro.getUserSend());
            User userRecieved = getUserRecipient(giro.getUserReceived());
            // crear datos bancarios
            BankData account = createAccount(giro);
            // Guardar Giro
            saveTransantion(giro, userSent, userRecieved, account);
            this.usersTotal++;
        }
        return "La migración de giros fue realizada con éxito."+this.usersTotal+ " giros migrados" ;

    }

    public String migrationContacts(String apiUrl) {
        this.usersTotal = 0l;
        ContactsOlds[] response = restTemplate.getForObject(apiUrl, ContactsOlds[].class);
        for (ContactsOlds contact : response) {
            //Crear los usuarios
            User newUser = new User();
            String document = keepOnlyNumbers(contact.getIdentification());
            if (document.isEmpty()) {
                Instant timestamp = Instant.now();
                Long milliseconds = timestamp.toEpochMilli();
                contact.setIdentification(milliseconds.toString());
            }
            Optional<User> user = userRepository.findByDocumentNumber(Long.parseLong(keepOnlyNumbers(contact.getIdentification())));
            if (user.isEmpty()) {
                newUser = createUserFromContact(contact);
                this.usersTotal++;
            } else {
                newUser = user.get();
            }
            // Crear datos bancarios
            String banktype = "Ahorros";
            if (contact.getAccount_type().equals("Checking")) {
                banktype = "Corriente";
            }
            TransantionOld accountOld = new TransantionOld();
            accountOld.setBank_count(contact.getAccount_number());
            accountOld.setUserReceived(newUser.getDocumentNumber());
            accountOld.setBank_type(banktype);
            createAccount(accountOld);
        }
        return "La migración fue realizada con éxito." + this.usersTotal + " usuarios creados";

    }

    private User createUserFromContact(ContactsOlds contact) {
        User newUser = convertUser((UserOld) contact);
        return userRepository.save(newUser);
    }

    private User getUserSend(Long DocumentNumber) {
        Optional<User> userSend = userRepository.findByDocumentNumber(DocumentNumber);
        return userSend.orElse(null);
    }

    private User getUserRecipient(Long DocumentNumber) {
        Optional<User> userReceived = userRepository.findByDocumentNumber(DocumentNumber);
        if (userReceived.isEmpty()) {
            UserOld[] userOlds = restTemplate.getForObject("http://localhost:3000/users/transactions/contacts?identification=" + DocumentNumber, UserOld[].class);
            if (userOlds == null || userOlds.length < 1) {
                System.out.println("[getUserRecipient] userOlds no encontrado  en la db vieja : " + DocumentNumber);
                return null;
            }
            if (convertUser(userOlds[0]) != null) {
                System.out.println("[getUserRecipient] Usuario Registrado");
                return userRepository.save(convertUser(userOlds[0]));
            }
        }
        return userReceived.get();
    }

    private BankData createAccount(TransantionOld giro) {
        if (bankDataRepository.findByAccountNumber(giro.getBank_count()).isPresent()) {
            return bankDataRepository.findByAccountNumber(giro.getBank_count()).get();
        }
        Optional<User> userReceived = userRepository.findByDocumentNumber(giro.getUserReceived());
        if (userReceived.isEmpty()) {
            System.out.println("[createAccount] Usuario no Encontrado - " + giro.getUserReceived());
            return null;
        }
        if (giro.getBank_count() == null || giro.getBank_count().isEmpty() || giro.getBank_count().length() < 4) {
            System.out.println("[createAccount] - Bank_count NULL");
            return null;
        }
        String code = giro.getBank_count().substring(0, 4);
        Optional<Bank> bank = bankRepository.findByCode(code);
        if (bank.isEmpty()) {
            System.out.println("[createAccount] No existe el banco: " + giro.getBank_count() + " - " + giro.getBank_name());
            return null;
        }
        BankData account = new BankData();
        account.setAccountNumber(giro.getBank_count());
        account.setBank(bank.get());
        account.setType(giro.getBank_type());
        account.setUser(userReceived.get());
        account.setCreatedAt(LocalDateTime.now(ZoneId.of("America/Bogota")));
        account.setUpdatedAt(LocalDateTime.now(ZoneId.of("America/Bogota")));
        return bankDataRepository.saveAndFlush(account);
    }

    private String saveTransantion(TransantionOld giro, User userSent, User userRecieved, BankData account) {
        try {
            Transaction transaction = new Transaction();
            transaction.setClient(userSent);
            transaction.setRecipient(userRecieved);
            transaction.setReceivingBank(account);
            transaction.setStatus(statusRepository.findById(giro.getId_marker()).get().getId());
            transaction.setAmountSent(giro.getFromValue());
            transaction.setCurrencyFrom(giro.getFromtype());
            transaction.setAmountReceived(giro.getTotalSend());
            transaction.setDateReceive(giro.getDateReceive());
            transaction.setDateSend(giro.getDateSend());
            transaction.setCurrencyTo(giro.getTotype());
            transaction.setImages(giro.getComprobant());
            transactionsRepository.save(transaction);
            return "Registro Guardado";
        } catch (Exception e) {
            System.out.println(e.toString());
            return "Hubo un error durante la migración de transacciones en el giro : " + giro.getId().toString();
        }
    }
}

