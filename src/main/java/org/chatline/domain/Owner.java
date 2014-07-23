package org.chatline.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "OWNERS")
public class Owner {
	@Id
	private String name;
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<Owner> followers;
	
	public Owner() {
		super();
	}
	
	public Owner(String name) {
		super();
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public Set<Owner> getFollowers() {
		return followers;
	}
	public void setFollowers(Set<Owner> followers) {
		this.followers = followers;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Owner)) {
			return false;
		}
		Owner other = (Owner) obj;
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}
	@Override
	public String toString() {
		return "Owner [name=" + name + ", followers=" + followers + "]";
	}
}
