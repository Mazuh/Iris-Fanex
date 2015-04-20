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
import br.com.fanex.mazuh.jpa.exceptions.NonexistentEntityException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author mazuh
 */
public class Curso_Modificar extends javax.swing.JFrame {

    private static Curso curso;
    private final CursoJpaController cursoDAO;
    
    /**
     * Creates new form Curso_Modificar
     * @param curso
     */
    public Curso_Modificar(Curso curso) {
        // vars
        Curso_Modificar.curso = curso;
        this.cursoDAO = new CursoJpaController(Sessao.getEntityManagerFactory());
        
        // config de janela
        initComponents();
        
        this.setTitle("Configurando Curso");
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        
        // preenche valores, caso o curso já tenha um id.
        if (curso.getId() != null){
            jID.setText(String.valueOf(curso.getId() < 0 ? "" : curso.getId()));
            jNome.setText(curso.getNome());
            jQtdAulas.setValue(curso.getQtdExercicios());
            jGabarito.setText(curso.getUrlGabarito());
            if (curso.getUrlGabaritoAlt() != null)
               jGabaritoAlt.setText(curso.getUrlGabaritoAlt());
        } // else: criando curso novo. 
        
    }
    
    /*
    Configura o objeto lógico do curso baseado nos campos do form.
    
    Se todos os valores foram validados corretamente, retorna true.
    Se não, retorna false e exibe uma mensagem de erro informando quais campos
    devem ser corrigidos.
    */
    private boolean configurarObjLocalCursoValido() {
        // preparação de vars para erros.
        final String MSG_PADRAO = "Checar seguintes campos inválidos:";
        String msg = MSG_PADRAO;
        
        // validação de campos
        
        // id
        int id;
        try{
            id = Integer.valueOf(jID.getText());
        }catch(NumberFormatException e){ // impossível converter "" pra int
            id = -1;
            // id não precisa de validação, é tudo feito automaticamente pelo postgres
        }
        
        // nome
        String nome = jNome.getText();
        if (nome.equals(""))
            msg += "\nNome do curso";
        if (nome.length() > 50 || nome.length() < 3)
            msg += "\nQuantidade de caractéres do nome (mínimo: 3, máximo: 50)";
        
        // qtd de aulas
        int qtdAulas;
        try{
            qtdAulas = (Integer) jQtdAulas.getValue();
            
            if (qtdAulas < 1)
                msg += "\nQuantidade de aulas (mínimo: 1)";
            else if (qtdAulas > 100)
                msg += "\nQuantidade de aulas (máximo: 100)";
            
        } catch(Exception e){
            qtdAulas = 0;
            msg += "\nQuantidade de aulas";
        }
        
                
        // gabarito principal ('NOT NULL' no banco de dados mas permite string "null")
        String gabarito = jGabarito.getText();
        if (gabarito.equals(""))
            msg += "\nURL do gabarito (clique no botão dúvida e leia-o até o fim!)";
        if (gabarito.length() > 200 || gabarito.length() < 10)
            msg += "\nQuantidade de caractéres do URL do gabarito (mínimo: 10, máximo: 200)";
        
        // gabarito alternativo, não precisa de validação.
        String gabaritoAlt = jGabarito.getText();
        if (gabaritoAlt.length() > 200)
            msg += "\nQuantidade de caractéres do URL do gabarito alternativo (sem mínimo, máximo: 200)";
        
        // define o que vai ser retornado.
        if (msg.equals(MSG_PADRAO)){ // não precisa exibir nada, deu certo.
            setters(id, nome, qtdAulas, gabarito, gabaritoAlt);
            return true;
            
        }else{
            // exiba a msg como erro.
            JOptionPane.showMessageDialog(null,
                    msg,
                    "'Easy, partner...'",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
    }
    
    /*
    Seta todos os atributos do objeto local Curso.
    */
    private void setters(int id, String nome, int qtdAulas, String gabarito, String gabaritoAlt){
        curso.setId(id);
        curso.setNome(nome);
        curso.setQtdExercicios(qtdAulas);
        curso.setUrlGabarito(gabarito);
        curso.setUrlGabaritoAlt(gabaritoAlt);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jID = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jNome = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jQtdAulas = new javax.swing.JSpinner();
        jLabel7 = new javax.swing.JLabel();
        jLabel = new javax.swing.JLabel();
        jLabel0 = new javax.swing.JLabel();
        btnSalvar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jGabarito = new javax.swing.JTextField();
        jGabaritoAlt = new javax.swing.JTextField();
        btnExplicarLinkGabarito = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(22, 160, 133));
        jLabel1.setText("Configuração de curso...");
        jLabel1.setOpaque(true);

        jLabel2.setBackground(new java.awt.Color(22, 160, 133));
        jLabel2.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("IDENTIFICAÇÃO:");
        jLabel2.setOpaque(true);

        jID.setEditable(false);
        jID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jIDActionPerformed(evt);
            }
        });

        jLabel3.setText("ID:");

        jLabel4.setText("Nome:");

        jLabel5.setBackground(new java.awt.Color(22, 160, 133));
        jLabel5.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("CARACTERÍSTICAS:");
        jLabel5.setOpaque(true);

        jLabel6.setText("Quantidade de aulas:");

        jLabel7.setBackground(new java.awt.Color(22, 160, 133));
        jLabel7.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("GABARITOS:");
        jLabel7.setOpaque(true);

        jLabel.setText("Cole aqui o link Google Docs para o .pdf do gabarito:");

        jLabel0.setText("URL alternativo (opcional, deixe em branco se não tiver):");

        btnSalvar.setText("Salvar");
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        jLabel10.setForeground(new java.awt.Color(153, 153, 153));
        jLabel10.setText("(Curso novo? ID em branco! )");

        btnExplicarLinkGabarito.setText("?");
        btnExplicarLinkGabarito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExplicarLinkGabaritoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jGabarito)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3))
                        .addGap(3, 3, 3)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jNome)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jID, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(2, 2, 2)
                                .addComponent(jLabel10)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addComponent(jGabaritoAlt)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jQtdAulas, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel7)
                            .addComponent(jLabel0)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnExplicarLinkGabarito)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnCancelar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSalvar)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jID, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jQtdAulas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel)
                    .addComponent(btnExplicarLinkGabarito))
                .addGap(4, 4, 4)
                .addComponent(jGabarito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel0)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jGabaritoAlt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSalvar)
                    .addComponent(btnCancelar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jIDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jIDActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        JOptionPane.showMessageDialog(null, "Nenhuma alteração foi feita.",
                "CANCELADO", JOptionPane.PLAIN_MESSAGE);
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    /*
    Abre uma caixa de diálogo explicando sobre o que colocar no campo de gabarito.
    */
    private void btnExplicarLinkGabaritoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExplicarLinkGabaritoActionPerformed
        JOptionPane.showMessageDialog(null,
                "Link Google Docs para .pdf do gabarito.\n"
                        + "Não sabe o que isso quer dizer?\n"
                        + "Permita-me explicar de forma superficial.\n"
                        + "\n"
                        + "- Use uma conta Google (a mesma do Gmail) para\n"
                        + "acessar o serviço Docs (ou Google Documentos).\n"
                        + "- Faça o upload do gabarito e abra-o usando o Docs.\n"
                        + "- Use o menu superior para compartilhar o arquivo.\n"
                        + "- Na caixa de diálogo que surgir, clique em \"receber\n"
                        + "link compartilhável\" e um link irá surgir pra\n"
                        + "você copiar e colá-lo aqui no sistema Íris.\n"
                        + "- Conclua o processo do Docs.\n"
                        + "\n"
                        + "É recomendável que você faça o teste do link colando-o\n"
                        + "na barra de url do seu navegador de Internet.\n"
                        + "\n"
                        + "O processo é simples e não demora nem 5min, mas em caso\n"
                        + "de dúvidas não se acanhe e peça ajuda ao desenvolvedor\n"
                        + "do Íris. As informações para contato estão nas\n"
                        + "configurações do seu painel de Instrutor ou Administrador.\n"
                        + "\n"
                        + "Caso precise emergencialmente criar este curso, digite\n"
                        + "a palavra \"null\" (sem aspas) no campo de gabarito\n"
                        + "que o curso será criado sem problemas.\n"
                        + "Infelizmente, fazendo isso você não poderá abrir o\n"
                        + "gabarito rapidamente quando for corrigir os exercícios\n"
                        + "de seus alunos.",
                "Link do Google Docs? Hã?",
                JOptionPane.QUESTION_MESSAGE);
    }//GEN-LAST:event_btnExplicarLinkGabaritoActionPerformed

    /*
    Tenta configurar o objeto local baseado nos campos jcomponents.
    Se tiver dado tudo certo, ocorre uma tentativa de persistência.
    
    Qualquer erro e caixas de diálogo serão exibidas ao usuário.
    */
    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        if (configurarObjLocalCursoValido()){ // validação na tentativa de config. deu true?
            
            String msg = ""; // var para armazenar possíveis erros com o DAO
            
            if (curso.getId() == null || curso.getId() < 0){
                // curso novo, criar!
                try{
                    
                    cursoDAO.create(curso);
                    this.dispose();
                    JOptionPane.showMessageDialog(null, "Curso criado com sucesso.");
                    
                } catch(Exception e){
                    msg += "\nCriação de curso falhou.";
                }
                
            }else{
                // curso já existente, editar!
                try {
                    
                    cursoDAO.edit(curso);
                    this.dispose();
                    JOptionPane.showMessageDialog(null, "Curso modificado com sucesso.");
                    
                } catch (NonexistentEntityException e) {
                    msg += "\nEdição de curso falhou."
                            + "\nEntidade de persistência não encontrada.";
                } catch (Exception e) {
                    msg += "\nEdição de curso falhou.";
                }
                
            }
            
            // se tiver algum erro a ser exibido, que seja exibido.
            if (!msg.equals("")){
                JOptionPane.showMessageDialog(null, msg, "ERRO", JOptionPane.ERROR_MESSAGE);
            }
            
        } // se a validação deu errado, ela própria irá emitir seus erros, este evento não fará mais nada
        
    }//GEN-LAST:event_btnSalvarActionPerformed

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
            java.util.logging.Logger.getLogger(Curso_Modificar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Curso_Modificar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Curso_Modificar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Curso_Modificar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Curso_Modificar(curso).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnExplicarLinkGabarito;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JTextField jGabarito;
    private javax.swing.JTextField jGabaritoAlt;
    private javax.swing.JTextField jID;
    private javax.swing.JLabel jLabel;
    private javax.swing.JLabel jLabel0;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JTextField jNome;
    private javax.swing.JSpinner jQtdAulas;
    // End of variables declaration//GEN-END:variables
}
