/* ========================================================================
 * Copyright 2014 G3xtreme
 *
 * Licensed under the MIT, The MIT License (MIT)
 * Copyright (c) 2014 G3xtreme

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
 * ========================================================================


Source generated by CrudMaker version 1.0.0.201410152247

*/

package co.edu.uniandes.csw.G3xtreme.info.logic.dto;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement 
public abstract class _InfoDTO {

	

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