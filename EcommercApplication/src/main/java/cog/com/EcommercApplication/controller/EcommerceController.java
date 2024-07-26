package cog.com.EcommercApplication.controller;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import cog.com.EcommercApplication.domain.Suplementos;
import cog.com.EcommercApplication.domain.Usuario;
import cog.com.EcommercApplication.service.FileStorageService;
import cog.com.EcommercApplication.service.SuplementosService;
import org.springframework.web.servlet.ModelAndView;
import cog.com.EcommercApplication.service.UsuarioService;
@Controller
public class EcommerceController {

    SuplementosService suplementosService;
    UsuarioService UsuarioService;
    private FileStorageService fileStorageService;

    public EcommerceController(SuplementosService suplementosService, UsuarioService usuarioService, FileStorageService fileStorageService){
        this.suplementosService = suplementosService;
        this.UsuarioService = usuarioService;
        this.fileStorageService = fileStorageService; 
    }

    @GetMapping("/index")//rota de teste
    public String teste(Model model){
        model.addAttribute("suplementos", suplementosService.listarSuplementos());  
        return "homeUser";
    }

    @GetMapping({"/"})
    public String index(Model model){
        model.addAttribute("suplementos", suplementosService.listarSuplementos());
        return "index";
    }

    @GetMapping({"/editarSuplemento"})
    public String doEditarSuplemento(){
        return "editarSuplementos";
    }

    @GetMapping("/deletar/{id}")
    public String doDeletar(@PathVariable Long id){
        suplementosService.softDelete(id);
        return "redirect:/";
    }

    @GetMapping({"/cadastroSuplementos"})
    public String CadastroSuplemento(Model model){
        Suplementos suplemento = new Suplementos();
        model.addAttribute("suplemento", suplemento);
        return "cadastroSuplementos";
    }

        @PostMapping("/docadastrarSuplemento")
    public ModelAndView docadastrarSuplemento(@ModelAttribute Suplementos s, Errors errors, @RequestParam("file") MultipartFile file) {
        if (errors.hasErrors()) {
            return new ModelAndView("cadastroPage");
        }
    
       String uniqueFileName = UUID.randomUUID().toString() + getFileExtension(file.getOriginalFilename());
        
        s.setImageUri(uniqueFileName);
      
        fileStorageService.save(file, uniqueFileName);
    
         suplementosService.cadastrarSuplemento(s);
    
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("msg", "Cadastro realizado com sucesso");
        modelAndView.addObject("suplementos", suplementosService.listarSuplementos());
        return modelAndView;
    }
    
    private String getFileExtension(String fileName) {
        if (fileName != null && fileName.contains(".")) {
            return fileName.substring(fileName.lastIndexOf('.'));
        } else {
            return "";
        }
    }
    
    
    @GetMapping("“/adicionarCarrinho/{id}")
    public String doAdicionarCarrinho(@PathVariable Long id){
        return "redirect:/";
    }
    
    @GetMapping("/produto/{id}")
    public ModelAndView produto(@PathVariable Long id, Model model){
         Optional<Suplementos> suplemento = suplementosService.buscarSuplemento(id);
         if (suplemento.isPresent()){
             model.addAttribute("suplementos", suplementosService.listarSuplementos());
             ModelAndView mv = new ModelAndView("produto");
             mv.addObject("suplemento", suplemento.get());
             return mv;
         }else{
             return new ModelAndView("redirect:/");
         }
     }

    @GetMapping({"/cadastro"})
    public String Cadastro(Model model){
        Usuario usuario = new Usuario();
        model.addAttribute("usuario", usuario);
        return "cadastroUsuario";
    }

    @PostMapping("/docadastro")
    public String docadastro(@ModelAttribute Usuario usuario){
        UsuarioService.cadastreUser(usuario);
        return "redirect:/";
    }

    @GetMapping({"/login"})
    public String doLogin(){
        return "login";
    }

    @GetMapping({"/carrinho"})
    public String doCarrinho(){
        return "carrinho";
    }
}
