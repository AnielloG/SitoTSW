package Model;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Prodotto {
	int id;
	int Quantita;
	String Nome;
	String Descrizione;
	double Prezzo;
	String Sesso;
	String categoria;
	private Double iva;
	
	byte [] img;
	ArrayList<immagine> allimg;
	
	
	public Prodotto() {
		super();
	}
	public Prodotto(int id, String nome, double prezzo) {
		super();
		this.id = id;
		Nome = nome;
		Prezzo = prezzo;
	}
	public int getID() {
		return id;
	}
	public void setID(int id) {
		this.id = id;
	}
	public int getQuantita() {
		return Quantita;
	}
	public void setQuantita(int quantita) {
		Quantita= quantita;
	}
	public String getDescrizione() {
		return Descrizione;
	}
	public void setDescrizione(String descrizione) {
		Descrizione = descrizione;
	}
	public String getNome() {
		return Nome;
	}
	public void setNome(String nome) {
		Nome = nome;
	}
	public double getPrezzo() {
		
		if (iva == null) {
	          // Imposta l'IVA predefinita al 22% se non specificata
	    }
		double importoIva = (Prezzo) * (iva / 100);
		double totale = Prezzo + importoIva;
		
		//sostituisco la virgola con un punto nella stringa e arrotondo a 2 numeri dopo la virgola
		DecimalFormat df = new DecimalFormat("#0.00");
		String totaleFormatted = df.format(totale);
		totaleFormatted = totaleFormatted.replace(",", ".");
		
		return Double.parseDouble(totaleFormatted);
		
	}
	public void setPrezzo(double prezzo) {
		Prezzo = prezzo;
	}
	public byte[] getImg() {
		return img;
	}
	public void setImg(byte[] img) {
		this.img = img;
	}
	public ArrayList<immagine> getAllimg() {
		return allimg;
	}
	public void setAllimg(ArrayList<immagine> allimg) {
		this.allimg = allimg;
	}
	public String getSesso() {
		return Sesso;
	}
	public void setSesso(String sesso) {
		Sesso = sesso;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	
	public Double getIva() {
        return iva;
    }

    public void setIva(Double iva) {
        this.iva = iva;
    }

}