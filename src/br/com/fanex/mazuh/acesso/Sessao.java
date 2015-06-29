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
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JOptionPane;
import org.eclipse.persistence.exceptions.DatabaseException;
import org.postgresql.util.PSQLException;

/**
 *
 * @author mazuh
 */
public class Sessao {

    private static Usuario usuario_logado = null;

    private static String ip = "", porta = "";
    public final static String PADRAO_IP = "localhost", PADRAO_PORTA = "5432";

    /*
     Retorna o objeto global do usuário logado.
     */
    public static Usuario usuario_logado() {
        if (usuario_logado == null) {
            // se for nulo, emita ume erro fatal.
            JOptionPane.showMessageDialog(null,
                    "Erro no getter da sessão.",
                    "ERRO",
                    JOptionPane.ERROR_MESSAGE);

            System.exit(1);
            return null;

        } else {
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
    public static void refresh() {
        try {

            // refresh:
            usuario_logado = getUsuarioDAO().findUsuario(usuario_logado().getId());

        } catch (Exception e) {
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
    public static boolean logar(int codigo, String tentativaDeSenha) {

        try {

            Usuario usuarioEncontrado = getUsuarioDAO().findUsuario(codigo);

            if (tentativaDeSenha.equals(usuarioEncontrado.getSenha())) {
                // Objeto encontrado e com senha compatível, então logado com sucesso.
                Sessao.usuario_logado = usuarioEncontrado;
                return true;

            } else {
                // Senha incorreta.
                return false;
            }

        } catch (Exception e) {
            // Erro com acesso ao objeto.
            return false;
        }

    }

    /*
     Simplesmente diz que ninguém está logado agora.
     */
    public static void sair() {
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
    private static UsuarioJpaController getUsuarioDAO() {
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
    public static EntityManagerFactory getEntityManagerFactory() {
        //ip = PADRAO_IP;
        //porta = PADRAO_PORTA;
        
        Map mapa = new HashMap();
        mapa.put("javax.persistence.jdbc.url",
                "jdbc:postgresql://" + ip + ":" + porta + "/db_iris");
        return Persistence.createEntityManagerFactory("Projeto_Iris__Fanex_PU", mapa);
    }

    /*
    Busca o arquivo 'iris-server-ip.conf' (cuja localização está no diretório
    local './iris.conf') e encontra o IP do servidor. Caso dê falha, tenta
    ir alterando o último octeto do IP encontrado de 0 a 255 até um dar certo.
    
    Caso conseguir resolver um IP que conecte, retorna true.
    Se não encontrar o arquivo mas conseguir resolver por localhost,
        retorna true e também emite uma janela de erro.
    
    Qualquer imprevisibilidade retorna false.
    Impossibilidade de resolver o novo IP também retorna false.
    
    */
    public static boolean resolveNovoIP() {
        if (Sessao.porta.equals(""))
            Sessao.porta = Sessao.PADRAO_PORTA;
        
        String ipEncontrado = descobrirIP();

        // verifica se tá vazio
        if (ipEncontrado.equals("")) {
            erro("O caminho fornecido por 'iris.conf' pode estar errado ou vazio.\n\n"
                    + "O IP do servidor do banco de dados por padrão é 'localhost'.\n"
                    + "Após dar OK nesta caixa de diálogo, aguarde alguns segundos.\n\n");
            Sessao.ip = Sessao.PADRAO_IP;
        } else{
            // se algum ip foi encontrado, verifica se o que foi encontrado funciona.
            Sessao.ip = ipEncontrado;
            
        }
        
        if (Sessao.consegue_buscar_teste())
            return true;
        // else: prossegue a tentativa de resolver o novo ip
        
        // se tinha algo, tenta brincar com o ip em busca de um novo
        try {
            
            String[] ip_quebrado = ipEncontrado.split("\\."); // quebra por pontos
            
            // acha três primeiros octetos do ip
            String ip_radical = ip_quebrado[0] + "." + ip_quebrado[1] + "." + ip_quebrado[2] + ".";
            
            // tenta busca o último octeto
            for (int i = 0; i <= 255; i++){
                
                // tenta com novo ip baseado no radical encontrado
                Sessao.ip = ip_radical + i;
                
                if (Sessao.consegue_buscar_teste()) // teste deu certo! uhu!
                    return true; // finaliza loop, achei o que queria
                
            }
        
        } catch(Exception e) { // provavelmente ip inválido: quebra deu exception
            return false;
        }
        
        return false; // nem o loop conseguiu achar, então não consegue resolver
    }
    
    /*
    Usa o o hibernate pra fazer uma busca qualquer.
    Se essa tentativa de busca lançar uma exception qualquer, retorna false.
    Se fez a busca e nenhum erro foi lançado, retorna true, indicando conexão ok.
    */
    public static boolean consegue_buscar_teste() {
        try {
            // testa
            System.out.println("Tentativa para: " + Sessao.ip + ":" + Sessao.porta);
            
            UsuarioJpaController foo = new UsuarioJpaController(Sessao.getEntityManagerFactory());
            foo.findUsuario(600);
            
            return true; // nenhuma exception lançada? deu certo.
            
        }catch(DatabaseException e){
            return false;
        }catch(Exception e){
            return false;
        }

    }
    
    /*
    Abre o arquivo iris.conf em busca do local do verdadeiro IP.
    Retorna string vazia em caso de erro.
     */
    private static String descobrirLocalDoIP() {

        String dir_encontrado = ""; // var com tudo o que for encontrado no arquivo

        try (Scanner leitor = new Scanner(new FileReader("iris.conf"))) {

            // se tiver algo, lê
            if (leitor.hasNext()) {
                dir_encontrado += leitor.nextLine();
            }

        } catch (FileNotFoundException e) {
            erro("Arquivo 'iris.conf' na pasta deste .jar não foi encontrado.");
        } catch (Exception e) {
            erro("Não conseguiu abrir 'iris.conf' na pasta local deste .jar.");
        }

        return dir_encontrado;

    }

    /*
    Tenta abrira arquivo server-ip.conf em busca do IP.
    Retorna IP encontrado ou string vazia em caso de erro.
    */
    private static String descobrirIP() {
        String ip_descoberto = "";
        String dir_ip = descobrirLocalDoIP();

        // o caminho deve terminar sempre no arquivo server-ip.conf
        if (!dir_ip.endsWith("iris-server-ip.conf")) {
            dir_ip += "iris-server-ip.conf";
        }

        try (Scanner leitor = new Scanner(new FileReader(dir_ip))) {

            // se tiver algo, lê
            if (leitor.hasNext()) {
                ip_descoberto += leitor.nextLine();
            }

        } catch (FileNotFoundException e) {
            erro("Arquivo 'iris-server-ip.conf' não foi encontrado.");
        } catch (Exception e) {
            erro("Não conseguiu abrir arquivo 'iris-server-ip.conf'.");
        }

        return ip_descoberto;
    }

    /*
     Abre uma caixa de diálogo de erro simples.
     */
    private static void erro(String msg) {
        JOptionPane.showMessageDialog(null,
                msg + "\n\nChame a(o) técnica(o) do laboratório!",
                "ERRO NA CONEXÃO",
                JOptionPane.ERROR_MESSAGE);
    }

}