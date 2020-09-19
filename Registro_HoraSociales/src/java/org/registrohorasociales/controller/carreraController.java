/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.registrohorasociales.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import org.registrohorasociales.config.ApplicationContextProvider;
import org.registrohorasociales.entity.Carrera;
import org.registrohorasociales.repository.CarreraRepository;

/**
 *
 * @author Miguel
 */
@SessionScoped
@ManagedBean
@ViewScoped
public class carreraController implements Serializable {

    private String formName,formCode;
    private CarreraRepository carreraRepo;
    private List<Carrera> carreras;
    private List<SelectItem> listaCarreras;
    private Carrera carreraSelector;
    
    
@PostConstruct
    public void init() {
        carreraRepo=ApplicationContextProvider.getApplicationContext().getBean(CarreraRepository.class);
        loadCarreras();
    }

    public String crearCarrera() {

        try {
            carreraRepo = ApplicationContextProvider.getApplicationContext().getBean(CarreraRepository.class);
            Carrera car = new Carrera();
            car.setCodigocarrera(formCode);
            car.setNombrecarrera(formName);
            carreraRepo.save(car);
            
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "Carrera creada con éxito",""));
        } catch (Exception e) {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se pudo almacenar la carrera", ""));
        }

        return null;
    }

    public void loadCarreras(){
        carreras = new ArrayList<Carrera>();
        this.listaCarreras= new ArrayList<SelectItem>();
        carreras = carreraRepo.carrerasList();
        listaCarreras.clear();
        for(Carrera car:carreras){
            SelectItem c = new SelectItem(car.getIdcarrera(),    car.getNombrecarrera());
            this.listaCarreras.add(c);
        }
    }
    
    public String findCarreraById(int id){
        carreraRepo = ApplicationContextProvider.getApplicationContext().getBean(CarreraRepository.class);
        Carrera ca = new Carrera();
        ca =  carreraRepo.getCarreraById(id);
        return ca.getNombrecarrera();
    }

    public String getFormName() {
        return formName;
    }
    
    public void deleteCarreraById(int id){
        try{
            carreraRepo = ApplicationContextProvider.getApplicationContext().getBean(CarreraRepository.class);
            carreraRepo.delete(id);
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    public void setFormName(String formName) {
        this.formName = formName;
    }

    public String getFormCode() {
        return formCode;
    }

    public void setFormCode(String formCode) {
        this.formCode = formCode;
    }

    public List<Carrera> getCarreras() {
        return carreras;
    }

    public void setCarreras(List<Carrera> carreras) {
        this.carreras = carreras;
    }

    public Carrera getCarreraSelector() {
        return carreraSelector;
    }

    public void setCarreraSelector(Carrera carreraSelector) {
        this.carreraSelector = carreraSelector;
    }

    public List<SelectItem> getListaCarreras() {
        return listaCarreras;
    }

    public void setListaCarreras(List<SelectItem> listaCarreras) {
        this.listaCarreras = listaCarreras;
    }


}
