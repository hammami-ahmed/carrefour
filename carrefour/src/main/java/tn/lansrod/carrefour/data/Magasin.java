package tn.lansrod.carrefour.data;

public class Magasin {
	public String product;
	public Double price;
	
	@Override
	public String toString() {
		return "Magasin [product=" + product + ", price=" + price + "]";
	}
	public Magasin(String product, Double price) {
		super();
		this.product = product;
		this.price = price;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	
}
