	<%@ page language="java" contentType="text/html; charset=UTF-8" %>
	<%@ page import="java.util.List" %>
	<%@ page import="com.model.Product" %>
	<%@ page import="com.model.User" %>
	
	<%
	User loggedUser = (User) session.getAttribute("user");
	%>
	<!DOCTYPE html>
	<html>
	<head>
	    <title>Products</title>
	    <link rel="stylesheet" href="css/style.css">
	</head>
	<body>
	<%
	String added = request.getParameter("added");
	if ("true".equals(added)) {
	%>
	<div id="cartPopup" class="popup-overlay">
	    <div class="popup">
	        <h3>✅ Added to Cart</h3>
	        <p>Product added successfully</p>
	    </div>
	</div>
	<%
	}
	%>
	
	<div class="header">
	    <div class="logo">
	        <h2>My Store</h2>
	    </div>
	
	<form action="products" method="get" class="nav-form">
	
	    <select name="category">
	        <option value="">All</option>
	        <option value="Mobile"
	            <%= "Mobile".equals(request.getParameter("category")) ? "selected" : "" %>>
	            Mobile
	        </option>
	        <option value="Laptop"
	            <%= "Laptop".equals(request.getParameter("category")) ? "selected" : "" %>>
	            Laptop
	        </option>
	<option value="Mobile Accessories"
	    <%= "Mobile Accessories".equals(request.getParameter("category")) ? "selected" : "" %>>
	    Accessories
	</option>
	    </select>
	
	    <input type="text" name="search"
	           placeholder="Search product..."
	           value="<%= request.getParameter("search") != null ? request.getParameter("search") : "" %>">
	               <button type="submit">Search</button>
	           
  <span class="sort-label">Sort by Price:</span>

    <button type="submit"
            name="sort"
            value="low"
            class="sort-btn">
        ₹ Low → High
    </button>

    <button type="submit"
            name="sort"
            value="high"
            class="sort-btn">
        ₹ High → Low
    </button>

	
	</form>
	
	
	<div class="nav-links">
	
	    <a href="cart">View Cart</a>
	
	    <!-- USER PROFILE DROPDOWN -->
	<div class="profile-menu">
	    <button class="profile-btn">
	        👤 <%= loggedUser.getName() %>
	    </button>
	
	    <div class="profile-dropdown">
	        <a href="profile">My Profile</a>
	        <a href="myOrders">My Orders</a>
	        <a href="logout" class="logout">Logout</a>
	    </div>
	</div>
	
	</div>
	</div>
	
	
	<div class="container">
	    <div class="products">
	
	<%
	List<Product> products = (List<Product>) request.getAttribute("products");
	
	if (products != null && !products.isEmpty()) {
	    for (Product p : products) {
	%>
	
	    <div class="product-card">
	        <img src="<%=request.getContextPath()%>/images/<%=p.getImage()%>" alt="product">
	
	        <h4><%= p.getName() %></h4>
	        <p class="price">₹ <%= p.getPrice() %></p>
	        <p class="desc"><%= p.getDescription() %></p>
	
	        <div class="product-actions">
	            <a class="btn cart-btn"
	               href="addtocart?pid=<%= p.getId() %>">
	                Add to Cart
	            </a>
	
	            <a class="btn buy-btn"
	               href="buynow?pid=<%= p.getId() %>&qty=1">
	                Buy Now
	            </a>
	        </div>
	    </div>
	
	<%
	    }
	} else {
	%>
	
	    <!-- 🔴 NO PRODUCTS FOUND -->
	    <div class="no-products">
	        <h3>❌ No products found</h3>
	        <p>
	            Try changing category, price range<br>
	            or search keywords.
	        </p>
	
	        <a href="products" class="clear-filter-btn">
	            Clear Filters
	        </a>
	    </div>
	
	<%
	}
	%>
	
	    </div>
	</div>
	
	<div class="about-us">
	    <h2>About Us</h2>
	
	    <p class="about-intro">
	        Welcome to <strong>Online Shopping Cart</strong> — your trusted destination
	        for quality products at affordable prices.
	    </p>
	
	    <div class="about-content">
	        <p>
	            We started our journey with a simple goal: to make online shopping easy,
	            reliable, and accessible for everyone. From electronics and accessories
	            to everyday essentials, we carefully select products that meet our
	            quality standards.
	        </p>
	 
	
	        <p>
	            With a growing customer base and continuous improvements, we aim to
	            provide a smooth and enjoyable shopping experience every time you visit.
	        </p>
	    </div>
	
	    <div class="about-highlights">
	        <div class="highlight">
	            <h4>✔ Quality Products</h4>
	            <p>Carefully curated items from trusted suppliers.</p>
	        </div>
	
	        <div class="highlight">
	            <h4>🔒 Secure Payments</h4>
	            <p>Safe and reliable payment processing.</p>
	        </div>
	
	        <div class="highlight">
	            <h4>🚚 Fast Delivery</h4>
	            <p>Quick and reliable shipping across regions.</p>
	        </div>
	
	        <div class="highlight">
	            <h4>📞 Customer Support</h4>
	            <p>Dedicated support to assist you anytime.</p>
	        </div>
	    </div>
	</div>
	
	<div class="footer">
	    © 2025 Online Shopping Cart
	</div>
	
	</body>
	<script>
	window.onload = function () {
	    const popup = document.getElementById("cartPopup");
	    if (popup) {
	        // Auto hide after 2.5 seconds
	        setTimeout(() => {
	            popup.style.opacity = "0";
	            setTimeout(() => popup.style.display = "none", 300);
	        }, 2500);
	    }
	};
	</script>
	
	
	</html>
