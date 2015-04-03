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
package br.com.fanex.mazuh;

import br.com.fanex.mazuh.janelas.Login;
import br.com.fanex.mazuh.jpa.DAO;
import br.com.fanex.mazuh.jpa.UsuarioJpaController;

/**
 *
 * @author mazuh
 */
public class Iris {

    /*
    Classe principal.
    */
    public static void main(String[] args) {
        
        // A primeira conexão com o banco de dados é lenta.
        // O objeto abaixo serve pra realizar esse lerdo processo antes da aplicação estar visível ao user.
        UsuarioJpaController foo = new UsuarioJpaController(DAO.getEntityManagerFactory());
        foo.findUsuario(0);
        foo = null; // manda o objeto para o garbage collector
        
        // Inicia a janela de login.
        new Login().setVisible(true);
        
    }
    
}