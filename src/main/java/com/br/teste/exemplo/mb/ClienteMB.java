/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.teste.exemplo.mb;

import com.br.teste.exemplo.datamodel.Cliente;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author vitcl
 */
@ManagedBean
@ViewScoped
public class ClienteMB implements Serializable{

    private Cliente cliente;
    private List<Cliente> clientes;
    private EntityManager entityManager;
    private boolean showCampoCPF;
    private boolean showCampoCNPJ;
    private String typePerson;

    public String getTypePerson() {
        return typePerson;
    }

    public void setTypePerson(String typePerson) {
        this.typePerson = typePerson;
    }

    @PostConstruct
    public void inicializar() {
        this.cliente = new Cliente();
        this.clientes = new ArrayList<>();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("exemplo-pu");
        this.entityManager = emf.createEntityManager();
        listarClientes();
  
    }
    
    public void typePersonChange(){
        if ("PF".equals(this.typePerson)) {
            setShowCampoCNPJ(false);
            setShowCampoCPF(true);
            System.out.println("entrou aqui.... PF");

        }else if("PJ".equals(this.typePerson)){
            setShowCampoCPF(false);
            setShowCampoCNPJ(true);
            System.out.println("entrou aqui PJ....");
        }
    }

    public void listarClientes() {

        this.clientes = this.entityManager.createQuery("SELECT c From Cliente c").getResultList();
    }

    public void salvar() {

        this.entityManager.getTransaction().begin();
        if (typePerson.equals("PF")) {
            cliente.setCnpj(null);
        }
        if (typePerson.equals("PJ")) {
            cliente.setCpf(null);
        }
        this.entityManager.persist(this.cliente);
        this.entityManager.getTransaction().commit();
        listarClientes();
        this.cliente = new Cliente();
    }
    
    public void remover(Cliente cliente){
        entityManager.getTransaction().begin();
        entityManager.remove(cliente);
        entityManager.getTransaction().commit();
        listarClientes();
    }
    
    public void editar(Cliente cliente){
        this.cliente = cliente;
        if (this.cliente.getCpf() != null) {
            this.showCampoCPF = true;
        }
        if (this.cliente.getCnpj() != null) {
            this.showCampoCNPJ = true;
        }
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    public boolean getShowCampoCPF() {
        return showCampoCPF;
    }

    public void setShowCampoCPF(boolean showCampoCPF) {
        this.showCampoCPF = showCampoCPF;
    }

    public boolean getShowCampoCNPJ() {
        return showCampoCNPJ;
    }

    public void setShowCampoCNPJ(boolean showCampoCNPJ) {
        this.showCampoCNPJ = showCampoCNPJ;
    }


}
