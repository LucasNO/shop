/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.carrinho.controller;

import com.app.carrinho.model.Produto;
import com.app.carrinho.service.ProdutoService;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

/**
 *
 * @author lucas
 */
@CrossOrigin
@RestController
@RequestMapping("/shop")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    /**
     * Método que retorna uma lista de produtos em JSON
     *
     * @return ResponseEntity&lt;List&lt;Produto&gt;&gt;
     */
    @GetMapping("/produtos")
    public ResponseEntity<List<Produto>> getProdutos() {

        List<Produto> produtos = produtoService.listarTodosProdutos();

        if (produtos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(produtos);
    }

    /**
     *
     * @param id Integer
     * @return
     */
    @GetMapping("/produto/{id}")
    public ResponseEntity<Produto> getProdutoById(@PathVariable("id") Integer id) {

        Produto produto = produtoService.findOne(id);

        if (produtoService.verificarProdutoNull(produto)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(produto);
    }

    @Transactional
    @PostMapping(path="/produto/")
    public ResponseEntity<Void> addProduto(@RequestBody Produto produto, UriComponentsBuilder ucBuilder) {
        if (produtoService.verificarProdutoJaCadastrado(produto)) {
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(ucBuilder.path("/shop/produto/{id}").buildAndExpand(produto.getId()).toUri());
            return ResponseEntity.status(HttpStatus.CONFLICT).headers(headers).build();
        } else {
            produtoService.save(produto);
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(ucBuilder.path("/shop/produto/{id}").buildAndExpand(produto.getId()).toUri());
            return ResponseEntity.status(HttpStatus.CREATED).headers(headers).build();
        }
    }

    @Transactional
    @DeleteMapping(path="/produto/{id}")
    public ResponseEntity<Produto> deleteProduto(@PathVariable("id") Integer id) {

        Produto produto = produtoService.findOne(id);

        if (produtoService.verificarProdutoNull(produto)) {
            return ResponseEntity.noContent().build();
        }
        produtoService.delete(produto);
        return ResponseEntity.ok(produto);
    }

    @Transactional
    @PutMapping(path="/produto/")
    public ResponseEntity<Void> updateProduto(@RequestBody Produto produto, UriComponentsBuilder ucBuilder) {
        if (produtoService.verificarProdutoNull(produto)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            produtoService.alterarProduto(produto);
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(ucBuilder.path("/shop/produto/{id}").buildAndExpand(produto.getId()).toUri());
            return ResponseEntity.status(HttpStatus.OK).headers(headers).build();
        }
    }
}
