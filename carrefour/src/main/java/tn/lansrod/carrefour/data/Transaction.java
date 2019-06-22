package tn.lansrod.carrefour.data;

public class Transaction {
	public String magasin;
	public String produit;
	public Integer qte;
	
	public Transaction() {
		super();
	}
	public Transaction(String magasin, String produit, Integer qte) {
		super();
		this.magasin = magasin;
		this.produit = produit;
		this.qte = qte;
	}
	public Transaction(String produit, Integer qte) {
		super();
		this.produit = produit;
		this.qte = qte;
	}
	public String getMagasin() {
		return magasin;
	}
	public void setMagasin(String magasin) {
		this.magasin = magasin;
	}
	public String getProduit() {
		return produit;
	}
	public void setProduit(String produit) {
		this.produit = produit;
	}
	public Integer getQte() {
		return qte;
	}
	public void setQte(Integer qte) {
		this.qte = qte;
	}
}
