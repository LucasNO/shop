/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.carrinho.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 *
 * @author lucas
 */
@Data
@Entity
@Table(name = "carrinho")
public class Carrinho implements Serializable {

    private static final long serialVersionUID = -8031916280982384478L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    @NotNull
    @ManyToOne
    @JoinColumn(name = "id_produto", referencedColumnName = "id")
    private Produto produto;

    @NotNull
    @Column(name = "quantidade")
    private int quantidade;

    @NotNull
    @Column(name = "comprador")
    private String comprador;

    public Carrinho(Produto produto, int quantidade, String comprador) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.comprador = comprador;
    }

    public Carrinho() {
    }
}
