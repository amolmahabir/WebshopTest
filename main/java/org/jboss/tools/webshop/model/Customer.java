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

@Entity
@Table(name="Customer", schema="webshop")
public class Customer implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	
	@Column(name = "customer_id", updatable = false, nullable = false)
	private Long customer_id;
	@Version
	@Column(name = "version")
	private int version;

	@Column
	private String firstname;

	@Column
	private String lastname;

	@Column
	private String address;

	@Column
	private String postalcode;

	@Column
	private String city;

	@Column
	private String emailaddress;

	public Long getId() {
		return this.customer_id;
	}

	public void setId(final Long id) {
		this.customer_id = id;
	}

	public int getVersion() {
		return this.version;
	}

	public void setVersion(final int version) {
		this.version = version;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Customer)) {
			return false;
		}
		Customer other = (Customer) obj;
		if (customer_id != null) {
			if (!customer_id.equals(other.customer_id)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((customer_id == null) ? 0 : customer_id.hashCode());
		return result;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostalcode() {
		return postalcode;
	}

	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getEmailaddress() {
		return emailaddress;
	}

	public void setEmailaddress(String emailaddress) {
		this.emailaddress = emailaddress;
	}

	@Override
	public String toString() {
		String result = getClass().getSimpleName() + " ";
		if (customer_id != null)
			result += "customer_id: " + customer_id;
		if (firstname != null && !firstname.trim().isEmpty())
			result += ", firstname: " + firstname;
		if (lastname != null && !lastname.trim().isEmpty())
			result += ", lastname: " + lastname;
		if (address != null && !address.trim().isEmpty())
			result += ", address: " + address;
		if (postalcode != null && !postalcode.trim().isEmpty())
			result += ", postalcode: " + postalcode;
		if (city != null && !city.trim().isEmpty())
			result += ", city: " + city;
		if (emailaddress != null && !emailaddress.trim().isEmpty())
			result += ", emailaddress: " + emailaddress;
		return result;
	}
}