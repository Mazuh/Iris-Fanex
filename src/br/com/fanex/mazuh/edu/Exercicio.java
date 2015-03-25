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

/**
 *
 * @author mazuh
 */
public class Exercicio {

    // ATRIBUTOS /////////////////////////////////////////
     
    private int codigo, 
            codigoAluno, 
            codigoCurso, 
            codigoInstrutor, 
            numAula, 
            qtdRespostas;
    
    private String respostas, 
            dtEnvio, 
            correcao;
    
    private boolean isCorrigido;
    
    // GETTERS E SETTERS ///////////////////////////   
    
    public String getSituacao() {
        if (this.dtEnvio == "00/00/00")
            return "Não enviado";
        
        if (this.isCorrigido)
            return "Corrigido";
        else
            return "Enviado em " + this.dtEnvio;
        
    }
    
    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigoAluno() {
        return codigoAluno;
    }

    public void setCodigoAluno(int codigoAluno) {
        this.codigoAluno = codigoAluno;
    }

    public int getCodigoCurso() {
        return codigoCurso;
    }

    public void setCodigoCurso(int codigoCurso) {
        this.codigoCurso = codigoCurso;
    }

    public int getCodigoInstrutor() {
        return codigoInstrutor;
    }

    public void setCodigoInstrutor(int codigoInstrutor) {
        this.codigoInstrutor = codigoInstrutor;
    }

    public int getNumAula() {
        return numAula;
    }

    public void setNumAula(int numAula) {
        this.numAula = numAula;
    }

    public int getQtdRespostas() {
        return qtdRespostas;
    }

    public void setQtdRespostas(int qtdRespostas) {
        this.qtdRespostas = qtdRespostas;
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

    public boolean isIsCorrigido() {
        return isCorrigido;
    }

    public void setIsCorrigido(boolean isCorrigido) {
        this.isCorrigido = isCorrigido;
    }
    
}