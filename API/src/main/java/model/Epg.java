package model;
// Generated 03-jun-2018 17:29:10 by Hibernate Tools 5.2.3.Final

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Epg generated by hbm2java
 */
@Entity
@Table(name = "epg", catalog = "suite")
public class Epg implements java.io.Serializable {

	private Integer idepg;
	private Channels channels;
	private String nombrePrograma;
	private String categoria;
	private int edadMinima;
	private Date fechaInicio;

	public Epg() {
	}

	public Epg(Channels channels, String nombrePrograma, String categoria, int edadMinima, Date fechaInicio) {
		this.channels = channels;
		this.nombrePrograma = nombrePrograma;
		this.categoria = categoria;
		this.edadMinima = edadMinima;
		this.fechaInicio = fechaInicio;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "IDEpg", unique = true, nullable = false)
	public Integer getIdepg() {
		return this.idepg;
	}

	public void setIdepg(Integer idepg) {
		this.idepg = idepg;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "IDChannels", nullable = false)
	public Channels getChannels() {
		return this.channels;
	}

	public void setChannels(Channels channels) {
		this.channels = channels;
	}

	@Column(name = "NombrePrograma", nullable = false, length = 10)
	public String getNombrePrograma() {
		return this.nombrePrograma;
	}

	public void setNombrePrograma(String nombrePrograma) {
		this.nombrePrograma = nombrePrograma;
	}

	@Column(name = "Categoria", nullable = false, length = 20)
	public String getCategoria() {
		return this.categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	@Column(name = "EdadMinima", nullable = false)
	public int getEdadMinima() {
		return this.edadMinima;
	}

	public void setEdadMinima(int edadMinima) {
		this.edadMinima = edadMinima;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FechaInicio", nullable = false, length = 19)
	public Date getFechaInicio() {
		return this.fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

}
