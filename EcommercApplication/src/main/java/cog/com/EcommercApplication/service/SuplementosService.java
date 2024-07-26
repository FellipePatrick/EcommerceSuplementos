package cog.com.EcommercApplication.service;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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

    public Optional<Suplementos>  buscarSuplemento(Long id){
        return suplementosRepository.findById(id);
    }

    public void softDelete(Long id){
        Suplementos suplementos = suplementosRepository.findById(id).get();
        suplementos.setIsDeleted(LocalDate.now());
        suplementosRepository.save(suplementos);
    }

    public List<Suplementos> listarSuplementos(){
       return suplementosRepository.findAllWhereIsDeletedIsNotNull();
     }
}
