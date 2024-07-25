package cog.com.EcommercApplication.service;


import java.util.List;

import org.springframework.stereotype.Service;

import cog.com.EcommercApplication.domain.Suplementos;
import cog.com.EcommercApplication.repository.SuplementosRepository;
@Service
public class SuplementosService {
    
    SuplementosRepository suplementosRepository;

    public SuplementosService(SuplementosRepository suplementosRepository){
        this.suplementosRepository = suplementosRepository;
    }

    public void cadastrarSuplemento(Suplementos suplemento){
        suplementosRepository.save(suplemento);
    }

    public Suplementos buscarSuplemento(String id){
        return suplementosRepository.findById(id).get();
    }

    public void softDelete(String id){
        Suplementos suplementos = buscarSuplemento(id);
        //suplementos.se
    }

    public List<Suplementos> listarSuplementos(){
       return suplementosRepository.findAll();
     }
}
