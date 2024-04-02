/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package projecttcc_2.pkg0.control;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;
import projecttcc_2.BD.ConexaoBD;
import projecttcc_2.DTO.VendasDTO;

/**
 * FXML Controller class
 *
 * @author reido
 */
public class FXMLVendasController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (dateDe.getValue() == null) {
            dateDe.setValue(LocalDate.now());
        }

        if (dateAte.getValue() == null) {
            dateAte.setValue(LocalDate.now()); 
        }

        LocalDate dataInicio = dateDe.getValue();
        LocalDate dataFim = dateAte.getValue();

        preencherTabelaVendas(dataInicio, dataFim,"");
        preencherGraficoVendas(dataInicio, dataFim,"");
    }

    @FXML
    private DatePicker dateAte;

    @FXML
    private DatePicker dateDe;

    @FXML
    private Button btnFiltrar;

    @FXML
    private BarChart<String, Integer> gfcVendas;

    @FXML
    private TableView<VendasDTO> tblVendas;

    @FXML
    private TextField txtPesquisa;

    private ObservableList<VendasDTO> dadosTabela = FXCollections.observableArrayList();

// Método para preencher a TableView com os dados da tabela Vendas
    public void preencherTabelaVendas(LocalDate dataInicio, LocalDate dataFim, String termoPesquisa) {
        limparTabela(); // Limpar a tabela antes de preencher com novos dados

        try (Connection conexao = ConexaoBD.conectar()) {
            String sql = "SELECT p.nome, "
                    + "SUM(v.quantidade_vendida) AS total_vendas, "
                    + "v.mes_ano "
                    + "FROM vendas v "
                    + "INNER JOIN produtos p ON v.id_produto = p.id "
                    + "WHERE v.mes_ano BETWEEN ? AND ? ";

            // Adicione a condição para filtrar pelo termo de pesquisa se ele não estiver vazio
            if (!termoPesquisa.isEmpty()) {
                sql += "AND p.nome LIKE ? ";
            }

            sql += "GROUP BY p.nome, v.mes_ano";

            try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
                // Convertendo LocalDate para String no formato 'YYYY-MM'
                String dataInicioStr = dataInicio.toString().substring(0, 7);
                String dataFimStr = dataFim.toString().substring(0, 7);

                stmt.setString(1, dataInicioStr);
                stmt.setString(2, dataFimStr);

                // Defina o parâmetro do termo de pesquisa se necessário
                if (!termoPesquisa.isEmpty()) {
                    stmt.setString(3, "%" + termoPesquisa + "%");
                }

                try (ResultSet rs = stmt.executeQuery()) {
                    dadosTabela.clear();

                    while (rs.next()) {
                        String nome = rs.getString("nome");
                        int quantidadeVendida = rs.getInt("total_vendas");
                        String mesAno = rs.getString("mes_ano");

                        // Adicione o objeto VendasDTO à lista de dados da tabela
                        VendasDTO venda = new VendasDTO(nome, quantidadeVendida, mesAno);
                        dadosTabela.add(venda);
                    }

                    // Atualize a TableView com os novos dados
                    tblVendas.setItems(dadosTabela);
                    inicializarColunasTabela(); // Inicializar as colunas da tabela
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao preencher a tabela de vendas.");
        }
    }


    private void inicializarColunasTabela() {
        tblVendas.getColumns().clear();

        TableColumn<VendasDTO, String> colunaNome = new TableColumn<>("Nome do Produto");
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

        TableColumn<VendasDTO, Integer> colunaQuantidade = new TableColumn<>("QTD Vendida");
        colunaQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidadeVendida"));

        TableColumn<VendasDTO, String> colunaMesAno = new TableColumn<>("Mês");
        colunaMesAno.setCellValueFactory(new PropertyValueFactory<>("mesAno"));

        tblVendas.getColumns().addAll(colunaNome, colunaQuantidade, colunaMesAno);
        tblVendas.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    @FXML
    void filtrarButtonAction(ActionEvent event) {
        LocalDate dataInicio = dateDe.getValue();
        LocalDate dataFim = dateAte.getValue();
        String termoPesquisa = txtPesquisa.getText().trim();

        preencherTabelaVendas(dataInicio, dataFim, termoPesquisa);
        preencherGraficoVendas(dataInicio, dataFim, termoPesquisa);
    }

    public void preencherGraficoVendas(LocalDate dataInicio, LocalDate dataFim, String termoPesquisa) {
        gfcVendas.getData().clear(); // Limpar os dados do gráfico antes de preencher com novos dados

        try (Connection conexao = ConexaoBD.conectar()) {
            String sql = "SELECT p.nome, COALESCE(SUM(v.quantidade_vendida), 0) AS total_vendas "
                    + "FROM produtos p "
                    + "LEFT JOIN vendas v ON p.id = v.id_produto AND v.mes_ano BETWEEN ? AND ? ";

            // Adicione a condição para filtrar pelo termo de pesquisa se ele não estiver vazio
            if (!termoPesquisa.isEmpty()) {
                sql += "WHERE p.nome LIKE ? ";
            }

            sql += "GROUP BY p.nome "
                    + "HAVING SUM(v.quantidade_vendida) > 0";

            try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
                // Convertendo LocalDate para String no formato 'YYYY-MM'
                String dataInicioStr = dataInicio.toString();
                String dataFimStr = dataFim.toString();

                stmt.setString(1, dataInicioStr);
                stmt.setString(2, dataFimStr);

                // Defina o parâmetro do termo de pesquisa se necessário
                if (!termoPesquisa.isEmpty()) {
                    stmt.setString(3, "%" + termoPesquisa + "%");
                }

                try (ResultSet rs = stmt.executeQuery()) {
                    XYChart.Series<String, Integer> series = new XYChart.Series<>();

                    while (rs.next()) {
                        String nomeProduto = rs.getString("nome");
                        int totalVendas = rs.getInt("total_vendas");

                        // Adicione os dados à série do gráfico
                        series.getData().add(new XYChart.Data<>(nomeProduto, totalVendas));
                    }

                    // Adicione a série ao gráfico
                    gfcVendas.getData().add(series);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao preencher o gráfico de vendas.");
        }
    }    


    public void limparTabela() {
        tblVendas.getColumns().clear();
        tblVendas.getItems().clear();
    }
}
