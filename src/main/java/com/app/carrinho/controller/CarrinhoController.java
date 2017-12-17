/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.carrinho.controller;

import com.app.carrinho.dto.CarrinhoDto;
import com.app.carrinho.model.Carrinho;
import com.app.carrinho.model.Produto;
import com.app.carrinho.service.CarrinhoService;
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
public class CarrinhoController {

    @Autowired
    private CarrinhoService carrinhoService;

    @Autowired
    private ProdutoService produtoService;

    @GetMapping("/listCarrinho/{comprador}")
    public ResponseEntity<CarrinhoDto> getCarrinhoByComprador(@PathVariable("comprador") String comprador) {

        List<Carrinho> carrinhos = carrinhoService.listarCarrinhoByComprador(comprador);
        
        CarrinhoDto carrinhoDto = carrinhoService.calcularValorTotal(carrinhos);

        if (carrinhos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(carrinhoDto);
    }

    @Transactional
    @PostMapping("/carrinho/{comprador}/{idProduto}/{quantidade}")
    public ResponseEntity<Void> addProdutoCarrinho(@PathVariable("comprador") String comprador, @PathVariable("idProduto") Integer idProduto, 
            @PathVariable("quantidade") Integer quantidade, UriComponentsBuilder ucBuilder) {
        if (carrinhoService.verificarProdutoNoCarrinho(comprador, idProduto)) {
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(ucBuilder.path("/shop/listCarrinho/{comprador}").buildAndExpand(comprador).toUri());
            return ResponseEntity.status(HttpStatus.CONFLICT).headers(headers).build();
        } else {
            Produto produto = produtoService.findOne(idProduto);
            
            Carrinho carrinho = new Carrinho(produto, quantidade, comprador);
            
            carrinhoService.save(carrinho);
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(ucBuilder.path("/shop/listCarrinho/{comprador}").buildAndExpand(comprador).toUri());
            return ResponseEntity.status(HttpStatus.CREATED).headers(headers).build();
        }
    }

    @Transactional
    @DeleteMapping("/carrinho/{id}")
    public ResponseEntity<Carrinho> deleteProduto(@PathVariable("id") Integer id) {

        Carrinho carrinho = carrinhoService.findOne(id);

        if (carrinho == null || carrinho.getId() == null) {
            return ResponseEntity.noContent().build();
        }
        carrinhoService.delete(carrinho);
        return ResponseEntity.ok(carrinho);
    }

    @Transactional
    @PutMapping("/carrinho/{comprador}/{idProduto}/{quantidade}")
    public ResponseEntity<Void> updateCarrinho(@PathVariable("comprador") String comprador, @PathVariable("idProduto") Integer idProduto, 
            @PathVariable("quantidade") Integer quantidade, UriComponentsBuilder ucBuilder) {
        Carrinho carrinho = carrinhoService.findCarrinhoByCompradorByProduto(comprador, idProduto);
        if (carrinho == null || carrinho.getId() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            carrinho.setQuantidade(quantidade);
            carrinhoService.alterarCarrinho(carrinho);
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(ucBuilder.path("/shop/listCarrinho/{comprador}").buildAndExpand(carrinho.getComprador()).toUri());
            return ResponseEntity.status(HttpStatus.CREATED).headers(headers).build();
        }
    }
}
