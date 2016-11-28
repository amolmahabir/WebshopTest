package org.jboss.tools.webshop.model;

import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Version;

@Entity
@Table(name="OrderCartItem", schema="webshop")
public class OrderCartItem implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, nullable = false)
	private Long ordercartitem_id;
	@Version
	@Column(name = "version")
	private int version;

	@Column(name = "purchase_id")
	private Long purchase_id;

	@Column(name = "cartitem_id")
	private Long cartitem_id;

	public Long getId() {
		return this.ordercartitem_id;
	}

	public void setId(final Long id) {
		this.ordercartitem_id = id;
	}

	public int getVersion() {
		return this.version;
	}

	public void setVersion(final int version) {
		this.version = version;
	}
	
	//@ManyToOne
	//@JoinColumn(name="cartitem_id")
	@OneToMany
	private Collection<CartItem> items;
	
	//@ManyToOne
	//@JoinColumn(name="purchase_id")
	@OneToMany
	private Collection<Purchase> orders;

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof OrderCartItem)) {
			return false;
		}
		OrderCartItem other = (OrderCartItem) obj;
		if (ordercartitem_id != null) {
			if (!ordercartitem_id.equals(other.ordercartitem_id)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((ordercartitem_id == null) ? 0 : ordercartitem_id.hashCode());
		return result;
	}

	public Long getOrder_id() {
		return purchase_id;
	}

	public void setOrder_id(Long order_id) {
		this.purchase_id = order_id;
	}

	public Long getCartitem_id() {
		return cartitem_id;
	}

	public void setCartitem_id(Long cartitem_id) {
		this.cartitem_id = cartitem_id;
	}

	@Override
	public String toString() {
		String result = getClass().getSimpleName() + " ";
		if (ordercartitem_id != null)
			result += "ordercartitem_id: " + ordercartitem_id;
		if (purchase_id != null)
			result += ", order_id: " + purchase_id;
		if (cartitem_id != null)
			result += ", cartitem_id: " + cartitem_id;
		return result;
	}
}