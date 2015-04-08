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
package br.com.fanex.mazuh.acesso;

import br.com.fanex.mazuh.jpa.DAO;
import br.com.fanex.mazuh.jpa.UsuarioJpaController;

/**
 *
 * @author mazuh
 */
public class Sessao {
    
    private static Usuario usuario_logado;
    
    /*
    Retorna o objeto global do usuário logado.
    */
    public static Usuario usuario_logado(){
        return Sessao.usuario_logado;
    }
    
    /*
    Para declarar como permanente alterações feitas no usuário logado, deve-se
    passar por argumento ao commit(arg) e ele irá tentar salvar tudo na persistência
    e na sessão.
    */
    public static boolean commit(Usuario usuario){
        
        if (Sessao.usuario_logado.equals(usuario)){
            // mesmo id
            try{
                // tenta salvar...
                getUsuarioDAO().edit(usuario); // ... no banco...
                Sessao.usuario_logado = usuario; // ... e se não tiver dado erro, salva na sessão.
                return true;
                
            } catch(Exception e){
                // erro com o objeto de persistência
                System.err.println("Erro ao tentar commitar na sessão.");
                return false;
            }
            
        } else{
            // id do usuário logado não é o mesmo que o que requisita o commit.
            System.err.println("Usuário não altorizado tentando commitar na sessão.");
            return false;
        }
    }
    
    /*
    Encontra o objeto com código correspondente no banco, confere sua senha
    e declara-o como objeto global da sessão.
    */
    public static boolean logar(int codigo, String tentativaDeSenha){
        
        try{
            
            Usuario usuarioEncontrado = getUsuarioDAO().findUsuario(codigo);
            
            if (tentativaDeSenha.equals(usuarioEncontrado.getSenha())){
                // Objeto encontrado e com senha compatível, então logado com sucesso.
                Sessao.usuario_logado = usuarioEncontrado;
                return true;
                
            }else{
                // Senha incorreta.
                return false;
            }
            
        }catch(Exception e){
            // Erro com acesso ao objeto.
            return false;
        }
        
    }
    
    /*
    Simplesmente diz que ninguém está logado agora.
    */
    public static void sair(){
        Sessao.usuario_logado = null;
    }
    
    /*
    Pega o controlador do objeto.
    */
    private static UsuarioJpaController getUsuarioDAO(){
        return new UsuarioJpaController(DAO.getEntityManagerFactory());
    }
}