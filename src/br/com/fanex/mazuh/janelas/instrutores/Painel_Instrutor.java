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
import br.com.fanex.mazuh.edu.Exercicio;
import br.com.fanex.mazuh.janelas.Login;
import br.com.fanex.mazuh.janelas.Usuario_Preferencias;
import br.com.fanex.mazuh.janelas.alunos.Exercicio_Ver;
import br.com.fanex.mazuh.jpa.ExercicioJpaController;
import br.com.fanex.mazuh.jpa.UsuarioJpaController;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author mazuh
 */
public class Painel_Instrutor extends javax.swing.JFrame {
    
    // lista de exercícios a preencher a tabela
    private List<Exercicio> exerciciosPendentes = getExerciciosNaoCorrigidos();
    
    // limitando matriz (tabela)
    private final int TB_ROWS_N = 15; // número de linhas
    private final int TB_COLS_N = 5; // número de colunas
        
    /**
     * Creates new form Painel_Instrutor
     */
    public Painel_Instrutor() {
        initComponents();
        
        this.setTitle("Iris - Painel de Instrutor(a)");
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        
        // welcome!
        jBemVindo.setText("Bem-vindo(a), instrutor(a) " 
                + Sessao.usuario_logado().getNome());
        
        // editando tamanho das colunas.
        tbExercicios.getColumnModel().getColumn(0).setMaxWidth(60); // id
        tbExercicios.getColumnModel().getColumn(3).setMaxWidth(40); // aula
    }

    /*
    Atualiza o usuário da sessão, recupera novos exercícios não corrigidos para
    uma lista, sendo esta usada para ajustar a nova paginação e registros da tabela.
    */
    private void atualizarTudo(){
        Sessao.refresh();
        
        exerciciosPendentes = getExerciciosNaoCorrigidos();
        
        setPaginacaoParaTantosExercicios(exerciciosPendentes.size());
        preencherTbDeExercicios();
        
        jUltimoAtualizar.setText(":: Atualizado por último às " + getHorario() + ".");
    }
    
    /*
    Retorna o horário atual.
    */
    private String getHorario(){
        
        Calendar tempo = Calendar.getInstance();
        
        return tempo.get(Calendar.HOUR) + ":" + tempo.get(Calendar.MINUTE);
        
    }
    
    /*
    Ajusta o limite de páginas de acordo com a quantidade de exercícios
    e seta no jLabel no formato "x/y", onde 'x' é resetado a 1 e 'y' é o limite.
    */
    private void setPaginacaoParaTantosExercicios(int qtdObjetos){
        int paginas;// novo limite de páginas

        boolean isDivisivel = qtdObjetos % 15 == 0; // se qtd é divisível por 15
        
        // divide a quantidade entre as linhas disponíveis
        paginas = (int) (qtdObjetos / 15); // irá receber o menor inteiro do quociente da divisão
        
        paginas += (isDivisivel ? 0 : 1); // se o número não for divisĩvel, +1 pra compensar
        
        
        jPagina.setText("1/" + paginas);
        
    }
    
    /*
    Preenche a tabela de acordo com os dados da lista de exercícios pendentes
    e também de acordo com a página atual.
    */
    private void preencherTbDeExercicios(){
        
        // índice para saber qual a primeira posição da lista a ser acessada
        // definindo primeiro valor do índice baseado na página atual da tabela
        // achando o número da página:
        //          "x/y"   ->   split("/")   ->   [0]="x", [1]="y".
        int indice = (Integer.valueOf(jPagina.getText().split("/")[0]) - 1) * 15;
                                                  // ex: pag = 1 -> indice = 0
                                                  //     pag = 2 -> indice = 15
                                                  //     pag = 3 -> indice = 30 (...)
        
        // preenchendo todas as linhas!
        // o laço irá parar caso chegue ao limite da lista ou da tabela.
        for (int row = 0; (row < exerciciosPendentes.size() && row < TB_ROWS_N); row++){
            
            // objeto com dados da linha atual do laço
            Exercicio foo = exerciciosPendentes.get(indice); // por que usar variável externa ao invés do index do laço?
                                                          // Ver comentários da declaração da int indice.
            
            // definindo dados para as colunas da linha atual do laço
            String[] cols = new String[]{
                String.valueOf(foo.getId()), // num id 
                foo.getIdAluno().getNome(), // nome aluno
                foo.getIdCurso().getNome(), // nome curso
                String.valueOf(foo.getNumAula()), // num aula
                foo.getSituacao() // nome situacao
            };
            
            // preenchendo as colunas com os dados preenchidos anteriormente
            for (int col = 0; col < TB_COLS_N; col++){
                tbExercicios.setValueAt(cols[col], row, col);
            }
            
            indice++; // avança o índice.
            
        }
        
    }
    
    /*
    Retorna todos os exercícios que o instrutor tem considerados "enviados".
    */
    private List<Exercicio> getExerciciosNaoCorrigidos(){
        // todos os exercícios
        List<Exercicio> exercicios = Sessao.usuario_logado().getExercicioList();
        
        // variável para armazenar os exercícios declarados 'enviados' não-corrigidos
        List<Exercicio> nao_corrigidos = new ArrayList<>();
        
        for (int i = 0; i < exercicios.size(); i++){
            // os que tiverem "enviado" ("enviado em xx/xx/xx") são...
            if (exercicios.get(i).getSituacao().contains("Enviado"))
                nao_corrigidos.add(exercicios.get(i)); // ... armazenados aqui
        
        }
        
        return nao_corrigidos; // retorna o que foi armazenado (pode ser null)
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

        jBemVindo = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btnAlunos = new javax.swing.JButton();
        btnBuscarAlunoPorID = new javax.swing.JButton();
        btnAlunoRecuperarSenhaPadrao = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        btnAtualizarExerciciosIncompletos = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        btnExercicios = new javax.swing.JButton();
        btnBuscarExercicioPorID = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        btnPreferencias = new javax.swing.JButton();
        btnContato = new javax.swing.JButton();
        btnCursos = new javax.swing.JButton();
        btnLogoff = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbExercicios = new javax.swing.JTable();
        btnAnt = new javax.swing.JButton();
        btnProx = new javax.swing.JButton();
        jPagina = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jUltimoAtualizar = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                formWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });

        jBemVindo.setBackground(new java.awt.Color(255, 255, 255));
        jBemVindo.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jBemVindo.setForeground(new java.awt.Color(22, 160, 133));
        jBemVindo.setText("Bem-vindo(a), instrutor(a)");
        jBemVindo.setOpaque(true);

        jLabel2.setBackground(new java.awt.Color(39, 174, 96));
        jLabel2.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("ALUNOS DESTA UNIDADE");
        jLabel2.setOpaque(true);

        btnAlunos.setText("Listar todos ou cadastrar");
        btnAlunos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlunosActionPerformed(evt);
            }
        });

        btnBuscarAlunoPorID.setText("Buscar por ID");
        btnBuscarAlunoPorID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarAlunoPorIDActionPerformed(evt);
            }
        });

        btnAlunoRecuperarSenhaPadrao.setText("Recuperar senha padrão");
        btnAlunoRecuperarSenhaPadrao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlunoRecuperarSenhaPadraoActionPerformed(evt);
            }
        });

        jLabel4.setBackground(new java.awt.Color(39, 174, 96));
        jLabel4.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("SEUS EXERCÍCIOS");
        jLabel4.setOpaque(true);

        btnAtualizarExerciciosIncompletos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/fanex/mazuh/janelas/imgs/icon/pasta.gif"))); // NOI18N
        btnAtualizarExerciciosIncompletos.setText("Atualizar");
        btnAtualizarExerciciosIncompletos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtualizarExerciciosIncompletosActionPerformed(evt);
            }
        });

        jLabel5.setBackground(new java.awt.Color(39, 174, 96));
        jLabel5.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("EXERCÍCIOS DE REVISÃO AGUARDANDO SUA CORREÇÃO");
        jLabel5.setOpaque(true);

        btnExercicios.setText("Listar todos");
        btnExercicios.setEnabled(false);
        btnExercicios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExerciciosActionPerformed(evt);
            }
        });

        btnBuscarExercicioPorID.setText("Buscar por ID");
        btnBuscarExercicioPorID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarExercicioPorIDActionPerformed(evt);
            }
        });

        jLabel6.setBackground(new java.awt.Color(39, 174, 96));
        jLabel6.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("SUPORTE E CONFIGURAÇÕES");
        jLabel6.setOpaque(true);

        btnPreferencias.setText("Alterar nome/senha");
        btnPreferencias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPreferenciasActionPerformed(evt);
            }
        });

        btnContato.setText("Fazer contato com desenvolvedor");
        btnContato.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnContatoActionPerformed(evt);
            }
        });

        btnCursos.setText("Gerenciar cursos");
        btnCursos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCursosActionPerformed(evt);
            }
        });

        btnLogoff.setText("Fazer logoff");
        btnLogoff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoffActionPerformed(evt);
            }
        });

        tbExercicios.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        tbExercicios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Aluno", "Curso", "Aula", "Situação"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbExercicios.setColumnSelectionAllowed(true);
        tbExercicios.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tbExercicios);
        tbExercicios.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

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

        jLabel7.setText("Páginas:");

        jUltimoAtualizar.setText(":: Atualizado por último às");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jUltimoAtualizar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel7)
                        .addGap(4, 4, 4)
                        .addComponent(btnAnt)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPagina)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnProx)
                        .addGap(53, 53, 53)
                        .addComponent(btnAtualizarExerciciosIncompletos, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jBemVindo)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(btnAlunoRecuperarSenhaPadrao, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnBuscarAlunoPorID, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnAlunos, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnExercicios, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnBuscarExercicioPorID, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnCursos, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnPreferencias, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnContato, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnLogoff, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 759, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jBemVindo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel4)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAlunos)
                    .addComponent(btnExercicios)
                    .addComponent(btnPreferencias))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBuscarAlunoPorID)
                    .addComponent(btnBuscarExercicioPorID)
                    .addComponent(btnContato))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAlunoRecuperarSenhaPadrao)
                    .addComponent(btnCursos)
                    .addComponent(btnLogoff))
                .addGap(43, 43, 43)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAnt)
                    .addComponent(btnProx)
                    .addComponent(jPagina)
                    .addComponent(jLabel7)
                    .addComponent(btnAtualizarExerciciosIncompletos)
                    .addComponent(jUltimoAtualizar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /*
    Retira o usuário da Sessao, fecha este painel e abre a tela de Login.
    */
    private void btnLogoffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoffActionPerformed
            Sessao.sair();
            this.dispose();
            
            new Login().setVisible(true);
    }//GEN-LAST:event_btnLogoffActionPerformed

    /*
    Pergunta o ID que quer ser buscado e, se for válido e de um aluno, irá
    abrir a tela de preferências dele.
    */
    private void btnBuscarAlunoPorIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarAlunoPorIDActionPerformed
        String msg = null;
        
        try{
            // busca
            UsuarioJpaController usuarioDAO = new UsuarioJpaController(Sessao.getEntityManagerFactory());
            Usuario aluno = usuarioDAO.findUsuario(inputValidoID());
            
            // é aluno?
            if (aluno.getIdHierarquia().getNome().equalsIgnoreCase("aluno"))
                new Usuario_Preferencias(aluno).setVisible(true); // prox tela!
            else // opa
                msg = "Parece que o usuário não é um aluno.\n"
                        + "Acesso NEGADO!\n\n"
                        + "Deixe de cabimento, " + Sessao.usuario_logado().getNome();
            
        } catch(Exception e){
            // erro com DAO. Busca por id <= 0 ou falha no acesso.
            msg = "Não foi possível buscar o ID do aluno.";
        }
        
        if (msg != null){ // se houver msg, que seja exibida ao mundo!
            JOptionPane.showMessageDialog(null, msg, "ERRO", JOptionPane.ERROR_MESSAGE);
        }
        
    }//GEN-LAST:event_btnBuscarAlunoPorIDActionPerformed

    /*
    Pergunta o ID do exercício a ser buscado e, se for válido, irá
    abrir a tela de visualização dele.
    */
    private void btnBuscarExercicioPorIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarExercicioPorIDActionPerformed
        try{
            // busca
            ExercicioJpaController exercicioDAO = new ExercicioJpaController(Sessao.getEntityManagerFactory());
            Exercicio exercicio = exercicioDAO.findExercicio(inputValidoID());
            
            // próx tela
            new Exercicio_Ver(exercicio).setVisible(true);
            
        } catch(Exception e){
            // erro com DAO. Busca por id <= 0 ou falha no acesso.
            JOptionPane.showMessageDialog(null,
                    "Não foi possível buscar o ID do exercício",
                    "ERRO",
                    JOptionPane.ERROR_MESSAGE
            );
            
        }
    }//GEN-LAST:event_btnBuscarExercicioPorIDActionPerformed

    /*
    Recebe o ID de um aluno sua senha retornará para o padrão (== id).
    */
    private void btnAlunoRecuperarSenhaPadraoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlunoRecuperarSenhaPadraoActionPerformed
        String msg = null;
        
        try{
            // busca
            UsuarioJpaController usuarioDAO = new UsuarioJpaController(Sessao.getEntityManagerFactory());
            Usuario aluno = usuarioDAO.findUsuario(inputValidoID());
            
            // é aluno?
            if (aluno.getIdHierarquia().getNome().equalsIgnoreCase("aluno")){
                
                aluno.setSenha(String.valueOf(aluno.getId())); // muda senha 
                usuarioDAO.edit(aluno); // persiste
                
                // msg continua null (sem erro!)
                JOptionPane.showMessageDialog(null, "Alteração efetuada com sucesso!"
                        + "\nAluno: " + aluno.getNome()
                        + "\nNova senha: " + aluno.getSenha());
                
            }else{ // opa, não é um aluno! :o
                msg = "Parece que o usuário não é um aluno.\n"
                        + "Acesso NEGADO!\n\n"
                        + "Deixe de cabimento, " + Sessao.usuario_logado().getNome();
                
            }
        } catch(Exception e){
            // erro com DAO. Busca por id <= 0 ou falha no acesso.
            msg = "Não foi possível buscar o ID do aluno ou houve falha na tentativa"
                    + "de modificar a senha.";
        }
        
        if (msg != null){ // se houver msg, que seja exibida ao mundo!
            JOptionPane.showMessageDialog(null, msg, "ERRO", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnAlunoRecuperarSenhaPadraoActionPerformed

    /*
    Exibe uma caixa de diálogo com dados do desenvolvedor para contato.
    */
    private void btnContatoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnContatoActionPerformed
        JOptionPane.showMessageDialog(null, "\"Mazuh\"\n"
                + "Marcell Guilherme\n"
                + "marcell-mz@hotmail.com\n\n"
                + "Reporte bugs, dê sugestões e peça suporte!");
    }//GEN-LAST:event_btnContatoActionPerformed

    /*
    Abre a tela de preferências usando o usuário logado.
    */
    private void btnPreferenciasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreferenciasActionPerformed
        new Usuario_Preferencias(Sessao.usuario_logado()).setVisible(true);
    }//GEN-LAST:event_btnPreferenciasActionPerformed

    /*
    Sempre que a janela entrar em foco, atualiza a tabela.
    */
    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
        atualizarTudo();
    }//GEN-LAST:event_formWindowGainedFocus

    /*
    Basicamente o botão de atualizar irá repreencher a tabela.
    */
    private void btnAtualizarExerciciosIncompletosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtualizarExerciciosIncompletosActionPerformed
        atualizarTudo();
    }//GEN-LAST:event_btnAtualizarExerciciosIncompletosActionPerformed

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
        
        preencherTbDeExercicios();
    }//GEN-LAST:event_btnProxActionPerformed

    /*
    Retrocede uma página, se puder.
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
        
        preencherTbDeExercicios();
    }//GEN-LAST:event_btnAntActionPerformed

    /*
    Abre a listagem de alunos.
    */
    private void btnAlunosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlunosActionPerformed
        new Usuarios_VerTodos().setVisible(true);
    }//GEN-LAST:event_btnAlunosActionPerformed

    private void btnExerciciosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExerciciosActionPerformed
        
    }//GEN-LAST:event_btnExerciciosActionPerformed

    /*
    Abre o painel CRUD de cursos.
    */
    private void btnCursosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCursosActionPerformed
        new Cursos_CRUD().setVisible(true);    
    }//GEN-LAST:event_btnCursosActionPerformed

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
            java.util.logging.Logger.getLogger(Painel_Instrutor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Painel_Instrutor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Painel_Instrutor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Painel_Instrutor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Painel_Instrutor().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAlunoRecuperarSenhaPadrao;
    private javax.swing.JButton btnAlunos;
    private javax.swing.JButton btnAnt;
    private javax.swing.JButton btnAtualizarExerciciosIncompletos;
    private javax.swing.JButton btnBuscarAlunoPorID;
    private javax.swing.JButton btnBuscarExercicioPorID;
    private javax.swing.JButton btnContato;
    private javax.swing.JButton btnCursos;
    private javax.swing.JButton btnExercicios;
    private javax.swing.JButton btnLogoff;
    private javax.swing.JButton btnPreferencias;
    private javax.swing.JButton btnProx;
    private javax.swing.JLabel jBemVindo;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jPagina;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel jUltimoAtualizar;
    private javax.swing.JTable tbExercicios;
    // End of variables declaration//GEN-END:variables
}
