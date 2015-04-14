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
package br.com.fanex.mazuh.janelas;

import br.com.fanex.mazuh.janelas.alunos.Painel_Estudante;
import br.com.fanex.mazuh.acesso.Sessao;
import br.com.fanex.mazuh.janelas.instrutores.Painel_Instrutor;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;

/**
 *
 * @author mazuh
 */
public class Login extends javax.swing.JFrame {

    /**
     * Creates new form Login
     */
    public Login() {
        initComponents();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setTitle("Iris - Login");
    }
    
    /*
    apagarCampos(), pasme, apaga campos!!!! :O
    */
    private void apagarCampos(){
        jCodigo.setText("");
        jSenha.setText("");
    }
    
    /*
    Lê os campos, verifica-os, valida-os e, se tiver dado tudo certo,
    inicia uma sessão de usuário no programa, se não erros serão emitidos na tela!
    */
    private void tentarLogar(){
        try{
            int codigo = Integer.valueOf(jCodigo.getText());
            String senha = String.valueOf(jSenha.getPassword());
        
            if (Sessao.logar(codigo, senha)){
                // Se a sessão logar, ou seja, se logar() retornou positivo
                // ... Logou! =D
                
                // Recupera objeto global da sessão,
                // verifica sua hierarquia,
                // abre a janela correspondente.
                
                switch ((Sessao.usuario_logado().getIdHierarquia()).getNome()){
                    
                    case "aluno":
                        new Painel_Estudante().setVisible(true);
                        break;
                    
                    case "instrutor":
                        new Painel_Instrutor().setVisible(true);
                        break;
                    
                    case "admin":
                        // FALTA IMPLEMENTAR
                        break;
                        
                    default:
                        
                        JOptionPane.showMessageDialog(null,
                                "Erro no parâmetro de hierarquia.\n"
                                    + "Comunique ao desenvolvedor!",
                                "ERRO FATAL",
                                JOptionPane.ERROR_MESSAGE);
                        
                        System.exit(1); // Não pode avançar! A mensagem bonitinha é só pra usuários autorizados!
                        break;
                        
                }
                
                // Fecha a tela de login
                this.dispose();
                
            } else{
                // Usuário ou senha não encontrados no banco.
                JOptionPane.showMessageDialog(null,
                        "Senha ou usuário incorretos!\nPeça ajuda ao seu instrutor!",
                        "SEGURANÇA",
                        JOptionPane.ERROR_MESSAGE);
                
                apagarCampos();
            
            }
            
        }catch(NumberFormatException e){
            // Talvez o problema esteja entre a cadeira e o usuário.
            // Apenas códigos numéricos e senhas alfanuméricas válidas!
            JOptionPane.showMessageDialog(null,
                    "Valores inválidos\nPeça ajuda ao seu instrutor!", 
                    "ERRO", 
                    JOptionPane.ERROR_MESSAGE);
            
            apagarCampos();
            
        }catch(Exception e){
            // OK. Talvez a falha seja do programador mesmo. '-'
            JOptionPane.showMessageDialog(null,
                    "Opa. Algo estranho aconteceu.\nFale com seu instrutor!", 
                    "ERRO", 
                    JOptionPane.ERROR_MESSAGE);
            
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

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jCodigo = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        btnLogar = new javax.swing.JButton();
        btnSobre = new javax.swing.JButton();
        jSenha = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                formFocusGained(evt);
            }
        });

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(22, 160, 133));
        jLabel1.setText("Sistema Iris");
        jLabel1.setOpaque(true);

        jLabel2.setBackground(new java.awt.Color(39, 174, 96));
        jLabel2.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Acesse agora!");
        jLabel2.setOpaque(true);

        jLabel3.setText("Código:");

        jLabel4.setText("Senha:");

        jCodigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jCodigoKeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jCodigoKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jCodigoKeyReleased(evt);
            }
        });

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/fanex/mazuh/janelas/imgs/bill_ensinando.jpeg"))); // NOI18N

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/fanex/mazuh/janelas/imgs/fanex_logo_transparente_menor.png"))); // NOI18N

        btnLogar.setText("Entrar");
        btnLogar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogarActionPerformed(evt);
            }
        });

        btnSobre.setText("?");
        btnSobre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSobreActionPerformed(evt);
            }
        });

        jSenha.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jSenhaKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jSenhaKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel5)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(btnSobre)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btnLogar))
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel3)
                                .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jCodigo, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
                                .addComponent(jSenha)))))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnLogar)
                            .addComponent(btnSobre))))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSobreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSobreActionPerformed
        // clique no botão de interrogação irá emitir uma caixa de diálogo explicando o sistema. 
        
        String sobre = "Sistema Iris: Copyright (c) 2015 Marcell Guilherme (\"Mazuh\")\n"
                + "E-mail: marcell-mz@hotmail.com\n"
                + "\n"
                + "Este programa é licenciado sob a MIT License (MIT).\n"
                + "Cópia da Licença: http://opensource.org/licenses/MIT\n"
                + "Código-Fonte: https://github.com/Mazuh/Projeto-Iris---Fanex\n"
                + "\n"
                + "Sistema projetado para alunos da escola profissionalizante\n"
                + "Fanex usarem a fim de enviarem atividades a seus instrutores,\n"
                + "recebê-las corrigidas, visualizar as feitas, pedir ajuda a eles\n"
                + "e outras coisas. Os instrutores teríam acesso a todas as informações\n"
                + "dos alunos e inclusive poderíam gerar relatórios e correções.";
        
        JOptionPane.showMessageDialog(null, sobre, "SOBRE - Sistema Iris", JOptionPane.QUESTION_MESSAGE);
    }//GEN-LAST:event_btnSobreActionPerformed

    private void btnLogarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogarActionPerformed
        // clique no botão de logar tenta logar (óbvio!)
        tentarLogar();
    }//GEN-LAST:event_btnLogarActionPerformed

    private void jCodigoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCodigoKeyReleased
        
    }//GEN-LAST:event_jCodigoKeyReleased

    private void jSenhaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jSenhaKeyReleased
        
    }//GEN-LAST:event_jSenhaKeyReleased

    private void formFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_formFocusGained
        // Quando a janela entra em foco, o ponteiro de digitação vai pro 1o campo
        jCodigo.requestFocus();
    }//GEN-LAST:event_formFocusGained

    private void jCodigoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCodigoKeyPressed
        // Se apertar enter, pulará pra o campo de senha (funcionará como um Tab)
        if (evt.getKeyCode() == KeyEvent.VK_ENTER)
            jSenha.requestFocus();
    }//GEN-LAST:event_jCodigoKeyPressed

    private void jCodigoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jCodigoKeyTyped

    }//GEN-LAST:event_jCodigoKeyTyped

    private void jSenhaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jSenhaKeyPressed
        // Se o campo marcado for o de senha ao apertar enter, tenta logar!
        if (evt.getKeyCode() == KeyEvent.VK_ENTER)
            tentarLogar();
    }//GEN-LAST:event_jSenhaKeyPressed

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
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLogar;
    private javax.swing.JButton btnSobre;
    private javax.swing.JTextField jCodigo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPasswordField jSenha;
    // End of variables declaration//GEN-END:variables
}
