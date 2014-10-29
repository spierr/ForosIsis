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

package co.edu.uniandes.csw.G3xtreme.dia.persistence.converter;

import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;


import co.edu.uniandes.csw.G3xtreme.dia.logic.dto.DiaDTO;
import co.edu.uniandes.csw.G3xtreme.dia.persistence.entity.DiaEntity;

public abstract class _DiaConverter {

 
	private final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
	public static DiaDTO entity2PersistenceDTO(DiaEntity entity){
		if (entity != null) {
			DiaDTO dto = new DiaDTO();
					dto.setId(entity.getId());
					dto.setName(entity.getName());
 
			    if(entity.getFecha() != null){
					dto.setFecha(DATE_FORMAT.format(entity.getFecha()));
				}	
					dto.setCosto(entity.getCosto());
			return dto;
		}else{
			return null;
		}
	}
	
	public static DiaEntity persistenceDTO2Entity(DiaDTO dto){
		if(dto!=null){
			DiaEntity entity=new DiaEntity();
					entity.setId(dto.getId());
			
					entity.setName(dto.getName());
			
 
			      try{ 
			        if(dto.getFecha() != null){
						entity.setFecha(DATE_FORMAT.parse(dto.getFecha()));
					}
				  } catch (Exception ex) {
                        Logger.getLogger(_DiaConverter.class.getName()).log(Level.SEVERE, null, ex);
                  }	
			
					entity.setCosto(dto.getCosto());
			
			return entity;
		}else {
			return null;
		}
	}
	
	public static List<DiaDTO> entity2PersistenceDTOList(List<DiaEntity> entities){
		List<DiaDTO> dtos=new ArrayList<DiaDTO>();
		for(DiaEntity entity:entities){
			dtos.add(entity2PersistenceDTO(entity));
		}
		return dtos;
	}
	
	public static List<DiaEntity> persistenceDTO2EntityList(List<DiaDTO> dtos){
		List<DiaEntity> entities=new ArrayList<DiaEntity>();
		for(DiaDTO dto:dtos){
			entities.add(persistenceDTO2Entity(dto));
		}
		return entities;
	}
}