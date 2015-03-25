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
public class Curso {
    
    // ATRIBUTOS /////////////////////////////////////////
     
    private int codigo, qtdExercicios;
    private String nome, url_gabarito, url_gabarito_alt;

    // GETTERS E SETTERS /////////////////////////////////////////
     
    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getQtdExercicios() {
        return qtdExercicios;
    }

    public void setQtdExercicios(int qtdExercicios) {
        this.qtdExercicios = qtdExercicios;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUrl_gabarito() {
        return url_gabarito;
    }

    public void setUrl_gabarito(String url_gabarito) {
        this.url_gabarito = url_gabarito;
    }

    public String getUrl_gabarito_alt() {
        return url_gabarito_alt;
    }

    public void setUrl_gabarito_alt(String url_gabarito_alt) {
        this.url_gabarito_alt = url_gabarito_alt;
    }
    
}