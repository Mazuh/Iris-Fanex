/*
 * The MIT License
 *
 * Copyright 2015 mazuh.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package br.com.fanex.mazuh.edu;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author mazuh
 */
@Entity
@Table(name = "cursos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Curso.findAll", query = "SELECT c FROM Curso c"),
    @NamedQuery(name = "Curso.findById", query = "SELECT c FROM Curso c WHERE c.id = :id"),
    @NamedQuery(name = "Curso.findByNome", query = "SELECT c FROM Curso c WHERE c.nome = :nome"),
    @NamedQuery(name = "Curso.findByQtdExercicios", query = "SELECT c FROM Curso c WHERE c.qtdExercicios = :qtdExercicios"),
    @NamedQuery(name = "Curso.findByUrlGabarito", query = "SELECT c FROM Curso c WHERE c.urlGabarito = :urlGabarito"),
    @NamedQuery(name = "Curso.findByUrlGabaritoAlt", query = "SELECT c FROM Curso c WHERE c.urlGabaritoAlt = :urlGabaritoAlt")})
public class Curso implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private Integer id;
    @Basic(optional = false)
    private String nome;
    @Basic(optional = false)
    @Column(name = "qtd_exercicios")
    private int qtdExercicios;
    @Basic(optional = false)
    @Column(name = "url_gabarito")
    private String urlGabarito;
    @Column(name = "url_gabarito_alt")
    private String urlGabaritoAlt;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCurso")
    private List<Exercicio> exercicioList;

    public Curso() {
    }

    public Curso(Integer id) {
        this.id = id;
    }

    public Curso(Integer id, String nome, int qtdExercicios, String urlGabarito) {
        this.id = id;
        this.nome = nome;
        this.qtdExercicios = qtdExercicios;
        this.urlGabarito = urlGabarito;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getQtdExercicios() {
        return qtdExercicios;
    }

    public void setQtdExercicios(int qtdExercicios) {
        this.qtdExercicios = qtdExercicios;
    }

    public String getUrlGabarito() {
        return urlGabarito;
    }

    public void setUrlGabarito(String urlGabarito) {
        this.urlGabarito = urlGabarito;
    }

    public String getUrlGabaritoAlt() {
        return urlGabaritoAlt;
    }

    public void setUrlGabaritoAlt(String urlGabaritoAlt) {
        this.urlGabaritoAlt = urlGabaritoAlt;
    }

    @XmlTransient
    public List<Exercicio> getExercicioList() {
        return exercicioList;
    }

    public void setExercicioList(List<Exercicio> exercicioList) {
        this.exercicioList = exercicioList;
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
        if (!(object instanceof Curso)) {
            return false;
        }
        Curso other = (Curso) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.fanex.mazuh.Curso[ id=" + id + " ]";
    }
    
}
