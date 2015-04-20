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

import br.com.fanex.mazuh.acesso.Sessao;
import br.com.fanex.mazuh.acesso.Usuario;
import br.com.fanex.mazuh.jpa.UsuarioJpaController;
import javax.swing.JOptionPane;

/**
 *
 * @author mazuh
 */
public class Usuario_Preferencias extends javax.swing.JFrame {

    private static Usuario user;
    private final UsuarioJpaController usuarioDAO;
    
    /**
     * Creates new form Preferencias
     * @param user
     */
    public Usuario_Preferencias(Usuario user) {
        // atributos 
        Usuario_Preferencias.user = user;
        this.usuarioDAO = new UsuarioJpaController(Sessao.getEntityManagerFactory());
        
        // form
        initComponents();
        
        this.setTitle("Configurações - " + user.getId());
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        
        // preenche jcomponents
        jID.setText(String.valueOf(user.getId()));
        JHierarquia.setText(user.getIdHierarquia().getNome().toUpperCase());
        jNome.setText(user.getNome());
    }
    
    /*
    Tenta fazer as possíveis alterações solicitadas e salvá-las via DAO.
    */
    private boolean persistirAtributoUsuario(){
        // tenta persistir
        try{
            
            usuarioDAO.edit(user);
            return true;
            
        } catch(Exception e){
            return false;
        }
        
    }
    
    /*
    Verifica se o campo obrigatório de senha foi preenchido corretamente.
    
    SEGURANÇA:
    Esta função deve ser chamada numa condicional do método de persistẽncia!
    */
    private boolean acessoAutorizado(){
        return String.valueOf(jSenha.getPassword()).equals(user.getSenha());
    }
    
    /*
    Tenta alterar o nome do usuário local se os campos assim indicarem.
    Retorna true se a alteração tiver ocorrido.
    */
    private boolean execAlteracaoDeNome(){
        String novoNome = jNome.getText();
        
        if (novoNome.equals("")){ // se estiver vazio, restaura padrão
            user.setNome(String.valueOf(user.getId()));
            return true;
        }else if (!novoNome.equals(user.getNome())){ // se estiver diferente do atual, modifique
            user.setNome(novoNome);
            return true; 
        } else{ // se não, deixa quieto. Ele nem solicitou alteração: nem mexeu no campo. 
            return false;
        }
        
    }
    
    /*
    Tenta alterar a senha do usuário local se os campos assim indicarem.
    Retorna true se a alteração tiver ocorrido.
    Pode exibir caixas de diálogo de erro.
    */
    private boolean execAlteracaoDeSenha(){
        String senha1 = String.valueOf(jNovaSenha.getPassword());
        String senha2 = String.valueOf(jNovaSenhaREP.getPassword());
        
        if (!senha1.equals("")) {
            
            // campo não está em branco, modificação requisitada!
            if (senha1.equals(senha2)){
                user.setSenha(senha1);
                return true;
            }else{
                mostrarErro("A nova senha e sua repetição não estão iguais!");
                return false;
            }
            
        } else{
            // nem quis alterar nada.
            return false;
        }
        
    }
    
    /*
    Apenas exibe uma caixa de diálogo de erro. Função auxiliar.
    */
    private void mostrarErro(String msg){
        
        JOptionPane.showMessageDialog(null, 
                msg,
                "ERRO",
                JOptionPane.ERROR_MESSAGE);
        
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
        jLabel5 = new javax.swing.JLabel();
        jID = new javax.swing.JTextField();
        jNome = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jNovaSenha = new javax.swing.JPasswordField();
        jNovaSenhaREP = new javax.swing.JPasswordField();
        jLabel7 = new javax.swing.JLabel();
        jSenha = new javax.swing.JPasswordField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        JHierarquia = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        btnSalvar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(22, 160, 133));
        jLabel1.setText("Configurações de usuário...");
        jLabel1.setOpaque(true);

        jLabel2.setBackground(new java.awt.Color(22, 160, 133));
        jLabel2.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("IDENTIFICAÇÃO:");
        jLabel2.setOpaque(true);

        jLabel3.setBackground(new java.awt.Color(22, 160, 133));
        jLabel3.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("MODIFICAR NOME:");
        jLabel3.setOpaque(true);

        jLabel4.setBackground(new java.awt.Color(22, 160, 133));
        jLabel4.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("ALTERAR SENHA:");
        jLabel4.setOpaque(true);

        jLabel5.setBackground(new java.awt.Color(22, 160, 133));
        jLabel5.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Digite sua senha atual:");
        jLabel5.setOpaque(true);

        jID.setEditable(false);

        jLabel6.setText("Nova senha:");

        jLabel7.setText("Repita:");

        jLabel8.setText("ID:");

        jLabel9.setText("Tipo:");

        JHierarquia.setEditable(false);
        JHierarquia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JHierarquiaActionPerformed(evt);
            }
        });

        jLabel10.setText("Novo nome:");

        jLabel11.setForeground(new java.awt.Color(153, 0, 0));
        jLabel11.setText("(É obrigatório preencher isto)");

        jLabel12.setForeground(new java.awt.Color(102, 102, 102));
        jLabel12.setText("(Deixe em branco se não quiser mudar)");

        jLabel13.setForeground(new java.awt.Color(102, 102, 102));
        jLabel13.setText("(Se não quiser mudar, deixe isto quieto)");

        btnSalvar.setText("Salvar alterações");
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel2)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(jLabel10)
                            .addGap(5, 5, 5)
                            .addComponent(jNome))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(jLabel8)
                            .addGap(3, 3, 3)
                            .addComponent(jID, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel9)
                            .addGap(1, 1, 1)
                            .addComponent(JHierarquia))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel6)
                                .addComponent(jLabel7))
                            .addGap(4, 4, 4)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jNovaSenhaREP)
                                .addComponent(jNovaSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13)
                    .addComponent(jLabel5)
                    .addComponent(jLabel11))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCancelar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnSalvar)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addGap(4, 4, 4)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addComponent(JHierarquia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addGap(2, 2, 2)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jNovaSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jNovaSenhaREP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSalvar)
                    .addComponent(btnCancelar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void JHierarquiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_JHierarquiaActionPerformed
        System.out.println("Deixa esse campo quieto, usuário.\n"
                + "Ele não vai mudar...");
    }//GEN-LAST:event_JHierarquiaActionPerformed

    
    /*
    Se algo tiver sido solicitado para modificação, retorna uma mensagem de confirmação.
    Se houver algum erro no uso do DAO, um erro é exibido.
    
    Independente do que aconteça, este método irá fechar a janela.
    */
    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        final String MSG_NORMAL = "Nenhuma alteração foi realizada.";
        final String MSG_ALTERACAO = "Alteração(ões) realizada(s):\n";
        final String MSG_ERRO = "Erro ao tentar persistir alterações no banco de dados.\n"
                + "Verificar conexão e se o nome já não existe.";
        final String MSG_ERRO_SEGURANCA = "O campo obrigatório de senha atual está incorreto!";
        
        String msg = MSG_NORMAL;
        
        if (acessoAutorizado()) { // Se a senha atual digitada estiver certa, então faça alterações

            // nome
            if (execAlteracaoDeNome()) {

                if (msg.equals(MSG_NORMAL))
                    msg = MSG_ALTERACAO;

                msg += "Nome do usuário;\n";

            }

            // senha
            if (execAlteracaoDeSenha()) {

                if (msg.equals(MSG_NORMAL))
                    msg = MSG_ALTERACAO;

                msg += "Senha de acesso;\n";

            }

            if (!persistirAtributoUsuario()) // tenta persistir. Se não conseguir
                msg = MSG_ERRO; // coloca mensagem de erro.
            
        } else { // (acesso não autorizado)
            
            // Se a senha atual estiver errada, coloca msg de erro.
            msg = MSG_ERRO_SEGURANCA;

        }


        // exibe uma caixa de diálogo cujos elementos irão variar bastante.
        JOptionPane.showMessageDialog(
                
                null,
                
                // corpo da mensagem
                msg,
                
                // título da caixa de diálogo
                msg.equals(MSG_ERRO) || msg.equals(MSG_ERRO_SEGURANCA) ? // se a msg for de erro
                        "ERRO" // o título será de erro
                        : "Configurando preferências...", // se não, título amigável 
                
                // ícone da caixa de diálogo
                msg.equals(MSG_NORMAL) ? // se a msg for normal
                        JOptionPane.PLAIN_MESSAGE // então deixe sem ícone
                        : (msg.equals(MSG_ERRO) || msg.equals(MSG_ERRO_SEGURANCA) ? // se não: ela é de erro?
                                JOptionPane.ERROR_MESSAGE // então exiba um ícone de erro
                                : JOptionPane.INFORMATION_MESSAGE) // se não, use o ícone de informação  
        
        ); // fim do showMessageDialog().
        
        this.dispose(); // tchau!
        
    }//GEN-LAST:event_btnSalvarActionPerformed

    /*
    Informa que o processo foi cancelado e fecha a janela.
    */
    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        JOptionPane.showMessageDialog(null, "Nenhuma modificação foi feita.",
                "Cancelado", JOptionPane.PLAIN_MESSAGE);
        
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

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
            java.util.logging.Logger.getLogger(Usuario_Preferencias.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Usuario_Preferencias.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Usuario_Preferencias.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Usuario_Preferencias.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Usuario_Preferencias(Usuario_Preferencias.user).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField JHierarquia;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JTextField jID;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTextField jNome;
    private javax.swing.JPasswordField jNovaSenha;
    private javax.swing.JPasswordField jNovaSenhaREP;
    private javax.swing.JPasswordField jSenha;
    // End of variables declaration//GEN-END:variables
}
