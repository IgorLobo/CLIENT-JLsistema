package br.controllers;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import javax.swing.JOptionPane;
import br.util.Janela;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;

public class TelaPrincipalController implements Initializable {
//************************ ATRIBUTOS ********************************
	private Janela utilJanela = new Janela();
	
	private Thread servidorStart = null;
	public static boolean servidorON = false;
	public static Socket socket = null;
	public static ObjectOutputStream objectOutputStream = null;
	public static ObjectInputStream objectInputStream = null;

	public volatile static String caminhoTxtBancoDados = "";
	public static String nomeArquivoCliente = "";
	public static String nomeArquivoJogos = "";
	public static String nomeArquivoAcessorios = "";
	public static String nomeArquivoConsoles = "";
	public static String nomeArquivoJogosLoc ="";
	public static String nomeArquivoAcessoriosLoc = "";
	public static String nomeArquivoConsolesLoc = "";
	public static String nomeArquivoPedidoVenda = "";
	public static String nomeArquivoPedidoLocProdutos = "";
	public static String nomeArquivoInfra = "";
	public static String nomeArquivoPedidoLocInfra = "";
	public static String nomeArquivoDataLocInfraestrutura = "";
	public static String ids = "";


//*********************** COMPONENTES *******************************	
	// Toolbar--
	@FXML
	private Button tbBtn_clientes;

	@FXML
	private Button tbBtn_produtos;

	@FXML
	private Button tbBtn_pedidos;

	@FXML
	private Button tbBtn_infra;

	@FXML
	private Button btn_estoque;

	@FXML
	private Button btn_configuracoes;

	// --
//*********************** ON-ACTION *********************************
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	@FXML
	void OnClick_btn_configuracoes(ActionEvent event) {
		if (servidorON) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Atenção");
			alert.setHeaderText(null);
			alert.setContentText("Cliente configurado e conectado");
			alert.show();
		} else {
			utilJanela.novaJanelaComOwnerWait("/br/view/TelaConfiguracao.fxml", false, "Configurações");
			if (servidorON) {
				iniciarServidor();
			}
		}
	}

	@FXML
	void OnClick_tbBtn_clientes(ActionEvent event) {
		utilJanela.novaJanelaComOwner("/br/view/TelaCliente.fxml", true, "Gerenciar clientes");
	}

	@FXML
	void OnClick_tbBtn_pedidos(ActionEvent event) {
		utilJanela.novaJanelaComOwner("/br/view/TelaPedidoInicial.fxml", true, "Fazer pedido");
	}

	@FXML
	void OnClick_tbBtn_Infra(ActionEvent event) {
		utilJanela.novaJanelaComOwner("/br/view/TelaInfra.fxml", true, "Gerenciar infraestrutura");
	}

	@FXML
	void OnClick_tbBtn_produtos(ActionEvent event) {
		utilJanela.novaJanelaComOwner("/br/view/TelaProduto.fxml", true, "Gerenciar produtos");
	}

	@FXML
	void OnClick_btn_estoque(ActionEvent event) {
		utilJanela.novaJanelaComOwner("/br/view/TelaEstoque.fxml", false, "Estoque da loja");
	}

//************************** METODOS AUXILIARES *********************
	private void iniciarServidor() {
		servidorStart = new Thread() {
			@Override
			public void run() {
				try {
					socket = new Socket(TelaConfiguracaoController.ipServer, TelaConfiguracaoController.porta);
					
					objectInputStream = new ObjectInputStream(socket.getInputStream());
					objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

					TelaConfiguracaoController.caminhoArquivoDB = objectInputStream.readUTF();
					atualizarCaminhos();

					objectOutputStream.writeUTF("");
					objectOutputStream.flush();
					socket.close();

				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
					e.getMessage();
				}
			}
		};
		servidorStart.start();
	}
	
	private void atualizarCaminhos() {
		caminhoTxtBancoDados = TelaConfiguracaoController.caminhoArquivoDB;
		nomeArquivoCliente = caminhoTxtBancoDados + "Clientes.csv";
		nomeArquivoJogos = caminhoTxtBancoDados + "Jogos.csv";
		nomeArquivoAcessorios = caminhoTxtBancoDados + "Acessorios.csv";
		nomeArquivoConsoles = caminhoTxtBancoDados + "Consoles.csv";
		nomeArquivoJogosLoc = caminhoTxtBancoDados + "JogosLoc.csv";
		nomeArquivoAcessoriosLoc = caminhoTxtBancoDados + "AcessoriosLoc.csv";
		nomeArquivoConsolesLoc = caminhoTxtBancoDados + "ConsolesLoc.csv";
		nomeArquivoPedidoVenda = caminhoTxtBancoDados + "PedidosVenda.csv";
		nomeArquivoPedidoLocProdutos = caminhoTxtBancoDados + "PedidosLocProduto.csv";
		nomeArquivoInfra = caminhoTxtBancoDados + "Infraestruturas.csv";
		nomeArquivoPedidoLocInfra = caminhoTxtBancoDados + "PedidosLocInfraestruturas.csv";
		nomeArquivoDataLocInfraestrutura = caminhoTxtBancoDados + "DataLocInfraestrutura.csv";
		ids = caminhoTxtBancoDados + "Ids.csv";
	}

}
