package cog.com.EcommercApplication.controller;
import java.util.HashMap;
import java.util.Map;
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

import cog.com.EcommercApplication.domain.CarrinhoItem;
import cog.com.EcommercApplication.domain.Suplementos;
import cog.com.EcommercApplication.domain.Usuario;
import cog.com.EcommercApplication.service.FileStorageService;
import cog.com.EcommercApplication.service.SuplementosService;
import org.springframework.web.servlet.ModelAndView;
import cog.com.EcommercApplication.service.UsuarioService;
import jakarta.servlet.http.HttpSession;

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
   
    
    @GetMapping({"/"})
    public String index(Model model, HttpSession session){
        @SuppressWarnings("unchecked")
        Map<Long, CarrinhoItem> carrinho = (Map<Long, CarrinhoItem>) session.getAttribute("carrinho");

        if(carrinho != null){
            double subtotal = carrinho.values().stream()
                        .mapToDouble(item -> item.getSuplemento().getPreco() * item.getQuantidade())
                        .sum();  
                        int totalItens = carrinho.values().stream()
                            .mapToInt(CarrinhoItem::getQuantidade)
                            .sum();
            model.addAttribute("carrinho", carrinho);
            model.addAttribute("totalItens", totalItens);
            model.addAttribute("subtotal", subtotal);
        }

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
            ModelAndView modelAndView = new ModelAndView("index");
            modelAndView.addObject("msg", "Erro ao cadastrar suplementos");
            return modelAndView;
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
    
    @GetMapping("/produto/{id}")
    public ModelAndView produto(@PathVariable Long id, Model model, HttpSession session){
         Optional<Suplementos> suplemento = suplementosService.buscarSuplemento(id);
         if (suplemento.isPresent()){
             @SuppressWarnings("unchecked")
            Map<Long, CarrinhoItem> carrinho = (Map<Long, CarrinhoItem>) session.getAttribute("carrinho");
            if(carrinho != null){
                double subtotal = carrinho.values().stream()
                            .mapToDouble(item -> item.getSuplemento().getPreco() * item.getQuantidade())
                            .sum();  
                int totalItens = carrinho.values().stream()
                                .mapToInt(CarrinhoItem::getQuantidade)
                                .sum();
                model.addAttribute("carrinho", carrinho);
                model.addAttribute("totalItens", totalItens);
                model.addAttribute("subtotal", subtotal);
            }
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

    
    // Carrinho Controladores

    @GetMapping("/carrinho")
    public ModelAndView doCarrinho(HttpSession session) {
        ModelAndView modelAndView = new ModelAndView();
    
        @SuppressWarnings("unchecked")
        Map<Long, CarrinhoItem> carrinho = (Map<Long, CarrinhoItem>) session.getAttribute("carrinho");
    
        if (carrinho == null || carrinho.isEmpty()) {
            modelAndView.addObject("msg", "Seu carrinho está vazio.");
            modelAndView.setViewName("redirect:/");
            return modelAndView;
        }
    
        double subtotal = carrinho.values().stream()
                                  .mapToDouble(item -> item.getSuplemento().getPreco() * item.getQuantidade())
                                  .sum();
    
        int totalItens = carrinho.values().stream()
                                 .mapToInt(CarrinhoItem::getQuantidade)
                                 .sum();
    
        modelAndView.addObject("carrinho", carrinho);
        modelAndView.addObject("subtotal", subtotal);
        modelAndView.addObject("totalItens", totalItens);
        modelAndView.setViewName("carrinho");
        return modelAndView;
    }
    
    
    @GetMapping("/finalizarCompra")
    public String finalizarCompraGet(HttpSession session) {
        session.invalidate();
        return "redirect:/"; 
    }


    @PostMapping("/finalizarCompra")
    public ModelAndView finalizarCompra(HttpSession session) {
        @SuppressWarnings("unchecked")
        Map<Long, CarrinhoItem> carrinho = (Map<Long, CarrinhoItem>) session.getAttribute("carrinho");
        
        ModelAndView modelAndView = new ModelAndView();

        if (carrinho == null || carrinho.isEmpty()) {
            modelAndView.setViewName("redirect:/");
            modelAndView.addObject("msg", "Seu carrinho está vazio.");
            return modelAndView;
        }

        session.removeAttribute("carrinho");
        modelAndView.addObject("msg", "Compra finalizada com sucesso!");
        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }


    @PostMapping("/adicionarAoCarrinho/{id}")
    public ModelAndView adicionarAoCarrinho(@PathVariable Long id, HttpSession session) {
        Optional<Suplementos> suplementoOptional = suplementosService.buscarSuplemento(id);
        
        if (!suplementoOptional.isPresent()) {
            return new ModelAndView("redirect:/error").addObject("message", "Item não encontrado");
        }

        Suplementos suplemento = suplementoOptional.get();

        @SuppressWarnings("unchecked")
        Map<Long, CarrinhoItem> carrinho = (Map<Long, CarrinhoItem>) session.getAttribute("carrinho");
        if (carrinho == null) {
            carrinho = new HashMap<>();
            session.setAttribute("carrinho", carrinho);
        }
        CarrinhoItem item = carrinho.get(suplemento.getId());
        if (item != null) {
            item.setQuantidade(item.getQuantidade() + 1);
        } else {
            item = new CarrinhoItem(suplemento, 1);
            carrinho.put(suplemento.getId(), item);
        }
        session.setAttribute("carrinho", carrinho);
        ModelAndView modelAndView = new ModelAndView("redirect:/");
        modelAndView.addObject("msg", "Atualize a página para que seja exibido a quantidade de itens no carrinho");
        return modelAndView;
    }

    @PostMapping("/removerDoCarrinho/{id}")
    public ModelAndView removerDoCarrinho(@PathVariable Long id, HttpSession session) {
        @SuppressWarnings("unchecked")
        Map<Long, CarrinhoItem> carrinho = (Map<Long, CarrinhoItem>) session.getAttribute("carrinho");
        if (carrinho != null) {
            carrinho.remove(id);
            session.setAttribute("carrinho", carrinho);
        }
        ModelAndView modelAndView = new ModelAndView("redirect:/carrinho");
        modelAndView.addObject("msg", "Item removido do carrinho com sucesso");
        return modelAndView;
    }


}
