package projecttcc_2.pkg0.control;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import projecttcc_2.BD.ConexaoBD;

public class FXMLRelatoriosController implements Initializable {

    @FXML
    private ComboBox<String> dateAno;

    @FXML
    private ComboBox<String> dateMes;

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
        // Inicializar ComboBox de anos
        initComboBoxAnos();

        // Inicializar ComboBox de meses
        initComboBoxMeses();

        // Selecionar ano e mês atuais
       selectCurrentDate();

        // Carregar valores iniciais
        carregarValores();

        // Adicionar ouvintes de alteração para ComboBoxes de ano e mês
        addComboBoxListeners();
    }

    private void initComboBoxAnos() {
        Platform.runLater(() -> {
            try {
                ObservableList<String> anos = FXCollections.observableArrayList();
                for (int i = 2024; i >= 1924; i--) {
                    anos.add(Integer.toString(i));
                }
                dateAno.setItems(anos);
            } catch (Exception e) {
                System.err.println("Erro ao inicializar ComboBox de anos: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }

    private void initComboBoxMeses() {
        ObservableList<String> meses = FXCollections.observableArrayList();
        meses.addAll("Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho",
                "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro");
        dateMes.setItems(meses);
    }

    private void selectCurrentDate() {
        int anoAtual = java.time.LocalDate.now().getYear();
        int mesAtual = java.time.LocalDate.now().getMonthValue();
        dateAno.setValue(Integer.toString(anoAtual));
        dateMes.setValue(dateMes.getItems().get(mesAtual - 1));
    }

    private void addComboBoxListeners() {
        dateAno.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            carregarValores();
        });

        dateMes.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            carregarValores();
        });
    }

    private void carregarValores() {
        Connection conexao = null;
        DecimalFormat df = new DecimalFormat("#,##0.00");

        try {
            // Estabelecer conexão com o banco de dados
            conexao = ConexaoBD.conectar();

            // Obter ano e mês selecionados
            String anoSelecionado = dateAno.getValue();
            String mesSelecionado = dateMes.getValue();

            // Se ano ou mês não forem selecionados, sair do método
            if (anoSelecionado == null || mesSelecionado == null) {
                return;
            }

            // Formatar mês/ano para a consulta
            int numMesSelecionado = dateMes.getItems().indexOf(mesSelecionado) + 1;
            String mesFormatado = String.format("%02d", numMesSelecionado);
            String mesAnoSelecionado = anoSelecionado + "-" + mesFormatado;

            // Executar consulta SQL
            String sql = "SELECT valor_gasto, valor_prejuizo, valor_lucro FROM Carteira WHERE mes_ano = ?";
            PreparedStatement statement = conexao.prepareStatement(sql);
            statement.setString(1, mesAnoSelecionado);
            ResultSet resultSet = statement.executeQuery();

            // Processar resultados da consulta
            if (resultSet.next()) {
                double valorGasto = resultSet.getDouble("valor_gasto");
                double valorPrejuizo = resultSet.getDouble("valor_prejuizo");
                double valorLucro = resultSet.getDouble("valor_lucro");

                // Atualizar PieChart
                atualizarPieChart(valorGasto, valorPrejuizo, valorLucro);

                // Atualizar Labels
                atualizarLabels(df, valorGasto, valorPrejuizo, valorLucro);
            } else {
                atualizarPieChart(0, 0, 0);
                atualizarLabels(df, 0, 0, 0);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao carregar valores do banco de dados: " + e.getMessage());
        } finally {
            // Fechar conexão com o banco de dados
            ConexaoBD.desconectar(conexao);
        }
    }

    private void atualizarPieChart(double valorGasto, double valorPrejuizo, double valorLucro) {
        // Criar os segmentos do PieChart
        PieChart.Data gastoData = new PieChart.Data("Gastos", valorGasto);
        PieChart.Data prejuizoData = new PieChart.Data("Prejuízos", valorPrejuizo);
        PieChart.Data lucroData = new PieChart.Data("Lucros", valorLucro);

        // Limpar e adicionar os novos dados ao PieChart
        pieGastos.getData().clear();
        pieGastos.getData().addAll(gastoData, prejuizoData, lucroData);

        // Aplicar estilos aos segmentos do PieChart
        pieGastos.layout(); // Garante que os nós dos segmentos sejam criados antes de acessá-los
        for (PieChart.Data data : pieGastos.getData()) {
            if (data.getNode() != null) {
                switch (data.getName()) {
                    case "Gastos":
                        data.getNode().setStyle("-fx-pie-color: #f39c12;");
                        break;
                    case "Prejuízos":
                        data.getNode().setStyle("-fx-pie-color: #f56954;");
                        break;
                    case "Lucros":
                        data.getNode().setStyle("-fx-pie-color: #00a65a;");
                        break;
                }
            }
        }
    }

    private void atualizarLabels(DecimalFormat df, double valorGasto, double valorPrejuizo, double valorLucro) {
        txtDespesas.setText("R$ " + df.format(valorGasto));
        txtPerdas.setText("R$ " + df.format(valorPrejuizo));
        txtReceita.setText("R$ " + df.format(valorLucro));
    }
}
