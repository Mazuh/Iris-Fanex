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

import br.com.fanex.mazuh.jpa.UsuarioJpaController;
import java.util.Calendar;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JOptionPane;

/**
 *
 * @author mazuh
 */
public class Sessao {
    
    private static Usuario usuario_logado = null;
    
    /*
    Retorna o objeto global do usuário logado.
    */
    public static Usuario usuario_logado(){
        if (usuario_logado == null){
            // se for nulo, emita ume erro fatal.
            JOptionPane.showMessageDialog(null,
                    "Erro no getter da sessão.",
                    "ERRO",
                    JOptionPane.ERROR_MESSAGE);
            
            System.exit(1);
            return null;
        
        } else{
            // se tiver alguém logado.
            return Sessao.usuario_logado;
        }
    }
    
    /*
    Para declarar como permanente alterações feitas no usuário logado, deve-se
    passar por argumento ao commit(arg) e ele irá tentar salvar tudo na persistência
    e na sessão.
    Qualquer erro aqui será tratado como fatal pelo sistema.
    */
    /*
    public static void commit(Usuario usuario){
        
        if (Sessao.usuario_logado().equals(usuario)){
            // mesmo id, pode tentar efetuar o commit
            try{
                // tenta salvar...
                getUsuarioDAO().edit(usuario); // ... no banco...
                Sessao.usuario_logado = usuario; // ... e se não tiver dado erro, salva na sessão.
                
            } catch(Exception e){
                // erro com o objeto de persistência
                JOptionPane.showMessageDialog(null, 
                        "Erro ao tentar commitar na sessão.",
                        "ERRO FATAL",
                        JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }
            
        } else{
            // id do usuário logado não é o mesmo que o que requisita o commit.
            JOptionPane.showMessageDialog(null, 
                        "Acesso não autorizado tentando commitar na sessão.",
                        "ERRO FATAL",
                        JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }*/
    
    /*
    Refaz a query do usuário logado ao banco de dados.
    */
    public static void refresh(){
        try{
            
            // refresh:
            usuario_logado = getUsuarioDAO().findUsuario(usuario_logado().getId());
            
        } catch(Exception e){
            // erro?
            JOptionPane.showMessageDialog(null,
                    "Erro ao tentar acessar banco de dados via refresh.",
                    "ERRO",
                    JOptionPane.ERROR_MESSAGE);
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
    Retorna string da data atual no formato DD/MM/YY
    Pode retornar string "dataerro" caso uma exceção inesperada seja disparada.
    */
    public static String getDataAtual() {

        String data = "";
        
        try {
            // pega instância do calendário atual
            Calendar calendario = Calendar.getInstance();

            // tratamento dia do mês: formato DD
            int numDia = calendario.get(Calendar.DAY_OF_MONTH);
            data += ((numDia > 9) ? (numDia) : ("0" + numDia)) + "/";

            // tratamento mês: formato MM
            int numMes = calendario.get(Calendar.MONTH);
            data += ((numMes > 9) ? (numMes) : ("0" + numMes)) + "/";

            // tratamento ano: pegar dois últimos caracteres apenas (YY).
            String strAno = String.valueOf(calendario.get(Calendar.YEAR));
            data += String.valueOf(strAno.charAt(strAno.length() - 2)
                    + String.valueOf(strAno.charAt(strAno.length() - 1)));
            
        } catch (Exception e) {
            
            data = "dataerro";
            
        }

        return data;
        
    }
    
    /*
    Pega o controlador do objeto.
    */
    private static UsuarioJpaController getUsuarioDAO(){
        return new UsuarioJpaController(Sessao.getEntityManagerFactory());
    }
    
    
    /*
    Retorna uma EntityManagerFactory para os DAO usarem.
    
    "Objeto de acesso a dados (ou simplesmente DAO, acrônimo de Data Access
    Object), é um padrão para persistência de dados que permite separar
    regras de negócio das regras de acesso a banco de dados. Numa aplicação
    que utilize a arquitetura MVC, todas as funcionalidades de bancos de
    dados, tais como obter as conexões, mapear objetos Java para tipos de
    dados SQL ou executar comandos SQL, devem ser feitas por classes DAO."
    (Wikipédia)

    As classes DAO são os controladores JPA presentes neste mesmo pacote.
    */

    public static EntityManagerFactory getEntityManagerFactory(){
        return Persistence.createEntityManagerFactory("Projeto_Iris__Fanex_PU");
    }
    
}