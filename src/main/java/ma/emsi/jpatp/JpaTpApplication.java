package ma.emsi.jpatp;


import ma.emsi.jpatp.DAO.Entities.Patient;
import ma.emsi.jpatp.DAO.repositories.PatientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

@SpringBootApplication
public class JpaTpApplication /* TP1 :implements CommandLineRunner*/ {

    public static void main(String[] args) {
        SpringApplication.run(JpaTpApplication.class, args);
    }

    //@Bean : au demarrage bean permet d'executer la methode commandlINERunner automatiquement
    CommandLineRunner commandLineRunner(PatientRepository patientRepository) {
        return args -> {
            patientRepository.save(new Patient(null, "Aya", new Date(), false, 112));
            patientRepository.save(new Patient(null, "Hanine", new Date(), false, 212));
            patientRepository.save(new Patient(null, "Youssef", new Date(), false, 312));
            patientRepository.save(new Patient(null, "Houria", new Date(), false, 124));
            patientRepository.save(new Patient(null, "Imane", new Date(), false, 512));
            patientRepository.save(new Patient(null, "Yahya", new Date(), false, 612));

            patientRepository.findAll().forEach(p -> {
                System.out.println(p.getNom());
            });
        };

    }
       @Bean
         PasswordEncoder passwordEncoder(){
            return new BCryptPasswordEncoder();
        }


    /* TP1 :
    @Autowired
    private PatientRepository patientRepository;
    public static void main(String[] args) {
        SpringApplication.run(JpaTpApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        for(int i=0;i<50;i++) {
            patientRepository.save(new Patient(null, "imane", new Date(), Math.random()>0.5?true:false, (int)(Math.random()*100)));

        }
       Page<Patient> patients = patientRepository.findAll(PageRequest.of(0, 5)); // le nombre d'elements enregistrés dans la base divisé par size pour avoir le nombre de page.
       System.out.println("Total pages : "+patients.getTotalPages());
       System.out.println("Total elements :"+patients.getTotalElements());
       System.out.println("Num Page :"+patients.getNumber());


       System.out.println("*********************");
        List<Patient> content = patients.getContent();
        Page<Patient> byMalade =patientRepository.findByMalade(true,PageRequest.of(0,4));

       byMalade.forEach(p-> {
           System.out.println(p.getId());
           System.out.println(p.getNom());
           System.out.println(p.getDateNaissance());
           System.out.println(p.isMalade());
           System.out.println(p.getScore());
       });
        System.out.println("*****************");
        Patient patient = patientRepository.findById(1L).orElse(null);
        if(patient != null){
            System.out.println(patient.getNom());
            System.out.println(patient.isMalade());
        }
        patient.setScore(789);
        patientRepository.save(patient); // si id==null save fait l'insert si on donne a save un objet il fait l'update

        patientRepository.deleteById(1L); // on ajoute le L pck id est de type Long;



    }*/
}
