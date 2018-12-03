package br.controllers;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

import br.model.Infraestrutura;
import br.model.Pedido;
import br.persistencia.InfraestruturaDAO;
import br.persistencia.PedidoAluguelInfraDAO;
import br.persistencia.PedidoAluguelProdutoDAO;
import br.persistencia.ProdutoDAO;
import br.util.Janela;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class TelaPedidoEscolherInfraController implements Initializable {

//************************ ATRIBUTOS ********************************
	Janela janelaUtil = new Janela();
	PedidoAluguelInfraDAO aluguelInfra = new PedidoAluguelInfraDAO(TelaPrincipalController.nomeArquivoPedidoLocInfra);
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	private DatePicker hoje = new DatePicker();

//*********************** COMPONENTES *******************************	

	@FXML
	private Button btn_adicionar;

	@FXML
	private Button btn_cancelar;

	@FXML
	private Button btn_detalhes;

	@FXML
	private DatePicker date_inicio;

	@FXML
	private TableView<Infraestrutura> tableView_infra;

	@FXML
	private TableColumn<Infraestrutura, Integer> tc_ID;

	@FXML
	private TableColumn<Infraestrutura, String> tc_nome;

	@FXML
	private TableColumn<Infraestrutura, String> tc_descricao;

	@FXML
	private TableColumn<Infraestrutura, Float> tc_precoDia;

//*********************** ON-ACTION *********************************
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		prepararTableViews();
		datasDisponiveis();
	}

	@FXML
	void OnClick_btn_adicionar(ActionEvent event) {
		try {
			if (!tableView_infra.getSelectionModel().isEmpty() && validaCamposObrigatorios()) {
				Infraestrutura infra = new InfraestruturaDAO(TelaPrincipalController.nomeArquivoInfra)
						.getInfraLoc(tableView_infra.getSelectionModel().getSelectedItem().getCodInfraestrutura(),
								pegarData(date_inicio));
				if(validaData(infra)) {
				TelaPedidoInfraController.obsSalas.add(infra);
				br.util.Janela.fecharJanela(btn_adicionar);
				}else {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Atenção");
					alert.setHeaderText(null);
					alert.setContentText("Escolha uma infraestrutura!");
					alert.setContentText("Data já adicionada");
					alert.show();
				};

			} else {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Atenção");
				alert.setHeaderText(null);
				alert.setContentText("Escolha uma infraestrutura!");
				if (date_inicio.getValue() == null)
					alert.setContentText("Defina uma data para locação");
				if (date_inicio.getValue().isEqual(LocalDate.now()))
					alert.setContentText("A data de locação não pode ser igual a data de hoje");

				alert.show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	void OnClick_btn_detalhes(ActionEvent event) {
		TelaInfraDetalhesController.infra = tableView_infra.getSelectionModel().getSelectedItem();
		janelaUtil.novaJanelaComOwner("/br/view/TelaInfraDetalhes.fxml", false, "Detalhes da sala");
	}

	@FXML
	void OnClick_btn_cancelar(ActionEvent event) {
		br.util.Janela.fecharJanela(btn_cancelar);
	}

//************************** METODOS AUXILIARES *********************
	private void prepararTableViews() {
		try {
			tc_ID.setCellValueFactory(new PropertyValueFactory<>("codInfraestrutura"));
			tc_nome.setCellValueFactory(new PropertyValueFactory<>("nomeInfraestrutura"));
			tc_precoDia.setCellValueFactory(new PropertyValueFactory<>("precoDiaInfraestrutura"));
			tc_descricao.setCellValueFactory(new PropertyValueFactory<>("descricaoInfraestrutura"));

			tableView_infra.setItems(FXCollections.observableArrayList(
					new InfraestruturaDAO(TelaPrincipalController.nomeArquivoInfra).listarInfraestruturas()));
			checkDataAtual();
			date_inicio.setConverter(new StringConverter<LocalDate>() {
				DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

				@Override
				public String toString(LocalDate date) {
					if (date != null) {
						return dateFormatter.format(date);
					} else {
						return "";
					}
				}

				@Override
				public LocalDate fromString(String string) {
					if (string != null && !string.isEmpty()) {
						return LocalDate.parse(string, dateFormatter);
					} else {
						return null;
					}
				}
			});
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void checkDataAtual() {
		hoje.setValue(LocalDate.now());
		final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
			@Override
			public DateCell call(final DatePicker datePicker) {
				return new DateCell() {
					@Override
					public void updateItem(LocalDate item, boolean empty) {
						super.updateItem(item, empty);

						if (item.isBefore(hoje.getValue().plusDays(1))) {
							setDisable(true);
							setStyle("-fx-background-color: #333333;");
						}
					}
				};
			}
		};
		date_inicio.setDayCellFactory(dayCellFactory);
	}

	private void checkDataAtual(ArrayList<String> datas) {
		hoje.setValue(LocalDate.now());
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
			@Override
			public DateCell call(final DatePicker datePicker) {
				return new DateCell() {
					@Override
					public void updateItem(LocalDate item, boolean empty) {
						super.updateItem(item, empty);

						if (item.isBefore(hoje.getValue().plusDays(1))) {
							setDisable(true);
							setStyle("-fx-background-color: #333333;");
						}
						for (int i = 0; i < datas.size(); i++) {
							if (item.isEqual(LocalDate.parse(datas.get(i),formatter))) {
								setDisable(true);
								setStyle("-fx-background-color: #333333;");
							}
						}						
					}
				};
			}
		};
		date_inicio.setDayCellFactory(dayCellFactory);
	}

	private void datasDisponiveis() {
		try {
			ArrayList<Pedido> listaDePedidosAluguelInfra = aluguelInfra.listarPedidos();
			tableView_infra.getSelectionModel().selectedItemProperty()
					.addListener((obs, oldSelection, newSelection) -> {
						if (newSelection != null) {
							date_inicio.setDisable(false);
							PedidoAluguelInfraDAO pedidos = new PedidoAluguelInfraDAO(
									TelaPrincipalController.nomeArquivoDataLocInfraestrutura);
							try {
								ArrayList<String> datas = pedidos
										.listarPedidosDatas(newSelection.getCodInfraestrutura());
								System.out.println(newSelection.getCodInfraestrutura());
								checkDataAtual(datas);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
		} catch (Exception e) {
		}
	}

	private boolean validaCamposObrigatorios() {
		if (date_inicio.getValue().isBefore(LocalDate.now()) || date_inicio.getValue() == null
				|| date_inicio.getValue().isEqual(LocalDate.now()))
			return false;
		return true;
	}

	private String pegarData(DatePicker date) {
		return date.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
	}

	private boolean validaData(Infraestrutura infra) {
		for (Infraestrutura i : TelaPedidoInfraController.obsSalas) {
			if (i.getCodInfraestrutura() == infra.getCodInfraestrutura()&& i.getDataLocacao().equals(infra.getDataLocacao()))
				return false;
		}
		return true;
	}

}
