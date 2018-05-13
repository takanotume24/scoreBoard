package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

class Student{
	int id;
	String name;
	int mathScore;
	int kokugoScore;
	double AverageScore;

	public Student(String name)  {
		this.name = name;
		// TODO 自動生成されたコンストラクター・スタブ
	}
	public Student() {
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public Student(String name,int mathScore , int kokugoScore) {
		this.name = name;
		this.mathScore=mathScore;
		this.kokugoScore = kokugoScore;
	}
//	void inputName() throws IOException {
//		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
//		Sys.println("学生の名前を入力してください：");
//		this.name = in.readLine();
//	}
//	void inputScore() throws IOException {
//		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
//		Sys.println("数学の点数：");
//		mathScore = Integer.valueOf(in.readLine());
//		Sys.println("国語の点数：");
//		kokugoScore = Integer.valueOf(in.readLine());
//	}
//	void showScore() {
//		Sys.println("数学の点数："+this.mathScore);
//		Sys.println("国語の点数："+kokugoScore);
//	}
//	void showAverageScore() {
//		AverageScore = (mathScore+kokugoScore)/2;
//		Sys.println("平均点："+AverageScore);
//	}
}

class Sys{
	static String inputString() {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String string = null;
		try {
			string = in.readLine();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return string;
	}
	static int inputNum() {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		int num = 0 ;
		try {
			num = Integer.parseInt(in.readLine());
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return num;
	}
	static void print(String string) {
		System.out.print(string);
	}
	static void println(String string) {
		System.out.println(string);
	}
	static void println(int num) {
		System.out.println(num);
	}
}
public class App{
	static boolean exit;
	static String mode;
	static ArrayList<Student> array = new ArrayList<Student>();
	Student student = new Student();
	static final String serverName = "localhost";
	static final String databaseName = "db";
	static final String user = "root";
	static final String password = "password";
	static final String serverEncoding = "sjis";
	static final String url =  "jdbc:mysql://localhost/" + databaseName;
	static final String tableName = "student";

	public static void init()  {
		try {
			//Controller controller = new Controller();
			//controller.initarize();
			Sys.println("初期化中");
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
			Sys.println("初期化失敗");
		}
	}
	/*
	public static void main(String[] args) {
		init();
	}

	public static void main(String[] args) throws NumberFormatException, IOException {

		try (

				Connection connection = DriverManager.getConnection(url,user,password);
				java.sql.Statement statement =connection.createStatement();
			){

			Sys.println("connected.");
			/*
			sqlStr = "insert into student(id,name,mathScore,kokugoScore) values(1,'takuya',123,234);";
			int result = statement.executeUpdate(sqlStr);
			sys.println("結果１："+result);
			sqlStr = "select * from student;";
			ResultSet resultSet = statement.executeQuery(sqlStr);

			sqlStr = "";
			result = statement.executeUpdate(sqlStr);
			sys.println("結果２："+result);


			while(resultSet.next()) {
				sys.println("[from db]id:"+resultSet.getString("id"));
				sys.println("[from db]name:"+resultSet.getString("name"));
				sys.println("[from db]mathScore:"+resultSet.getString("mathScore"));
				sys.println("[from db]kokugoScore:"+resultSet.getString("kokugoScore"));
			}

		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} finally {
		}

		App seisekihyou = new App();
		while(exit != true) {
			seisekihyou.selectMode();
		}

	}
	*/


	static class Sql extends App{
		static String querryINSERT(int id, String name, int mathScore, int kokugoScore) {
			Sys.println(url);
			String sqlStr = "INSERT INTO "+tableName+" (id,name,mathScore,kokugoScore) VALUES("+id+",'"+name+"',"+mathScore+","+kokugoScore+");";
			try (Connection connection = DriverManager.getConnection(url,user,password);
					java.sql.Statement statement =connection.createStatement();
				){
				Sys.println(sqlStr);
				statement.executeUpdate(sqlStr);
				Sys.println("dbに書き込まれました。");
			} catch (SQLException e) {
				e.printStackTrace();
				// TODO: handle exception
			}
			return sqlStr;
		}
		static void qureeyDELETE(Data data) {
			data.sqlStr = "DELETE FROM student WHERE id = "+data.selectedId+" name = \""+data.selectedName+"\";"
			;

		}

		static String querrySELECT(String name,Student student,Data data) {
			data.sqlStr = "SELECT * FROM student WHERE "+data.selectedWhere+" = "+"\""+name+"\""+" ORDER BY "+data.selectedOrderBy+" asc;";
			Sys.println("[Querry]"+data.sqlStr);
			try (Connection connection = DriverManager.getConnection(url,user,password);
					java.sql.Statement statement =connection.createStatement();
				){
				//ResultSet resultSet = null;
				data.resultSet = statement.executeQuery(data.sqlStr);
				Sys.println("dbから読み込みます。");
				/*
				resultSet.next();
				student.id = Integer.parseInt(resultSet.getString("id"));
				student.name = resultSet.getString("name");
				student.mathScore = Integer.parseInt(resultSet.getString("mathScore"));
				student.kokugoScore = Integer.parseInt(resultSet.getString("kokugoScore"));
				while(resultSet.next()) {
					sys.print("[from db]id:"+resultSet.getString("id"));
					sys.print(" name:"+resultSet.getString("name"));
					sys.print(" mathScore:"+resultSet.getString("mathScore"));
					sys.println(" kokugoScore:"+resultSet.getString("kokugoScore"));
				}
				*/
				return data.sqlStr;

			} catch (SQLException e) {
				e.printStackTrace();
				// TODO: handle exception
			}
			return null;

		}

	}

//	static void makeStudent() throws IOException {
//		String name;
//		int id,mathScore,kokugoScore;
//		Sys.print("idを入力してください：");	id = Sys.inputNum();
//		Sys.print("名前を入力してください：");	name = Sys.inputString();
//		Sys.print("数学の点数を入力してください：");	mathScore = Sys.inputNum();
//		Sys.print("国語の点数を入力してください：");	kokugoScore = Sys.inputNum();
//		Sql.querryINSERT(id, name, mathScore, kokugoScore);
//		//array.add(new student(name,mathScore,kokugoScore));
//	}

	/*
	static void addScore() {
		int mathScore,kokugoScore;
		String name;
		student temp = new student();
		sys.println("データを追加したい学生の名前を入力");
		name = seisekihyou.inputString();
		int whereOfArrayList = array.indexOf(name);
		temp =array.get(whereOfArrayList);
		array.set(whereOfArrayList,student(temp.name,mathScore,kokugoScore));
	}
	*/

//	static void showAllScore() {
//		//array内のすべてのデーターを表示する。
//		for (int i = 0; i < array.size(); i++) {
//
//		}
//	}
//	void selectMode() throws NumberFormatException, IOException {
//		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
//		Sys.println("select mode from,");
//		Sys.println("-makeStudent");
//		Sys.println("-showScore");
//		Sys.println("-inputScore");
//		Sys.println("-showAverageScore");
//		Sys.println("-showScoreOfOneStudent");
//		Sys.println("-exit");
//		Sys.print("mode:");
//		mode = in.readLine();
//		switch (mode) {
//			case "inputScore":
//				student.inputScore();
//				break;
//			case "showScore":
//				student.showScore();
//				break;
//
//			case "showAverageScore":
//				student.showAverageScore();
//				break;
//
//			case "makeStudent":
//				App.makeStudent();
//				break;
//
//			case "exit":
//				exit = true;
//				break;
//
//			default:
//				Sys.println("Error");
//				break;
//		}
//
//	}
}

class SQLQuerry{

}

//map