/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import helpers.Hash;
import java.security.NoSuchAlgorithmException;
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
import javax.ws.rs.core.Response;
import model.Fornecedor;
import persistence.FornecedorDAO;

@Path("/api")
public class FornecedorAPI {
    FornecedorDAO fornecedorDAO;
    
    public FornecedorAPI() throws SQLException {
        fornecedorDAO = new FornecedorDAO();
    }  

    @GET
    @Path("/fornecedor")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Fornecedor> listarFornecedors() {
        List<Fornecedor> fornecedors = fornecedorDAO.getAll();
        return fornecedors;
    } 
    
    @GET
    @Path("/fornecedor/{idfornecedor}")
    @Produces(MediaType.APPLICATION_JSON)
    public Fornecedor buscarFornecedor(@DefaultValue("0") @PathParam("idfornecedor") int idfornecedor) {
        Fornecedor fornecedor = fornecedorDAO.getById(idfornecedor);
        return fornecedor;
    }
    
    @POST
    @Path("/fornecedor")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Fornecedor inserirFornecedor(Fornecedor fornecedor) {
        fornecedor = fornecedorDAO.insert(fornecedor);
        return fornecedor;
    }
    
    @PUT
    @Path("/fornecedor/{idfornecedor}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Fornecedor alterarFornecedor(Fornecedor fornecedor) {
        fornecedor = fornecedorDAO.update(fornecedor);
        return fornecedor;
    }
    
    @DELETE
    @Path("/fornecedor/{idfornecedor}")
    @Produces(MediaType.APPLICATION_JSON)
    public Boolean excluirFornecedor(@DefaultValue("0") @PathParam("idfornecedor") int idfornecedor) {
        return fornecedorDAO.delete(idfornecedor);
    }
    
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(Fornecedor fornecedor) throws NoSuchAlgorithmException {
        fornecedor = fornecedorDAO.login(fornecedor);
        if (!fornecedor.getToken().equals("")) {
            return Response.ok(fornecedor).build();
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }
    
    private boolean checarToken(String token) {

        String dadosLogin = "";
        try {
            dadosLogin = Hash.base64decode(token);
            String dadosSplit[] = dadosLogin.split("&");
            String login = dadosSplit[0].replace("login=", "");
            String senha = dadosSplit[1].replace("senha=", "");

            Fornecedor fornecedor = new Fornecedor();
            fornecedor.setLogin(login);
            fornecedor.setSenha(senha);

            return fornecedorDAO.checarLogin(fornecedor);
        } catch (Exception ex) {
            return false;
        }
    }
}
