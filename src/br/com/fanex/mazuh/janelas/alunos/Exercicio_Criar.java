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
import br.com.fanex.mazuh.edu.Curso;
import br.com.fanex.mazuh.edu.Exercicio;
import br.com.fanex.mazuh.jpa.CursoJpaController;
import java.awt.Color;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author mazuh
 */
public class Exercicio_Criar extends javax.swing.JFrame {

    /**
     * Creates new form Exercicio_Criar
     */
    public Exercicio_Criar() {
        this.setUndecorated(true); // oculta barra superior
        
        initComponents();
        
        this.setResizable(false);
        this.setLocationRelativeTo(null); // centraliza
        this.getContentPane().setBackground(new Color(39,174,96)); // cor de fundo verde
        
    }
    
    /*
    Verifica se há cursos no bd e, se houver, coloca-os na combobox.
    */
    private void preencherCbCursos(){
        CursoJpaController cursosDAO = new CursoJpaController(Sessao.getEntityManagerFactory());
        
        // verifica se há cursos
        if (cursosDAO.getCursoCount() <= 0)
            die("Não foram encontrados cursos no banco de dados!", "Ops...");
        
        // busca-os
        List cursos = cursosDAO.findCursoEntities();
        
        // preenche combobox a combobox com o que foi encontrado
        jCursos.removeAllItems();
        jCursos.addItem("---");
        for (int i = 0; i < cursos.size(); i++){
            jCursos.addItem(cursos.get(i));
        }
        
    }
    
    /*
    Se os campos estiverem ok, um novo exercício "vazio" será criado
    e enviado ao form seguinte via parâmetro.
    */
    private void irParaFormDeResposta(){
        if (this.camposEstaoOk()){ // checagem
            
            // cria exercício novo
            Exercicio exercicio = new Exercicio();
            exercicio.setIdAluno(Sessao.usuario_logado());
            exercicio.setIdCurso((Curso) jCursos.getSelectedItem());
            exercicio.setNumAula((Integer) jNumDaAula.getValue());
            exercicio.setQtdPerguntas(-1);
            
            // envia pro form seguinte
            new Exercicio_Responder(Exercicio_Responder.MODO_INICIAR, exercicio)
                    .setVisible(true);
            this.dispose();
            
            
        } else{ // se os campos estiverem inválidos
            JOptionPane.showMessageDialog(null, 
                    "Há algum campo inválido.\nChame seu instrutor!", 
                    "ERRO",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /*
    Verifica se os campos são válidos. Return false se tiver algo errado.
    */
    private boolean camposEstaoOk(){
        // validação da combobox
        Object curso = jCursos.getSelectedItem();
        try {
            if (curso.toString().equals("---")) // se o texto padrão, erro!
                return false;
            else // então é um objeto Curso
                curso = (Curso) curso; // é quase certeza ser um Curso, mas...
            
        } catch (Exception e) {
            // ...se não puder ser convertido em Curso, dispara erro
            return false;
        }
        
        
        // validação do número da aula. Checagem de mínimo e máximo.
        int valor;
        
        try{
            valor = (Integer) jNumDaAula.getValue();
        }catch(NumberFormatException e){
            // se não for um número de verdade (?)
            return false;
        }
        
        // valor mínimo
        if (valor < 1)
            return false;
        
        // valor máximo
        if (valor > ((Curso) curso).getQtdExercicios())
            return false;
        
        // se nenhuma das validações disparou false, então logicamente é true.
        return true;
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

        jLabel1 = new javax.swing.JLabel();
        jCursos = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jNumDaAula = new javax.swing.JSpinner();
        btnNovo = new javax.swing.JToggleButton();
        btnCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                formWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Nome do curso (presente na frente da apostila):");

        jCursos.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "---" }));

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Número da aula:");

        btnNovo.setText("Começar!");
        btnNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovoActionPerformed(evt);
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
                    .addComponent(jCursos, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(4, 4, 4)
                                .addComponent(jNumDaAula, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 119, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnCancelar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNovo)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCursos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jNumDaAula, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNovo)
                    .addComponent(btnCancelar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /*
    Botão de cancelar fecha a janela.
    */
    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    /*
    Botão que tentará terminar o processo de criação e avançará à próxima tela
    */
    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed
        this.irParaFormDeResposta();
    }//GEN-LAST:event_btnNovoActionPerformed

    /*
    Sempre que a janela ganhar foco, a combobox será atualizada com o que tiver no bd.
    */
    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
        this.preencherCbCursos();
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
            java.util.logging.Logger.getLogger(Exercicio_Criar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Exercicio_Criar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Exercicio_Criar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Exercicio_Criar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Exercicio_Criar().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancelar;
    private javax.swing.JToggleButton btnNovo;
    private javax.swing.JComboBox jCursos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JSpinner jNumDaAula;
    // End of variables declaration//GEN-END:variables
}
