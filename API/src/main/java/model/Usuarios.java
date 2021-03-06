package model;
// Generated 03-jun-2018 17:29:10 by Hibernate Tools 5.2.3.Final

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 * Usuarios generated by hbm2java
 */
@Entity
@Table(name = "usuarios", catalog = "suite", uniqueConstraints = @UniqueConstraint(columnNames = "Email"))
public class Usuarios implements java.io.Serializable {

	private Integer idUsuario;
	private String nombre;
	private String email;
	private String password;
	private Date fechaCreacion;
	private Date fechaUltimoAcceso;
	private byte activo;
	private int rol;
	private Set<Organizacion> organizacions = new HashSet<Organizacion>(0);

	public Usuarios() {
	}

	public Usuarios(String nombre, String email, String password, Date fechaCreacion, Date fechaUltimoAcceso,
			byte activo, int rol) {
		this.nombre = nombre;
		this.email = email;
		this.password = password;
		this.fechaCreacion = fechaCreacion;
		this.fechaUltimoAcceso = fechaUltimoAcceso;
		this.activo = activo;
		this.rol = rol;
	}

	public Usuarios(String nombre, String email, String password, Date fechaCreacion, Date fechaUltimoAcceso,
			byte activo, int rol, Set<Organizacion> organizacions) {
		this.nombre = nombre;
		this.email = email;
		this.password = password;
		this.fechaCreacion = fechaCreacion;
		this.fechaUltimoAcceso = fechaUltimoAcceso;
		this.activo = activo;
		this.rol = rol;
		this.organizacions = organizacions;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "IdUsuario", unique = true, nullable = false)
	public Integer getIdUsuario() {
		return this.idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	@Column(name = "Nombre", nullable = false, length = 20)
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Column(name = "Email", unique = true, nullable = false, length = 100)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "Password", nullable = false, length = 100)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "FechaCreacion", nullable = false, length = 10)
	public Date getFechaCreacion() {
		return this.fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "FechaUltimoAcceso", nullable = false, length = 10)
	public Date getFechaUltimoAcceso() {
		return this.fechaUltimoAcceso;
	}

	public void setFechaUltimoAcceso(Date fechaUltimoAcceso) {
		this.fechaUltimoAcceso = fechaUltimoAcceso;
	}

	@Column(name = "Activo", nullable = false)
	public byte getActivo() {
		return this.activo;
	}

	public void setActivo(byte activo) {
		this.activo = activo;
	}

	@Column(name = "Rol", nullable = false)
	public int getRol() {
		return this.rol;
	}

	public void setRol(int rol) {
		this.rol = rol;
	}

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "usuarioses")
	public Set<Organizacion> getOrganizacions() {
		return this.organizacions;
	}

	public void setOrganizacions(Set<Organizacion> organizacions) {
		this.organizacions = organizacions;
	}

}
