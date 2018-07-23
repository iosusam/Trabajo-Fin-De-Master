package model;
// Generated 03-jun-2018 17:29:10 by Hibernate Tools 5.2.3.Final

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Suscriptores generated by hbm2java
 */
@Entity
@Table(name = "suscriptores", catalog = "suite")
public class Suscriptores implements java.io.Serializable {

	private String idsuscriptor;
	private App app;
	private Organizacion organizacion;
	private byte activo;

	public Suscriptores() {
	}

	public Suscriptores(String idsuscriptor, App app, Organizacion organizacion, byte activo) {
		this.idsuscriptor = idsuscriptor;
		this.app = app;
		this.organizacion = organizacion;
		this.activo = activo;
	}

	@Id

	@Column(name = "IDSuscriptor", unique = true, nullable = false, length = 20)
	public String getIdsuscriptor() {
		return this.idsuscriptor;
	}

	public void setIdsuscriptor(String idsuscriptor) {
		this.idsuscriptor = idsuscriptor;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "IDApp", nullable = false)
	public App getApp() {
		return this.app;
	}

	public void setApp(App app) {
		this.app = app;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "IDOrganizacion", nullable = false)
	public Organizacion getOrganizacion() {
		return this.organizacion;
	}

	public void setOrganizacion(Organizacion organizacion) {
		this.organizacion = organizacion;
	}

	@Column(name = "Activo", nullable = false)
	public byte getActivo() {
		return this.activo;
	}

	public void setActivo(byte activo) {
		this.activo = activo;
	}

}
