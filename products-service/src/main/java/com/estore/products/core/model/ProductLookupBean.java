package com.estore.products.core.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product_lookup")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductLookupBean implements Serializable{

	private static final long serialVersionUID = -5294732147229975080L;
	
	@Id
	@Column(name = "id", unique = true)
	private String productId;
	
	@Column(unique = true)
	private String title;

}
