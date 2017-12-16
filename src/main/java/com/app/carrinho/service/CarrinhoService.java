/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.carrinho.service;

import com.app.carrinho.dto.CarrinhoDto;
import com.app.carrinho.model.Carrinho;
import com.app.carrinho.repository.CarrinhoRepository;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author lucas
 */
@Service
public class CarrinhoService {

    @Autowired
    private CarrinhoRepository carrinhoRepository;

    public List<Carrinho> findAll() {
        return carrinhoRepository.findAll();
    }

    public void save(Carrinho carrinho) {
        carrinhoRepository.save(carrinho);
    }

    public Carrinho findOne(Integer id) {
        return carrinhoRepository.findOne(id);
    }

    public void delete(Carrinho carrinho) {
        carrinhoRepository.delete(carrinho);
    }

    public void alterarCarrinho(Carrinho carrinho) {
        carrinhoRepository.saveAndFlush(carrinho);
    }

    public List<Carrinho> listarCarrinhoByComprador(String comprador) {
        return carrinhoRepository.carregarProdutoByNome(comprador);
    }

    public Carrinho findCarrinhoByCompradorByProduto(String comprador, Integer idProduto) {
        return carrinhoRepository.findCarrinhoByCompradorByProduto(comprador, idProduto);
    }

    public boolean verificarProdutoNoCarrinho(String comprador, Integer idProduto) {
        Carrinho carrinho = carrinhoRepository.findCarrinhoByCompradorByProduto(comprador, idProduto);
        if (carrinho != null && carrinho.getId() != null) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }
    
    public CarrinhoDto calcularValorTotal(List<Carrinho> carrinho){
        CarrinhoDto carrinhoDto = new CarrinhoDto();
        BigDecimal valorTotal = new BigDecimal(0);
        for(Carrinho c : carrinho){
            BigDecimal quantidade = new BigDecimal(c.getQuantidade());
            valorTotal = valorTotal.add(c.getProduto().getPreco().multiply(quantidade));
        }
        carrinhoDto.setCarrinho(carrinho);
        carrinhoDto.setValorTotal(valorTotal);
        return carrinhoDto;
    }
}
