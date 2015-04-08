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
package br.com.fanex.mazuh.janelas.alunos;

import br.com.fanex.mazuh.acesso.Sessao;
import br.com.fanex.mazuh.edu.Exercicio;
import br.com.fanex.mazuh.jpa.ExercicioJpaController;
import javax.swing.JOptionPane;

/**
 *
 * @author mazuh
 */
public class Exercicio_Responder extends javax.swing.JFrame {

    // exercício que pode estar sem respostas (novo) ou não (a continuar)
    private static Exercicio exercicio;
    private final ExercicioJpaController exercicioDAO; // DAO usado frequentemente
    // baseado nisso, no futuro será definido se será usado o metodo de alteração ou de criação
    private static int modo;
    
    // códigos que o atributo 'modo' pode receber.
    public final static int MODO_INICIAR = 1;
    public final static int MODO_CONTINUAR = 2;
    
    /**
     * Creates new form Exercicio_Responder
     * @param modo
     * @param exercicio
     */
    
    public Exercicio_Responder(int modo, Exercicio exercicio) {
        Exercicio_Responder.modo = modo;
        Exercicio_Responder.exercicio = exercicio;
        this.exercicioDAO = new ExercicioJpaController(Sessao.getEntityManagerFactory());
        
        initComponents();
        
        // "converte" o modo int em modo ascii...
        String modoEmASCII = modo == MODO_INICIAR ? "NOVO"
                : modo == MODO_CONTINUAR ? "CONTINUAÇÃO" : "...";
        
        // ... e coloca isso na barra de título.
        this.setTitle("[" + modoEmASCII  + "] "
                + "Exercício de Revisão de " + exercicio.getIdAluno().getNome());
        
        // outras propriedades
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        
        // coloca um subtítulo bem bonito com as informações do exercício
        jSubtitulo.setText(exercicio.toString());
        
        // se for o caso, preenche com respostas encontradas.
        if (modo == MODO_CONTINUAR)
            txtRespostas.setText(exercicio.getRespostas());
        
    }
    
    /*
    Se houver um exercício na persistência, ele será destruído.
    */
    private boolean destruirExercicio(){
        try{
            
            exercicioDAO.destroy(exercicio.getId());
            this.dispose(); // não faz sentido estar numa janela morta...
            return true;
            
        }catch(Exception e){
            mostrarErro("Não foi possível deletar este exercício.\n"
                    + "Peça orientação a seu instrutor quanto a isso.");
            return false;
        }
    }
    
    /*
    Irá persistir o objeto lógico Exercicio no banco de dados.
    Isso pode ocorrer por edição ou por criação, algo que o método irá escolher.
    */
    private boolean persistirExercicio(){
        try {
        
            // escolhe qual método usar para persistir
            if (modo == MODO_INICIAR) {
                exercicioDAO.create(exercicio);
                JOptionPane.showConfirmDialog(null, "Exercício criado com sucesso!");
                return true;
                
            } else if (modo == MODO_CONTINUAR) {
                exercicioDAO.edit(exercicio);
                JOptionPane.showMessageDialog(null, "Exercício editado com sucesso!");
                return true;
                
            } else{ // se não foi possível escolher nenhum método
                mostrarErro("Parâmetro desconhecido na tentativa de persistência.");
                return false;
            }

        } catch (Exception e) {
            // Métodos falharam.
            mostrarErro("Falha na tentativa de uso dos DAO na persistência.");
            return false;
        }
    }
    
    /*
    Método auxiliar. Apenas mite um erro na tela.
    */  
    private void mostrarErro(String mensagem){
        JOptionPane.showMessageDialog(null, mensagem, "ERRO", JOptionPane.ERROR_MESSAGE);
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
        jSubtitulo = new javax.swing.JLabel();
        txtRespostas = new java.awt.TextArea();
        btnEnviar = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        btnDeletar = new javax.swing.JButton();
        btnVoltar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(22, 160, 133));
        jLabel1.setText("Respondendo Exercício de Revisão...");
        jLabel1.setOpaque(true);

        jSubtitulo.setBackground(new java.awt.Color(39, 174, 96));
        jSubtitulo.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jSubtitulo.setForeground(new java.awt.Color(255, 255, 255));
        jSubtitulo.setText("NULL");
        jSubtitulo.setOpaque(true);

        btnEnviar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/fanex/mazuh/janelas/imgs/icon/ok.gif"))); // NOI18N
        btnEnviar.setText("Terminei o exercício!");

        btnSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/fanex/mazuh/janelas/imgs/icon/salvar.gif"))); // NOI18N
        btnSalvar.setText("Salvar exercício para terminar depois");
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        btnDeletar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/fanex/mazuh/janelas/imgs/icon/lixo.gif"))); // NOI18N
        btnDeletar.setText("Destruir este exercício");
        btnDeletar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeletarActionPerformed(evt);
            }
        });

        btnVoltar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/fanex/mazuh/janelas/imgs/icon/usuario.gif"))); // NOI18N
        btnVoltar.setText("Voltar para o painel");
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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 76, Short.MAX_VALUE)
                        .addComponent(btnVoltar))
                    .addComponent(jSubtitulo)
                    .addComponent(txtRespostas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnDeletar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSalvar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEnviar)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(btnVoltar))
                .addGap(11, 11, 11)
                .addComponent(jSubtitulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtRespostas, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEnviar)
                    .addComponent(btnDeletar)
                    .addComponent(btnSalvar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /*
    Pergunta se o usuário quer salvar as alterações.
    Volta para o painel (a exceção do clique no botão de cancelar).
    */
    private void btnVoltarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVoltarActionPerformed
        int resposta = JOptionPane.showConfirmDialog(null,
                "Parece que você está tentando sair desta tela.\n"
                        + "Se não salvar antes disso, você vai perder as mudanças feitas!\n"
                        + "Deseja salvar?");
        
        if (resposta == JOptionPane.YES_OPTION){
            // salva e sai
            exercicio.setRespostas(txtRespostas.getText());
            persistirExercicio();
            this.dispose();
        
        } else if (resposta == JOptionPane.NO_OPTION){
            // sṕ sai.
            this.dispose();
        
        } // else cancelar: faz nada.
        
    }//GEN-LAST:event_btnVoltarActionPerformed

    /*
    Com a devida permissão do usuário, o exercício será deletado do bd!
    */
    private void btnDeletarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeletarActionPerformed
        int resposta = JOptionPane.showConfirmDialog(null,
                "Deseja realmente deletar este exercício de revisão?\n"
                + "Ele será destruido para sempre (e 'sempre' é um tempo beeem longo)!");
        
        if (resposta == JOptionPane.YES_OPTION){
            // destrua!!!
            boolean deletou = destruirExercicio();
            
            if (deletou){ // Msg bonitinha.
                JOptionPane.showMessageDialog(null,
                        "Exercício destruído com sucesso!",
                        "THIS IS SPARTA!!!",
                        JOptionPane.PLAIN_MESSAGE);
            }
        } else{
            System.out.println("I find your lack of faith is disturbing...");
        }
    }//GEN-LAST:event_btnDeletarActionPerformed

    /*
    Salva o progresso e volta ao painel.
    */
    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        // persiste
        exercicio.setRespostas(txtRespostas.getText());
        boolean salvou = persistirExercicio();
        
        // avisa que deu tudo certo e sai!
        if (salvou) {
            
            JOptionPane.showMessageDialog(null,
                    "Exercício salvo com sucesso.\nClique em 'Continuar' em seu painel quando quiser voltar e finalizá-lo.",
                    "TUDO OK!",
                    JOptionPane.PLAIN_MESSAGE);
            
            this.dispose(); // tchau!
        }
        
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
            java.util.logging.Logger.getLogger(Exercicio_Responder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Exercicio_Responder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Exercicio_Responder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Exercicio_Responder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Exercicio_Responder(Exercicio_Responder.modo, Exercicio_Responder.exercicio).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDeletar;
    private javax.swing.JButton btnEnviar;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JButton btnVoltar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jSubtitulo;
    private java.awt.TextArea txtRespostas;
    // End of variables declaration//GEN-END:variables
}
