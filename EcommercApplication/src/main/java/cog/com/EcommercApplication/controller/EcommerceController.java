package cog.com.EcommercApplication.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import cog.com.EcommercApplication.domain.Suplementos;
import cog.com.EcommercApplication.service.SuplementosService;


@Controller
public class EcommerceController {

    SuplementosService suplementosService;

    public EcommerceController(SuplementosService suplementosService){
        this.suplementosService = suplementosService;
    }


    @GetMapping({"/"})
    public String index(Model model){
        model.addAttribute("suplementos", suplementosService.listarSuplementos());
        return "index";
    }
    
    @GetMapping({"/cadastroSuplementos"})
    public String CadastroSuplemento(Model model){
        Suplementos suplemento = new Suplementos();
        model.addAttribute("suplemento", suplemento);
        return "cadastroSuplementos";
    }

    @PostMapping({"/docadastrarSuplemento"})
    public String docadastrarSuplemento(@ModelAttribute Suplementos suplemento){
        suplementosService.cadastrarSuplemento(suplemento);
        return "redirect:/";
    }

    @GetMapping({"/editarSuplemento"})
    public String doEditarSuplemento(){
        return "editarSuplementos";
    }

    @GetMapping({"/cadastro"})
    public String doCadastro(){
        return "cadastroUsuario";
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
