/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.app.carrinho.dto;

import com.app.carrinho.model.Carrinho;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

/**
 *
 * @author lucas
 */
@Data
public class CarrinhoDto {
    
    private List<Carrinho> carrinho;
    
    private BigDecimal valorTotal;
}
