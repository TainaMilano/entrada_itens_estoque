package controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.imageio.ImageIO;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import model.Entrada;
import persistence.EntradaDAO;

@Path("/api")
public class EntradaAPI {
    EntradaDAO entradaDAO;
    protected String projectPath = "C:\\Users\\Milano\\Documents\\NetBeansProjects\\ControleEstoque\\src\\main\\webapp";
    
    public EntradaAPI() throws SQLException {
        entradaDAO = new EntradaDAO();
    }  

    @GET
    @Path("/entrada")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Entrada> listarEntradas() {
        List<Entrada> entradas = entradaDAO.getAll();
        return entradas;
    } 
    
    @GET
    @Path("/entrada/{identrada}")
    @Produces(MediaType.APPLICATION_JSON)
    public Entrada buscarEntrada(@DefaultValue("0") @PathParam("identrada") int identrada) {
        Entrada entrada = entradaDAO.getById(identrada);
        return entrada;
    }
    
    @POST
    @Path("/entrada")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Entrada inserirEntrada(Entrada entrada) {
        entrada = entradaDAO.insert(entrada);
        return entrada;
    }
    
    @PUT
    @Path("/entrada/{identrada}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Entrada alterarEntrada(Entrada entrada) {
        entrada = entradaDAO.update(entrada);
        return entrada;
    }
    
    @DELETE
    @Path("/entrada/{identrada}")
    @Produces(MediaType.APPLICATION_JSON)
    public Boolean excluirEntrada(@DefaultValue("0") @PathParam("identrada") int identrada) {
        return entradaDAO.delete(identrada);
    }
    
    @POST
    @Path("/nfe")
    @Produces(MediaType.APPLICATION_JSON)
    public boolean storeNFe(@FormParam("nfe") String nfe) {
        try {
            String base64Image = nfe.split(",")[1];
            byte[] imageBytes = javax.xml.bind.DatatypeConverter.parseBase64Binary(base64Image);
            BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imageBytes));
            String imageNewName = System.currentTimeMillis() + ".jpg";
            File imageFile = new File(this.projectPath + "uploads/" + imageNewName);
            ImageIO.write(bufferedImage, "jpg", imageFile);
            return true;
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
            return false;
        }
    }

    @GET
    @Path("/image/{name}")
    @Produces("image/jpg")
    public Response getImage(@DefaultValue("0") @PathParam("name") String name) throws IOException {
        BufferedImage image = ImageIO.read(new File(this.projectPath + "uploads/" + name));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", baos);
        byte[] imageData = baos.toByteArray();
        return Response.ok(new ByteArrayInputStream(imageData)).build();
    }
}
