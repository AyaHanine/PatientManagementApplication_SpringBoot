package ma.emsi.jpatp.DAO.repositories;

import ma.emsi.jpatp.DAO.Entities.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

//une methode dans spring data qui a find = select et by =where
//on doit faire attention aux noms des methodes qu'on donne
public interface PatientRepository extends JpaRepository<Patient, Long> {

    Page<Patient> findByNomContains(String kw, Pageable pageable);
    /* JPA TP1 :
    public Page<Patient> findByMalade(boolean m, Pageable pageable);//pour retourner une page il faut toujours ajouter le parametre pageable


    List<Patient> findByMaladeAndScoreLessThan(boolean m,int score); //retourne les malades avec le score inferieur a la valeur donnée en parametre


    List<Patient> findByMaladeIsTrueAndScoreLessThan(int score);
    List<Patient> findByDateNaissanceBetweenAndMaladeIsTrueOrNomLike(Date d1, Date d2, String nom);

    @Query("select p from Patient p where p.dateNaissance between :x and :y or p.nom like :z")
    List<Patient> chercherPatients(@Param("x") Date d1, @Param("y") Date d2, @Param("z") String nom);//Spring data ne reconnaitra pas la methode si on ajoute pas @Query avec la requete sql en (""); @Param pour specifier quel parametre equivalent a celui dans la requete sql;

    List<Patient> findByMalade(boolean m); */

}
