package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

class Data{
	public String selectedWhere = null;
	public String selectedOrderBy;
	ResultSet resultSet = null;

	public String sqlStr;
	public String selectedId;
	public String selectedName;

}

public class Controller {
	@FXML private TableView <TableRecord>tableView;
	@FXML private TableColumn <TableRecord,String>idColumn;
	@FXML private TableColumn <TableRecord,String>nameColumn;
	@FXML private TableColumn <TableRecord,String>mathColumn;
	@FXML private TableColumn <TableRecord,String>kokugoColumn;
	@FXML private ComboBox<String> comboBox;
	@FXML private ComboBox<String> sortComboBox;
	@FXML private Button inpurtButton;
	@FXML private Button addButton;
	@FXML private Button Window2Button;
	@FXML private Label idLabel;
	@FXML private Label nameLabel;
	@FXML private Label mathLabel;
	@FXML private Label kokugoLabel;
	@FXML private TextArea querryLabel;
	@FXML private TextField textField;
	@FXML private TextField addIdTextField;
	@FXML private TextField addNameTextField;
	@FXML private TextField addMathTextField;
	@FXML private TextField addKokugoTextField;

	private static String str;
	private static String sqlStr;
	private static int comboBoxOnce;
	private static int sortComboBoxOnce;

	private ObservableList<TableRecord> list = FXCollections.observableArrayList();

	/**
	 * SQL文を用いてDBからデータを取得し、リストに追加する。
	 * @param data
	 */
	public void getFromDBtoList(Data data) {
		try (Connection connection = DriverManager.getConnection(App.url,App.user,App.password);
				java.sql.Statement statement =connection.createStatement();
			){
			list.clear();
			ResultSet resultSet = statement.executeQuery(data.sqlStr);
			//int rowNumber = 0;
			//while(resultSet.next()) {++rowNumber;}
			//Sys.println(rowNumber);
			//resultSet.first();
			while(resultSet.next()){
				// observableArrayListにTableRecordを登録
				list.add(new TableRecord(resultSet.getString("id"), resultSet.getString("name"), resultSet.getString("mathScore"),resultSet.getString("kokugoScore")));
				//Sys.println(resultSet.getString("id")+resultSet.getString("name")+resultSet.getString("mathScore")+resultSet.getString("kokugoScore"));
			}
			idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
			nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
			mathColumn.setCellValueFactory(new PropertyValueFactory<>("mathScore"));
			kokugoColumn.setCellValueFactory(new PropertyValueFactory<>("kokugoScore"));
			tableView.setItems(list);

		} catch (NullPointerException e) {
			Sys.print("空ではいけません。");
			//App.error("空ではいけません。");
			e.printStackTrace();
			// TODO: handle exception
		} catch (SQLException e1) {
			// TODO 自動生成された catch ブロック
			e1.printStackTrace();
		}
	}

	@FXML
	/**
	 * ComboBoxの初期化
	 */
	public void initComboBox() {
		if (comboBoxOnce < 1) {
			comboBox.getItems().addAll(
					"学籍番号",
					"名前",
					"数学の点数",
					"国語の点数"
			);
			comboBoxOnce++;
			comboBox.setValue("選択");
		}
	}
	/**
	 * ソート用のcomboboxの初期化
	 */
	public void initSortComboBox() {
		if (sortComboBoxOnce < 1) {
			sortComboBox.getItems().addAll(
					"学籍番号",
					"名前",
					"数学の点数",
					"国語の点数"
			);
			sortComboBox.setValue("学籍番号");
			sortComboBoxOnce++;
		}
	}

	/**
	 * comboBox用に日本語の選択肢を英語に変える。
	 * @param string
	 * @return
	 */
	public String comboBox2SqlQuerry(String string){
		switch (string) {
		case "学籍番号":
			return "id";

		case "名前":
			return "name";

		case "数学の点数":
			return "mathScore";

		case "国語の点数":
			return "kokugoScore";
		}
		return null;
	}

	public static class TableRecord {
		private final StringProperty id;
		private final StringProperty name;
		private final StringProperty mathScore;
		private final StringProperty kokugoScore;

		private TableRecord(String id, String name, String mathScore,String kokugoScore) {
			this.id = new SimpleStringProperty(id);
			this.name = new SimpleStringProperty(name);
			this.mathScore = new SimpleStringProperty(mathScore);
			this.kokugoScore= new SimpleStringProperty(kokugoScore);
		}

		public String getId() {
			return id.get();
		}
		public void setId(String string) {
			id.set(string);
		}

		public String getName() {
			return name.get();
		}
		public void setName(String string) {
			name.set(string);
		}

		public String getMathScore() {
			return mathScore.get();
		}
		public void setMathScore(String string){
			mathScore.set(string);
		}

		public String getKokugoScore() {
			return kokugoScore.get();
		}
		public void setKokugoScore(String string) {
			kokugoScore.set(string);
		}

	}

	/**
	 * インポートボタンがクリックされたときに実行される。
	 * whereとOrderByのTextFieldから文字列を取得して、SQL文を構築する。
	 * @param event
	 */
	@FXML
	public void onInportButtonClicked(ActionEvent event) {
		try {
			Student student = new Student();
			Data data = new Data();
			Sys.println("clicked");
			str = this.textField.getText();
			data.selectedWhere = comboBox2SqlQuerry(comboBox.getValue());
			data.selectedOrderBy = comboBox2SqlQuerry(sortComboBox.getValue());
			sqlStr=App.Sql.querrySELECT(str,student,data);
			getFromDBtoList(data);
			updateQuerryLabel(sqlStr);
		} catch (NullPointerException e) {
			e.printStackTrace();
			Sys.print("空ではいけません。");
		}

		//updateStudentLabels(data,sqlStr);
		//this.idLabel.setText("clicked:"+count+" "+str);
	}

	public void onClickedWindow2ButtonClicked(ActionEvent event) {

		Stage stage = new Stage();
		Parent root = null;
		try {
			root = FXMLLoader.load(Controller.class.getResource("window2.fxml"));
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		stage.setScene(new Scene(root));
		stage.setTitle("My modal window");
		stage.initModality(Modality.WINDOW_MODAL);
		stage.initOwner(((Node)event.getSource()).getScene().getWindow() );
		stage.show();

	}
	public void addToObservableList() {
		//final ObservableList<Student> data = FXCollections.observableArrayList();

	}
	public void initTable() {
		tableView.setEditable(true);getClass();
		//table.setItems(value);

	}

	/**
	 * 追加ボタンがクリックされたとき、実行されるメソッド。
	 * @param event
	 */
	public void onAddButtonClicked(ActionEvent event) {
		//int studentID = 0,mathScore = 0, kokugoScore =0;
		Sys.println("+"+this.addMathTextField.getText()+"+");
		int studentID = Integer.parseInt(this.addIdTextField.getText());
		String studentName= this.addNameTextField.getText();
		int studentMathScore = Integer.parseInt(this.addMathTextField.getText());
		int studentKokugoScore = Integer.parseInt(this.addKokugoTextField.getText());
		sqlStr=App.Sql.querryINSERT(studentID, studentName, studentMathScore, studentKokugoScore);
		updateQuerryLabel(sqlStr);
	}

	/**
	 * 作成されるクエリーをウィンドウ内に反映する。
	 * @param string
	 */
	public void updateQuerryLabel(String string){
		Sys.println(string);
		this.querryLabel.setText(string);
	}
	/*
	public void updateStudentLabels(Data data,String sqlStr) { //ここから
		try (Connection connection = DriverManager.getConnection(App.url,App.user,App.password);
				java.sql.Statement statement =connection.createStatement();
			){
			ResultSet resultSet = statement.executeQuery(sqlStr);
			resultSet.next();
			this.idLabel.setText(resultSet.getString("id"));
			this.nameLabel.setText(resultSet.getString("name"));
			this.mathLabel.setText(resultSet.getString("mathScore"));
			this.kokugoLabel.setText(resultSet.getString("kokugoScore"));

		} catch (SQLException e) {
			e.printStackTrace();
			// TODO: handle exception
		}
	}
	*/


}
