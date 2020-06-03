/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.teste.exemplo.mb;

import com.br.teste.exemplo.datamodel.Cliente;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;


/**
 *
 * @author vitcl
 */
@ManagedBean
@ViewScoped
public class ClienteMB {

    private Cliente cliente;
    private List<Cliente> clientes;
    
    @PostConstruct
    public void inicializar(){
        this.cliente = new Cliente();
        this.clientes = new ArrayList<>();
    }
    
    public void salvar(Cliente cliente){
        System.out.println("Cliente salvo!");
        this.clientes.add(cliente);
        this.cliente = new Cliente();
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
 
}
