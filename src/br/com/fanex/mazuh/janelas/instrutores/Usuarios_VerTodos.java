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

import br.com.fanex.mazuh.acesso.Hierarquia;
import br.com.fanex.mazuh.acesso.Sessao;
import br.com.fanex.mazuh.acesso.Usuario;
import br.com.fanex.mazuh.jpa.HierarquiaJpaController;
import br.com.fanex.mazuh.jpa.UsuarioJpaController;
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
        
        // botão de addInstrutor só é liberado pra admins
        btnAddInstrutor.setEnabled(isAdmin);
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
    
    /*
    Abre uma caixa de diálogo para o usuário digitar um ID válido.
    
    O ID pode ser de qualquer uma das entidades de persistência.
    É feita uma checagem básica de integridade e tratamento de erro.
    Caixas de diálogo de erro podem surgir informando que o valor é inválido.
    
    Retorna -1 se algo der errado.
    Retorna o valor digitado caso ele seja válido.
    */
    private int inputValidoID(){
        int id = -1; // variável para armazenar o ID que o usuário irá digitar.
        
        // caixa de diálogo pra entrada de dados em texto.
        String entrada = JOptionPane.showInputDialog(null,
                "Digite o ID (código numérico) válido:");
        
        // botão de cancelar: retorna sinal inválido.
        if (entrada == null)
            return -1; // o return aqui evita processamentos desnecessários das linhas seguintes
        
        // botão de ok: bloco de try-catch-finally para mais verificações.
        try {
           
            // tenta converter pra inteiro
            id = Integer.valueOf(entrada);
            
        } catch (NumberFormatException e) {
            
            // erro?
            id = -1;
            
        } finally {
             
            if (id > 0){  
                // está tudo ok, retorna o id digitado em Integer.
                return id;
            
            } else{ // id é 0 ou negativo seja porque o usuário digitou assim, ou porque o catch pegou algo 
                
                // mensagenzinha de erro!
                JOptionPane.showMessageDialog(
                    null,
                    "Calma aí com esse botão de \"OK\", fera!\n"
                            + "Por favor, apenas um código numérico decimal\n"
                            + "que pertencer ao conjunto dos inteiros\n"
                            + "maiores que zero e menores que "
                            + Integer.MAX_VALUE + ".",
                   "Ops...",
                    JOptionPane.ERROR_MESSAGE);
                
                return -1; // e retorno sinalizando invalidez
            }
        }
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
        btnAddAluno = new javax.swing.JButton();
        btnAnt = new javax.swing.JButton();
        btnProx = new javax.swing.JButton();
        jPagina = new javax.swing.JLabel();
        jTitulo = new javax.swing.JLabel();
        btnVoltar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btnAddInstrutor = new javax.swing.JButton();

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

        btnAddAluno.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/fanex/mazuh/janelas/imgs/icon/mais.gif"))); // NOI18N
        btnAddAluno.setText("Novo Aluno");
        btnAddAluno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddAlunoActionPerformed(evt);
            }
        });

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

        jLabel1.setText("De preferência, mantenha os alunos com IDs (códigos)");

        jLabel2.setText("iguais ao que eles usam para acessar a aula. E mantenha");

        jLabel3.setText("os NOMES e SENHAS diferentes do padrão para melhor ");

        jLabel4.setText("identificação e segurança de todos.");

        btnAddInstrutor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/fanex/mazuh/janelas/imgs/icon/mais.gif"))); // NOI18N
        btnAddInstrutor.setText("Novo INSTRUTOR");
        btnAddInstrutor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddInstrutorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAddAluno)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAddInstrutor)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAnt)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPagina)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnProx))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                        .addComponent(btnVoltar))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel1)
                            .addComponent(jTitulo))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTitulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddAluno)
                    .addComponent(btnAnt)
                    .addComponent(btnProx)
                    .addComponent(jPagina)
                    .addComponent(btnAddInstrutor))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3))
                    .addComponent(btnVoltar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4))
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

    /*
    Pergunta o novo id.
    É criado um novo objeto ALUNO na memória com esse id.
    É tentado persistir esse objeto.
    
    É possível que mensagens de erro sejam emitidas em caixas de diálogo.
    */
    private void btnAddAlunoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddAlunoActionPerformed
        int id = inputValidoID();

        if (id > 0) {
            try {
                // busca hierarquia de aluno
                Hierarquia cargoAluno = (new HierarquiaJpaController(Sessao.getEntityManagerFactory()))
                        .findHierarquia(3);

                if (!cargoAluno.getNome().equalsIgnoreCase("aluno")) {

                    JOptionPane.showMessageDialog(null,
                            "Falha na integridade de hierarquias no banco de dados.\n"
                            + "Se o erro persistir, contate o desenvolvedor!",
                            "ERRO FATAL",
                            JOptionPane.ERROR_MESSAGE);

                    System.exit(1);
                }

                Usuario novoAluno = new Usuario();
                novoAluno.setId(id); // id
                novoAluno.setNome(String.valueOf(id)); // nome 
                novoAluno.setSenha(String.valueOf(id)); // senha
                novoAluno.setIdHierarquia(cargoAluno); // cargo 

                new UsuarioJpaController(Sessao.getEntityManagerFactory()).create(novoAluno);

                JOptionPane.showMessageDialog(null,
                        "USUÁRIO CRIADO"
                        + "\nID: " + id
                        + "\nNome: " + id
                        + "\nSenha: " + id
                        + "\nCargo: " + cargoAluno.getNome().toUpperCase()
                        + "\n\nFaça o novo usuário efetuar o login e definir novos 'nome' e 'senha'."
                );

            } catch (Exception e) {

                JOptionPane.showMessageDialog(null,
                        "Aluno não criado. Verifique: \n"
                                + "- se o código já existe (todos devem ser únicos e válidos);"
                                + "- se a conexão com o banco de dados está ok;",
                        "Ops...",
                        JOptionPane.ERROR_MESSAGE);

            }
        }
        
        
    }//GEN-LAST:event_btnAddAlunoActionPerformed

    /*
    Pergunta o novo id.
    É criado um novo objeto INSTRUTOR na memória com esse id.
    É tentado persistir esse objeto.
    
    É possível que mensagens de erro sejam emitidas em caixas de diálogo.
    */
    private void btnAddInstrutorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddInstrutorActionPerformed
        JOptionPane.showMessageDialog(null, "ATENÇÃO, ADMINISTRADOR!\n\n"
                + "Se você estiver TROCANDO de instrutores, NÃO crie um novo."
                + "\nCancele isto."
                + "\nApenas troque o nome e senha do instrutor antigo!"
                + "\n\nIsso evita poluição do banco de dados, evitando que\n"
                + "haja um monte de instrutores fantasmas para os alunos."
                + "\nEm caso de dúvida, contate o desenvolvedor!");
        
        int id = inputValidoID();

        if (id > 0) {
            try {
                
                // deixa o instrutor com um código diferenciado
                if (id < 9000)
                    id += 9000;
                
                // busca hierarquia de aluno
                Hierarquia cargoInstrutor = (new HierarquiaJpaController(Sessao.getEntityManagerFactory()))
                        .findHierarquia(2);

                if (!cargoInstrutor.getNome().equalsIgnoreCase("instrutor")) {

                    JOptionPane.showMessageDialog(null,
                            "Falha na integridade de hierarquias no banco de dados.\n"
                            + "Se o erro persistir, contate o desenvolvedor!",
                            "ERRO FATAL",
                            JOptionPane.ERROR_MESSAGE);

                    System.exit(1);
                }

                Usuario novoAluno = new Usuario();
                novoAluno.setId(id); // id
                novoAluno.setNome("Fessôr"); // nome 
                novoAluno.setSenha("risos"); // senha
                novoAluno.setIdHierarquia(cargoInstrutor); // cargo 

                new UsuarioJpaController(Sessao.getEntityManagerFactory()).create(novoAluno);

                JOptionPane.showMessageDialog(null,
                        "USUÁRIO CRIADO"
                        + "\nID: " + id + " (código de instrutores são diferenciados)"
                        + "\nNome: Fessôr"
                        + "\nSenha: risos"
                        + "\nCargo: " + cargoInstrutor.getNome().toUpperCase()
                        + "\n\nO novo instrutor DEVE mudar seu nome e senha!"
                );

            } catch (Exception e) {

                JOptionPane.showMessageDialog(null,
                        "Instrutor não criado. Verifique: \n"
                                + "- se o código" + id + "já existe (todos devem ser únicos e válidos);"
                                + "- se a conexão com o banco de dados está ok;",
                        "Ops...",
                        JOptionPane.ERROR_MESSAGE);

            }
        }
        
    }//GEN-LAST:event_btnAddInstrutorActionPerformed

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
    private javax.swing.JButton btnAddAluno;
    private javax.swing.JButton btnAddInstrutor;
    private javax.swing.JButton btnAnt;
    private javax.swing.JButton btnProx;
    private javax.swing.JButton btnVoltar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jPagina;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel jTitulo;
    private javax.swing.JTable tbUsers;
    // End of variables declaration//GEN-END:variables
}
