package kt5;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableModel;

import com.mysql.jdbc.PreparedStatement;

import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.ScrollPane;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Taulukko2 extends JFrame {
	private JToolBar toolBar;
	private ScrollPane scrollPane;
	static JTable table;
	static DefaultTableModel model;
//	private static int dbItems = 0;
	static Kirja[] myDB = new Kirja[800];

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		model = new DefaultTableModel();
		model.addColumn("Kirjan nimi");
		model.addColumn("Tekij‰");
		model.addColumn("Julkaisuvuosi");

		table = new JTable(model);
  	    JScrollPane scrollPane = new JScrollPane();
  	    
  	    JToolBar toolBar = new JToolBar();
  	    JButton lis‰‰ = new JButton("Lis‰‰ kirja");
  	    lis‰‰.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				uusiKirja();				
			}
		});
  	    
  	    JButton poista = new JButton("Poista kirja");
  	    poista.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				int rowIndex = table.getSelectedRow();
				poistaKirja(rowIndex);
				
			}
		});
  	    
  	    JButton p‰ivit‰ = new JButton("P‰ivit‰ taulukko");
  	    p‰ivit‰.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				p‰ivit‰();
				
			}
		});

		JFrame ikkuna = new JFrame();
		ikkuna.setTitle("Lukemani kirjat");

		ikkuna.getContentPane().add(scrollPane, BorderLayout.CENTER);
		scrollPane.setViewportView(table);
		ikkuna.getContentPane().add(toolBar, BorderLayout.SOUTH);
		toolBar.add(lis‰‰);
		toolBar.add(poista);
		toolBar.add(p‰ivit‰);

		
		ikkuna.pack();
		ikkuna.setVisible(true);
		
		try {

			// Luodaan tietokantayhteys
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/8rdc7qjrlq", "root", "");

			// Luodaan Statement-olio, joka keskustelee tietokannan kanssa
			Statement stmt = con.createStatement();

			// Luodaan tulosjoukko, johon sijoitetaan kyselyn tulos
			ResultSet rs = stmt.executeQuery("SELECT * FROM luetut");

			// Tulosjoukko k‰yd‰‰n silmukassa l‰pi, joka kierroksella taulukkoon lis‰t‰‰n dataa

			while (rs.next()) {
				System.out.println(rs.getString(1) + "  " + rs.getString(2) + "  " + rs.getInt(3));
						model.addRow(new Object[] { rs.getString(1), rs.getString(2), rs.getString(3) });
				}
				
		// Suljetaan tietokanyhteys
		con.close();

		// Varaudutaan virheisiin
		} catch (Exception e) {
			System.out.println(e);
		}

	}
	
	private static void p‰ivit‰() {
		
		model.setRowCount(0);
		
		try {

			// Luodaan tietokantayhteys
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/8rdc7qjrlq", "root", "");

			// Luodaan Statement-olio, joka keskustelee tietokannan kanssa
			Statement stmt = con.createStatement();

			// Luodaan tulosjoukko, johon sijoitetaan kyselyn tulos
			ResultSet rs = stmt.executeQuery("SELECT * FROM luetut");

			// Tulosjoukko k‰yd‰‰n silmukassa l‰pi, joka kierroksella taulukkoon lis‰t‰‰n dataa

			while (rs.next()) {
				System.out.println(rs.getString(1) + "  " + rs.getString(2) + "  " + rs.getInt(3));
						model.addRow(new Object[] { rs.getString(1), rs.getString(2), rs.getString(3) });
				}
				
		// Suljetaan tietokanyhteys
		con.close();

		// Varaudutaan virheisiin
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	private static void poistaKirja(int rowIndex) {
		
//		int columnIndex = table.getSelectedColumn();
		
		String pnimi = (String) table.getModel().getValueAt(rowIndex, 0);
		String pkirjailija = (String) table.getModel().getValueAt(rowIndex, 1);
		int pjvuosi = Integer.parseInt((String) table.getModel().getValueAt(rowIndex, 2));
		
		System.out.println(pnimi + " " + pkirjailija + " " + pjvuosi);
		
		try {

			// Luodaan tietokantayhteys
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/8rdc7qjrlq", "root", "");

			String query = "delete from luetut where nimi = ? AND kirjailija = ?";

		      // create the mysql delete preparedstatement
		      PreparedStatement preparedStmt = (PreparedStatement) con.prepareStatement(query);
		      preparedStmt.setString (1, pnimi);
		      preparedStmt.setString (2, pkirjailija);
		      //SQL k‰sky ei jostain syyst‰ pit‰nyt julkaisuvuoden k‰ytˆst‰, joten kirja poistetaan vain nimen ja tekij‰n perusteella

		      // execute the preparedstatement
		      preparedStmt.execute();
				
		// Suljetaan tietokanyhteys
		con.close();

		// Varaudutaan virheisiin
		} catch (Exception e) {
			System.out.println(e);
		}
		
		((DefaultTableModel)table.getModel()).removeRow(rowIndex);
	}
	
	private static void uusiKirja() {

		JTextField nimi = new JTextField(20);
		JTextField kirjailija = new JTextField(20);
		JTextField jvuosi = new JTextField(10);

		JPanel myPanel = new JPanel();

		myPanel.add(new JLabel("Kirjan nimi:"));
		myPanel.add(nimi);

		myPanel.add(new JLabel("Kirjailija:"));
		myPanel.add(kirjailija);

		myPanel.add(new JLabel("Julkaisu vuosi:"));
		myPanel.add(jvuosi);

		int result = JOptionPane.showConfirmDialog(null, myPanel, "Lis‰‰ uusi kirja",
				JOptionPane.OK_CANCEL_OPTION);

		if (result == JOptionPane.OK_OPTION) {

			model.addRow(new Object[] { nimi.getText(), kirjailija.getText(), jvuosi.getText() });
			
			try {
				
				String knimi = nimi.getText();
				String tekij‰ = kirjailija.getText();
				int juvuosi = Integer.parseInt(jvuosi.getText());

				// Luodaan tietokantayhteys
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/8rdc7qjrlq", "root", "");

				String query = (" insert into luetut (nimi, kirjailija, jvuosi)" + " values (?, ?, ?)");

			      // create the mysql insert preparedstatement
			      PreparedStatement preparedStmt = (PreparedStatement) con.prepareStatement(query);
			      preparedStmt.setString (1, knimi);
			      preparedStmt.setString (2, tekij‰);
			      preparedStmt.setInt    (3, juvuosi);

			      // execute the preparedstatement
			      preparedStmt.execute();
					
			// Suljetaan tietokanyhteys
			con.close();

			// Varaudutaan virheisiin
			} catch (Exception e) {
				System.out.println(e);
			}

		}
	}
}
