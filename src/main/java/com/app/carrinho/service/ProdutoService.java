/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.carrinho.service;

import java.util.List;
import com.app.carrinho.model.Produto;
import com.app.carrinho.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author lucas
 */
@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public List<Produto> listarTodosProdutos() {
        return produtoRepository.findAll();
    }

    public void save(Produto produto) {
        produtoRepository.save(produto);
    }

    public Produto findOne(Integer id) {
        return produtoRepository.findOne(id);
    }
    
    public Boolean verificarProdutoJaCadastrado(Produto produto) {
        
        Produto p = produtoRepository.carregarProdutoByNome(produto.getNome());
        if (p != null && p.getId()!= null) {
            return true;
        } else {
            return false;
        }
    }
    
    public void delete(Produto produto){
        produtoRepository.delete(produto);
    }
    
    public Boolean verificarProdutoNull(Produto produto) {
        
        if (produto == null || produto.getId()== null) {
            return true;
        } else {
            return false;
        }
    }
    
    public void alterarProduto(Produto produto){
        produtoRepository.saveAndFlush(produto);
    }
}
