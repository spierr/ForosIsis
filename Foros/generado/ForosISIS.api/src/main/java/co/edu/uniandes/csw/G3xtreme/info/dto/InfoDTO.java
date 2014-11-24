/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package co.edu.uniandes.csw.G3xtreme.info.dto;

/**
 *
 * @author estudiante
 */
public class InfoDTO {
    

	private Long id;
	

	private String error;
	

	private String exito;
	

	private Boolean fallo;
	

	private Integer numInt;
	

	private Double numDouble;
	
        
        private String listaComas;
        

        public Long getId() {
		return id;
	}
 
	public void setId(Long id) {
		this.id = id;
	}
        
        public String getError() {
		return error;
	}
 
	public void setError(String error) {
		this.error = error;
	}
        
         public String getExito() {
		return exito;
	}
 
	public void setExito(String exito) {
		this.exito = exito;
	}
        
         public Boolean getFallo() {
		return fallo;
	}
 
	public void setError(Boolean fallo) {
		this.fallo = fallo;
	}
        
         public Integer getNumInt() {
		return numInt;
	}
 
	public void setNumInt(Integer numInt) {
		this.numInt = numInt;
	}
        
         public Double getNumDouble() {
		return numDouble;
	}
 
	public void setNumDouble(Double numDouble) {
		this.numDouble = numDouble;
	}
        
        public String getListaComas() {
		return listaComas;
	}
 
	public void setListaComas(String listaComas) {
		this.listaComas = listaComas;
	}
    
}
