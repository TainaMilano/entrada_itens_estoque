/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 *
 * @author Milano
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Produto.findAll", query = "SELECT p FROM Produto p"),
    @NamedQuery(name = "Produto.findById", query = "SELECT p FROM Produto p WHERE p.id = :id"),
    @NamedQuery(name = "Produto.findByDescricao", query = "SELECT p FROM Produto p WHERE p.descricao = :descricao"),
    @NamedQuery(name = "Produto.findByTipo", query = "SELECT p FROM Produto p WHERE p.tipo = :tipo"),
    @NamedQuery(name = "Produto.findByTamanho", query = "SELECT p FROM Produto p WHERE p.tamanho = :tamanho"),
    @NamedQuery(name = "Produto.findBySubstrato", query = "SELECT p FROM Produto p WHERE p.substrato = :substrato"),
    @NamedQuery(name = "Produto.findByVias", query = "SELECT p FROM Produto p WHERE p.vias = :vias"),
    @NamedQuery(name = "Produto.findByCores", query = "SELECT p FROM Produto p WHERE p.cores = :cores"),
    @NamedQuery(name = "Produto.findByQuantidade", query = "SELECT p FROM Produto p WHERE p.quantidade = :quantidade"),
    @NamedQuery(name = "Produto.findByQuantidadeMinima", query = "SELECT p FROM Produto p WHERE p.quantidadeMinima = :quantidadeMinima")})
public class Produto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    @Size(max = 255)
    private String descricao;
    @Size(max = 100)
    private String tipo;
    @Size(max = 20)
    private String tamanho;
    @Size(max = 20)
    private String substrato;
    private Integer vias;
    @Size(max = 100)
    private String cores;
    private Integer quantidade;
    @Column(name = "quantidade_minima")
    private Integer quantidadeMinima;
    @OneToMany(mappedBy = "idProduto")
    private Collection<ItemEntrada> itemEntradaCollection;

    public Produto() {
    }

    public Produto(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    public String getSubstrato() {
        return substrato;
    }

    public void setSubstrato(String substrato) {
        this.substrato = substrato;
    }

    public Integer getVias() {
        return vias;
    }

    public void setVias(Integer vias) {
        this.vias = vias;
    }

    public String getCores() {
        return cores;
    }

    public void setCores(String cores) {
        this.cores = cores;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Integer getQuantidadeMinima() {
        return quantidadeMinima;
    }

    public void setQuantidadeMinima(Integer quantidadeMinima) {
        this.quantidadeMinima = quantidadeMinima;
    }

    @XmlTransient
    @JsonIgnore
    public Collection<ItemEntrada> getItemEntradaCollection() {
        return itemEntradaCollection;
    }

    public void setItemEntradaCollection(Collection<ItemEntrada> itemEntradaCollection) {
        this.itemEntradaCollection = itemEntradaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Produto)) {
            return false;
        }
        Produto other = (Produto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.stackoverflow.controleestoque.model.Produto[ id=" + id + " ]";
    }
    
}
