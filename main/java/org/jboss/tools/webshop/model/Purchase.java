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
@Table(name="Purchase", schema="webshop")
public class Purchase implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	
	@Column(name = "purchase_id", updatable = false, nullable = false)
	private Long purchase_id;
	@Version
	@Column(name = "version")
	private int version;
	
	@Column(name = "customer_id")
	private Long customer_id;

	public Long getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(Long customer_id) {
		this.customer_id = customer_id;
	}
	
	//@ManyToOne
	//@JoinColumn(name="customer_id")
	@OneToMany
	private Collection<Customer> customers;

	public Long getId() {
		return this.purchase_id;
	}

	public void setId(final Long id) {
		this.purchase_id = id;
	}

	public int getVersion() {
		return this.version;
	}

	public void setVersion(final int version) {
		this.version = version;
	}

	@Override
	public String toString() {
		String result = getClass().getSimpleName() + " ";
		if (purchase_id != null)
			result += "id: " + purchase_id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Purchase)) {
			return false;
		}
		Purchase other = (Purchase) obj;
		if (purchase_id != null) {
			if (!purchase_id.equals(other.purchase_id)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((purchase_id == null) ? 0 : purchase_id.hashCode());
		return result;
	}
}