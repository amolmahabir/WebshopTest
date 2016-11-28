package org.jboss.tools.webshop.model;

import javax.persistence.Entity;
import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
@Table(name="CartItem", schema="webshop")
public class CartItem implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "cartitem_id", updatable = false, nullable = false)
	private Long cartitem_id;
	@Version
	@Column(name = "version")
	private int version;

	@Column(nullable = false)
	@NotNull
	private String name;

	@Column
	private String description;

	@Column
	private String picture;
	
	@Column
	private String category;
	
	@Column
	private double price;
	
	@Column
	private int amount;

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Long getId() {
		return this.cartitem_id;
	}

	public void setId(final Long id) {
		this.cartitem_id = id;
	}

	public int getVersion() {
		return this.version;
	}

	public void setVersion(final int version) {
		this.version = version;
	}
	
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof CartItem)) {
			return false;
		}
		CartItem other = (CartItem) obj;
		if (cartitem_id != null) {
			if (!cartitem_id.equals(other.cartitem_id)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cartitem_id == null) ? 0 : cartitem_id.hashCode());
		return result;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}
	
	public void amountPlusOne(){
		this.amount++;
	}
		

	@Override
	public String toString() {
		String result = getClass().getSimpleName() + " ";
		if (name != null && !name.trim().isEmpty())
			result += "name: " + name;
		if (description != null && !description.trim().isEmpty())
			result += ", description: " + description;
		if (picture != null && !picture.trim().isEmpty())
			result += ", picture: " + picture;
		return result;
	}
}