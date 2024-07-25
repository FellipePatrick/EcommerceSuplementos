package cog.com.EcommercApplication.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class EcommerceController {

    @GetMapping({"/"})
    public String index(){
        return "index";
    }
    
    @GetMapping({"/cadastroSuplemento"})
    public String doCadastroSuplemento(){
        return "cadastroSuplementos";
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
    
    
}
