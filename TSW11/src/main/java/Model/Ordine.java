package Model;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Ordine {
    private int numeroOrdine;
    private String indirizzo;
    private String cap;
    private String provincia ;
    private String citta;
    private Date dataOrdine;
    private double totale;
    private String stato;
    private String email;
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public int getNumeroOrdine() {
        return numeroOrdine;
    }
    
    public void setNumeroOrdine(int numeroOrdine) {
        this.numeroOrdine = numeroOrdine;
    }
    
    
    
    public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getCap() {
		return cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getCitta() {
		return citta;
	}

	public void setCitta(String citta) {
		this.citta = citta;
	}

	public Date getData() {
        return dataOrdine;
    }
    
    public void setData(Date data) {
        this.dataOrdine = data;
    }
    
    public double getTotale() {
        return totale;
    }
    
    public void setTotale(double totale) {
        this.totale = totale;
    }
    
    public String getStato() {
        return stato;
    }
    
    public void setStato(String stato) {
        this.stato = stato;
    }
    
    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dataFormattata = dateFormat.format(dataOrdine);
        return "Numero ordine: " + numeroOrdine +
               ", Data: " + dataFormattata +
               ", Totale: " + totale +
               ", Stato: " + stato;
    }

}
