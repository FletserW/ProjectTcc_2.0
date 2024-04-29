package projecttcc_2.pkg0.control;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import projecttcc_2.BD.ConexaoBD;

public class FXMLRelatoriosController implements Initializable {

    @FXML
    private DatePicker datePeriodo;

    @FXML
    private PieChart pieGastos;

    @FXML
    private Label txtDespesas;

    @FXML
    private Label txtPerdas;

    @FXML
    private Label txtReceita;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Ao inicializar, carregar os valores do banco de dados
        carregarValores();
    }

    private void carregarValores() {
        Connection conexao = null;
        DecimalFormat df = new DecimalFormat("#,##0.00");
        try {
            // Estabelecer conexão com o banco de dados
            conexao = ConexaoBD.conectar();

            // Consulta SQL para obter os valores da tabela Carteira
            String sql = "SELECT valor_gasto, valor_prejuizo, valor_lucro FROM Carteira";
            PreparedStatement statement = conexao.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                double valorGasto = resultSet.getDouble("valor_gasto");
                double valorPrejuizo = resultSet.getDouble("valor_prejuizo");
                double valorLucro = resultSet.getDouble("valor_lucro");

                // Criar os segmentos do PieChart
                PieChart.Data gastoData = new PieChart.Data("Gastos", valorGasto);
                PieChart.Data prejuizoData = new PieChart.Data("Prejuízos", valorPrejuizo);
                PieChart.Data lucroData = new PieChart.Data("Lucros", valorLucro);

                // Adicionar os segmentos ao PieChart
                pieGastos.getData().addAll(gastoData, prejuizoData, lucroData);

                // Formatando os valores com o padrão "R$ X,XX"
                String valorFormatadoGasto = "R$ " + df.format(valorGasto);
                String valorFormatadoPrejuizo = "R$ " + df.format(valorPrejuizo);
                String valorFormatadoLucro = "R$ " + df.format(valorLucro);

                // Atualizar os textos das labels
                txtDespesas.setText(valorFormatadoGasto);
                txtPerdas.setText(valorFormatadoPrejuizo);
                txtReceita.setText(valorFormatadoLucro);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao carregar valores do banco de dados: " + e.getMessage());
        } finally {
            // Fechar a conexão com o banco de dados
            ConexaoBD.desconectar(conexao);
        }
    }
}
