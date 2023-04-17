package ma.emsi.jpatp.WEB;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import ma.emsi.jpatp.DAO.Entities.Patient;
import ma.emsi.jpatp.DAO.repositories.PatientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@AllArgsConstructor
public class PatientController {
    private PatientRepository patientRepository;

   @GetMapping("/user/index")
    public String index(Model model,
                        @RequestParam(name="page",defaultValue="0") int page,
                        @RequestParam(name="size",defaultValue = "5") int size,
                        @RequestParam(name="keyword",defaultValue = "") String keyword){
        Page<Patient> pagePatients= patientRepository.findByNomContains(keyword,PageRequest.of(page,size));
        model.addAttribute("ListPatients",pagePatients.getContent());
        model.addAttribute("pages",new int[pagePatients.getTotalPages()]);
        model.addAttribute("currentPage",page);
        model.addAttribute("keyword",keyword);
        return "patients";
    }

    @GetMapping("/admin/deletePatient")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String deletePatient( @RequestParam(name="id") Long id,
                                 String keyword,int page){

       patientRepository.deleteById(id);
       return "redirect:/user/index?page="+page+"&keyword="+keyword;
    }

    @GetMapping("/")
    public String home(){

       return "redirect:/user/index";
    }

    @GetMapping("/admin/patients")
    @ResponseBody //le resultat sera affiché en mode json dans la page web
    public List<Patient> listPatients(){
       return patientRepository.findAll();
    }

    @GetMapping("/admin/formPatients")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String formPatient(Model model){
       model.addAttribute("patient",new Patient());
        return "formPatients";
    }

    @PostMapping("/admin/savePatient")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String savePatient(@Valid Patient patient, BindingResult bindingResult)
                       {
       if(bindingResult.hasErrors()) return "formPatients";
       patientRepository.save(patient);
       return "formPatients";
    }

    @GetMapping("/admin/editPatient")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String editPatient(@RequestParam(name="id") Long id,Model model){
       Patient patient=patientRepository.findById(id).orElse(null);
       if(patient==null) throw new RuntimeException("Patient Introuvable");

       model.addAttribute("patient",patient);

       return "editPatient";
    }


}