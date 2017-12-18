# Shop

## Carrinho de compra via Rest

Esse projeto simula um carrinho de compras via Rest, onde é possivel inserir, atualizar e deletar produto e o carrinho de compras.

Para testar é recomendado usar o plugin RESTED para firefox ou chrome

## Endpoints

### Inserindo Produto

URL: https://shop-rest.herokuapp.com/shop/produto/

Method: Post

Headers
Content-Type:application/json

Request body 
Type: Json

Adicionar os parametros 

* nome
* preco

### Listar Produtos Cadastrados 

URL: https://shop-rest.herokuapp.com/shop/produtos/

Method: Get

### Atualizando Produto

URL: https://shop-rest.herokuapp.com/shop/produto/{id}

Method: Put

Headers
Content-Type:application/json

Request body 
Type: Json

Adicionar os parametros 

* id
* nome
* preco

### Deletando Produto

URL: https://shop-rest.herokuapp.com/shop/produto/{id}

Method: Delete

Request body 
Type: Json

### Inserindo Produto No Carrinho

URL: https://shop-rest.herokuapp.com/shop/carrinho/{nomeComprador}/{idPrduto}/{quantidade}

Method: Post

Headers
Content-Type:application/json

Request body 
Type: Json

### Listar Carrinho de Compras de um comprador 

URL: https://shop-rest.herokuapp.com/shop/listCarrinho/{nomeComprador}

Method: Get

### Atualizando Produto no Carrinho

URL: https://shop-rest.herokuapp.com/shop/carrinho/{nomeComprador}/{idPrduto}/{quantidade}

Method: Put

Headers
Content-Type:application/json

Request body 
Type: Json


### Deletando Produto do Carrinho

URL: https://shop-rest.herokuapp.com/shop/carrinho/{id}

Method: Delete
