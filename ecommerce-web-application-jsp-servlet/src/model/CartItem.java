package com.model;

 public class CartItem {
	    private int cartId;
	    private int productId;
	    private int userId;
 	    private double price;
	    private String image;
	    private int quantity;
	    private String productName;
	    
	    public String getProductName() {
			return productName;
		}

		public void setProductName(String productName) {
			this.productName = productName;
		}

		public CartItem() {
			 
	    }

	    public int getUserId() {
			return userId;
		}

		public void setUserId(int userId) {
			this.userId = userId;
		}

		public CartItem(int cartId, int productId, int userId, String name, double price, String image, int quantity,String productName) {
			super();
			this.cartId = cartId;
			this.productId = productId;
			this.userId = userId;
 			this.price = price;
			this.image = image;
			this.quantity = quantity;
			this.productName = productName;
		}

		public int getCartId() {
			return cartId;
		}

		public void setCartId(int cartId) {
			this.cartId = cartId;
		}

		public int getProductId() {
			return productId;
		}

		public void setProductId(int productId) {
			this.productId = productId;
		}

 
		public double getPrice() {
			return price;
		}

		public void setPrice(double price) {
			this.price = price;
		}

		public String getImage() {
			return image;
		}

		public void setImage(String image) {
			this.image = image;
		}

		public int getQuantity() {
			return quantity;
		}

		public void setQuantity(int quantity) {
			this.quantity = quantity;
		}

		@Override
		public String toString() {
			return "CartItem [cartId=" + cartId + ", productId=" + productId + ", name=" + productName + ", price=" + price
					+ ", image=" + image + ", quantity=" + quantity + "]";
		}
	    
	    
	    
}



