/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.SQLException;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import model.Produto;
import persistence.ProdutoDAO;

@Path("/api")
public class ProdutoAPI {
    ProdutoDAO produtoDAO;
    
    public ProdutoAPI() throws SQLException {
        produtoDAO = new ProdutoDAO();
    }  

    @GET
    @Path("/produto")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Produto> listarProdutos() {
        List<Produto> produtos = produtoDAO.getAll();
        return produtos;
    } 
    
    @GET
    @Path("/produto/{idproduto}")
    @Produces(MediaType.APPLICATION_JSON)
    public Produto buscarProduto(@DefaultValue("0") @PathParam("idproduto") int idproduto) {
        Produto produto = produtoDAO.getById(idproduto);
        return produto;
    }
    
    @POST
    @Path("/produto")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Produto inserirProduto(Produto produto) {
        produto = produtoDAO.insert(produto);
        return produto;
    }
    
    @PUT
    @Path("/produto/{idproduto}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Produto alterarProduto(Produto produto) {
        produto = produtoDAO.update(produto);
        return produto;
    }
    
    @DELETE
    @Path("/produto/{idproduto}")
    @Produces(MediaType.APPLICATION_JSON)
    public Boolean excluirProduto(@DefaultValue("0") @PathParam("idproduto") int idproduto) {
        return produtoDAO.delete(idproduto);
    }
}
