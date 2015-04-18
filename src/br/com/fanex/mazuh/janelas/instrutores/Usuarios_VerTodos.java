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
package br.com.fanex.mazuh.janelas.instrutores;

import br.com.fanex.mazuh.acesso.Sessao;
import br.com.fanex.mazuh.acesso.Usuario;
import br.com.fanex.mazuh.jpa.UsuarioJpaController;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author mazuh
 */
public class Usuarios_VerTodos extends javax.swing.JFrame {

    private final boolean isAdmin; // var para nível de permissão.
                                  // um não-admin só pode ver senhas de alunos
    private final int TB_ROWS_N = 15; // número de linhas da tabela
     
    /**
     * Creates new form Alunos_VerTodos
     */
    public Usuarios_VerTodos() {
        initComponents();
        
        // verificação de nível de segurança
        switch ((Sessao.usuario_logado().getIdHierarquia()).getNome()) {
            // não é admin, vai poder ver só alunos
            case "instrutor":
                isAdmin = false;
                break;

            // é admin, vai poder ver todos os usuários
            case "admin":
                isAdmin = true;
                break;

            default:
                isAdmin = false;
                JOptionPane.showMessageDialog(null,
                        "ACESSO NEGADO.",
                        "DETECTADO ALGO ERRADO NA VERIFICAÇÃO SEGURANÇA",
                        JOptionPane.ERROR_MESSAGE);
                System.exit(1); // Erro fatal: sai do sistema imediatamente.
                break;

        }

        // configs janela
        this.setTitle("Lista - " + (isAdmin ? "Usuários" : "Alunos"));
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        
        // título
        jTitulo.setText(jTitulo.getText() + (isAdmin ? "USUARIOS" : "ALUNOS"));
    }
    
    /*
    Atualiza a paginação e preenche a tabela de alunos.
    */
    private void atualizar(){
        setPaginacaoParaTantosObjetos(getQtdUsers(), TB_ROWS_N);
        preencherTbDeAlunos();
    }
    
    /*
    Ajusta o limite de páginas de acordo com a quantidade de objetos
    e seta no jLabel no formato "x/y", onde 'x' é resetado a 1 e 'y' é o limite.
    */
    private void setPaginacaoParaTantosObjetos(int qtdObjetos, int qtdLinhas){
        int paginas;// novo limite de páginas

        boolean isDivisivel = qtdObjetos % qtdLinhas == 0; // se qtd é divisível pela qtd de linhas
        
        // divide a quantidade entre as linhas disponíveis
        paginas = (int) (qtdObjetos / qtdLinhas); // irá receber o menor inteiro do quociente da divisão
        
        paginas += (isDivisivel ? 0 : 1); // se o número não for divisĩvel, +1 pra compensar
        
        // pronto!
        jPagina.setText("1/" + paginas);
        
    }
    
    private void preencherTbDeAlunos(){
        // índice para saber qual a primeira posição do bd a ser consultadop
        // é definido baseado na página atual da tabela
        // ps: achando o número da página:
        //          "x/y"   ->   split("/")   ->   [0]="x", [1]="y".
        int first = (Integer.valueOf(jPagina.getText().split("/")[0]) - 1) * TB_ROWS_N;
                                                  // Sendo tb_rows_n = 15, ex: 
                                                  //     pag = 1 -> indice = 0
                                                  //     pag = 2 -> indice = 15
                                                  //     pag = 3 -> indice = 30 (...)
        
        // recupera as strings a serem colocadas na tabela baseada na query
        String[] registros = getRegistros(TB_ROWS_N, first);
        
        // escreve as strings na tabela
        for (int row = 0; row < TB_ROWS_N; row++){
            tbUsers.setValueAt(registros[row], row, 0);
        }
        
    }
    
    /*
    Realiza uma consulta limitada entre os usuários do sistema e retorna
    apenas um vetor de textos referentes a estas consultadas.
    O conteúdo dos textos podem ser "censurados" baseado no nível de permissão
    do usuário.
    */
    private String[] getRegistros(int max, int first){
        // vars 
        String[] registros = new String[max]; // vai receber as linhas para retornar
        List<Usuario> users; // armazenar-se-á todos os obj consultados 
        
        // data access object
        UsuarioJpaController usuarioDAO = new UsuarioJpaController(Sessao.getEntityManagerFactory());
        
        // busca limitada (buscar por todos causaria prejuízo de desempenho)
        users = usuarioDAO.findUsuarioEntities(max, first);
        
        for (int i = 0; (i < users.size() && i < max); i++){
            String linha; // a se adicionado no array 
            
            Usuario user = (users.get(i)); // acesso mais rápido ao user do índice 
            
            String hierarquiaDoUser = user.getIdHierarquia().getNome(); // aux 
            
            // pega id e senha
            linha = ("[ID " + user.getId() + "] - " + user.getNome() + " ");
            
            // Outras informações...
            // NÍVEL DE PERMISSÃO DE ACESSO: admin (ver todos menos outro admin)
            if (this.isAdmin){
                
                // achou outro admin
                if (hierarquiaDoUser.equalsIgnoreCase("admin"))
                    linha += "(Senha oculta: outro administrador do Íris)";
                
                // achou um instrutor ou aluno, pode ver a senha
                else
                    linha += "(Senha: " + user.getSenha() + ")";
                
            } else{ // NÍVEL DE PERMISSÃO DE ACESSO: instrutor (ver só aluno)
                
                // achou admin
                if (hierarquiaDoUser.equalsIgnoreCase("admin"))
                    linha += "(Senha oculta: administrador do Íris)";
                
                // achou outro colega instrutor
                else if(hierarquiaDoUser.equalsIgnoreCase("instrutor"))
                    linha += "(Senha oculta: instrutor do Íris)";
                
                // achou aluno, pode ver a senha!
                else
                    linha += "(Senha: " + user.getSenha() + ")";
                
            }
            
            // adiciona a linha depois de ter os dados filtrados
            try {
                registros[i] = linha;
                
            } catch (NullPointerException e) {
                 System.err.println("Erro no getter de registro.\n"
                         + "Tentativa falha de armazenar no vetor.");
            }
            
        } // fim do laço for
        
        return registros;
        
    }
    
    /*
    Retorna a quantidade de linhas de usuários no banco de dados
    */
    private int getQtdUsers(){
        return new UsuarioJpaController(Sessao.getEntityManagerFactory()).getUsuarioCount();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tbUsers = new javax.swing.JTable();
        btnNovo = new javax.swing.JButton();
        btnAnt = new javax.swing.JButton();
        btnProx = new javax.swing.JButton();
        jPagina = new javax.swing.JLabel();
        jTitulo = new javax.swing.JLabel();
        btnVoltar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                formWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });

        tbUsers.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Usuários"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbUsers.setColumnSelectionAllowed(true);
        tbUsers.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tbUsers);
        tbUsers.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        if (tbUsers.getColumnModel().getColumnCount() > 0) {
            tbUsers.getColumnModel().getColumn(0).setResizable(false);
        }

        btnNovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/fanex/mazuh/janelas/imgs/icon/mais.gif"))); // NOI18N
        btnNovo.setText("Novo ID");

        btnAnt.setText("<");
        btnAnt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAntActionPerformed(evt);
            }
        });

        btnProx.setText(">");
        btnProx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProxActionPerformed(evt);
            }
        });

        jPagina.setText("1/1");

        jTitulo.setBackground(new java.awt.Color(39, 174, 96));
        jTitulo.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jTitulo.setForeground(new java.awt.Color(255, 255, 255));
        jTitulo.setText("LISTA DE ");
        jTitulo.setOpaque(true);

        btnVoltar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/fanex/mazuh/janelas/imgs/icon/janela.gif"))); // NOI18N
        btnVoltar.setText("Voltar");
        btnVoltar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVoltarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 557, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnNovo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTitulo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAnt)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPagina)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnProx))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnVoltar)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(21, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNovo)
                    .addComponent(btnAnt)
                    .addComponent(btnProx)
                    .addComponent(jPagina)
                    .addComponent(jTitulo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnVoltar)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /*
    Simplesmente sai da janela.
    */
    private void btnVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVoltarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnVoltarActionPerformed

    /*
    Retrocede uma página, se for possível.
    */
    private void btnAntActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAntActionPerformed
                // encontra página
        String[] paginacao = jPagina.getText().split("/"); // var auxiliar para split 
        int pagina = Integer.valueOf(paginacao[0]);
        
        // se não tiver no limite, pode retroceder 
        if (pagina > 1)
            pagina--;
        
        // seta nova página
        jPagina.setText(pagina + "/" + paginacao[1]);
        
        preencherTbDeAlunos();
    }//GEN-LAST:event_btnAntActionPerformed

    /*
    Avança uma página, se puder.
    */
    private void btnProxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProxActionPerformed
        // encontra página e limite de páginas 
        String[] paginacao = jPagina.getText().split("/"); // var auxiliar para split 
        int pagina = Integer.valueOf(paginacao[0]);
        int limite = Integer.valueOf(paginacao[1]);
        
        // se não tiver no limite, pode avançar 
        if (pagina < limite)
            pagina++;
        
        // seta nova página
        jPagina.setText(pagina + "/" + limite);
        
        preencherTbDeAlunos();
    }//GEN-LAST:event_btnProxActionPerformed

    /*
    Quando o form entra em foco, realiza os procedimentos iniciais de atualização.
    */
    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
        atualizar();
    }//GEN-LAST:event_formWindowGainedFocus

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Usuarios_VerTodos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Usuarios_VerTodos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Usuarios_VerTodos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Usuarios_VerTodos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Usuarios_VerTodos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAnt;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnProx;
    private javax.swing.JButton btnVoltar;
    private javax.swing.JLabel jPagina;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel jTitulo;
    private javax.swing.JTable tbUsers;
    // End of variables declaration//GEN-END:variables
}
