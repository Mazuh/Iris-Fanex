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
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author mazuh
 */
@MappedSuperclass
@Table(name = "cursos")
@XmlRootElement
public class Curso implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nome")
    private String nome;
    @Column(name = "qtd_exercicios")
    private Integer qtdExercicios;
    @Column(name = "url_gabarito")
    private String urlGabarito;
    @Column(name = "url_gabarito_alt")
    private String urlGabaritoAlt;
    @OneToMany(mappedBy = "idCurso")
    private List<Exercicio> exerciciosList;

    public Curso() {
    }

    public Curso(Integer id) {
        this.id = id;
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

    public Integer getQtdExercicios() {
        return qtdExercicios;
    }

    public void setQtdExercicios(Integer qtdExercicios) {
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
    public List<Exercicio> getExerciciosList() {
        return exerciciosList;
    }

    public void setExerciciosList(List<Exercicio> exerciciosList) {
        this.exerciciosList = exerciciosList;
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
        return "br.com.fanex.mazuh.Cursos[ id=" + id + " ]";
    }
    
}
