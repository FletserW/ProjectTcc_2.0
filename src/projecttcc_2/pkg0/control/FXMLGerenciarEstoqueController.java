/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package projecttcc_2.pkg0.control;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import projecttcc_2.BD.DepositoDAO;
import projecttcc_2.BD.ProdutosDAO;
import projecttcc_2.BD.VendasDAO;
import projecttcc_2.DTO.ProdutosDTO;
import java.time.LocalDate; // Importe a classe LocalDate para obter a data atual
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import projecttcc_2.BD.ConexaoBD;

/**
 * FXML Controller class
 *
 * @author reido
 */
public class FXMLGerenciarEstoqueController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {       
        
    }

    @FXML
    private ToggleGroup acao;

    @FXML
    private Button bntCancelarEditar;

    @FXML
    private Button bntEditar;

    @FXML
    private ToggleGroup quantidade;

    @FXML
    private RadioButton raddioVender;

    @FXML
    private RadioButton radioAdicionar;

    @FXML
    private RadioButton radioCaixa;

    @FXML
    private RadioButton radioPerder;

    @FXML
    private RadioButton radioUnidade;

    @FXML
    public TextField txtQtdDeposito;
    
    @FXML
    private Stage stage;

    @FXML
    private TextField txtQuantidadeEstoque;

    private ProdutosDTO produtoSelecionado;

    private TableView<ProdutosDTO> tblProdutos; // Declare uma variável para armazenar a referência da tabela

    @FXML
    private FXMLDepositoController depositoController;

    @FXML
    void cancelarEditarActionButton(ActionEvent event) {
        fecharJanela(event);
    }

    @FXML
    void editarActionButton(ActionEvent event) throws SQLException {
        // Obter o produto selecionado na tabela
        ProdutosDTO produtoSelecionado = tblProdutos.getSelectionModel().getSelectedItem();

        // Verificar se um produto foi selecionado
        if (produtoSelecionado != null) {
            if (txtQtdDeposito.getText().isEmpty()) {
                exibirMensagemErro("Campo Vazio", "Por favor, insira uma quantidade antes de editar o estoque.");
                return;
            }

            int quantidadeDigitada = Integer.parseInt(txtQtdDeposito.getText());

            // Verificar se a quantidade digitada é maior que 0
            if (quantidadeDigitada <= 0) {
                exibirMensagemErro("Quantidade Inválida", "A quantidade digitada deve ser maior que 0.");
                return;
            }

            // Verificar se a opção "radioCaixa" está selecionada
            if (radioCaixa.isSelected()) {
                try (Connection conexao = ConexaoBD.conectar()) {
                    // Consulta para obter a quantidade do produto
                    String consultaQuantidade = "SELECT quantidade FROM produtos WHERE id = ?";
                    try (PreparedStatement stmtConsultaQuantidade = conexao.prepareStatement(consultaQuantidade)) {
                        stmtConsultaQuantidade.setInt(1, produtoSelecionado.getId());
                        try (ResultSet rsQuantidade = stmtConsultaQuantidade.executeQuery()) {
                            if (rsQuantidade.next()) {
                                int quantidadeProduto = rsQuantidade.getInt("quantidade");
                                quantidadeDigitada *= quantidadeProduto; // Multiplica pela quantidade do produto no banco de dados
                                System.out.println("Quantidade selecionada: " + quantidadeProduto);
                            } else {
                                System.out.println("Erro: Produto não encontrado no banco de dados.");
                            }
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    // Trate a exceção conforme necessário
                }
            }

            if (radioAdicionar.isSelected()) {
                System.out.println("Novo valor no estoque: " + produtoSelecionado.getQuantidadeEstoque() + quantidadeDigitada);
                produtoSelecionado.setQuantidadeEstoque(produtoSelecionado.getQuantidadeEstoque() + quantidadeDigitada);
                try (Connection conexao = ConexaoBD.conectar()) {
                    // Consulta para obter o preço e a quantidade do produto
                    String consulta = "SELECT preco, quantidade FROM produtos WHERE id = ?";
                    try (PreparedStatement stmtConsulta = conexao.prepareStatement(consulta)) {
                        stmtConsulta.setInt(1, produtoSelecionado.getId());
                        try (ResultSet rsConsulta = stmtConsulta.executeQuery()) {
                            if (rsConsulta.next()) {
                                BigDecimal precoUnitario = new BigDecimal(rsConsulta.getString("preco")).divide(new BigDecimal(rsConsulta.getInt("quantidade")), 2, RoundingMode.HALF_UP);

                                // Calcula os gastos com o produto
                                BigDecimal gastosProduto = precoUnitario.multiply(BigDecimal.valueOf(quantidadeDigitada).multiply(BigDecimal.valueOf(rsConsulta.getInt("quantidade"))));

                                // Verifica se já existe um registro para o mês/ano atual na tabela Carteira
                                String verificarRegistro = "SELECT * FROM Carteira WHERE mes_ano = ?";
                                try (PreparedStatement stmtVerificarRegistro = conexao.prepareStatement(verificarRegistro)) {
                                    stmtVerificarRegistro.setString(1, LocalDate.now().toString().substring(0, 7)); // Obtém o mês/ano atual
                                    try (ResultSet rsVerificarRegistro = stmtVerificarRegistro.executeQuery()) {
                                        if (rsVerificarRegistro.next()) {
                                            // Se já existe um registro, atualize-o com os novos valores
                                            String atualizarGastos = "UPDATE Carteira SET valor_gasto = valor_gasto + ? WHERE mes_ano = ?";
                                            try (PreparedStatement stmtAtualizarGastos = conexao.prepareStatement(atualizarGastos)) {
                                                stmtAtualizarGastos.setBigDecimal(1, gastosProduto);
                                                stmtAtualizarGastos.setString(2, LocalDate.now().toString().substring(0, 7)); // Obtém o mês/ano atual
                                                stmtAtualizarGastos.executeUpdate();
                                            }
                                        } else {
                                            // Se não existe um registro, insira um novo registro
                                            String inserirGastos = "INSERT INTO Carteira (mes_ano, valor_gasto) VALUES (?, ?)";
                                            try (PreparedStatement stmtInserirGastos = conexao.prepareStatement(inserirGastos)) {
                                                stmtInserirGastos.setString(1, LocalDate.now().toString().substring(0, 7)); // Obtém o mês/ano atual
                                                stmtInserirGastos.setBigDecimal(2, gastosProduto);
                                                stmtInserirGastos.executeUpdate();
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    // Trate a exceção conforme necessário
                }
            } else if (raddioVender.isSelected()) {
                // Verifica se a quantidade digitada é maior que zero
                if (quantidadeDigitada <= 0) {
                    System.out.println("A quantidade digitada deve ser maior que zero.");
                    return;
                }

                // Verifica se a quantidade digitada é maior que a quantidade em estoque
                if (quantidadeDigitada > produtoSelecionado.getQuantidadeEstoque()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erro");
                    alert.setHeaderText(null);
                    alert.setContentText("Valor invalido, adicione um valor menor que estoque!");
                    alert.showAndWait();
                    return;
                }
                produtoSelecionado.setQuantidadeEstoque(produtoSelecionado.getQuantidadeEstoque() - quantidadeDigitada);
                try (Connection conexao = ConexaoBD.conectar()) {
                    // Consulta para obter o preço e a quantidade do produto
                    String consulta = "SELECT preco, quantidade FROM produtos WHERE id = ?";
                    try (PreparedStatement stmtConsulta = conexao.prepareStatement(consulta)) {
                        stmtConsulta.setInt(1, produtoSelecionado.getId());
                        try (ResultSet rsConsulta = stmtConsulta.executeQuery()) {
                            if (rsConsulta.next()) {
                                BigDecimal precoUnitario = new BigDecimal(rsConsulta.getString("preco")).divide(new BigDecimal(rsConsulta.getInt("quantidade")), 2, RoundingMode.HALF_UP);

                                BigDecimal precoVenda = produtoSelecionado.getPreco_venda(); // Obtenha o preço de venda do produto
                                // Verifique se o preço de venda não é nulo
                                if (precoVenda != null) {
                                    // Calcula o lucro por unidade vendida
                                    BigDecimal lucroPorUnidade = precoVenda.subtract(precoUnitario);
                                    // Calcula o lucro total
                                    BigDecimal lucroTotal = lucroPorUnidade.multiply(BigDecimal.valueOf(quantidadeDigitada));

                                    // Verifica se já existe um registro para o mês/ano atual na tabela Carteira
                                    String verificarRegistro = "SELECT * FROM Carteira WHERE mes_ano = ?";
                                    try (PreparedStatement stmtVerificarRegistro = conexao.prepareStatement(verificarRegistro)) {
                                        stmtVerificarRegistro.setString(1, LocalDate.now().toString().substring(0, 7)); // Obtém o mês/ano atual
                                        try (ResultSet rsVerificarRegistro = stmtVerificarRegistro.executeQuery()) {
                                            if (rsVerificarRegistro.next()) {
                                                // Se já existe um registro, atualize o valor do lucro
                                                String atualizarLucro = "UPDATE Carteira SET valor_lucro = valor_lucro + ? WHERE mes_ano = ?";
                                                try (PreparedStatement stmtAtualizarLucro = conexao.prepareStatement(atualizarLucro)) {
                                                    stmtAtualizarLucro.setBigDecimal(1, lucroTotal);
                                                    stmtAtualizarLucro.setString(2, LocalDate.now().toString().substring(0, 7)); // Obtém o mês/ano atual
                                                    stmtAtualizarLucro.executeUpdate();
                                                }
                                            } else {
                                                // Se não existe um registro, insira um novo registro
                                                String inserirLucro = "INSERT INTO Carteira (mes_ano, valor_lucro) VALUES (?, ?)";
                                                try (PreparedStatement stmtInserirLucro = conexao.prepareStatement(inserirLucro)) {
                                                    stmtInserirLucro.setString(1, LocalDate.now().toString().substring(0, 7)); // Obtém o mês/ano atual
                                                    stmtInserirLucro.setBigDecimal(2, lucroTotal);
                                                    stmtInserirLucro.executeUpdate();
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    System.out.println("Preço de venda não definido.");
                                }
                            }
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    // Trate a exceção conforme necessário
                }
            } else if (radioPerder.isSelected()) {
                // Verifica se a quantidade digitada é maior que zero
                if (quantidadeDigitada <= 0) {
                    System.out.println("A quantidade digitada deve ser maior que zero.");
                    return;
                }

                // Verifica se a quantidade digitada é maior que a quantidade em estoque
                if (quantidadeDigitada > produtoSelecionado.getQuantidadeEstoque()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erro");
                    alert.setHeaderText(null);
                    alert.setContentText("Valor invalido, adicione um valor menor que estoque!");
                    alert.showAndWait();
                    return;
                }
                produtoSelecionado.setQuantidadeEstoque(produtoSelecionado.getQuantidadeEstoque() - quantidadeDigitada);
                try (Connection conexao = ConexaoBD.conectar()) {
                    // Consulta para obter o preço e a quantidade do produto
                    String consulta = "SELECT preco, quantidade FROM produtos WHERE id = ?";
                    try (PreparedStatement stmtConsulta = conexao.prepareStatement(consulta)) {
                        stmtConsulta.setInt(1, produtoSelecionado.getId());
                        try (ResultSet rsConsulta = stmtConsulta.executeQuery()) {
                            if (rsConsulta.next()) {
                                BigDecimal precoUnitario = new BigDecimal(rsConsulta.getString("preco")).divide(new BigDecimal(rsConsulta.getInt("quantidade")), 2, RoundingMode.HALF_UP);

                                // Calcula a perda por unidade
                                BigDecimal perdaPorUnidade = produtoSelecionado.getPreco_venda().subtract(precoUnitario);

                                // Calcula a perda total
                                BigDecimal perdaTotal = perdaPorUnidade.multiply(BigDecimal.valueOf(quantidadeDigitada));

                                // Verifica se o lucro atual é maior que zero
                                BigDecimal lucroAtual = getValorAtualLucro();
                                if (lucroAtual.compareTo(BigDecimal.ZERO) > 0) {
                                    // Se o lucro atual for maior que zero, subtraia a perda do lucro
                                    BigDecimal novoLucro = lucroAtual.subtract(perdaTotal);
                                    // Verifica se o novo lucro é negativo
                                    if (novoLucro.compareTo(BigDecimal.ZERO) < 0) {
                                        // Se o novo lucro for negativo, ajuste para zero e defina o valor do prejuízo
                                        perdaTotal = lucroAtual;
                                        novoLucro = BigDecimal.ZERO;
                                    }
                                    // Atualiza o valor do lucro e do prejuízo na tabela Carteira
                                    String atualizarCarteira = "UPDATE Carteira SET valor_lucro = ?, valor_prejuizo = valor_prejuizo + ? WHERE mes_ano = ?";
                                    try (PreparedStatement stmtAtualizarCarteira = conexao.prepareStatement(atualizarCarteira)) {
                                        stmtAtualizarCarteira.setBigDecimal(1, novoLucro);
                                        stmtAtualizarCarteira.setBigDecimal(2, perdaTotal);
                                        stmtAtualizarCarteira.setString(3, LocalDate.now().toString().substring(0, 7)); // Obtém o mês/ano atual
                                        stmtAtualizarCarteira.executeUpdate();
                                    }
                                } else {
                                    // Se o lucro atual for zero ou negativo, apenas adicione a perda ao prejuízo
                                    String atualizarCarteira = "UPDATE Carteira SET valor_prejuizo = valor_prejuizo + ? WHERE mes_ano = ?";
                                    try (PreparedStatement stmtAtualizarCarteira = conexao.prepareStatement(atualizarCarteira)) {
                                        stmtAtualizarCarteira.setBigDecimal(1, perdaTotal);
                                        stmtAtualizarCarteira.setString(2, LocalDate.now().toString().substring(0, 7)); // Obtém o mês/ano atual
                                        stmtAtualizarCarteira.executeUpdate();
                                    }
                                }
                            }
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    // Trate a exceção conforme necessário
                }
            }
            // Em algum lugar onde você precisa chamar preencherTabela()
            if (depositoController != null) {
                System.out.println("Deposito chamdo com exito");
                depositoController.limparTabela();
                depositoController.preencherTabela();
            }else{
                System.out.println("Deposito null");
            }
            

            DepositoDAO depositoDAO = new DepositoDAO();
            // Atualizar o estoque no banco de dados utilizando o DepositoDAO
            boolean atualizadoComSucesso = depositoDAO.atualizarQuantidadeEstoque(produtoSelecionado.getQuantidadeEstoque(), produtoSelecionado.getId());
        } else {
            // Exibir uma mensagem de erro ao usuário informando que nenhum produto foi selecionado
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, selecione um produto para editar.");
            alert.showAndWait();
        }
        fecharJanela(event);
        
    }
    
    public BigDecimal getValorAtualLucro() throws SQLException {
        BigDecimal valorAtualLucro = BigDecimal.ZERO; // Inicialmente, define o valor do lucro como zero

        try (Connection conexao = ConexaoBD.conectar()) {
            String consulta = "SELECT valor_lucro FROM Carteira WHERE mes_ano = ?";
            try (PreparedStatement stmtConsulta = conexao.prepareStatement(consulta)) {
                stmtConsulta.setString(1, LocalDate.now().toString().substring(0, 7)); // Obtém o mês/ano atual
                try (ResultSet rsConsulta = stmtConsulta.executeQuery()) {
                    if (rsConsulta.next()) {
                        valorAtualLucro = rsConsulta.getBigDecimal("valor_lucro");
                    }
                }
            }
        }

        return valorAtualLucro;
    }


    // Método para receber a referência do tblProdutos do FXMLDepositoController
    public void setTabelaProdutos(TableView<ProdutosDTO> tblProdutos) {
        this.tblProdutos = tblProdutos;
    }

    public void setDepositoController(FXMLDepositoController depositoController) {
        this.depositoController = depositoController;
    }

    // Método para definir o produto selecionado
    public void setProdutoSelecionado(ProdutosDTO produtoSelecionado) {
        this.produtoSelecionado = produtoSelecionado;
    }

    private void exibirMensagemErro(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    private void fecharJanela(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
