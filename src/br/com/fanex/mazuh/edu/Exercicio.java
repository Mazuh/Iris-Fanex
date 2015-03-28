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

import br.com.fanex.mazuh.acesso.Usuario;
import br.com.fanex.mazuh.edu.Curso;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mazuh
 */
@Entity
@Table(name = "exercicios", catalog = "db_iris", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Exercicio.findAll", query = "SELECT e FROM Exercicio e"),
    @NamedQuery(name = "Exercicio.findById", query = "SELECT e FROM Exercicio e WHERE e.id = :id"),
    @NamedQuery(name = "Exercicio.findByNumAula", query = "SELECT e FROM Exercicio e WHERE e.numAula = :numAula"),
    @NamedQuery(name = "Exercicio.findByQtdPerguntas", query = "SELECT e FROM Exercicio e WHERE e.qtdPerguntas = :qtdPerguntas"),
    @NamedQuery(name = "Exercicio.findByRespostas", query = "SELECT e FROM Exercicio e WHERE e.respostas = :respostas"),
    @NamedQuery(name = "Exercicio.findByDtEnvio", query = "SELECT e FROM Exercicio e WHERE e.dtEnvio = :dtEnvio"),
    @NamedQuery(name = "Exercicio.findByCorrecao", query = "SELECT e FROM Exercicio e WHERE e.correcao = :correcao"),
    @NamedQuery(name = "Exercicio.findByIsCorrigido", query = "SELECT e FROM Exercicio e WHERE e.isCorrigido = :isCorrigido")})
public class Exercicio implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "num_aula")
    private Integer numAula;
    @Column(name = "qtd_perguntas")
    private Integer qtdPerguntas;
    @Column(name = "respostas")
    private String respostas;
    @Column(name = "dt_envio")
    private String dtEnvio;
    @Column(name = "correcao")
    private String correcao;
    @Column(name = "is_corrigido")
    private Short isCorrigido;
    @JoinColumn(name = "id_instrutor", referencedColumnName = "id")
    @ManyToOne
    private Usuario idInstrutor;
    @JoinColumn(name = "id_aluno", referencedColumnName = "id")
    @ManyToOne
    private Usuario idAluno;
    @JoinColumn(name = "id_curso", referencedColumnName = "id")
    @ManyToOne
    private Curso idCurso;

    public Exercicio() {
    }

    public Exercicio(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNumAula() {
        return numAula;
    }

    public void setNumAula(Integer numAula) {
        this.numAula = numAula;
    }

    public Integer getQtdPerguntas() {
        return qtdPerguntas;
    }

    public void setQtdPerguntas(Integer qtdPerguntas) {
        this.qtdPerguntas = qtdPerguntas;
    }

    public String getRespostas() {
        return respostas;
    }

    public void setRespostas(String respostas) {
        this.respostas = respostas;
    }

    public String getDtEnvio() {
        return dtEnvio;
    }

    public void setDtEnvio(String dtEnvio) {
        this.dtEnvio = dtEnvio;
    }

    public String getCorrecao() {
        return correcao;
    }

    public void setCorrecao(String correcao) {
        this.correcao = correcao;
    }

    public Short getIsCorrigido() {
        return isCorrigido;
    }

    public void setIsCorrigido(Short isCorrigido) {
        this.isCorrigido = isCorrigido;
    }

    public Usuario getIdInstrutor() {
        return idInstrutor;
    }

    public void setIdInstrutor(Usuario idInstrutor) {
        this.idInstrutor = idInstrutor;
    }

    public Usuario getIdAluno() {
        return idAluno;
    }

    public void setIdAluno(Usuario idAluno) {
        this.idAluno = idAluno;
    }

    public Curso getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(Curso idCurso) {
        this.idCurso = idCurso;
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
        if (!(object instanceof Exercicio)) {
            return false;
        }
        Exercicio other = (Exercicio) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.fanex.mazuh.Exercicio[ id=" + id + " ]";
    }
    
}
