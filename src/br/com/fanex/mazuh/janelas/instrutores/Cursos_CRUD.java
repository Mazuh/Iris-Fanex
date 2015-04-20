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
import br.com.fanex.mazuh.edu.Curso;
import br.com.fanex.mazuh.jpa.CursoJpaController;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author mazuh
 */
// CRU: create, read e update.
public class Cursos_CRUD extends javax.swing.JFrame { 

    private final int TB_ROWS_N = 15; // número de linhas da tabela
    
    /**
     * Creates new form Cursos_CRU
     */
    public Cursos_CRUD() {
        initComponents();
        
        this.setResizable(false);
        this.setTitle("Cursos");
        this.setLocationRelativeTo(null);
        
        // editando tamanho das colunas.
        tbCursos.getColumnModel().getColumn(0).setMaxWidth(60); // id
        
        // só habilita o botão de deletar se for admin
        btnDelete.setEnabled(Sessao.usuario_logado().getIdHierarquia().getNome().equalsIgnoreCase("admin"));
    }

    /*
    Atualiza a paginação e preenche a tabela de alunos.
    */
    private void atualizar(){
        setPaginacaoParaTantosObjetos(getQtdCursos(), TB_ROWS_N);
        preencherTbDeCursos();
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
    
    /*
    Faz uma pesquisa na lista de cursos baseado na página atual e
    preenche a tabela.
    */
    private void preencherTbDeCursos(){
        // índice para saber qual a primeira posição do bd a ser consultado
        // é definido baseado na página atual da tabela
        // ps: achando o número da página:
        //          "x/y"   ->   split("/")   ->   [0]="x", [1]="y".
        int first = (Integer.valueOf(jPagina.getText().split("/")[0]) - 1) * TB_ROWS_N;
                                                  // Sendo tb_rows_n = 15, ex: 
                                                  //     pag = 1 -> indice = 0
                                                  //     pag = 2 -> indice = 15
                                                  //     pag = 3 -> indice = 30 (...)
        
        // recupera as strings a serem colocadas na tabela baseada na query
        String[][] registros = getRegistros(TB_ROWS_N, first);
        
        // escreve as strings na tabela
        for (int row = 0; (row < registros.length && row < TB_ROWS_N); row++){
            tbCursos.setValueAt(registros[row][0], row, 0);
            tbCursos.setValueAt(registros[row][1], row, 1);
        }
        
    }
    
    /*
    Realiza uma consulta limitada entre os usuários do sistema e retorna
    apenas um vetor de textos referentes a estas consultadas
    */
    private String[][] getRegistros(int max, int first){
        // vars 
        String[][] registros = new String[max][2]; // vai receber as linhas para retornar
        List<Curso> cursos; // armazenar-se-á todos os obj consultados 
        
        // data access object
        CursoJpaController cursoDAO = new CursoJpaController(Sessao.getEntityManagerFactory());
        
        // busca limitada (buscar por todos causaria prejuízo de desempenho)
        cursos = cursoDAO.findCursoEntities(max, first);
        
        for (int i = 0; (i < cursos.size() && i < max); i++){
            String txtId; // a se adicionado no array 
            String txtCurso;
            
            Curso curso = (cursos.get(i)); // acesso mais rápido ao curso do índice 
            
            // com id, nome e quantidade de aulas
            txtCurso = (curso.getNome() + " [" + curso.getQtdExercicios() + " aulas]");
            txtId = String.valueOf(curso.getId());
            
            // adiciona a linha depois de ter os dados filtrados
            try {
                registros[i][0] = txtId;
                registros[i][1] = txtCurso;
                
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
    private int getQtdCursos(){
        return new CursoJpaController(Sessao.getEntityManagerFactory()).getCursoCount();
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
        tbCursos = new javax.swing.JTable();
        btnEdit = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        btnAnt = new javax.swing.JButton();
        btnProx = new javax.swing.JButton();
        jPagina = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                formWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });

        tbCursos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "ID", "Curso"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbCursos.setColumnSelectionAllowed(true);
        tbCursos.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tbCursos);
        tbCursos.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        if (tbCursos.getColumnModel().getColumnCount() > 0) {
            tbCursos.getColumnModel().getColumn(0).setResizable(false);
            tbCursos.getColumnModel().getColumn(1).setResizable(false);
        }

        btnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/fanex/mazuh/janelas/imgs/icon/lapis.gif"))); // NOI18N
        btnEdit.setText("Modificar");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/fanex/mazuh/janelas/imgs/icon/lixo.gif"))); // NOI18N
        btnDelete.setText("Deletar");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/fanex/mazuh/janelas/imgs/icon/mais.gif"))); // NOI18N
        btnAdd.setText("Adicionar novo curso");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/fanex/mazuh/janelas/imgs/icon/janela.gif"))); // NOI18N
        btnBack.setText("Voltar ao paInel");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        jLabel2.setBackground(new java.awt.Color(39, 174, 96));
        jLabel2.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("LISTA DE CURSOS DISPONÍVEIS PARA OS ALUNOS");
        jLabel2.setOpaque(true);

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBack))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnAdd)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAnt)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPagina)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnProx)
                        .addGap(43, 43, 43)
                        .addComponent(btnEdit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDelete)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(btnBack))
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEdit)
                    .addComponent(btnDelete)
                    .addComponent(btnAdd)
                    .addComponent(btnAnt)
                    .addComponent(btnProx)
                    .addComponent(jPagina))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /*
    Atualiza tudo quando ganha foco. A paginação e a tabela resetadas.
    */
    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
        atualizar();
    }//GEN-LAST:event_formWindowGainedFocus

    /*
    Vai para a página anterior, se for possível.
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
        
        preencherTbDeCursos();
    }//GEN-LAST:event_btnAntActionPerformed

    /*
    Vai para a página seguinte, se possível.
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
        
        preencherTbDeCursos();
    }//GEN-LAST:event_btnProxActionPerformed

    /*
    Cria um curso vazio e manda para a janela configuradora de cursos.
    */
    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        Curso cursoVazio = new Curso();
        new Curso_Modificar(cursoVazio).setVisible(true);
    }//GEN-LAST:event_btnAddActionPerformed

    /*
    Fecha o form.
    */
    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnBackActionPerformed

    /*
    Cria um objeto Curso baseado na seleção da tabela e envia ao
    form configurador de cursos.
    */
    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        int linhaSelecionada = tbCursos.getSelectedRow();
        
        if (linhaSelecionada < 0){
            
            // nada selecionado
            JOptionPane.showMessageDialog(null, "Selecione um curso na tabela!",
                    "Ops...",
                    JOptionPane.PLAIN_MESSAGE);
            
        } else{
            
            // ok, pega o id do curso na coluna 0 (a primeira)
            int id = Integer.valueOf((String) tbCursos.getValueAt(linhaSelecionada, 0));
            // busca o id
            try {
                Curso cursoSelecionado = new CursoJpaController(Sessao.getEntityManagerFactory())
                        .findCurso(id);
                // joga pro form responsável (NO DIA EM QUE SAÍ DE CASA... MINHA MÃE ME DISSE... Ok, parei.)
                new Curso_Modificar(cursoSelecionado).setVisible(true);
            } catch (Exception e) {

                JOptionPane.showMessageDialog(null, "OPA! Ocorreu algum erro esquisito!\n\n"
                        + e.getMessage(),
                        "ERRO",
                        JOptionPane.ERROR_MESSAGE);

            }
        }
        
    }//GEN-LAST:event_btnEditActionPerformed

    /*
    Deleta o curso da linha selecionada na tabela.
    */
    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        int linhaSelecionada = tbCursos.getSelectedRow();
        
        if (linhaSelecionada < 0){
            
            // nada selecionado
            JOptionPane.showMessageDialog(null, "Selecione um curso na tabela!",
                    "Ops...",
                    JOptionPane.PLAIN_MESSAGE);
            
        } else{
            
            // ok, pega o id do curso na coluna 0 (a primeira) e o nome na 1 (segunda)
            int id = Integer.valueOf((String) tbCursos.getValueAt(linhaSelecionada, 0));
            String nome = (String) tbCursos.getValueAt(linhaSelecionada, 1);
            
            // busca o id
            try {
                
                int resposta = JOptionPane.showConfirmDialog(null, 
                        "Você tem certeza que deseja deletar o curso (ID: " + id + ") '" + nome + "'?");
                
                if (resposta == JOptionPane.OK_OPTION) {
                    
                    // cria um DAO e usa pra destruir o id tal.
                    new CursoJpaController(Sessao.getEntityManagerFactory())
                            .destroy(id);
                    
                    // não lançou exception, então exiba confirmação
                    JOptionPane.showMessageDialog(null, "Curso " + nome + " deletado.");
                    
                } // else faça nada
                
            } catch (Exception e) {

                JOptionPane.showMessageDialog(null, "OPA! Ocorreu algum erro esquisito!\n\n"
                        + e.getMessage(),
                        "ERRO",
                        JOptionPane.ERROR_MESSAGE);

            }
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

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
            java.util.logging.Logger.getLogger(Cursos_CRUD.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Cursos_CRUD.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Cursos_CRUD.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Cursos_CRUD.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Cursos_CRUD().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnAnt;
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnProx;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jPagina;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbCursos;
    // End of variables declaration//GEN-END:variables
}
