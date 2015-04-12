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
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author mazuh
 */
public class Exercicio_Escolha extends javax.swing.JFrame {

    private static int modo; // define o que será feita com a escolha...
    
    public final static int MODO_VER = 1; // ... se é pra visualizar o escolhido...
    public final static int MODO_CONTINUAR = 2; // ... ou prosseguir uma resolução
    
    /**
     * Creates new form Exercicio_Responder_Escolha
     * @param objetivo
    */
    public Exercicio_Escolha(int objetivo){
        Exercicio_Escolha.modo = objetivo;
        
        initComponents();
        
        this.setTitle("Seleção de Exercício");
        this.setResizable(false);
        this.setLocationRelativeTo(null); // centraliza
        this.getContentPane().setBackground(new Color(39,174,96)); // cor de fundo verde
    }

    /*
    Preenche a combobox apenas com exercícios pertinentes à razão de existência desta janela.
    */
    private void preencherTodasComboBox() {
        
        jExercicios.removeAllItems();
        jExercicios.addItem("---");
        
        switch (Exercicio_Escolha.modo) {
            
            // escolha de disponíveis para read
            case Exercicio_Escolha.MODO_VER:
                
                if (temExercicios()) {
                    
                    btnOK.setText("Ver!");
                    preencherCbComTodosExercicios();
                    
                } else{ // não tem exercícios
                    die("Nenhum exercício seu foi encontrado!\n"
                            + "Se quiser iniciar um novo exercício, use o botão "
                            + "'Novo exercício' do seu painel!",
                            "Opa!");
                }
                
                break;

                
            // escolha de disponíveis para update
            case Exercicio_Escolha.MODO_CONTINUAR:
                
                if (temExerciciosIncompletos()) {
                    
                    btnOK.setText("Continuar!");
                    preencherCbComExerciciosIncompletos();
                    
                } else{ // não tem exercícios incompletos
                    die("Parece que você não tem exercícios não enviados!\n"
                            + "Se quiser iniciar um novo exercício, use o botão "
                            + "'Novo exercício' do seu painel!",
                            "Opa!");
                }
                
                break;

                
            // hã?
            default:
                die("Erro no parâmetro de escolha.\n"
                        + "Reporte os detalhes deste bug ao desenvolvedor.", "ERRO");
                break;
                
        }

    }
    
    /*
    Se os campos estiverem ok, avançada para o próximo form de resposta enviando
    para lá o exercício incompleto.
    */
    private void irParaFormDeResposta(){
        if (camposEstaoOk()){
            
            this.dispose(); // bye!
            
            new Exercicio_Responder(Exercicio_Responder.MODO_CONTINUAR, 
                    (Exercicio) jExercicios.getSelectedItem()) // new
                    .setVisible(true); // setvisible
            
        } else{ 
            // erro
            JOptionPane.showMessageDialog(null, 
                    "Selecione algum exercício.", 
                    "Calma aí, jovem!", 
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /*
    Se os campos estiverem ok, avançada para uma tela de visualização.
    */
    private void irParaFormDeVisualizacao(){
        
    }
    
    /*
    Verifica se a combobox está selecionada com algo aceitável para clicar em OK.
    Se sim, retorna true.
    */
    private boolean camposEstaoOk(){
        // null pointer
        if (jExercicios.getSelectedItem() == null)
            return false;
        
        // não selecionado
        if (jExercicios.getSelectedItem().toString().equals("---"))
            return false;
        
        // ??? (tudo é possível rs)
        if (!(jExercicios.getSelectedItem() instanceof Exercicio))
            return false;
        
        return true; // se nada deu errado, então retorna true.
    }
    
    /*
    Um laço percorre a lista de exercícios preenchendo a combobox.
    Não possui formas de proteção contra null pointer.
    */
    private void preencherCbComTodosExercicios(){
        
        for (int i = 0; i < getExercicios().size(); i++)
            jExercicios.addItem(getExercicios().get(i));
        
    }
    
    /*
    Retorna exercícios que o usuário aluno logado possui.
    */
    private List<Exercicio> getExercicios(){
        return Sessao.usuario_logado().getExercicioList1();
    }
    
    /*
    Um laço percorre a lista de exercícios não enviados preenchendo a combobox.
    Não possui formas de proteção contra null pointer.
    */
    private void preencherCbComExerciciosIncompletos(){
        
        for (int i = 0; i < getExerciciosIncompletos().size(); i++)
            jExercicios.addItem(getExerciciosIncompletos().get(i));
        
    }
    
    /*
    Retorna exercícios marcados como incompletos e disponíveis para update
    do usuário aluno logado.
    */
    private List<Exercicio> getExerciciosIncompletos(){
        // evita um possível futuro laço inútil nas linhas seguintes 
        if (!temExercicios())
            return null;
        
        // variável para armazenar os exercícios declarados 'não enviados'
        List <Exercicio> exerciciosIncompletos = new ArrayList<Exercicio>();
        // busca os tais exercícios 'não enviados'
        for (int i = 0; i < getExercicios().size(); i++){
            if (getExercicios().get(i).getSituacao().contains("Não"))
                exerciciosIncompletos.add(getExercicios().get(i));
        }
        
        return exerciciosIncompletos; // retorna o que foi armazenado (pode ser null)
    }
    
    /*
    Retorna true se a lista de exercícios 'não enviado's do usuário logado
    existir e conter algo.
    */
    private boolean temExerciciosIncompletos(){
        return (getExerciciosIncompletos() != null 
                && getExerciciosIncompletos().size() > 0);
    }
    
    /*
    Retorna true se a lista de exercícios do usuário logado existir e conter algo.
    */
    private boolean temExercicios(){
        return (getExercicios() != null && getExercicios().size() > 0);
    }
    
    /*
    Força a saída do usuário desta caixa de diaĺogo.
    Antes disso, é emitida uma mensagem na tela.
    */
    private void die(String msg, String titulo){
        this.dispose();
        JOptionPane.showMessageDialog(null, msg, titulo, JOptionPane.ERROR_MESSAGE);
        
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jExercicios = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        btnOK = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                formWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });
        addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                formFocusGained(evt);
            }
        });

        jExercicios.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "---" }));

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Escolha o abaixo o Exercício de Revisão que você quer pegar:");

        btnOK.setText("OK");
        btnOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOKActionPerformed(evt);
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
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnCancelar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnOK))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 236, Short.MAX_VALUE))
                    .addComponent(jExercicios, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jExercicios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnOK)
                    .addComponent(btnCancelar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /*
    Botão de cancelar quando ativado irá fechar esta janela.
    */
    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    /*
    Botão de continuar irá abrir o formulário de exercício já passando o obj escolhido por argumento.
    */
    private void btnOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOKActionPerformed
        irParaFormDeResposta();
    }//GEN-LAST:event_btnOKActionPerformed

    private void formFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_formFocusGained
        
    }//GEN-LAST:event_formFocusGained

    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
        preencherTodasComboBox();
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
            java.util.logging.Logger.getLogger(Exercicio_Escolha.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Exercicio_Escolha.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Exercicio_Escolha.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Exercicio_Escolha.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Exercicio_Escolha(Exercicio_Escolha.modo).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnOK;
    private javax.swing.JComboBox jExercicios;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
